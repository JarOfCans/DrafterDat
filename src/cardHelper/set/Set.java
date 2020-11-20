package cardHelper.set;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import cardHelper.MainClass;
import cardHelper.Settings;
import cardHelper.card.Card;
import cardHelper.customErrors.NoValidCardException;
import cardHelper.pack.Pack;
import cardHelper.pack.PackSetup;
import cardHelper.player.Player;

public class Set {
	/**
	 * The cards of Common rarity of the Set.
	 */
	private ArrayList<Card> commons = new ArrayList<Card>();
	/**
	 * The cards of Uncommon rarity of the Set.
	 */
	private ArrayList<Card> uncommons= new ArrayList<Card>();
	/**
	 * The cards of Rare rarity of the Set.
	 */
	private ArrayList<Card> rares= new ArrayList<Card>();
	/**
	 * The cards of Mythic rare rarity of the set.
	 */
	private ArrayList<Card> mythics= new ArrayList<Card>();
	/**
	 * The cards under the Special rarity slot of the set.
	 */
	private ArrayList<Card> special= new ArrayList<Card>();
	/**
	 * The cards under the Basic land rarity slot of the set. Does not have classic basic lands.
	 */
	private ArrayList<Card> basic_lands= new ArrayList<Card>();
	/**
	 * Name of the set.
	 */
	private String name;
	/**
	 * PackSetup object with the instructions on how to set up a new pack.
	 */
	private PackSetup packsetup;
	/**
	 * Double array object with the matrix of [Color_ID][Quadrant]
	 */
	private double [][] qstats = new double[5][6];
	private ArrayList<String> validArchetypes;
	/**
	 * Makes a Set of the given name and sets up a Set of that name.
	 * @param setName Name of the Set. Value "All" returns the baseline set to make a full set.
	 */
	public Set(String setName)
	{
		name = setName;
		validArchetypes = new ArrayList<String>(7);
		packsetup = new PackSetup(name);
		gatherdata();
	}
	/**
	 * Default constructor for making a blank set.
	 */
	public Set()
	{
		name = " ";
		validArchetypes = new ArrayList<String>(7);
		packsetup = new PackSetup("blank");
	}
	/**
	 * Gets the number if cards in the Set.
	 * @return Number of cards in the Set.
	 */
	public int getSetSize(){
		return (commons.size()+uncommons.size()+rares.size()+mythics.size()+special.size()+basic_lands.size());
	}
	/**
	 * Obtains the card at index i.
	 * @param i Index number of the card.
	 * @return Card of the given index number.
	 */
	public Card getCard(int i){
		//Specifically for Collection
		int errorhold = i;
		int C = commons.size();
		int U = uncommons.size();
		int R = rares.size();
		int M = mythics.size();
		int S = special.size();
		int L = basic_lands.size();
		if (i < C){
			return commons.get(i);
		}
		else{
			i-=C;
		}
		if (i < U){
			return uncommons.get(i);
		}
		else{
			i-=U;
		}
		if (i < R){
			return rares.get(i);
		}
		else{
			i-=R;
		}
		if (i < M){
			return mythics.get(i);
		}
		else{
			i-=M;
		}
		if (i < S){
			return special.get(i);
		}
		else{
			i-=S;
		}
		if (i < L){
			return basic_lands.get(i);
		}
		else{
			i-=L;
		}
		System.out.println("Card slot given error: "+errorhold);
		return commons.get(0);
	}
	public void gatherdata(){
		
		//Find a way to simulate print run odds
		String holdData = "";
		int errorCheck = 0;
		int setId = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(MainClass.programDataFolder()+"Packs/"+name+"/Draft.txt"))){
		while ((!((holdData=br.readLine())==null))&&!holdData.equals("done")){
								//name,rarity,cost,type,(-->)//
			boolean isFirst = true;
			ArrayList<String> cardData = new ArrayList<String>();
			String rarity = "common";
			//cardData.add(holdData);
			for (boolean doneCheck = false; !doneCheck;)
			{
				String nextLine = holdData;
				if (!isFirst)
				{
					nextLine = br.readLine();
				}
				isFirst = false;
				if (nextLine == null) {
					errorCheck++;
					if (errorCheck >= 5) {
						doneCheck = true;
						errorCheck = 0;
					}
				}
				else if (nextLine.equals("//")||nextLine.equals("done")) {
					doneCheck = true;
				}
				else {
					errorCheck = 0;
					//System.out.println(nextLine + " hoi.");
					if (Settings.checkValid(nextLine))
					{
						if(Settings.getNameHelper(nextLine).equals("rarity"))
						{
							rarity = Settings.getValueHelper(nextLine);
						}
						else if (Settings.getNameHelper(nextLine).equals("s"))
						{
							String archetype = Settings.getValueHelper(nextLine);
							if (!validArchetypes.contains(archetype.toLowerCase()))
							{
								validArchetypes.add(archetype.toLowerCase());
							}
							cardData.add(nextLine);
						}
						else
						{
							cardData.add(nextLine);
						}
					}
				}
			}
			//String [] cardData = {holdData,br.readLine(),br.readLine(),br.readLine(),br.readLine()};
			//Card hoi =new Card(cardData[0],cardData[2],cardData[3],stack,group,false);
			Card hoi = new Card(cardData, this);
			hoi.setsetId(setId++);
			if (!hoi.nonBasic())
			{
				continue;
			}
			if (rarity.equals("common")){
				commons.add(hoi);
			}
			else if (rarity.equals("uncommon")){
				uncommons.add(hoi);
			}
			else if (rarity.equals("rare")){
				rares.add(hoi);
			}
			else if (rarity.equals("mythic rare")){
				mythics.add(hoi);
			}
			else if (rarity.equals("special")){
				special.add(hoi);
			}
			else if (rarity.equals("basic land")){
				basic_lands.add(hoi);
			}
			else {
				if (!hoi.nonBasic())
				{
					continue;
				}
				System.out.println("Error reading rarity of " + holdData);
				commons.add(hoi);
			}
		}
		}
		catch(IOException e){
			System.out.println("Error finding set "+name);
		}
	}
	public Pack buildpack() {
		Pack doom = new Pack(false);
		boolean checked = false;
		doom.packSet=name;
		ArrayList<Card> uncommonP = new ArrayList<Card>(uncommons);
		ArrayList<Card> rareP = new ArrayList<Card>(rares);
		ArrayList<Card> mythicP = new ArrayList<Card>(mythics);
		ArrayList<Card> specialP = new ArrayList<Card>(special);
		ArrayList<Card> basicP = new ArrayList<Card>(basic_lands);
		ArrayList<Card> commonP = new ArrayList<Card>(commons);
		ArrayList<Card> trash0=new ArrayList<Card>();
		ArrayList<Card> trash1=new ArrayList<Card>(),trash2=new ArrayList<Card>(),
				trash3=new ArrayList<Card>(),trash4=new ArrayList<Card>();
			int [] vals = packsetup.getRarities();
			int oof =0;

			int yep = vals[0];
			while (yep>0) {
				doom.addCard(nextCard(commonP,trash0,false));
				yep--;
			}
			yep = vals[1];
			while (yep>0) {
				doom.addCard(nextCard(uncommonP,trash1,false));
				yep--;
			}
			yep = vals[2];
			while (yep>0) {
				doom.addCard(nextCard(rareP,trash2,false));
				yep--;
			}
			yep = vals[3];
			while (yep>0) {
				doom.addCard(nextCard(mythicP,trash3,false));
				yep--;
			}
			yep = vals[4];
			while (yep>0) {
				doom.addCard(nextCard(specialP,trash4,false));
				yep--;
			}
			yep = vals[5];
			while (yep>0) {
				doom.addCard(nextCard(basicP,trash4,false));
				yep--;
			}
			doom.setIds();
		return doom;
	}
	/**
	 * Builds and returns a Pack with the given card, if possible.
	 * @param guaranteeCard The card that the pack will have.
	 * @return A Pack with the guaranteed card, if the Pack can have the card.
	 */
	public Pack buildPack(Card guaranteeCard) {
		Pack doom = new Pack(false);
		boolean checked = false;
		String guaranteedRarity = "None";
		doom.packSet=name;
		ArrayList<Card> uncommonP = new ArrayList<Card>(uncommons);
		if (uncommonP.contains(guaranteeCard))
		{
			guaranteedRarity = "U";
		}
		ArrayList<Card> rareP = new ArrayList<Card>(rares);
		if (rareP.contains(guaranteeCard))
		{
			guaranteedRarity = "R";
		}
		ArrayList<Card> mythicP = new ArrayList<Card>(mythics);
		if (mythicP.contains(guaranteeCard))
		{
			guaranteedRarity = "M";
		}
		ArrayList<Card> specialP = new ArrayList<Card>(special);
		if (specialP.contains(guaranteeCard))
		{
			guaranteedRarity = "S";
		}
		ArrayList<Card> basicP = new ArrayList<Card>(basic_lands);
		if (basicP.contains(guaranteeCard))
		{
			guaranteedRarity = "L";
		}
		ArrayList<Card> commonP = new ArrayList<Card>(commons);
		if (commonP.contains(guaranteeCard))
		{
			guaranteedRarity = "C";
		}
		//System.out.println(String.format("%s: Rarity %s", guaranteeCard, guaranteedRarity));
		ArrayList<Card> trash0=new ArrayList<Card>();
		ArrayList<Card> trash1=new ArrayList<Card>(),trash2=new ArrayList<Card>(),
				trash3=new ArrayList<Card>(),trash4=new ArrayList<Card>();
		int [] vals;
		if (guaranteedRarity != "None")
		{
			vals = packsetup.getGuaranteedRarities(guaranteedRarity);
		}
		else {
			vals = packsetup.getRarities();
		}
			int oof =0;

			int yep = vals[0];
			while (yep>0) {
				if (!checked && guaranteedRarity == "C")
				{
					doom.addCard(guaranteeCard);
					checked = true;
				}
				else {
					doom.addCard(nextCard(commonP,trash0,false));
				}
				yep--;
			}
			yep = vals[1];
			while (yep>0) {
				if (!checked && guaranteedRarity == "U")
				{
					doom.addCard(guaranteeCard);
					checked = true;
				}
				else {
					doom.addCard(nextCard(uncommonP,trash1,false));
				}
				yep--;
			}
			yep = vals[2];
			while (yep>0) {
				if (!checked && guaranteedRarity == "R")
				{
					doom.addCard(guaranteeCard);
					checked = true;
				}
				else {
					doom.addCard(nextCard(rareP,trash2,false));
				}
				yep--;
			}
			yep = vals[3];
			while (yep>0) {
				if (!checked && guaranteedRarity == "M")
				{
					doom.addCard(guaranteeCard);
					checked = true;
				}
				else {
					doom.addCard(nextCard(mythicP,trash3,false));
				}
				yep--;
			}
			yep = vals[4];
			while (yep>0) {
				if (!checked && guaranteedRarity == "S")
				{
					doom.addCard(guaranteeCard);
					checked = true;
				}
				else {
					doom.addCard(nextCard(specialP,trash4,false));
				}
				yep--;
			}
			yep = vals[5];
			while (yep>0) {
				if (!checked && guaranteedRarity == "L")
				{
					doom.addCard(guaranteeCard);
					checked = true;
				}
				else {
					doom.addCard(nextCard(basicP,trash4,false));
				}
				yep--;
			}
			doom.setIds();
		return doom;
	}
	protected Card nextCard(ArrayList<Card> input, ArrayList<Card> trash, boolean nonmulti) {
		if (input.size()==0) {
			input=trash;
			trash.clear();
		}
		Collections.shuffle(input);
		Card doom = input.get(0);
		if (nonmulti&&doom.getColors().length()==1&&!doom.getColors().equals("C")) {
			Collections.shuffle(input);
			doom = input.get(0);
		}
		input.remove(doom);
		trash.add(doom);
		return doom;
	}
	protected Card nextColor(ArrayList<Card> input, ArrayList<Card> trash,int color) {
		if (input.size()==0) {
			input=trash;
			trash.clear();
		}
		Collections.shuffle(input);
		Card doom = input.get(0);
		switch (color) {
		case 0:
			for (Card hoi:input) {
				if (hoi.getColors().equals("W")) {
					doom=hoi;
					break;
				}
			}
			input.remove(doom);
			trash.add(doom);
			return doom;
		case 1:
			for (Card hoi:input) {
				if (hoi.getColors().equals("U")) {
					doom=hoi;
					break;
				}
			}
			input.remove(doom);
			trash.add(doom);
			return doom;
		case 2:
			for (Card hoi:input) {
				if (hoi.getColors().equals("B")) {
					doom=hoi;
					break;
				}
			}
			input.remove(doom);
			trash.add(doom);
			return doom;
		case 3:
			for (Card hoi:input) {
				if (hoi.getColors().equals("R")) {
					doom=hoi;
					break;
				}
			}
			input.remove(doom);
			trash.add(doom);
			return doom;
		case 4:
			for (Card hoi:input) {
				if (hoi.getColors().equals("G")) {
					doom=hoi;
					break;
				}
			}
			input.remove(doom);
			trash.add(doom);
			return doom;
		default:
			
		}
		input.remove(doom);
		trash.add(doom);
		return doom;
	}
	/**
	 * Pulls a random letter from a given String.
	 * @param ran A String to pull a random letter from.
	 * @return A random letter from the given String in the String formant.
	 */
	public String randomise(String ran) {
		int tran = (int)Math.floor(Math.random()*ran.length());
		return ran.substring(tran,tran+1);
	}
	/**
	 * Calculates each of the color's quadrant stats.
	 * Each rarities value: Commons*4, Uncommons*2, Rares(all)*1. Does not use basic land slot
	 */
	public void calculateStats(){
		for (int i = 0;i < 30;i++) {
			qstats[i%5][i/5] = 0;
		}
		int [] colorWins = new int[5];
		int [] colorLoses = new int[5];
		double [] counter = new double[5];
		for (int i = 0; i < commons.size();i++) {
			if (commons.get(i).getWhite()>0) {
			qstats[0][0]+=commons.get(i).getq1()*4;
			qstats[0][1]+=commons.get(i).getq2()*4;
			qstats[0][2]+=commons.get(i).getq3()*4;
			qstats[0][3]+=commons.get(i).getq4()*4;
			qstats[0][4]+=commons.get(i).getqt()*4;
			colorWins[0] += commons.get(i).wins*4;
			colorLoses[0] += commons.get(i).loses*4;
			counter[0]+=4;
			}
			if (commons.get(i).getBlue()>0) {
			qstats[1][0]+=commons.get(i).getq1()*4;
			qstats[1][1]+=commons.get(i).getq2()*4;
			qstats[1][2]+=commons.get(i).getq3()*4;
			qstats[1][3]+=commons.get(i).getq4()*4;
			qstats[1][4]+=commons.get(i).getqt()*4;
			qstats[1][5]+=commons.get(i).winrate()*4;
			colorWins[1] += commons.get(i).wins*4;
			colorLoses[1] += commons.get(i).loses*4;
			counter[1]+=4;
			}
			if (commons.get(i).getBlack()>0) {
			qstats[2][0]+=commons.get(i).getq1()*4;
			qstats[2][1]+=commons.get(i).getq2()*4;
			qstats[2][2]+=commons.get(i).getq3()*4;
			qstats[2][3]+=commons.get(i).getq4()*4;
			qstats[2][4]+=commons.get(i).getqt()*4;
			qstats[2][5]+=commons.get(i).winrate()*4;
			colorWins[2] += commons.get(i).wins*4;
			colorLoses[2] += commons.get(i).loses*4;
			counter[2]+=4;
			}
			if (commons.get(i).getRed()>0) {
			qstats[3][0]+=commons.get(i).getq1()*4;
			qstats[3][1]+=commons.get(i).getq2()*4;
			qstats[3][2]+=commons.get(i).getq3()*4;
			qstats[3][3]+=commons.get(i).getq4()*4;
			qstats[3][4]+=commons.get(i).getqt()*4;
			qstats[3][5]+=commons.get(i).winrate()*4;
			colorWins[3] += commons.get(i).wins*4;
			colorLoses[3] += commons.get(i).loses*4;
			counter[3]+=4;
			}
			if (commons.get(i).getGreen()>0) {
			qstats[4][0]+=commons.get(i).getq1()*4;
			qstats[4][1]+=commons.get(i).getq2()*4;
			qstats[4][2]+=commons.get(i).getq3()*4;
			qstats[4][3]+=commons.get(i).getq4()*4;
			qstats[4][4]+=commons.get(i).getqt()*4;
			qstats[4][5]+=commons.get(i).winrate()*4;
			colorWins[4] += commons.get(i).wins*4;
			colorLoses[4] += commons.get(i).loses*4;
			counter[4]+=4;
			}
		}
		for (int i = 0; i < uncommons.size();i++) {
			if (uncommons.get(i).getWhite()>0) {
			qstats[0][0]+=uncommons.get(i).getq1()*2;
			qstats[0][1]+=uncommons.get(i).getq2()*2;
			qstats[0][2]+=uncommons.get(i).getq3()*2;
			qstats[0][3]+=uncommons.get(i).getq4()*2;
			qstats[0][4]+=uncommons.get(i).getqt()*2;
			qstats[0][5]+=uncommons.get(i).winrate()*2;
			colorWins[0] += uncommons.get(i).wins*2;
			colorLoses[0] += uncommons.get(i).loses*2;
			counter[0]+=2;
			}
			if (uncommons.get(i).getBlue()>0) {
			qstats[1][0]+=uncommons.get(i).getq1()*2;
			qstats[1][1]+=uncommons.get(i).getq2()*2;
			qstats[1][2]+=uncommons.get(i).getq3()*2;
			qstats[1][3]+=uncommons.get(i).getq4()*2;
			qstats[1][4]+=uncommons.get(i).getqt()*2;
			qstats[1][5]+=uncommons.get(i).winrate()*2;
			colorWins[1] += uncommons.get(i).wins*2;
			colorLoses[1] += uncommons.get(i).loses*2;
			counter[1]+=2;
			}
			if (uncommons.get(i).getBlack()>0) {
			qstats[2][0]+=uncommons.get(i).getq1()*2;
			qstats[2][1]+=uncommons.get(i).getq2()*2;
			qstats[2][2]+=uncommons.get(i).getq3()*2;
			qstats[2][3]+=uncommons.get(i).getq4()*2;
			qstats[2][4]+=uncommons.get(i).getqt()*2;
			qstats[2][5]+=uncommons.get(i).winrate()*2;
			colorWins[2] += uncommons.get(i).wins*2;
			colorLoses[2] += uncommons.get(i).loses*2;
			counter[2]+=2;
			}
			if (uncommons.get(i).getRed()>0) {
			qstats[3][0]+=uncommons.get(i).getq1()*2;
			qstats[3][1]+=uncommons.get(i).getq2()*2;
			qstats[3][2]+=uncommons.get(i).getq3()*2;
			qstats[3][3]+=uncommons.get(i).getq4()*2;
			qstats[3][4]+=uncommons.get(i).getqt()*2;
			qstats[3][5]+=uncommons.get(i).winrate()*2;
			colorWins[3] += uncommons.get(i).wins*2;
			colorLoses[3] += uncommons.get(i).loses*2;
			counter[3]+=2;
			}
			if (uncommons.get(i).getGreen()>0) {
			qstats[4][0]+=uncommons.get(i).getq1()*2;
			qstats[4][1]+=uncommons.get(i).getq2()*2;
			qstats[4][2]+=uncommons.get(i).getq3()*2;
			qstats[4][3]+=uncommons.get(i).getq4()*2;
			qstats[4][4]+=uncommons.get(i).getqt()*2;
			qstats[4][5]+=uncommons.get(i).winrate()*2;
			colorWins[4] += uncommons.get(i).wins*2;
			colorLoses[4] += uncommons.get(i).loses*2;
			counter[4]+=2;
			}
		}
		for (int i = 0; i < rares.size();i++) {
			if (rares.get(i).getWhite()>0) {
			qstats[0][0]+=rares.get(i).getq1();
			qstats[0][1]+=rares.get(i).getq2();
			qstats[0][2]+=rares.get(i).getq3();
			qstats[0][3]+=rares.get(i).getq4();
			qstats[0][4]+=rares.get(i).getqt();
			colorWins[0] += rares.get(i).wins;
			colorLoses[0] += rares.get(i).loses;
			counter[0]++;
			}
			if (rares.get(i).getBlue()>0) {
			qstats[1][0]+=rares.get(i).getq1();
			qstats[1][1]+=rares.get(i).getq2();
			qstats[1][2]+=rares.get(i).getq3();
			qstats[1][3]+=rares.get(i).getq4();
			qstats[1][4]+=rares.get(i).getqt();
			qstats[1][5]+=rares.get(i).winrate();
			colorWins[1] += rares.get(i).wins;
			colorLoses[1] += rares.get(i).loses;
			counter[1]++;
			}
			if (rares.get(i).getBlack()>0) {
			qstats[2][0]+=rares.get(i).getq1();
			qstats[2][1]+=rares.get(i).getq2();
			qstats[2][2]+=rares.get(i).getq3();
			qstats[2][3]+=rares.get(i).getq4();
			qstats[2][4]+=rares.get(i).getqt();
			qstats[2][5]+=rares.get(i).winrate();
			colorWins[2] += rares.get(i).wins;
			colorLoses[2] += rares.get(i).loses;
			counter[2]++;
			}
			if (rares.get(i).getRed()>0) {
			qstats[3][0]+=rares.get(i).getq1();
			qstats[3][1]+=rares.get(i).getq2();
			qstats[3][2]+=rares.get(i).getq3();
			qstats[3][3]+=rares.get(i).getq4();
			qstats[3][4]+=rares.get(i).getqt();
			qstats[3][5]+=rares.get(i).winrate();
			colorWins[3] += rares.get(i).wins;
			colorLoses[3] += rares.get(i).loses;
			counter[3]++;
			}
			if (rares.get(i).getGreen()>0) {
			qstats[4][0]+=rares.get(i).getq1();
			qstats[4][1]+=rares.get(i).getq2();
			qstats[4][2]+=rares.get(i).getq3();
			qstats[4][3]+=rares.get(i).getq4();
			qstats[4][4]+=rares.get(i).getqt();
			qstats[4][5]+=rares.get(i).winrate();
			colorWins[4] += rares.get(i).wins;
			colorLoses[4] += rares.get(i).loses;
			counter[4]++;
			}
		}
		for (int i = 0; i < mythics.size();i++) {
			if (mythics.get(i).getWhite()>0) {
			qstats[0][0]+=mythics.get(i).getq1();
			qstats[0][1]+=mythics.get(i).getq2();
			qstats[0][2]+=mythics.get(i).getq3();
			qstats[0][3]+=mythics.get(i).getq4();
			qstats[0][4]+=mythics.get(i).getqt();
			qstats[0][5]+=mythics.get(i).winrate();
			colorWins[0] += mythics.get(i).wins;
			colorLoses[0] += mythics.get(i).loses;
			counter[0]++;
			}
			if (mythics.get(i).getBlue()>0) {
			qstats[1][0]+=mythics.get(i).getq1();
			qstats[1][1]+=mythics.get(i).getq2();
			qstats[1][2]+=mythics.get(i).getq3();
			qstats[1][3]+=mythics.get(i).getq4();
			qstats[1][4]+=mythics.get(i).getqt();
			qstats[1][5]+=mythics.get(i).winrate();
			colorWins[1] += mythics.get(i).wins;
			colorLoses[1] += mythics.get(i).loses;
			counter[1]++;
			}
			if (mythics.get(i).getBlack()>0) {
			qstats[2][0]+=mythics.get(i).getq1();
			qstats[2][1]+=mythics.get(i).getq2();
			qstats[2][2]+=mythics.get(i).getq3();
			qstats[2][3]+=mythics.get(i).getq4();
			qstats[2][4]+=mythics.get(i).getqt();
			qstats[2][5]+=mythics.get(i).winrate();
			colorWins[2] += mythics.get(i).wins;
			colorLoses[2] += mythics.get(i).loses;
			counter[2]++;
			}
			if (mythics.get(i).getRed()>0) {
			qstats[3][0]+=mythics.get(i).getq1();
			qstats[3][1]+=mythics.get(i).getq2();
			qstats[3][2]+=mythics.get(i).getq3();
			qstats[3][3]+=mythics.get(i).getq4();
			qstats[3][4]+=mythics.get(i).getqt();
			qstats[3][5]+=mythics.get(i).winrate();
			colorWins[3] += mythics.get(i).wins;
			colorLoses[3] += mythics.get(i).loses;
			counter[3]++;
			}
			if (mythics.get(i).getGreen()>0) {
			qstats[4][0]+=mythics.get(i).getq1();
			qstats[4][1]+=mythics.get(i).getq2();
			qstats[4][2]+=mythics.get(i).getq3();
			qstats[4][3]+=mythics.get(i).getq4();
			qstats[4][4]+=mythics.get(i).getqt();
			qstats[4][5]+=mythics.get(i).winrate();
			colorWins[4] += mythics.get(i).wins;
			colorLoses[4] += mythics.get(i).loses;
			counter[4]++;
			}
		}
		for (int i = 0; i < 5; i++)
		{
			//System.out.printf("[%d]: %d - %d\n", i, colorWins[i], colorLoses[i]);
			qstats[i][5] = ((double) colorWins[i])/((double)colorWins[i]+colorLoses[i])*100.0;
			//System.out.println(qstats[i][5]);
		}
		
		for (int i = 0;i < 30;i++) {
			qstats[i%5][i/5]= Math.round((qstats[i%5][i/5]*10)/counter[i%5])/10.0;
			if (i >= 20)
			{
				//Used to let the winrate, which doesn't use the counter, to act properly
				counter[i%5] = 1;
			}
			//System.out.printf("[%d][%d] = %s\n", i%5, i / 5, String.valueOf(qstats[i%5][i/5]));
		}
		//System.out.println(qstats[0][5]);
	}
	/**
	 * Resets and saves the cards in the set.
	 */
	public void resetCount() {
		for (int i = 0;i < commons.size();i++) {
			commons.get(i).Save(true);
		}
		for (int i = 0;i < uncommons.size();i++) {
			uncommons.get(i).Save(true);
		}
		for (int i = 0;i < rares.size();i++) {
			rares.get(i).Save(true);
		}
		for (int i = 0;i < mythics.size();i++) {
			mythics.get(i).Save(true);
		}
		for (int i = 0;i < special.size();i++) {
			special.get(i).Save(true);
		}
		for (int i = 0;i < special.size();i++) {
			basic_lands.get(i).Save(true);
		}
	}
	public int arraySum(int [] hoi) {
		int doom=0;
		for (int yep:hoi) {
			doom+=yep;
		}
		return doom;
	}
	public ArrayList<Card> sortedCards(String sortStyle)
	{
		ArrayList<Card> output = new ArrayList<Card>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		output.addAll(new ArrayList<Card>(commons));
		output.addAll(new ArrayList<Card>(uncommons));
		output.addAll(new ArrayList<Card>(rares));
		output.addAll(new ArrayList<Card>(mythics));
		output.addAll(new ArrayList<Card>(special));
		output.addAll(new ArrayList<Card>(basic_lands));
		switch (sortStyle)
		{
		case "q1":
			for (Card hoi: output)
			{
				values.add(hoi.getq1());
			}
			break;
		case "q2":
			for (Card hoi: output)
			{
				values.add(hoi.getq2());
			}
			break;
		case "q3":
			for (Card hoi: output)
			{
				values.add(hoi.getq3());
			}
			break;
		case "q4":
			for (Card hoi: output)
			{
				values.add(hoi.getq4());
			}
			break;
		case "qt":
			for (Card hoi: output)
			{
				values.add(hoi.getqt());
			}
			break;
		case "rarity":
			int isize = output.size();
			for (int i = 0; i < isize; i++)
			{
				values.add(0);
			}
			break;
		case "winrate":
			for (Card hoi: output)
			{
				if (hoi.isTested())
				{
					values.add(hoi.winrate());
				}
				else {
					values.add(101);
				}
			}
			break;
		default:
			for (Card hoi: output)
			{
				values.add(hoi.getsetId());
			}
		}
		return insertionSort(output, values);
	}
	/**
	 * 
	 * @author Dr. Dorn and Mr. Cavanaugh. Modified by John H.
	 * @param input
	 * @return
	 */
	public ArrayList<Card> insertionSort(ArrayList<Card> input, ArrayList<Integer> values) 
	{
		Card insert; // temporary variable to hold element to insert
		Integer inValue;
		ArrayList<Card> output = new ArrayList<Card>(input);
		
		// loop over data.length - 1 elements
		for (int next = 1; next < output.size(); next++) 
		{
			insert = output.get(next); // store value in current element
			inValue = values.get(next);
			int moveItem = next; // initialize location to place element
		   
			// shift items in the sorted part of the array to make room for next element
			// making sure we don't step off the front of the array
			while (moveItem > 0 && values.get(moveItem - 1) > inValue) 
			{
				//SWAP pasted here to avoid method invocation overhead in timing data
				//swap(data, moveItem, moveItem - 1); // shift element right one slot
				Card temp = output.get(moveItem);
				Integer tempIn = values.get(moveItem);
				output.set(moveItem, output.get(moveItem - 1));
				values.set(moveItem, values.get(moveItem - 1));
				output.set(moveItem - 1, temp);		
				values.set(moveItem - 1, tempIn);
				
				moveItem--;
			} 
		   
			output.set(moveItem, insert); // place inserted element
			values.set(moveItem, inValue);
		}
		return output;
	}
	/**
	 * Returns a double from the requested quadrant and color.
	 * @param quadrant Pulls from the given quadrant. 0- Q1, 1- Q2, 2 - Q3, 3 - Q4, 4 - Qt, 5 - WinPercentage
	 * @param color 0 - White, 1 - Blue, 2 - Black, 3 - Red, 4 - Green
	 * @return Double from the requested quadrant from all cards of the requested color.
	 */
	public double getQ(int quadrant, int color) {
		calculateStats();
		return qstats[color][quadrant];
	}
	/**
	 * Returns a random card of the color parameters.
	 * @param rarity The rarity of the card. 0 - common/basic land, 1- uncommon, 2 - rare, 3 - mythic.
	 * @param colorsSelected Boolean array of length 5 that specifies in WUBRG which colors are allowed
	 * @oaram nonLand Whether or not the card is allowed to be a land. (true=not a land, false = any)
	 * @param upgradeChance chance to upgrade by one.
	 * @param archetype Card archetype to guarantee. Will ignore if the archetype doesn't exist in this set.
	 * @return A random card of the specified rarity and castable in the selected colors.
	 */
	public Card pullChaosCard(int rarity, boolean[] colorsSelected, boolean nonLand, int upgradeChance, String archetype)
	{
		ArrayList<Card> cards;
		if (archetype != null && !validArchetypes.contains(archetype.toLowerCase()))
		{
			archetype = null;
		}
		if (rarity<3&&Math.random()*100<=upgradeChance)
		{
			rarity++;
		}
		switch (rarity)
		{
			case 0:
				cards = new ArrayList<Card>(commons);
				cards.addAll(basic_lands);
				break;
			case 1:
				cards = new ArrayList<Card>(uncommons);
				break;
			case 2:
				cards = new ArrayList<Card>(rares);
				break;
			case 3:
				cards = new ArrayList<Card>(mythics);
				break;
			default:
				throw new RuntimeException("There exists no rarity of "+rarity);
		}
		Collections.shuffle(cards);
		for (Card hoi: cards)
		{
			if (Player.testColors(hoi, colorsSelected))
			{
				//Restrictions
				if ((!nonLand||nonLand&&!hoi.testType("Land"))&&(archetype == null|| hoi.hasArchetype(archetype)))
				{
					System.out.println(archetype + " - found");
					return hoi;
				}
			}
		}
		//Archetypeless
		for (Card hoi: cards)
		{
			if (Player.testColors(hoi, colorsSelected))
			{
				//Restrictions
				if ((!nonLand||nonLand&&!hoi.testType("Land")))
				{
					System.out.println(archetype + " - not found");
					return hoi;
				}
			}
		}
		throw new NoValidCardException();
	}
	/**
	 * Retrieves the name of the Set.
	 * @return Name of the Set.
	 */
	public String getName()
	{
		return name;
	}
	public int validArchetypeCount(String input)
	{
		int output = 0;
		for (Card card: allCards())
		{
			output = card.hasArchetype(input) ? output + 1: output;
		}
		return output;
	}
	public ArrayList<String> getArchetypes()
	{
		return validArchetypes;
	}
	private ArrayList<Card> allCards()
	{
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(commons);
		allCards.addAll(uncommons);
		allCards.addAll(rares);
		allCards.addAll(mythics);
		return allCards;
	}
	public void unloadCardImages()
	{
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(new ArrayList<Card>(commons));
		allCards.addAll(new ArrayList<Card>(uncommons));
		allCards.addAll(new ArrayList<Card>(rares));
		allCards.addAll(new ArrayList<Card>(mythics));
		allCards.addAll(new ArrayList<Card>(special));
		allCards.addAll(new ArrayList<Card>(basic_lands));
		for (Card card : allCards) {
			card.unloadImage();
		}
	}
	
	/**
	 * Merges the input set with this one if this set name is ALL, then returns this set reguardless
	 * @return This set
	 */
	public Set mergeSets(Set input) {
		if (name.equals("All")) {
			commons.addAll(input.commons);
			uncommons.addAll(input.uncommons);
			rares.addAll(input.rares);
			mythics.addAll(input.mythics);
			special.addAll(input.special);
			basic_lands.addAll(input.basic_lands);
		}
		return this;
	}
}
