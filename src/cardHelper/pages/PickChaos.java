package cardHelper.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.Settings;
import cardHelper.card.Card;
import cardHelper.set.Set;
import cardHelper.toolBox.Clickable;
import cardHelper.toolBox.ClickableButton;
import cardHelper.toolBox.ClickableTextBox;

public class PickChaos extends Page {
	private boolean [] colorSelected = new boolean[5];
	private Set setChosen;
	private String lastMadeDeck;
	private ClickableTextBox textBox;
	public PickChaos(MainClass main) {
		super(main, Pages.pickChaos);
		for (int i = 0; i < main.setList.size();i++) {
			click.add(new ClickableButton(main.setList.get(i).getName(), 30+100*(i%8), 60+80*(i/8), 90, 50, new Font("Arial",Font.PLAIN,20), Color.decode("#00F0EC")));
		//cellar.fillRect(150,50,90,50);
		}
		textBox = new ClickableTextBox("Archetype Box", 700, 825, 500, 30, new Font("Arial",Font.PLAIN,20), Color.BLACK);
		click.add(textBox);
		for (int i = 0; i < 5; i++)
		{
			colorSelected[i] = false;
		}
		if (!main.setList.isEmpty()) {
			setChosen = main.setList.get(0);
		}
		else {
			setChosen = null;
		}
		
		//click.add(new ClickableButton("Clear",30+100*(6),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		click.add(new ClickableButton("Generate",30+60*(11),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		
		click.add(new ClickableButton("White",30+60*(0),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		click.add(new ClickableButton("Blue",30+60*(2),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		click.add(new ClickableButton("Black",30+60*(4),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		click.add(new ClickableButton("Red",30+60*(6),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		click.add(new ClickableButton("Green",30+60*(8),60+80*(8),90,50,new Font("Arial",Font.PLAIN,20),Color.decode("#00F0EC")));
		lastMadeDeck = "No deck has been generated yet.";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		// Draws the "Selected" if the color is selected
		g.setColor(Color.BLACK);
		for (int i = 0; i < 5; i++)
		{
			if (colorSelected[i])
			{
				g.drawString("Selected", 30+60*(i*2), 660);
			}
		}
		if (setChosen != null) {
			g.drawString(setChosen.getName(), 30+60*(11), 660);
			g.drawString(String.format("Cards found of the archetype: %d", setChosen.validArchetypeCount(textBox.getText())), 700, 875);
			if (textBox.getText().equals("archetypes"))
			{
				g.drawString("Archetypes include:", 1300, 700);
				ArrayList<String> archetypeList = setChosen.getArchetypes();
				int i = 720;
				for (String hoi: archetypeList)
				{
					g.drawString(hoi, 1300, i);
					i+=20;
				}
			}
		}
		else {
			g.drawString("No Sets Available", 30+60*11, 660);
		}
		g.drawString(lastMadeDeck, 90, 800);
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		// TODO Auto-generated method stub
		String action="none";
		for (Clickable ping:click) {
			if (ping.Check(x, y)) {
				action=ping.word;
			}
		}
		System.out.println(action);
		if (!action.equals("none")) {
			switch (action) {
			case "Generate":
				if (anySelected())
				{
					if (setChosen != null) {
						generateChaosDeck();
					}
				}
				break;
			case "White":
				colorSelected[0] = !colorSelected[0];
				break;
			case "Blue":
				colorSelected[1] = !colorSelected[1];
				break;
			case "Black":
				colorSelected[2] = !colorSelected[2];
				break;
			case "Red":
				colorSelected[3] = !colorSelected[3];
				break;
			case "Green":
				colorSelected[4] = !colorSelected[4];
				break;
			case "Archetype Box":
				textBox.select();
				break;
			default:
				System.out.println(setChosen.getName());
				int check = main.getidSet(action);
				System.out.println(check);
				if (check>=0) {
					setChosen = main.setList.get(check);
				}
			}
		}
		main.repaint();
	}
	
	@Override
	public void onType(char c)
	{
		textBox.typed(c);
	}
	private boolean anySelected()
	{
		return colorSelected[0] || colorSelected[1] || colorSelected[2] || colorSelected[3] || colorSelected[4];
	}
	private void generateChaosDeck()
	{
		ArrayList<Card> deck = new ArrayList<Card>(23);
		ArrayList<Card> lands = new ArrayList<Card>(17);
		//three rares, 2 have archetype
		deck.add(setChosen.pullChaosCard(2, colorSelected, true, 15, textBox.getText()));
		for (int i = 0; i < 2; i++)
		{
			String archetype = (i < 1) ? textBox.getText() : null;
			Card input = setChosen.pullChaosCard(2, colorSelected, false, 12, archetype);
			if (input.testType("Land"))
			{
				lands.add(input);
			}
			else
			{
				deck.add(input);
			}
		}
		//seven uncommons, 4 have archetype
		for (int i = 0; i < 7; i++)
		{
			String archetype = (i < 4) ? textBox.getText() : null;
			Card input = setChosen.pullChaosCard(1, colorSelected, false, 0, archetype);
			if (input.testType("Land"))
			{
				lands.add(input);
			}
			else
			{
				deck.add(input);
			}
		}
		//thirteen commons, 8 have archetype
		

		for (int i = 0; i < 13; i++)
		{
			String archetype = (i < 8) ? textBox.getText() : null;
			Card input = setChosen.pullChaosCard(0, colorSelected, false, 0, archetype);
			if (input.testType("Land"))
			{
				lands.add(input);
			}
			else
			{
				deck.add(input);
			}
		}
		//fill rest with commons
		while (deck.size() < 23)
		{
			deck.add(setChosen.pullChaosCard(0, colorSelected, true, 0, null));
		}
				
				Card [] landCheck = new Card[5];
				landCheck[0] = new Card("Plains","0","Basic land - Plains",new String[0],new String[0],false);
				landCheck[1] = new Card("Island","0","Basic land - Island",new String[0],new String[0],false);
				landCheck[2] = new Card("Swamp","0","Basic land - Swamp",new String[0],new String[0],false);
				landCheck[3] = new Card("Mountain","0","Basic land - Mountain",new String[0],new String[0],false);
				landCheck[4] = new Card("Forest","0","Basic land - Forest",new String[0],new String[0],false);
				int [] landNeed={0,0,0,0,0};
				
				for (Card hoi: deck)
				{
					landNeed[0]+=hoi.getWhite();
					landNeed[1]+=hoi.getBlue();
					landNeed[2]+=hoi.getBlack();
					landNeed[3]+=hoi.getRed();
					landNeed[4]+=hoi.getGreen();
				}
				
				
				int countCheck=17;
				int [] landsHad= {0,0,0,0,0};
				
				for (Card yep:lands) {
					if (yep.getWhite()>0) {landsHad[0]++;}
					if (yep.getBlue()>0) {landsHad[1]++;}
					if (yep.getBlack()>0) {landsHad[2]++;}
					if (yep.getRed()>0) {landsHad[3]++;}
					if (yep.getGreen()>0) {landsHad[4]++;}
				}
				
				int landTotal=landsHad[0]+landsHad[1]+landsHad[2]+landsHad[3]+landsHad[4];
				
				for (int i = 0; i < 5; i++) {
					if (colorSelected[i]) {
						landTotal++;
						landsHad[i]++;
						lands.add(landCheck[i]);
					}
				}
				
				do {
					int doom = 0; double check=1000;
					for (int i = 0; i < 5; i++) {
						if (colorSelected[i]) {
						if (((double)landsHad[i]/(double)landTotal)/((double)landNeed[i]/(double)landTotal)<check) {
							doom = i;check = ((double)landsHad[i]/(double)landTotal)/((double)landNeed[i]/(double)landTotal);
						}
						}
					}
					landTotal++;
					landsHad[doom]++;
					lands.add(landCheck[doom]);
				} while (lands.size()<17);
				
				double value = 0;
				for (Card yep:deck) {
					value+=yep.getqt();
				}
				//System.out.println("Sideboard:");
				value = Math.round(value/2.3)/10.0;
				deck.addAll(lands);
				writedecklist(deck,value);
				System.out.println(deckName());
	}
	public void writedecklist(ArrayList<Card> mainDeck, double value){
		//System.out.println("player"+player+": "+value+" "+deckName()+".cod");s
		(new File(MainClass.programDataFolder()+"ChaoticDecks/"+setChosen.getName())).mkdirs();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"ChaoticDecks/"+setChosen.getName()+"/"+" #"+
				Settings.settingValue("chaosDeckId", "0")+" "+deckName()+ "("+value+").cod"))){
			lastMadeDeck = "Deck last made: #"+Settings.settingValue("chaosDeckId", "0")+" "+deckName()+ "("+value+").cod";
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.write(System.lineSeparator());
			bw.write("<cockatrice_deck version=\"1\">");
			bw.write(System.lineSeparator());
			bw.write("<deckname>"+deckName()+" Draft: #"+Settings.settingValue("chaosDeckId", "0")+"</deckname>");
			bw.write(System.lineSeparator());
			bw.write("<comments></comments>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"main\">");
			bw.write(System.lineSeparator());
			for (int i = 0;i <mainDeck.size();i++){
				bw.write("<card number=\"1\" name=\""+mainDeck.get(i).getName()+"\"/>");
				bw.write(System.lineSeparator());
			}
			bw.write("</zone>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"side\">");
			bw.write(System.lineSeparator());
			bw.write("</zone>");
			bw.write(System.lineSeparator());
			bw.write("</cockatrice_deck>");
			bw.write(System.lineSeparator());
			Settings.setSettingValue("chaosDeckId", Integer.parseInt(Settings.settingValue("chaosDeckId", "0"))+1);
			System.out.println("Completed deck "+deckName());
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	public String deckName(){
		String doom=" ";
		if (colorSelected[0]) {
			doom+="W";
		}
		if (colorSelected[1]) {
			doom+="U";
		}
		if (colorSelected[2]) {
			doom+="B";
		}
		if (colorSelected[3]) {
			doom+="R";
		}
		if (colorSelected[4]) {
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
}
