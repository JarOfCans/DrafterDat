package cardHelper.toolBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import cardHelper.card.Card;

public class ToolBox {
	//permanent variables
	public ArrayList<ClickableCard>click = new ArrayList<ClickableCard>(20);
	public int getIntValue(String storageFile,String varName){
		String doomString = "0";
		try (BufferedReader br = new BufferedReader(new FileReader(storageFile))){
			boolean foundvar = false;
			String value;
			while(!((value = br.readLine())==null)&&!foundvar){
				if (value.length()>=3){
				if (value.substring(0,3).equals("var")){
					if(getVarName(value).equals(varName)){
						doomString = (getVarValue(value));
						foundvar = true;
					}
				}
			}
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		doomString = doomString.replaceAll("[^\\d.^-]", "");
		  if (doomString.equals("")){
		   doomString = "0";
		  }
		return Integer.parseInt(doomString);
	}
	public void addVarInt(String storageFile,String varName,int increase){
		//System.out.println(storageFile);
		setVar(storageFile,varName,Integer.toString((getIntValue(storageFile,varName)+increase)));
	}	
	public String getStringValue(String storageFile,String varName){
		String doomString = "0";
		try (BufferedReader br = new BufferedReader(new FileReader(storageFile))){
			boolean foundvar = false;
			String value;
			while(!((value = br.readLine())== null)&&!foundvar){
				if (value.length() >= 8){
				if (value.substring(0,3).equals("var")){
					if(getVarName(value).equals(varName)){
						doomString = (getVarValue(value));
						foundvar = true;
					}
					}
				}
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		return (doomString);
	}
	
	
	public String getVarName(String varString){
		String var = varString;
		for (int i = 4;i< var.length()-1;i++){
			if(var.substring(i, i+2).equals(" =")){
				return (var.substring(4,i));
			}
		}
		return "error";
	}
	
	
	public String getVarValue(String var){
		int equal_sign = 0;
		int semiColon = 0;
		for (int i = 4;i< var.length()-1;i++){
			if(var.substring(i, i+2).equals(" =")){
				equal_sign = i+3;
			}
			if(var.substring(i+1).equals(";")){
				semiColon= i+1;
			}
		}
		String doom;
		if (semiColon == 0){
			doom = var.substring(equal_sign);
		}
		else{
		doom = var.substring(equal_sign,semiColon);
		}
		if (doom.equals("")){
			return "0";
		}
		return doom;
	}
	public void createVar(String storageFile,String varName){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(storageFile,true))){
			bw.write("var "+ varName + " = " + "0;");
			bw.newLine();
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void setVar(String storageFile,String varName,String value){
		if (getStringValue(storageFile,varName).equals(value)){
			System.out.println("No change made to " + varName+".");
		}
		else{
			try (BufferedReader br = new BufferedReader(new FileReader(storageFile))){
				String bw = "";
				boolean setComplete = false;
				String letters;
				while (!((letters = br.readLine()) == null)){
					if (letters.length()>=6+varName.length()){
					if ((letters.substring(0,(6 + varName.length()))).equals("var "+ varName+ " =")){
						bw += ("var "+varName + " = " + value + ";");
						bw += System.lineSeparator();
						System.out.println("Succesfully changed the value of " + varName + " to " + value+".");
						setComplete = true;
					}
					else {
						bw += letters;
						bw += System.lineSeparator();
					}
					}
					else {
						bw += letters;
						bw += System.lineSeparator();
					}
				}
				try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(storageFile))){
					buffWriter.write(bw);
				}
				catch (IOException e){
					System.out.println(e.getMessage());
				}
				if (!setComplete){
					createVar(storageFile,varName);
					bw = "";
					//to prevent infinite loops, the above has been repeated rather than call setVar again
						try (BufferedReader br1 = new BufferedReader(new FileReader(storageFile))){
					while (!((letters = br1.readLine()) == null)){
						if (letters.length()>= 6 + varName.length()) {
						if ((letters.substring(0,(6 + varName.length()))).equals("var "+ varName+ " =")){
							bw+=("var "+varName + " = " + value + ";");
							bw+= System.lineSeparator();
							System.out.println("Succesfully changed the value of " + varName + " to " + value+".");
							setComplete = true;
						}
						else {
							bw += (letters);
							bw += System.lineSeparator();
						}
					}
					else {
						bw += (letters);
						bw += System.lineSeparator();
					}
					}
					try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(storageFile))){
						buffWriter.write(bw);
					}
					catch (IOException e){
						System.out.println(e.getMessage());
					}
					
						}
						catch (IOException e){
							System.out.println(e.getMessage());
						}
					}
				}
			catch (IOException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	//utility
	public double RandomRange(double value1,double value2){
		if (value1 >= value2){
		double difference = value1 - value2;
		double doom = Math.random() * difference;
		return (doom+value2);
		}
		double difference = value2 - value1;
		double doom = Math.random() * difference;
		return (doom+value1);
	}
	public int addIntArray (int [] intArray){
		int sum = 0;
		for (int i = 0; i < intArray.length;i++){
			sum += intArray[i];
		}
		return sum;
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
	public int [] randomUnique(int count, int max){
		int [] list = new int[max];
		for (int i = 0; i < max; i++){
			list[i] = i;
		}
		shuffleArray(list);
		int [] doom = new int[count];
		for (int i = 0; i<count;i++){
			doom[i] = list[i];
		}
		return doom;
	}
	public void shuffleArray(int[] ar){
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
	public String [] arrayMerge(String [] array1, String [] array2){
		int emerge = 0;
		for (int i = 0; i < array1.length;i++){
			if (array1[i]==null&&array2.length>emerge){
				array1[i]=(array2[emerge]);
				emerge++;
			}
		}
		return array1;
	}
	public Image makeImage(String hi){
		Image hello = (new ImageIcon(hi)).getImage();
		return hello;
	}
	public int generateCMC(String castingCost){
		String numberCost = castingCost.replaceAll("[^\\d.^-]", "");
		castingCost = castingCost.replaceAll("[\\dX]", "");
		if (numberCost.length()>0){
		return Integer.parseInt(numberCost) + castingCost.length();
		}
		else {
			return castingCost.length();
		}
	}
	public int [] quadrantValues(String set, String cardName){
		try (BufferedReader readCard = new BufferedReader(new FileReader("Packs/"+set+"/DraftData.txt"))){
			String name;
			//int [] cardValues = new int[5];//Early, Stalemate, Winning, Loosing, Synergy (only with keyword)
			while (!((name= readCard.readLine())==null)){
				/*cost*/ readCard.readLine();
				/*cardType*/ readCard.readLine();
				String extractValue = readCard.readLine();
				/*keyWord*/ readCard.readLine();
				if (name.equals(cardName)){ //lazily done the long way
					int [] cardValues = {Integer.parseInt(extractValue.substring(8,9)),Integer.parseInt(extractValue.substring(10,11)),Integer.parseInt(extractValue.substring(12,13)),Integer.parseInt(extractValue.substring(14,15))};
					return cardValues;
				}
				/*playStyle*/readCard.readLine();
				/*skip line*/readCard.readLine();
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		int [] cardValues = {0,0,0,0};
		return cardValues;
	}
	public String [] getCardStats(String cardName){
		if (!(cardName==null)){
		try (BufferedReader rC = new BufferedReader(new FileReader("DraftData/"+cardName.substring(0,1).toUpperCase()+".txt"))){
			String name;
			//int [] cardValues = new int[5];//Early, Stalemate, Winning, Loosing
			while (!((name= rC.readLine())==null)){
				if(name.equals(cardName)){
					String one11 = rC.readLine();
					String two11 = rC.readLine();
					String three11 = rC.readLine();
					rC.readLine();
					String theOne = rC.readLine();
					String [] doom = {name,one11,two11.substring(0, two11.length()-4),three11.substring(8),theOne.substring(8) };
					return doom;
				}
				rC.readLine();rC.readLine();rC.readLine();rC.readLine();rC.readLine();rC.readLine();
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
		System.out.println("Error: data not located with card: " + cardName);
		String [] nope = {cardName,"","","",""};
		return nope;
	}
	public String [] generateCards(String set, String rarity, int count){
		String [] doom = new String[count];
		try (BufferedReader br = new BufferedReader(new FileReader("Packs/"+set+"/"+rarity+".txt"))){
			int total =  Integer.parseInt(br.readLine());
			if (count>= total){
				for (int i = 0; i<total;i++){
					doom[i] = br.readLine();
				}
			}
			else {
				int [] notdoom = new int[count];
				notdoom = randomUnique(count,total);
				for (int i = 0; i < total; i++){
					String current = br.readLine();
					for (int noti = 0; noti < doom.length;noti++){
						if (notdoom[noti]==i){
							doom[noti]=current;
						}
					}
				}
			}
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		return doom;
	}
	public String [] generatePack(String set){
		String [] cards = new String[15];
		if (set.equals("U17")){
			boolean special = (Math.random()<=0.1);
			boolean mythic = (Math.random()<=0.125);
			//Commons
			if (special){
				String [] commons = generateCards(set,"Common",10);
				arrayMerge(cards,commons);
			}
			else{
				String [] commons = generateCards(set,"Common",11);
				arrayMerge(cards,commons);
			}
			String [] uncommons = generateCards(set,"Uncommon",3);
			arrayMerge(cards,uncommons);
			if (mythic){
				String [] rare = generateCards(set,"Mythic",1);
				arrayMerge(cards,rare);
			}
			else {
				String [] rare = generateCards(set,"Rare",1);
				arrayMerge(cards,rare);
			}
			if (special){
				String [] special1 = generateCards(set,"Special",1);
				arrayMerge(cards,special1);
			}
			return cards;
		}
		if (set.equals("NOIR")){
			boolean mythic = (Math.random()<=0.125);
			//Commons
				String [] commons = generateCards(set,"Common",11);
				arrayMerge(cards,commons);
			String [] uncommons = generateCards(set,"Uncommon",3);
			arrayMerge(cards,uncommons);
			if (mythic){
				String [] rare = generateCards(set,"Mythic",1);
				arrayMerge(cards,rare);
			}
			else {
				String [] rare = generateCards(set,"Rare",1);
				arrayMerge(cards,rare);
			}
			return cards;
		}
		else{
			return cards;
		}
	}
	public String [] generatePack(String set,int [] hi){
			String [] cards = new String[15];
			//Commons
			try (BufferedReader br = new BufferedReader(new FileReader("Packs/"+set+"/Cube1.txt"))){
				br.readLine();
				for (int i = 0;i < 360;i++){
					String nextCard= br.readLine();
					for (int m = 0;m < 15;m++){
						if (i == hi[m]){
							cards[m]=nextCard;
						}
					}
				}
			}
			catch (IOException e){
				System.out.println(e.getMessage());
			}
			return cards;
		}
	
	
	/*public Pack [] generateCube(String name, int [] hi){
		Pack [] doom = new Pack[24];
		int count = 0;
		for (int i = 0;i < 24;i++){
			int [] called = new int[15];
			for (int m = 0;m < 15;m++){
				called[m] = hi[count++];
			}
			//System.out.println(Arrays.toString(called));
			doom[i] = new Pack("U17",called);
		}
		return doom;
	}*/
	
	
	public String imageFilter(String cardName){
		cardName = cardName.replaceAll(",","");
		cardName = cardName.replaceAll("#","");
		cardName = cardName.replaceAll("’","");/*
		if (testString(cardName,",")||testString(cardName,"#")){
			return "cardImages/"+cardName+".jpg";
		}
		else{*/
		//System.out.println(cardName);
			return "cardImages/"+cardName+".full.jpg";
		//}
	}
	public String mainClassFunction(int x, int y,String screen,int width, int height,int currentPack){
		if (x>=1100&&x<=1220){
			if (y>=height-60&&y<=height){
				return "Menu";
			}
		}
		if (screen.equals("Main")){
			if (x>=width*2/3&&x<=width*2/3+100){
				if (y>=height*1/3&&y<=height*1/3+50){
					return "MainButtonHit";
				}
				if (y>=height*1/3+100&&y<=height*1/3+150){
					return "DraftButton";
				}
				if (y>=height*1/3-100&&y<=height*1/3-50){
					return "toCollection";
				}
				if (y>=height*1/3+200&&y<=height*1/3+250){
					return "SealedButton";
				}
				/*if (y>=height*1/3+300&&y<=height*1/3+350){
					return "CubeButton";
				}*/
			}
			if (x>=width-100&&x<=width){
				if (y>=height-50&&y<=height){
					return "DataResetButton";
				}
			}
		}
		if (screen.equals("Pick Pack Open")||screen.equals("Pick Draft")||screen.equals("Pick Collection")||
				screen.equals("Pick Sealed")){
			return "PickedPack"+Integer.toString(getNumLocal(x,y));
		}
		if (screen.equals("Pick Cube")){
			if (x>=50&&x<=150){
				if (y>=50&&y<=100){
					return "Cube1";
				}
			}
		}
		if (screen.equals("Pack")){
			if (x>=1000&&x<=1140){
				if (y>=400&&y<=450){
					return "PickedPack"+currentPack;
				}
				else {
					//for (int i = 0; i < 45; i+=)
				}
			}
			/*if (x>=20&&x<=200&&y>=20&&y<=270){
				return "Pick1";
			}
			else if (x>=200&&x<=380&&y>=20&&y<=270){
				return "Pick2";
			}
			else if (x>=380&&x<=560&&y>=20&&y<=270){
				return "Pick3";
			}
			else if (x>=560&&x<=740&&y>=20&&y<=270){
				return "Pick4";
			}
			else if (x>=740&&x<=920&&y>=20&&y<=270){
				return "Pick5";
			}
			else if (x>=20&&x<=200&&y>=270&&y<=520){
				return "Pick6";
			}
			else if (x>=200&&x<=380&&y>=270&&y<=520){
				return "Pick7";
			}
			else if (x>=380&&x<=560&&y>=270&&y<=520){
				return "Pick8";
			}
			else if (x>=560&&x<=740&&y>=270&&y<=520){
				return "Pick9";
			}
			else if (x>=740&&x<=920&&y>=270&&y<=520){
				return "Pick10";
			}
			else if (x>=20&&x<=200&&y>=520&&y<=770){
				return "Pick11";
			}
			else if (x>=200&&x<=380&&y>=520&&y<=770){
				return "Pick12";
			}
			else if (x>=380&&x<=560&&y>=520&&y<=770){
				return "Pick13";
			}
			else if (x>=560&&x<=740&&y>=520&&y<=770){
				return "Pick14";
			}
			else if (x>=740&&x<=920&&y>=520&&y<=770){
				return "Pick15";
			}*/
			int hold = getclicknum(x,y);
			if (hold>-1) {
				return "Pick"+hold;
			}
		}
		//if (screen.equals("Pack")){
			
		//}
		if (screen.equals("Draft")){
			/*if (x>=20&&x<=200&&y>=20&&y<=270){
				return "Pick1";
			}
			else if (x>=200&&x<=380&&y>=20&&y<=270){
				return "Pick2";
			}
			else if (x>=380&&x<=560&&y>=20&&y<=270){
				return "Pick3";
			}
			else if (x>=560&&x<=740&&y>=20&&y<=270){
				return "Pick4";
			}
			else if (x>=740&&x<=920&&y>=20&&y<=270){
				return "Pick5";
			}
			else if (x>=20&&x<=200&&y>=270&&y<=520){
				return "Pick6";
			}
			else if (x>=200&&x<=380&&y>=270&&y<=520){
				return "Pick7";
			}
			else if (x>=380&&x<=560&&y>=270&&y<=520){
				return "Pick8";
			}
			else if (x>=560&&x<=740&&y>=270&&y<=520){
				return "Pick9";
			}
			else if (x>=740&&x<=920&&y>=270&&y<=520){
				return "Pick10";
			}
			else if (x>=20&&x<=200&&y>=520&&y<=770){
				return "Pick11";
			}
			else if (x>=200&&x<=380&&y>=520&&y<=770){
				return "Pick12";
			}
			else if (x>=380&&x<=560&&y>=520&&y<=770){
				return "Pick13";
			}
			else if (x>=560&&x<=740&&y>=520&&y<=770){
				return "Pick14";
			}
			else if (x>=740&&x<=920&&y>=520&&y<=770){
				return "Pick15";
			}
			else {
				//for (int i = 0; i < 45; i+=)
			}*/
			int hold = getclicknum(x,y);
			if (hold>-1) {
				return "Pick"+hold;
			}
		}
		if (screen.equals("Collection")){
			if (y<=height/2+40&&y>=height/2-40){
				if (x>=width-200&&x<=width-120){
					return "PageLeft";
				}
				if (x>width-100&&x<=width-20){
					return "PageRight";
				}
			}
			if (y<=height/3+40&&y>=height/3-40){
				if (x>=width-200&&x<=width-120){
					return "EditLeft";
				}
				if (x>width-100&&x<=width-20){
					return "EditRight";
				}
			}
			/*int imageHeight = (int)(523/1.5);
			int imageWidth = (int)(375/1.5);
			for (int i = 1;i <= 8;i++){
				if (x>=20+(imageWidth*((i-1)%4))&&x<=20+imageWidth+(imageWidth*((i-1)%4))){
					if (y>=(int)(20+(imageHeight+30)*Math.floor((i-1)/4))&&y<=(int)(20+imageHeight+(imageHeight+30)*Math.floor((i-1)/4))){
						return "Collection"+i;
					}
				}
			}*/
			int hold = getclicknum(x,y);
			if (hold>-1) {
				return "Collection"+hold;
			}
		}
		return (x+","+y);
	}
	public String [] openPack(String set){
		String [] packInQuestion = generatePack(set);
		for (int i = 0; i < packInQuestion.length;i++ ){
			addVarInt("Packs/"+set+"/Opened"+ set +".txt",packInQuestion[i],1);
		}
		addVarInt("Packs/"+set+"/Opened"+set+".txt","Packs",1);
		return packInQuestion;
	}
	public int pullColor(String cost, String color){
	if (color.equals("R")){
		cost = cost.replaceAll("[^R]", "");
	}
	else if (color.equals("U")){
		cost = cost.replaceAll("[^U]", "");
	}
	else if (color.equals("G")){
		cost = cost.replaceAll("[^G]", "");
	}
	else if (color.equals("B")){
		cost = cost.replaceAll("[^B]", "");
	}
	else if (color.equals("W")){
		cost = cost.replaceAll("[^W]", "");
	}
		return cost.length();
	}
	public void writedecklist(Card [] maincards,Card [] sidecards, int player){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("player"+player+"picks.cod"))){
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.write(System.lineSeparator());
			bw.write("<cockatrice_deck version=\"1\">");
			bw.write(System.lineSeparator());
			bw.write("<deckname>PlayerDraft"+player+"</deckname>");
			bw.write(System.lineSeparator());
			bw.write("<comments></comments>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"main\">");
			bw.write(System.lineSeparator());
			for (int i = 0;i <maincards.length;i++){
				if (maincards[i].testBlank()){}
				else{
				bw.write("<card number=\"1\" name=\""+maincards[i].getName()+"\"/>");
				bw.write(System.lineSeparator());
				}
			}
			bw.write("</zone>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"side\">");
			bw.write(System.lineSeparator());
			for (int i = 0;i <sidecards.length;i++){
				if (sidecards[i].isError()||sidecards[i].testBlank()){}
				else{
				bw.write("<card number=\"1\" name=\""+sidecards[i].getName()+"\"/>");
				bw.write(System.lineSeparator());
				}
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
	public void writestringdecklist(String [] cards, int player){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("player"+player+"picks.cod"))){
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.write(System.lineSeparator());
			bw.write("<cockatrice_deck version=\"1\">");
			bw.write(System.lineSeparator());
			bw.write("<deckname>PlayerDraft"+player+"</deckname>");
			bw.write(System.lineSeparator());
			bw.write("<comments></comments>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"side\">");
			bw.write(System.lineSeparator());
			for (int i = 0;i <cards.length;i++){
				bw.write("<card number=\"1\" name=\""+cards[i]+"\"/>");
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
	public int getNumLocal(int x, int y){
		int hit = -1;
		//cellar.fillRect(30+100*(i%8),60+80*(i/8),90,50);
		for (int i = 0; i < 64;i++) {
			int tx = 30+100*(i%8), ty = 60+80*(i/8);
			if (x>=tx&&x<=tx+90&&y>=ty&&y<=ty+50){hit = i;}
		}
		return hit;
	}
	public void clear() {
		click.clear();
	}
	public void drawClickables(Graphics g) {
		for (ClickableCard drawing:click) {
			drawing.draw(g);
		}
	}
	public int getclicknum(int x, int y) {
		for (int i = 0;i < click.size();i++) {
			if (click.get(i).Check(x, y)) {
				return i;
			}
		}
		return -1;
	}
	public void fulladdclick(Image image,int x,int y, int x1,int y1) {
		//click.add(new ClickableCard(image, x, y, x1, y1));
	}
	public void clickablereport() {
		System.out.println("Clickable Report:");
		System.out.println(click.size()+" clickables");
		int doom = 0;
		for (ClickableCard card:click) {
			System.out.println("Clickable: "+(doom++)+" with location "+card.x+" "+card.y+".");
		}
	}
}
