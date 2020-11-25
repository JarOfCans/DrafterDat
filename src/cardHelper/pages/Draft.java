package cardHelper.pages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

import cardHelper.MainClass;
import cardHelper.Settings;
import cardHelper.MainClass.Pages;
import cardHelper.card.Card;
import cardHelper.pages.draft.*;
import cardHelper.player.Player;
import cardHelper.table.ClassicDraftTable;
import cardHelper.toolBox.Clickable;

public class Draft extends Page {
	/**
	 * Table object of the current Draft.
	 */
	private ClassicDraftTable table;
	//private boolean newCards=true;
	private ArrayList<Integer> intnumbers;
	public Draft(MainClass main) {
		super(main, Pages.draft);
	}

	@Override
	public void draw(Graphics cellar) {
		//System.out.println(MainClass.screenWidth);
		if (table!=null) {
		if (table.waitplayer==true) {
				Player player = table.players.get(table.wait);
				//Pack checkPack = player[0].getPack();
				
				//Draws pack cards, aborts the deck lists if there is an error if the draft is deep enough
				if (table.getNewPack()) {//If the cards are new, gets new clickables of the potential cards
					click.clear();
					if (Settings.settingValue("packsSeeable", "True").equals("True"))
					{
						table.waitPack().setBeenSeen(true);
					}
					for (int i = 0;i<20;i++) {
						if (table.waitPack().numOfCards()>i) {
							String hoi=imageFilter(table.waitPack().getCard(i).getName(),".full.jpg");
							try
							{
								click.add(new DFCard(i, makeImage((hoi))));
							}
							catch (NullPointerException npe)
							{
								String output = "";
								if (player.cardsPicked.size() >= 10)
								{
									output += "Emergency Printing Cards. " + System.lineSeparator();
									for (Player yep: table.players)
									{
										int r = -1;
										if (yep.isPlayer)
										{
											yep.constructDeck(r);
										}
										r--;
									}
								}
								
								output += "Error ocured with card " + table.waitPack().getCard(i).getName();
								MainClass.sendMainWithError(output);
								break;
							}
						}
					}
					
					//Print cards picked on side.
					for (int i = 0; i < player.cardsPicked.size(); i++)
					{
						click.add(new DFHoverCards(player.cardsPicked.get(i), i));
					}
				}
				/*for (Clickable clack:click) {
					clack.draw(cellar);
				}*/
			
			
			cellar.setFont(MainClass.BASICFONT);
			cellar.setColor(Color.decode("#000000"));
			int x = -1;
			if (MainClass.screenWidth > 1580) {
				x = 930;
			}
			else if (MainClass.screenWidth < 1420 && MainClass.screenWidth > 1230) {
				x = DFCard.MODIFY_X_SIZE*4 + 30;
			}
			if (x > 0) {
			for (int i = 1; i <=6;i++){
				if (i <= 5){
					cellar.drawString(Integer.toString(i), x, (i)*60+8);
				}
				else{
					cellar.drawString("6+", x, 360);
				}
				cellar.setColor(Color.decode("#00D60E"));
				if (player.greatestCMC()>0){
					cellar.fillRect(x+40, (i)*60-30,table.players.get(table.wait).getCMC(i)*120/(table.players.get(table.wait).greatestCMC()), 60);
				}
				cellar.setColor(Color.decode("#000000"));
				cellar.drawString(Integer.toString(table.players.get(table.wait).getCMC(i)),x + 70,(i)*60+8);
			}
			}
			
			
			cellar.drawString(Integer.toString(table.players.get(table.wait).chooseCard()+1),MainClass.screenWidth-475,420);
			int m = 0;
			for (int i = 0; i <table.waitPack().numOfCards();i++,m++){
				Card cardHolder = player.myPack().getCard(i);
				cellar.drawString((m+1) +": "+(int)table.players.get(table.wait).getScore(m,true)+"("+cardHolder.winrateString()+", "+cardHolder.getqt()+")",MainClass.screenWidth-445,i*40+40);
			}
			m = 0;
			cellar.setColor(Color.BLACK);
			/*cellar.drawString("W= " + table.players.get(table.wait).White, MainClass.screenWidth - 200, 600);
			cellar.drawString("U= " + table.players.get(table.wait).Blue, MainClass.screenWidth - 200, 620);
			cellar.drawString("B= " + table.players.get(table.wait).Black, MainClass.screenWidth - 200, 640);
			cellar.drawString("R= " + table.players.get(table.wait).Red, MainClass.screenWidth - 200, 660);
			cellar.drawString("G= " + table.players.get(table.wait).Green, MainClass.screenWidth - 200, 680);*/
			//cellar.drawString("Player= "+table.wait,1000,700);
		//TODO make setting to turn off/on
		if (table.waitPack().pickedNames.size()>0) {
			if (table.getNewPack()) {
			numset(table.waitPack().pickedNames.size());
			}
			//Seen Card names
			int base = 0;
			if (MainClass.screenWidth > DFCard.MODIFY_SIZE) {
			for (int i = table.waitPack().numOfCards(); i < table.waitPack().numOfCards()+table.waitPack().pickedNames.size();i++) {
				cellar.drawString(table.waitPack().pickedNames.
						get(intnumbers.get(base++)),
						25+180*((table.waitPack().numOfCards()+base-1)%5),
						40+50*(table.waitPack().numOfCards()+base-1));
			}
			} else {
				cellar.setFont(MainClass.SMOLFONT);
				for (int i = table.waitPack().numOfCards(); i < table.waitPack().numOfCards()+table.waitPack().pickedNames.size();i++) {
					cellar.drawString(table.waitPack().pickedNames.
							get(intnumbers.get(base++)),
							15+DFCard.MODIFY_X_SIZE*((table.waitPack().numOfCards()+base-1)%4),
							40+(DFCard.MODIFY_Y_SIZE/4)*(table.waitPack().numOfCards()+base-1));
				}
				cellar.setFont(MainClass.BASICFONT);
			}
		}
		for (int i = 0; i < table.players.size(); i++)
		{
			Player thePlayer = table.players.get(i);
			if (i == table.wait)
			{
				cellar.setColor(Color.decode("#C739C0"));
			}
			else {
				cellar.setColor(Color.BLACK);
			}
			if (thePlayer.isPlayer)
			{
				cellar.drawString(String.format("P%d = ", i), MainClass.screenWidth - 465, 640 + (i*20));
				thePlayer.drawColorBar(MainClass.screenWidth - 417, 624 + (i*20), 100, 18, cellar);
			}
			else
			{
				cellar.drawString(String.format("P%d = AI", i), MainClass.screenWidth - 455, 640 + (i*20));
			}
		}
		table.setNewPack(false);
		}
	}
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		try {
			Player player = table.players.get(table.wait);
			String action="none";
			for (Clickable ping:click) {
				if (ping.Check(x, y)) {
					action= ping.word;
				}
			}
			if (!action.equals("none")&&table.waitplayer) {
				for (int i = 0; i < table.waitPack().numOfCards();i++) {
					if (action.equals(Integer.toString(i))) {
						//TODO have a select before pick feature
						player.pickCard(i);
					}
				}
			}
			main.repaint();
		}
		catch (NullPointerException e)
		{
			if (table == null) {
				MainClass.sendMainWithError("A null table was created.");
			}
		}
	}
	/**
	 * Sets the table of the current Draft session. null will result in no change.
	 * @param tableIn New table.
	 */
	public void setTable(ClassicDraftTable tableIn) {
		if (tableIn != null) {
			table = tableIn;
		}
		else {
			MainClass.sendMainWithError("A null table has been attempted to become drafted.");
		}
	}
	/**
	 * Creates the image for the card. MainClass.programDataFolder() added in the function.
	 * @param hi Card file location, ignoring file path
	 * @return Card image
	 */
	public Image makeImage(String hi){
		/*System.out.println(hi);
		File f = new File(MainClass.programDataFolder()+hi+".full.jpg");
		if (f.exists()) {*/
			Image hello = (new ImageIcon(hi)).getImage();
			return hello;
		//}
		//return null;
	}
	public String imageFilter(String cardName,String extension){
		cardName = cardName.replaceAll(",","");
		cardName = cardName.replaceAll("#","");
		cardName = cardName.replaceAll("’","");
		cardName = cardName.replaceAll("'", "");/*
		if (testString(cardName,",")||testString(cardName,"#")){
			return "cardImages/"+cardName+".jpg";
		}
		else{*/
		//System.out.println(cardName);
			File f = new File(MainClass.programDataFolder()+"cardImages/"+cardName+".full.jpg");
			if(!f.exists()) {
				System.out.println("Card image for "+cardName+" not found");
			}
			return MainClass.programDataFolder()+"cardImages/"+cardName+".full.jpg";
		//}
	}
	public void numset(int hoi){
		ArrayList<Integer> doom=new ArrayList<Integer>();
		for (int i = 0; i < hoi;i++) {
			doom.add(i);
		}
		intnumbers = doom;
		numshuffle();
	}
	public void numshuffle() {
		Collections.shuffle(intnumbers);
	}
}
