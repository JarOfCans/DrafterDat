package cardHelper.player;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cardHelper.MainClass;
import cardHelper.Settings;
import cardHelper.card.Card;
import cardHelper.pack.Pack;
import cardHelper.table.ClassicDraftTable;

public class Player {
	
	public ArrayList<Card> cardsPicked =new ArrayList<Card>(60);
	ArrayList<Card> mainDeck =new ArrayList<Card>(60);
	ArrayList<Card> sideboard =new ArrayList<Card>(60);
	ArrayList<Card> lands =new ArrayList<Card>(60);
	ArrayList<Card> avalibleCards =new ArrayList<Card>(60);
	
	ArrayList<Integer> bufferScores = new ArrayList<Integer>(15);
	ArrayList<Pack> bufferPack = new ArrayList<Pack>(15);
	
	
	int [] basics = {0,0,0,0,0};
	public int id;
	public ClassicDraftTable table;
	int [] stacks = new int[0];
	int [] groups = new int[0];
	Card blankCard = new Card("","","",new String[0],new String[0],true);
	public Pack [] packs = new Pack[3];
	public String [] packnames = new String[3];
	/*Pack blankPack = new Pack(true);
	Pack heldpack;
	Pack currentpack;*/
	public boolean isPlayer;
	public int Red =0, Green=0, Blue=0, Black=0, White=0, CTotal=0,TTotal=0,Pick=0,Multi=0;
	public double [] valueC = {0,0,0,0,0,0};
	public boolean [] theColors,addSplash;
	int q1=0,q2=0,q3=0,q4=0,qt=0;
	int Land=0;
	public int [] creatureCMC = new int[6];
	public int [] CMC = new int[6];
	boolean rotateOpposite = false;
	public int creaturePick=0;

	public Player(int id, ClassicDraftTable table, boolean controlledByPlayer){
		isPlayer = controlledByPlayer;
		this.id = id;
		this.table = table;
		for (int i = 0; i < 15; i++)
		{
			bufferScores.add(0);
		}
	}
	
	public void pickCard(int num){
		if (!isPlayer){
			num = chooseCard();
		}
		cardsPicked.add(table.getMypack(id).getCard(num));
		Card theCard = cardsPicked.get(cardsPicked.size()-1);
		if (!theCard.testType("Land")){
			incCMC(theCard.getCMC(),theCard.testType("Creature"));
		}
		if (theCard.testType("Creature")) {
			creaturePick++;
		}
		else {Land++;}
		for (int m = 0;m < stacks.length;m++){
			stacks[m] += theCard.stackdata[m];
		}
		for (int m = 0;m < groups.length;m++){
			groups[m] += theCard.groupdata[m];
		}
		int value = theCard.getqt();
		int minValue = Integer.parseInt(Settings.settingValue("0PointColor", "15"));
		int intervalValue = Integer.parseInt(Settings.settingValue("PointInterval", "2"));
		if (value>=minValue) {
			if (theCard.getWhite()>0) {
				White += ((theCard.getqt()-minValue+intervalValue)/intervalValue);
				CTotal += ((theCard.getqt()-minValue+intervalValue)/intervalValue);
			}
			if (theCard.getBlue()>0) {
				Blue += ((value-minValue+intervalValue)/intervalValue);
				CTotal += ((value-minValue+intervalValue)/intervalValue);
			}
			if (theCard.getBlack()>0) {
				Black += ((value-minValue+intervalValue)/intervalValue);
				CTotal += ((value-minValue+intervalValue)/intervalValue);
			}
			if (theCard.getRed()>0) {
				Red += ((value-minValue+intervalValue)/intervalValue);
				CTotal += ((value-minValue+intervalValue)/intervalValue);
			}
			if (theCard.getGreen()>0) {
				Green += ((value-minValue+intervalValue)/intervalValue);
				CTotal += ((value-minValue+intervalValue)/intervalValue);
			}
		}
		Pick++;
		/*if (isPlayer)
		System.out.println(theCard.getColors());*/
		myPack().pickedCard(num);
		table.pickcheck();
	}
	
	public boolean checkRotate(){return rotateOpposite;}
	/*public Pack sentPack(){
		Pack mail = currentpack;
		currentpack= blankPack;
		return mail;
	}*/
	
	/**
	 * Calculates the value of the card for the player.
	 * @param i Index of the card.
	 * @param pack True if the card is in the current pack, false otherwise, in which it will use the card in the mainDeck.
	 * @return 0 if the card is blank, the Value of the card if it isn't.
	 */
	public int getScore(int i, boolean pack){
			try {
				//Gets the card.
				Card theCard;
				if (pack){
					Pack thisPack = table.getMypack(id);
					try
					{
						if (thisPack == bufferPack.get(i))
						{
							//System.out.println(String.format("%d", bufferScores.get(i)));
							return bufferScores.get(i);
						}
						else {
							theCard = thisPack.getCard(i);
							bufferPack.set(i, thisPack);
						}
					}
					catch(java.lang.IndexOutOfBoundsException ioobe)
					{
						theCard = thisPack.getCard(i);
						bufferPack.add(thisPack);
					}
				}
				else {
					theCard = mainDeck.get(i);
				}
			
				//If the card is blank, return 0.
				if (theCard.testBlank()){
					return 0;
				}
				
				//Sets initial value based on a cards qt
				int value = (int)theCard.getqt()*10;
				int opcheck = theCard.getqt();
				
				//Grants a bonus if the card is over a specific qt.
				/*int opbonus=0;
				for (;opcheck>19;opcheck--) {
					opbonus+=checkMax(opbonus+5,10);
				}
				value += opbonus;*/
			
				//Grants a bonus based on stacking and grouping of the card and your cards
			
			
				/*
				//Depricated
				for (int m = 0; m < stacks.length;m++){
					value += theCard.stackdata[m] * stacks[m]/3;
				}
				for (int m = 0; m < groups.length;m++){
					int r = m + ((m+1)%2-1);
					value += theCard.groupdata[r] * groups[m]/3;
				}
			 */
			
			
				//Boosts score if the card is a creature.
				if (theCard.testType("Creature")){
					if (pack){
						value += 5;
					}
					else {
						value += 15;
					}
				}
			
				//Changes the value of the card based on the cmc of the card and your cards.
				if (!theCard.testType("Land")){
					value *= CMCbonus(theCard.getCMC(),theCard.testType("Creature"));
				}
				
				//Changes the value of the card based on the colors of the card.
				value *= colorChange(i,pack);
				
				//Sets the buffer score if it is a pack
				if (pack)
				{
					try
					{
						bufferScores.set(i, value);
					}
					catch (java.lang.IndexOutOfBoundsException ioobe)
					{
						bufferScores.add(value);
					}
				}
				//return the value of the card.
				return value;
			}
		
			catch (NullPointerException e){
				throw new IllegalStateException("", e);
			}
		}
	public int chooseCard(){
		int [] best = {0,0,0};
		int count = 0;
		int i = 0;
		for (Card theCard:myPack().getCards()){
			int value = getScore(i++,true);
			if (value > best[1]){
				best[0] = count;
				best[1] = value;
				best[2] = theCard.getq4();
			}
			else if (value == best[1]){
				if (theCard.getq4()>=best[2]){
					best[0] = count;
					best[1] = value;
					best[2] = theCard.getq4();
				}
			}
			else{}
			count++;
		}
		return best[0];
	}
	
	public double colorChange(int i, boolean pack){
		//Gets the card.
		Card aCard;
		if (pack){
			aCard = table.getMypack(id).getCard(i);
		}
		else {
			aCard = mainDeck.get(i);
		}
		
		double bonus = 1.0;
		if (aCard.testBlank()){
			bonus = 0;
		}
		double holdbonus = 0;
		
		//Gets the colors of the card.
		int R = aCard.getRed();
		int U = aCard.getBlue();
		int G = aCard.getGreen();
		int B = aCard.getBlack();
		int W = aCard.getWhite();
		if (!aCard.isHybrid()||(getRedWorth(aCard,pack)<=0&&getWhiteWorth(aCard,pack)<=0&&getBlueWorth(aCard,pack)<=0&&getBlackWorth(aCard,pack)<=0&&getGreenWorth(aCard,pack)<=0)) {
			if (R>0||U>0||G>0||B>0||W>0){
				if ((R)>0){
					holdbonus += getRedWorth(aCard,pack);
				}
				if ((U)>0){
					holdbonus += getBlueWorth(aCard,pack);
				}
				if ((G)>0){
					holdbonus += getGreenWorth(aCard,pack);
				}
				if ((B)>0){
					holdbonus += getBlackWorth(aCard,pack);
				}
				if ((W)>0){
					holdbonus += getWhiteWorth(aCard,pack);
				}
			}
		}
		else {
			if(getRedWorth(aCard,pack)>0) {
				holdbonus += getRedWorth(aCard,pack);
			}
			if (getBlueWorth(aCard,pack)>0){
				holdbonus += getBlueWorth(aCard,pack);
			}
			if (getGreenWorth(aCard,pack)>0){
				holdbonus += getGreenWorth(aCard,pack);
			}
			if (getBlackWorth(aCard,pack)>0){
				holdbonus += getBlackWorth(aCard,pack);
			}
			if (getWhiteWorth(aCard,pack)>0){
				holdbonus += getWhiteWorth(aCard,pack);
			}
		}
		bonus *= 1 +holdbonus;
		if (aCard.getCMC()<((W+U+B+R+G)/2)&&cardsPicked.size()<5&&aCard.getqt()<20) {
			if (W+U+B+R+G>1) {
			bonus = ((double)(bonus)/1.1);
			}
		}
		return bonus;
	}
	public double CMCbonus(int num, boolean isCreature){
		if (num >= 6){
			num = 5;
		}
		else if (num <= 1) {
			num = 0;
		}
		else {
			num--;
		}
		if (Pick-Land>0){
		if (!isCreature) {
			int percent = CMC[num]*100/(Pick-Land);
		if (percent<10&&num<=4&&Pick>14||percent<5&&num>4&&Pick>14){
			return 1.05;
		}
		else if (percent>40&&num<=4&&Pick>14||percent>20&&num>4&&Pick>14){
			return 0.93;
		}
		else if (percent>30&&num<=4&&Pick>14||percent>20&&num>4&&Pick>14){
			return 0.95;
		}
		}
		else {
			if (Pick>20) {
			double bonusValue=3;
			if (num<1) {
				bonusValue=1;
			}
			if (num>=5) {
				bonusValue=2;
			}
			if (num==1) {
				bonusValue=4;
			}
			if (creatureCMC[num]<bonusValue) {
				return 1.08;
			}
			if (creatureCMC[num]-2>bonusValue) {
				return 0.92;
			}
			}
		}
		}
		return 1;
	}
	public boolean getPlayer(){
		return isPlayer;
	}
	public int getPick(){
		return Pick;
	}
	public double getColorWorth(double yourColor, Card card, double cardColor, boolean midPick) {
		double total = CTotal;
		double obonus=0;
		if (cardColor>0) {
			if (Pick>12) {
				if (yourColor>total/3){
					obonus += 0.13;
				}
				else if (yourColor>total/4) {
					obonus += 0.08;
				}
				else if (yourColor<4) {
					obonus -= 0.12;
				}
				if (Pick>20&&midPick) {
					if (yourColor<4) {
						obonus-=0.04;
					}
					if (yourColor<6) {
						obonus-=0.04;
					}
				}
			}
			else {
				if (yourColor>=4) {
					obonus += 0.03;
				}
				if (yourColor>Pick/3) {
					obonus += 0.03;
				}
				if (yourColor>1&&card.getqt()<16) {
					obonus -= 0.03;
				}
				if (total>=7&&yourColor<2) {
					obonus-=0.05;
				}
			}
		}
		return obonus;
	}
	public double getRedWorth(Card R, boolean midPick){
			return getColorWorth(Red,R,R.getRed(),midPick);
	}
	public double getBlueWorth(Card U, boolean midPick){
			return getColorWorth(Blue,U,U.getBlue(),midPick);
	}
	public double getGreenWorth(Card G, boolean midPick){
			return getColorWorth(Green,G,G.getGreen(),midPick);
	}
	public double getBlackWorth(Card B, boolean midPick){
		return getColorWorth(Black,B,B.getBlack(),midPick);
	}
	public double getWhiteWorth(Card W, boolean midPick){
		return getColorWorth(White,W,W.getWhite(),midPick);
	}
	public int getCMC(int num){return CMC[num-1];}
	public void incCMC(int num,boolean isCreature){
		if (num<=1){
			CMC[0]++;
			if (isCreature) {
				creatureCMC[0]++;
			}
		}
		else if(num>=6){
				CMC[5]++;
				if (isCreature) {
					creatureCMC[5]++;
				}
			}
		else {
			CMC[num-1]++;
			if (isCreature) {
				creatureCMC[num-1]++;
			}
		}
	}
	/**
	 * Gets the CMC group with the most cards in it.
	 * @return The CMC with the most cards.
	 */
	public int greatestCMC(){
		int doom = 0;
		for (int i = 1;i<=6;i++){
			if (getCMC(i)>doom){
				doom = getCMC(i);
			}
		}
		return doom;
	}
	public ArrayList<Card> getCards(){return cardsPicked;}
	//public Pack takePack(){return currentpack;}
	public String viewPick(int num){return cardsPicked.get(num).getName();}
	//public void pickCard(String card,String set){cardsPicked[CTotal]=card;if(isPlayer){addVarInt("Packs/"+set+"/Storage.txt",card,1);}}
	/*public Pack getPack(){
		return currentpack;
	}*/
	public void constructDeck(int num){
		//The following is to use while this method is in edit
		//Set 
		/*for (int i = 0;i < 42;i++){mainDeck[i] = cardsPicked[i];}
		for (int i = 0;i < 42;i++){sideboard[i] = blankCard;}
		for (int i = 0;i < 42;i++){avalibleCards[i] = blankCard;}
		for (int i = 0;i < 42;i++){lands[i] = blankCard;}
		//writedecklist(mainDeck,cardsPicked,num);
		int mainInc = 0;
		int sideInc=0;
		int landInc=0;
		for (int i = 0;i < 42 ;i++){mainDeck[i] = blankCard;}*/
		//set Lands
		orderQt();
		double baseline = 3;
		int baseValue = Integer.parseInt(Settings.settingValue("0PointColor", "15"));
		int boostValue = baseValue + 2 * Integer.parseInt(Settings.settingValue("PointInterval", "2"));
		while (!testIfPlayable(GoodColors(baseline), baseValue, boostValue) && baseline < 8) {
				baseline+=0.2;
		}
		System.out.print(baseline+" ");
		theColors = GoodColors(baseline);
		Card [] landCheck = new Card[5];
		landCheck[0] = new Card("Plains","0","Basic land - Plains",new String[0],new String[0],false);
		landCheck[1] = new Card("Island","0","Basic land - Island",new String[0],new String[0],false);
		landCheck[2] = new Card("Swamp","0","Basic land - Swamp",new String[0],new String[0],false);
		landCheck[3] = new Card("Mountain","0","Basic land - Mountain",new String[0],new String[0],false);
		landCheck[4] = new Card("Forest","0","Basic land - Forest",new String[0],new String[0],false);
		//If no good color, red is set to good color.
		if (!(theColors[0]||theColors[1]||theColors[2]||theColors[3]||theColors[4])){
			theColors[3]=true;
		}
		goodDrops(2,3);
		goodDrops(3,2);
		goodDrops(4,2);
		goodDrops(5,1);
		goodDrops(6,1);
		addSplash= theColors;
		int [] landNeed={0,0,0,0,0};
		while (!(cardsPicked.size()==0)) {
			Card theCard = cardsPicked.get(0);
			if (theCard.testType("Land")) {
				lands.add(theCard);
				cardsPicked.remove(theCard);
			}
			else if (!(mainDeck.size()>=23)&&testColors(theCard,theColors)) {
				mainDeck.add(theCard);
				cardsPicked.remove(theCard);
				landNeed[0]+=theCard.getWhite();
				landNeed[1]+=theCard.getBlue();
				landNeed[2]+=theCard.getBlack();
				landNeed[3]+=theCard.getRed();
				landNeed[4]+=theCard.getGreen();
			}
			else if (!(mainDeck.size()>=23)&&offColors(theCard)<2&&theCard.getqt()>=20) {
				mainDeck.add(theCard);
				cardsPicked.remove(theCard);
				if (theCard.getWhite()>0) {addSplash[0]=true;}
				if (theCard.getBlue()>0) {addSplash[1]=true;}
				if (theCard.getBlack()>0) {addSplash[2]=true;}
				if (theCard.getRed()>0) {addSplash[3]=true;}
				if (theCard.getGreen()>0) {addSplash[4]=true;}
				landNeed[0]+=theCard.getWhite();
				landNeed[1]+=theCard.getBlue();
				landNeed[2]+=theCard.getBlack();
				landNeed[3]+=theCard.getRed();
				landNeed[4]+=theCard.getGreen();
			}
			else {
				sideboard.add(theCard);
				cardsPicked.remove(theCard);
			}
		}
		int countCheck=17;
		int [] landsHad= {0,0,0,0,0};
		/*if (theColors[0]){landTotal+=White;}
		if (theColors[1]){landTotal+=Blue;}
		if (theColors[2]){landTotal+=Black;}
		if (theColors[3]){landTotal+=Red;}
		if (theColors[4]){landTotal+=Green;}*/
		ArrayList<Card> concurrentRemoval = new ArrayList<Card>();
		for (Card yep:lands) {
			if (testColors(yep,addSplash)) {
				if (yep.getWhite()>0) {landsHad[0]++;}
				if (yep.getBlue()>0) {landsHad[1]++;}
				if (yep.getBlack()>0) {landsHad[2]++;}
				if (yep.getRed()>0) {landsHad[3]++;}
				if (yep.getGreen()>0) {landsHad[4]++;}
			}
			else {
				concurrentRemoval.add(yep);
				sideboard.add(yep);
			}
		}
		for (Card yep:concurrentRemoval) {
				lands.remove(yep);
		}
		int landTotal=landsHad[0]+landsHad[1]+landsHad[2]+landsHad[3]+landsHad[4];
		for (int i = 0; i < 5; i++) {
			if (theColors[i]||(addSplash[i]&&landsHad[i]==0)) {
				landTotal++;
				landsHad[i]++;
				lands.add(landCheck[i]);
			}
		}
		do {
			int doom = 0; double check=1000;
			for (int i = 0; i < 5; i++) {
				if (addSplash[i]) {
					// W: 7/12 / 5/12 = 1.4, U: 4/12 / 7/12 = 0.571
					//System.out.println(landsHad[i]+"/"+landTotal+" / "+landNeed[i]+"/"+landTotal);
				if (((double)landsHad[i]/(double)landTotal)/((double)landNeed[i]/(double)landTotal)<check) {
					doom = i;check = ((double)landsHad[i]/(double)landTotal)/((double)landNeed[i]/(double)landTotal);
				}
				}
			}
			landTotal++;
			landsHad[doom]++;
			lands.add(landCheck[doom]);
		} while (lands.size()<17);
		//boolean [] splashColors = {(White<=0.15*CTotal&&theColors[0]),(Blue<=0.15*CTotal&&theColors[1]),(Black<=0.15*CTotal&&theColors[2]),
			//	(Red<=0.15*CTotal&&theColors[3]),(Green<=0.15*CTotal&&theColors[4])};
		
		/*for (Card seenCard:cardsPicked){
			if ((testColors(seenCard,theColors)&&(!testSplash(seenCard,splashColors)))){
				if (seenCard.testType("Land")){
					lands.add(seenCard);
				}
				else{
				mainDeck.add(seenCard);
				}
			}
			else{
				sideboard.add(seenCard);
			}
		}*/
		/*countCheck-=lands.size();
		if (theColors[0]){landNeed[0]=Math.round(White*countCheck/landTotal);}
		if (theColors[1]){landNeed[1]=Math.round(Blue*countCheck/landTotal);}
		if (theColors[2]){landNeed[2]=Math.round(Black*countCheck/landTotal);}
		if (theColors[3]){landNeed[3]=Math.round(Red*countCheck/landTotal);}
		if (theColors[4]){landNeed[4]=Math.round(Green*countCheck/landTotal);}
		int greatestColor = 0;
		for (int i = 0; i < 5;i++){
			if (landNeed[i]>landNeed[greatestColor]){
				greatestColor=i;
			}
		}
		for (int i = 0; i < 5;i++){
			if (landNeed[i]==1&&landNeed[greatestColor]>=3){
				landNeed[greatestColor]--;
				landNeed[i]++;
			}
		}
		int errorCheck=0;
		for (int i = 0;i < 17;i++){
			for (int m = 0;m < 5;m++){
			if (landNeed[m]>0&&i>=lands.size()){
				lands.add(landCheck[m]);
				landNeed[m]--;
				continue;
			}
			}
			while (i>=lands.size()){
				//System.out.println(errorCheck%5);
					if ((theColors[errorCheck%5])){
						lands.add(landCheck[errorCheck%5]);
					}
					errorCheck++;
			}
		}
		
		sortQtDeposite();
		for (int i = 0;i < 40;i++){
			if (i > 22){
					sideboard.add(mainDeck.get(0));
					mainDeck.remove(0);
			}
		}
		int landCount=0;
		for (int i= 0;i<17;i++){
				mainDeck.add(lands.get(landCount++));
		}*/
		double value = 0;
		for (Card yep:mainDeck) {
			value+=yep.getqt();
		}
		//System.out.println("Sideboard:");
		value = Math.round(value/2.3)/10.0;
		mainDeck.addAll(lands);
		writedecklist(mainDeck,sideboard,value,num);
		System.out.println(deckName());
	}
	public boolean [] GoodColors(double baseline){
		//int WUB=0,WUR=0,WUG=0,WBR=0,WBG=0,WRG=0,UBR=0,UBG=0,URG=0,BRG=0/*,WUBR=0,WUBG=0,WURG=0,WBRG=0,UBRG=0,All=0*/;
		//int value=0;
		boolean [] doom= {false,false,false,false,false};
		int [] yep = {0,0,0,0,0};
		for (int i = 0; i < 35&&i < cardsPicked.size();i++) {
			for (int m = 0; m < (2*((cardsPicked.get(i).getqt()-14)/4))+1||m<1;m++) {
			yep[0]+=cardsPicked.get(i).getWhite();
			yep[1]+=cardsPicked.get(i).getBlue();
			yep[2]+=cardsPicked.get(i).getBlack();
			yep[3]+=cardsPicked.get(i).getRed();
			yep[4]+=cardsPicked.get(i).getGreen();
			}
		}
		int yepyep=yep[0]+yep[1]+yep[2]+yep[3]+yep[4];
		if (yep[0]>=yepyep/baseline){doom[0]=true;}
		if (yep[1]>=yepyep/baseline){doom[1]=true;}
		if (yep[2]>=yepyep/baseline){doom[2]=true;}
		if (yep[3]>=yepyep/baseline){doom[3]=true;}
		if (yep[4]>=yepyep/baseline){doom[4]=true;}
		//System.out.println(Arrays.toString(doom));
		return doom;
	}
	public int GoodColorsInt(double baseline){
		//int WUB=0,WUR=0,WUG=0,WBR=0,WBG=0,WRG=0,UBR=0,UBG=0,URG=0,BRG=0/*,WUBR=0,WUBG=0,WURG=0,WBRG=0,UBRG=0,All=0*/;
		//int value=0;
		int doom= 0;
		int [] yep = {0,0,0,0,0};
		for (int i = 0; i < 35&&i < cardsPicked.size();i++) {
			for (int m = 0; m < (2*((cardsPicked.get(i).getqt()-10)/5))+1||m<1;m++) {
			yep[0]+=cardsPicked.get(i).getWhite();
			yep[1]+=cardsPicked.get(i).getBlue();
			yep[2]+=cardsPicked.get(i).getBlack();
			yep[3]+=cardsPicked.get(i).getRed();
			yep[4]+=cardsPicked.get(i).getGreen();
			}
		}
		int yepyep=yep[0]+yep[1]+yep[2]+yep[3]+yep[4];
		if (yep[0]>=yepyep/baseline){doom++;}
		if (yep[1]>=yepyep/baseline){doom++;}
		if (yep[2]>=yepyep/baseline){doom++;}
		if (yep[3]>=yepyep/baseline){doom++;}
		if (yep[4]>=yepyep/baseline){doom++;}
		//System.out.println(Arrays.toString(doom));
		return doom;
	}
	/**
	 * Tests if there are a number if cards in the color that can make a playable deck.
	 * @param colors The current colors in question.
	 * @param minValue The minimum value of decent cards
	 * @param goodVal The minimum value for a card to be considered good.
	 * @return Returns true if their are enough playables, false otherwise.
	 */
	public boolean testIfPlayable(boolean [] colors, int minValue, int goodVal) {
		int doom = 0;
		int playable = 0;
		for (Card yep:cardsPicked) {
			if (testColors(yep,colors)&&!yep.testType("Land")) {
				playable++;
				if (yep.getqt()>=minValue) {
					doom++;
				}
			}
		}
		//System.out.println((doom>18&&playable>22)+"||"+colors.equals(new boolean[]{true,true,true,true,true}));
		if (doom>18&&playable>24) {
			return true;
		}
		return false;
	}
	public boolean testSplash(Card card, boolean [] hello){
		boolean doom = false;
		if (card.testType("Land")){
			
		}
		else{
		if(card.getWhite()>=2&&hello[0]){
			doom = true;
		}
		if(card.getBlue()>=2&&hello[1]){
			doom = true;
		}
		if(card.getBlack()>=2&&hello[2]){
			doom = true;
		}
		if(card.getRed()>=2&&hello[3]){
			doom = true;
		}
		if(card.getGreen()>=2&&hello[4]){
			doom = true;
		}
		}
		return doom;
	}
	public static boolean testColors(Card card, boolean [] colors){
		/*String hi = cardsPicked[num].getColors();
		//System.out.println(cardsPicked[num].getName() + ": " + hi);
		String WUBRG = "WUBRG";
		for (int i = 0;i<5;i++){
			if (testString(hi,WUBRG.substring(i,i+1))){
				if (colors[i]==false){
					return false;
				}
			}
		}*/
		if (card.isHybrid())
		{
			if ((card.getWhite()>0&&colors[0])||(card.getBlue()>0&&colors[1])||(card.getBlack()>0&&colors[2])||
					(card.getRed()>0&&colors[3])||(card.getGreen()>0&&colors[4]))
			{
				return true;
			}
			return false;
		}
		if (card.getWhite()>0&&!colors[0]) {
			return false;
		}
		if (card.getBlue()>0&&!colors[1]) {
			return false;
		}
		if (card.getBlack()>0&&!colors[2]) {
			return false;
		}
		if (card.getRed()>0&&!colors[3]) {
			return false;
		}
		if (card.getGreen()>0&&!colors[4]) {
			return false;
		}
		return true;
	}
	public void sortQtDeposite(){
		if (isPlayer){
		//System.out.println("CommenceSort");
		}
		//work on later
		int trueCards = testAvalible();
		for (int m = 0;m<trueCards;m++){
			for (int n = 0; n <trueCards-1;n++){
				int card1 = getScore(n,false);
				int card2 = getScore(n+1,false);
				if (card1<card2){
					Card theshifted = mainDeck.get(n);
					mainDeck.set(n, mainDeck.get(n+1));
					mainDeck.set(n+1, theshifted);
				}
			}
		}
	}
	public int testAvalible(){
		/*int doom=0;
		if (isPlayer){
			//System.out.println("testAvalible");
		}
		for (int i = 0;i < cardsPicked.length;i++){
			if (!(cardsPicked[i].testBlank()||cardsPicked[i].isError())){
				doom++;
			}
		}*/
		return mainDeck.size();
	}
	public boolean testString(String original, String testfor){
		if (original.equals(testfor)){
			return true;
		}
		if (original.length()<testfor.length()){
			return false;
		}
		for (int i = 0; i<= (original.length()-testfor.length());i++){
			if(original.substring(i,i+testfor.length()).equals(testfor)){
				return true;
			}
		}
			return false;
	}
	public void writedecklist(ArrayList<Card> mainDeck2,ArrayList<Card> sideboard2,double value, int player){
		//System.out.println("player"+player+": "+value+" "+deckName()+".cod");
		(new File(MainClass.programDataFolder()+"drafts/"+packnames[0]+","+packnames[1]+","+packnames[2]+"/"+isPlayerWrite())).mkdirs();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"drafts/"+packnames[0]+","+packnames[1]+","+packnames[2]+"/"+isPlayerWrite()+"/"+deckName()+" #"+
				Settings.settingValue("currentDeckId", "0")+" "+Double.toString(value)+"- player"+player+".cod"))){
			Settings.setSettingValue("currentDeckId", Integer.parseInt(Settings.settingValue("currentDeckId", "0"))+1);
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.write(System.lineSeparator());
			bw.write("<cockatrice_deck version=\"1\">");
			bw.write(System.lineSeparator());
			bw.write("<deckname>"+deckName()+" Draft: "+Double.toString(value)+" "+player+"</deckname>");
			bw.write(System.lineSeparator());
			bw.write("<comments>"+isPlayerWrite()+" drafted</comments>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"main\">");
			bw.write(System.lineSeparator());
			for (int i = 0;i <mainDeck2.size();i++){
				bw.write("<card number=\"1\" name=\""+mainDeck2.get(i).getName()+"\"/>");
				bw.write(System.lineSeparator());
			}
			bw.write("</zone>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"side\">");
			bw.write(System.lineSeparator());
			for (int i = 0;i <sideboard2.size();i++){
				bw.write("<card number=\"1\" name=\""+sideboard2.get(i).getName()+"\"/>");
				bw.write(System.lineSeparator());
			}
			bw.write("</zone>");
			bw.write(System.lineSeparator());
			bw.write("</cockatrice_deck>");
			bw.write(System.lineSeparator());
			System.out.println("Completed deck "+player);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	public String deckName(){
		String doom=" ";
		if (addSplash[0]) {
			doom+="W";
		}
		if (addSplash[1]) {
			doom+="U";
		}
		if (addSplash[2]) {
			doom+="B";
		}
		if (addSplash[3]) {
			doom+="R";
		}
		if (addSplash[4]) {
			doom+="G";
		}
		if (doom.equals(" W")) {
			doom="monoWhite ";
		}
		else if (doom.equals(" U")) {
			doom="monoBlue ";
		}
		else if (doom.equals(" B")) {
			doom="monoBlack ";
		}
		else if (doom.equals(" R")) {
			doom="monoRed ";
		}
		else if (doom.equals(" G")) {
			doom="monoGreen ";
		}
		else if (doom.equals(" WU")) {
			doom="Azorious ";
		}
		else if (doom.equals(" UB")) {
			doom="Dimir ";
		}
		else if (doom.equals(" BR")) {
			doom="Rakdos ";
		}
		else if (doom.equals(" RG")) {
			doom="Gruul ";
		}
		else if (doom.equals(" WG")) {
			doom="Selesnya ";
		}
		else if (doom.equals(" WB")) {
			doom="Orzhov ";
		}
		else if (doom.equals(" UR")) {
			doom="Izzet ";
		}
		else if (doom.equals(" BG")) {
			doom="Golgari ";
		}
		else if (doom.equals(" WR")) {
			doom="Boros ";
		}
		else if (doom.equals(" UG")) {
			doom="Simic ";
		}
		
		else if (doom.equals(" WUB")) {
			doom="Esper ";
		}
		else if (doom.equals(" UBR")) {
			doom="Grixus ";
		}
		else if (doom.equals(" BRG")) {
			doom="Jund ";
		}
		else if (doom.equals(" WRG")) {
			doom="Naya ";
		}
		else if (doom.equals(" WUG")) {
			doom="Bant ";
		}
		else if (doom.equals(" WBR")) {
			doom="Mardu ";
		}
		else if (doom.equals(" URG")) {
			doom="Temur ";
		}
		else if (doom.equals(" WBG")) {
			doom="Abzan ";
		}
		else if (doom.equals(" WUR")) {
			doom="Jeskai ";
		}
		else if (doom.equals(" UBG")) {
			doom="Sultai ";
		}
		else {
			doom+=" ";
		}
		return doom;
	}
	public Pack myPack() {
		return table.getMypack(id);
	}
	public void orderQt() {
		ArrayList<Card> qtvalue = new ArrayList<Card>();
		for (Card hoi:cardsPicked) {
			qtvalue.add(hoi);
			for (int i =qtvalue.size()-1;i>0;i--) {
				if (qtvalue.get(i).getqt()>qtvalue.get(i-1).getqt()) {
					Collections.swap(qtvalue, i, i-1);
				}
			}
		}
		cardsPicked=qtvalue;
	}
	public void goodDrops(int drop,int num) {
		for (int i = 0; i < num;i++) {
			boolean found=false;
			for (int m = 0;(m<num||!found)&&m<cardsPicked.size();m++) {
				if (cardsPicked.get(m).typeCreature()&&cardsPicked.get(m).getCMC()==drop&&testColors(cardsPicked.get(m),theColors)) {
					mainDeck.add(cardsPicked.get(m));
					cardsPicked.remove(cardsPicked.get(m));
					found=true;
				}
			}
		}
	}
	public int offColors(Card theCard) {
		int doom = 0;
		if (theCard.getWhite()>0&&theColors[0]==false) {
			doom+=theCard.getWhite();
		}
		if (theCard.getBlue()>0&&theColors[0]==false) {
			doom+=theCard.getBlue();
		}
		if (theCard.getBlack()>0&&theColors[0]==false) {
			doom+=theCard.getBlack();
		}
		if (theCard.getRed()>0&&theColors[0]==false) {
			doom+=theCard.getRed();
		}
		if (theCard.getGreen()>0&&theColors[0]==false) {
			doom+=theCard.getGreen();
		}
		return doom;
	}
	public int smallestint(double [] test) {
		int doom = 0;
		for (int i = 1; i < test.length;i++) {
			if (test[i]<test[doom]) {
				doom=i;
			}
		}
		return doom;
	}
	public int colorvalue(Card theCard) {
		double holdvalue = 0;
		int colors=0;
		if (theCard.getWhite()>0) {
			holdvalue+=valueC[0];
			colors++;
		}
		if (theCard.getBlue()>0) {
			holdvalue+=valueC[1];
			colors++;
		}
		if (theCard.getBlack()>0) {
			holdvalue+=valueC[2];
			colors++;
		}
		if (theCard.getRed()>0) {
			holdvalue+=valueC[3];
			colors++;
		}
		if (theCard.getGreen()>0) {
			holdvalue+=valueC[4];
			colors++;
		}	
		double overal = holdvalue/(valueC[5]*colors);
		
		return (int) (overal*20);
	}
	public String isPlayerWrite() {
		if (isPlayer) {
			return "Player";
		}
		return "AI";
	}
	public int checkMax(int value,int max) {
		if (value>max) {
			return max;
		}
		return value;
	}
	/**
	 * Draws a color rectangle based on the colors of the chosen player.
	 * @param x The x coordinate of the top right point.
	 * @param y The y coordinate of the top right point.
	 * @param x1 Width of the color box.
	 * @param y1 Height of the color box.
	 * @param cellar Graphics object to paint on.
	 */
	public void drawColorBar(int x, int y, int x1, int y1, Graphics cellar)
	{
		if (CTotal == 0)
		{
			cellar.setColor(Color.decode("#989898"));
			cellar.fillRect(x, y, x1, y1);
			return;
		}
		int holdx = x;
		cellar.setColor(Color.decode("#C7CB58"));
		cellar.fillRect(holdx, y, (White*x1)/CTotal, y1);
		holdx += (White*x1)/CTotal;
		cellar.setColor(Color.decode("#2130DF"));
		cellar.fillRect(holdx, y, (Blue*x1)/CTotal, y1);
		holdx += (Blue*x1)/CTotal;
		cellar.setColor(Color.BLACK);
		cellar.fillRect(holdx, y, (Black*x1)/CTotal, y1);
		holdx += (Black*x1)/CTotal;
		cellar.setColor(Color.RED);
		cellar.fillRect(holdx, y, (Red*x1)/CTotal, y1);
		holdx += (Red*x1)/CTotal;
		cellar.setColor(Color.decode("#029400"));
		cellar.fillRect(holdx, y, (Green*x1)/CTotal, y1);
		holdx += (Green*x1)/CTotal;
	}
}
