package cardHelper.pages;

import java.awt.Color;
import java.awt.Font;
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
import cardHelper.player.Player;
import cardHelper.table.ClassicDraftTable;
import cardHelper.toolBox.Clickable;
import cardHelper.toolBox.ClickableCard;
import cardHelper.toolBox.HoverableCardName;

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
								click.add(new ClickableCard(i, makeImage((hoi)),20+180*(i%5),20+250*(i/5),180,250));
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
						click.add(new HoverableCardName(player.cardsPicked.get(i), screenWidth - 245, i*20+50, 245, 20));
					}
				}
				for (Clickable clack:click) {
					clack.draw(cellar);
				}
				
			cellar.setFont(new Font("Arial",Font.PLAIN,20));
			cellar.setColor(Color.decode("#000000"));
			for (int i = 1; i <=6;i++){
				if (i <= 5){
					cellar.drawString(Integer.toString(i), 930, (i)*60+8);
				}
				else{
					cellar.drawString("6+", 930, 360);
				}
				cellar.setColor(Color.decode("#00D60E"));
				if (player.greatestCMC()>0){
					cellar.fillRect(970, (i)*60-30,table.players.get(table.wait).getCMC(i)*120/(table.players.get(table.wait).greatestCMC()), 60);
				}
				cellar.setColor(Color.decode("#000000"));
				cellar.drawString(Integer.toString(table.players.get(table.wait).getCMC(i)),1000,(i)*60+8);
			}
			cellar.drawString(Integer.toString(table.players.get(table.wait).chooseCard()+1),screenWidth-475,screenHeight/2);
			int m = 0;
			for (int i = 0; i <table.waitPack().numOfCards();i++,m++){
				Card cardHolder = player.myPack().getCard(i);
				cellar.drawString((m+1) +": "+(int)table.players.get(table.wait).getScore(m,true)+"("+cardHolder.winrateString()+", "+cardHolder.getqt()+")",screenWidth-445,i*40+100);
			}
			m = 0;
			//Picked card stats and view
			cellar.setColor(Color.BLACK);
			//for (Card hoi:table.players.get(table.wait).cardsPicked) {
				//Cards picked
				/*cellar.setColor(Color.BLACK);
				cellar.drawString(hoi.cost+" "+hoi.getName(), screenWidth-240, m*20+50);
				cellar.setColor(Color.DARK_GRAY);//TODO Colorcode based on cards color
				cellar.drawLine(screenWidth-245, m*20+52, screenWidth-245, (m-1)*20+67);//Y line
				cellar.drawLine(screenWidth-245, m*20+52, screenWidth, m*20+52);//X line
				m++;*/
				//Moved to clickables
			//}
			cellar.drawString("W= " + table.players.get(table.wait).White, 1000, 600);
			cellar.drawString("U= " + table.players.get(table.wait).Blue, 1000, 620);
			cellar.drawString("B= " + table.players.get(table.wait).Black, 1000, 640);
			cellar.drawString("R= " + table.players.get(table.wait).Red, 1000, 660);
			cellar.drawString("G= " + table.players.get(table.wait).Green, 1000, 680);
			cellar.drawString("Player= "+table.wait,1000,700);
		//TODO make setting to turn off/on
		if (table.waitPack().pickedNames.size()>0) {
			if (table.getNewPack()) {
			numset(table.waitPack().pickedNames.size());
			}
			//Seen Card names
			int base = 0;
			for (int i = table.waitPack().numOfCards(); i < table.waitPack().numOfCards()+table.waitPack().pickedNames.size();i++) {
				//System.out.println(intnumbers.get(base));
				cellar.drawString(table.waitPack().pickedNames.
						get(intnumbers.get(base++)),
						25+180*((table.waitPack().numOfCards()+base-1)%5),
						40+50*(table.waitPack().numOfCards()+base-1));
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
				cellar.drawString(String.format("P%d = ", i), 1000, 720 + (i*20));
				thePlayer.drawColorBar(1048, 704 + (i*20), 100, 18, cellar);
			}
			else
			{
				cellar.drawString(String.format("P%d = AI", i), 1000, 720 + (i*20));
			}
		}
		table.setNewPack(false);
		}
			/*if (x1>=xtrue&&hover>=0) {
				if (hover<10) {
					cellar.drawImage(table.players.get(table.wait).getPack().getCard(hover).getPic(main.flip),20+180+(180*((hover)%5)),(int)(20+(250)*Math.floor((hover)/5)),360,500,this);
				}
				else {
					cellar.drawImage(player[0].getPack().getCard(hover).getPic(main.flip),20+180+(180*((hover)%5)),(int)(20+(250)*Math.floor((hover)/5))-250,360,500,this);
				}
			}*/
			//System.out.println(player[0].chooseCard()+1);
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
	/**
	 * Checks the picked cards (on the side of the screen), will paint if one is being hovered.
	 */
	@Override
	protected void pageHoverCheck(int x, int y,Graphics cellar)
	{
		//TODO allow delay setting.
		int cardHovered = cardIndex(x, y);
		//System.out.println(cardHovered);
		if (cardHovered >= 0)
		{
			int top = cardHovered * 20 + 50;
			int left = screenWidth - 240;
			int height = (int) (250 * imgMulti);
			int width = (int) (180 * imgMulti);
			Image img = table.players.get(table.wait).cardsPicked.get(cardHovered).getPic(false);
			if (top < main.SCREENHEIGHT - height) {
				cellar.drawImage(img,left+width,top,(int)(width*imgMulti),(int)(height*imgMulti),main);
			}
			else {
				cellar.drawImage(img,left+width,(int)(top-height*(imgMulti-1)),(int)(width*imgMulti),(int)(height*imgMulti),main);
			}
		}
	}
	/**
	 * Helper method to find which card is currently being hovered over, if any.
	 * @param x X coordinate of the mouse.
	 * @param y Y coordinate of the mouse.
	 * @return An index of a potential hover card, -1 if it does not exist or the card has
	 * yet to be added.
	 */
	public int cardIndex(int x , int y)
	{
		if (x > screenWidth - 240)
		{
			int checkOutput = (x - 50) / 20;
			if (checkOutput < table.players.get(table.wait).cardsPicked.size() && checkOutput >= 0)
			{
				return checkOutput;
			}
		}
		return -1;
	}
}
