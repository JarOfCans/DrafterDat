package cardHelper.pack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cardHelper.MainClass;
import cardHelper.card.Card;
import cardHelper.toolBox.ToolBox;

public class PackCallings {
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
		try (BufferedReader readCard = new BufferedReader(new FileReader(MainClass.programDataFolder()+"Packs/"+set+"/DraftData.txt"))){
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
	public String [] getCardStats(String set, String cardName){
		if (!(cardName==null)){
		try (BufferedReader rC = new BufferedReader(new FileReader(MainClass.programDataFolder()+"DraftData/"+cardName.substring(0,1).toUpperCase()+".txt"))){
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
		try (BufferedReader br = new BufferedReader(new FileReader(MainClass.programDataFolder()+"Packs/"+set+"/"+rarity+".txt"))){
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
			try (BufferedReader br = new BufferedReader(new FileReader(MainClass.programDataFolder()+"Packs/"+set+"/Cube1.txt"))){
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
	public Pack [] generateCube(String name, int [] hi){
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
	}
	public String [] generateSealed(String set1,String set2, String set3,String set4, String set5,String set6){
		String [] doom = new String[90];
		String [][] thePacks = {generatePack(set1),generatePack(set2),generatePack(set3),generatePack(set4),generatePack(set5),generatePack(set6)};
		for (int i = 0; i < 6;i++){
			for (int m = 0;m < 15;m++){
				doom[i*15+m] = thePacks[i][m];
			}
		}
		return doom;
	}
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
	public String mainClassFunction(int x, int y,String screen,int width, int height,String currentPack){
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
				if (y>=height*1/3+300&&y<=height*1/3+350){
					return "CubeButton";
				}
			}
			if (x>=width-100&&x<=width){
				if (y>=height-50&&y<=height){
					return "DataResetButton";
				}
			}
		}
		if (screen.equals("Pick Pack Open")){
			if (x>=50&&x<=140){
				if (y>=50&&y<=100){
					return "OpenU17";
				}
			}
			if (x>=150&&x<=240){
				if (y>=50&&y<=100){
					return "OpenNOIR";
				}
			}
		}
		if (screen.equals("Pick Draft")){
			if (x>=50&&x<=140){
				if (y>=50&&y<=100){
					return "DraftU17";
				}
			}
			if (x>=150&&x<=240){
				if (y>=50&&y<=100){
					return "DraftNOIR";
				}
			}
		}
		if (screen.equals("Pick Collection")){
			if (x>=50&&x<=140){
				if (y>=50&&y<=100){
					return "ViewU17";
				}
			}
			if (x>=150&&x<=240){
				if (y>=50&&y<=100){
					return "ViewNOIR";
				}
			}
		}
		if (screen.equals("Pick Sealed")){
			if (x>=50&&x<=140){
				if (y>=50&&y<=100){
					return "SealedU17";
				}
			}
			if (x>=150&&x<=240){
				if (y>=50&&y<=100){
					return "SealedNOIR";
				}
			}
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
					return "Open"+currentPack;
				}
			}
		}
		//if (screen.equals("Pack")){
			if (x>=1100&&x<=1220){
				if (y>=height-60&&y<=height){
					return "Menu";
				}
			}
		//}
		if (screen.equals("Draft")){
			if (x>=20&&x<=200&&y>=20&&y<=270){
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
			}
		}
		if (screen.equals("Collection")){
			if (y<=height/2+40||y>=height/2-40){
				if (x>=width-200&&x<=width-120){
					return "PageLeft";
				}
				if (x>width-100&&x<=width-20){
					return "PageRight";
				}
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
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"player"+player+"picks.cod"))){
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
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"player"+player+"picks.cod"))){
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
}
