package cardHelper.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.Settings;
import cardHelper.card.Card;
import cardHelper.pages.collection.*;
import cardHelper.set.Set;
import cardHelper.toolBox.Clickable;
import cardHelper.toolBox.ClickableButton;
import cardHelper.toolBox.ClickableCard;
import cardHelper.toolBox.ClickableTri;

public class Collection extends Page {
	private static final String[] SORT_STYLES = {"setid", "qt", "q1", "q2", "q3", "q4", "rarity", "winrate"};
	public int viewPage;
	public int maxPage;
	public Set viewSet;
	private static double diviser = 1.5;
	private static int imageHeight = 523;
	private static int imageWidth = 375;
	private ArrayList<Card> setCards;
	private int sortIndex;
	private String [] colorStat;
	public Collection(MainClass main) {
		super(main, Pages.collection);//Add any clickables to clear
		String setting = Settings.settingValue("CollectionSortStyle", "setid");
		System.out.println(setting);
		int i = 0;
		boolean hasBeenSet = false;
		for (String hoi: SORT_STYLES)
		{
			if (setting.equals(hoi)) {
				sortIndex = i;
				hasBeenSet = true;
				break;
			}
			i++;
		}
		if (!hasBeenSet)
		{
			sortIndex = 0;
			System.out.println("ouoh");
			Settings.setSettingValue("CollectionSortStyle", SORT_STYLES[0]);
		}
		setconstantclicks();
		imgMulti=1.5;
	}
	public void setconstantclicks() {
		//click.add(new ClickableButton("ListView", imageHeight, imageHeight, imageHeight, imageHeight, main.basicfont, Color.decode("#00F0EC")));
		click.add(new CLRightButton());
		click.add(new CLLeftButton());
		click.add(new CLSortButton());
	}
	@Override
	public void draw(Graphics cellar) {
		if (MainClass.screenWidth <= 1220) {
			//System.out.println("Small");
			diviser = 2;
		}
		else {
			//System.out.println("Large");
			diviser = 1.5;
		}
			//int startx = 20;
			//int starty = 20;
			cellar.setColor(Color.decode("#000000"));
			cellar.setFont(new Font("Arial",Font.PLAIN,20));
			int m = 0;
			for (Clickable yep:click) {
				if (yep instanceof ClickableCard) {
					Card thatcard = setCards.get(Integer.parseInt(yep.word));
					cellar.drawString( thatcard.q1+", "+thatcard.q2+", "+thatcard.q3+", "+thatcard.q4+" = "+thatcard.getqt() + "  " + thatcard.winrateString(),
							22+(getImageWidth()*((m%8)%4)),(int)(30*Math.floor((m++%8)/4)+MainClass.screenHeight)-60);
				}
			}
			cellar.drawString("White:"+colorStat[0], MainClass.screenWidth - 150, 180);
			cellar.drawString("Blue:"+colorStat[1], MainClass.screenWidth - 150, 200);
			cellar.drawString("Black:"+colorStat[2], MainClass.screenWidth - 150, 220);
			cellar.drawString("Red:"+colorStat[3], MainClass.screenWidth - 150, 240);
			cellar.drawString("Green:"+colorStat[4], MainClass.screenWidth - 150, 260);
			cellar.drawString(SORT_STYLES[sortIndex], MainClass.screenWidth - 150, 140);
			//TODO
			/*cellar.setColor(Color.yellow);
			 * for (int i = 0; i < 10;i++){
				cellar.drawRect(20+i+(imageWidth*((main.editCard)%4)), (int)(20+i+(imageHeight+30)*Math.floor((editCard)/4)), imageWidth-2*i, imageHeight-2*i);
			}*/
			//cellar.setColor(Color.decode("#07D600"));
			//cellar.setColor(Color.decode("#FFFFFF"));
			//cellar.fillPolygon(new int [] {screenWidth-120,screenWidth-120,screenWidth-200},new int [] {screenHeight/3+20,screenHeight/3-20,screenHeight/3},3);
			//cellar.fillPolygon(new int [] {screenWidth-100,screenWidth-100,screenWidth-20},new int [] {screenHeight/3+20,screenHeight/3-20,screenHeight/3},3);
			//cellar.drawImage()
			/*if (x1>=xtrue&&hover>=0) {
				if (hover<10) {
					cellar.drawImage(viewedSet.getCard(hover+(8*viewPage)).getPic(flip),20+imageWidth+(imageWidth*((hover)%4)),(int)(20+(imageHeight+30)*Math.floor((hover)/4)),(int)(imageWidth*1.4),(int)(imageHeight*1.4),this);
				}
				else {
					cellar.drawImage(viewedSet.getCard(hover+(8*viewPage)).getPic(flip),20+180+(180*((hover)%5)),(int)(20+(250)*Math.floor((hover)/5))-250,360,500,this);
				}
			}*/
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		String action="none";
		for (Clickable ping:click) {
			if (ping.Check(x, y)) {
				action= ping.word;
			}
		}
		if (!action.equals("none")) {
		switch(action) {
		case "Left":
			decPage();
			break;
		case "Right":
			incPage();
			break;
		case "Change Sort":
			sortIndex++;
			if (sortIndex >= SORT_STYLES.length)
			{
				sortIndex = 0;
			}
			Settings.setSettingValue("CollectionSortStyle", SORT_STYLES[sortIndex]);
			reSort();
			break;
		default:
			try
			{
				int check = Integer.parseInt(action);
				if (check>=0) {
					main.setPage(new CardView(main, setCards.get(check), this));
				}
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("The clickable " + action + " has not yet been inplemented in collection.");
			}
		}
		}
	}
	public void setSet(Set set) {
		main.setPage(Pages.collection);
		viewSet = set;
		reSort();
		viewPage = 0;
		maxPage = viewSet.getSetSize()/8;
		if (viewSet.getSetSize()%8==0) {
			maxPage--;
		}
		noteCards();
	}
	public void incPage() {
		if (viewPage<maxPage) {
			viewPage++;
			noteCards();
		}
	}
	public void decPage() {
		if (viewPage>0) {
			viewPage--;
			noteCards();
		}
	}
	protected void noteCards() {
		click.clear();
		setconstantclicks();
		for (int i = 0+8*viewPage;i < 8+8*viewPage&&i<setCards.size(); i++) {
			click.add(new CLCard(i, setCards.get(i).getPic(false)));
		}
		calculateStats();
		main.repaint();
	}
	protected void testintarray(int[] hoi) {
		for (int hoi1:hoi) {
			System.out.println(hoi1);
		}
	}
	public void reSort()
	{
		setCards = viewSet.sortedCards(SORT_STYLES[sortIndex]);
		noteCards();
	}
	private void calculateStats()
	{
		colorStat = new String[5];
		int quadrantIndex = 0;
		switch(SORT_STYLES[sortIndex])
		{
		case "setid":
			colorStat[0] = "-";
			colorStat[1] = "-";
			colorStat[2] = "-";
			colorStat[3] = "-";
			colorStat[4] = "-";
			break;
		case "qt":
			colorStat[0] = String.valueOf(viewSet.getQ(4, 0));
			colorStat[1] = String.valueOf(viewSet.getQ(4, 1));
			colorStat[2] = String.valueOf(viewSet.getQ(4, 2));
			colorStat[3] = String.valueOf(viewSet.getQ(4, 3));
			colorStat[4] = String.valueOf(viewSet.getQ(4, 4));
			break;
		case "q1":
			colorStat[0] = String.valueOf(viewSet.getQ(0, 0));
			colorStat[1] = String.valueOf(viewSet.getQ(0, 1));
			colorStat[2] = String.valueOf(viewSet.getQ(0, 2));
			colorStat[3] = String.valueOf(viewSet.getQ(0, 3));
			colorStat[4] = String.valueOf(viewSet.getQ(0, 4));
			break;
		case "q2":
			colorStat[0] = String.valueOf(viewSet.getQ(1, 0));
			colorStat[1] = String.valueOf(viewSet.getQ(1, 1));
			colorStat[2] = String.valueOf(viewSet.getQ(1, 2));
			colorStat[3] = String.valueOf(viewSet.getQ(1, 3));
			colorStat[4] = String.valueOf(viewSet.getQ(1, 4));
			break;
		case "q3":
			colorStat[0] = String.valueOf(viewSet.getQ(2, 0));
			colorStat[1] = String.valueOf(viewSet.getQ(2, 1));
			colorStat[2] = String.valueOf( viewSet.getQ(2, 2));
			colorStat[3] = String.valueOf(viewSet.getQ(2, 3));
			colorStat[4] = String.valueOf(viewSet.getQ(2, 4));
			break;
		case "q4":
			colorStat[0] = String.valueOf(viewSet.getQ(3, 0));
			colorStat[1] = String.valueOf(viewSet.getQ(3, 1));
			colorStat[2] = String.valueOf(viewSet.getQ(3, 2));
			colorStat[3] = String.valueOf(viewSet.getQ(3, 3));
			colorStat[4] = String.valueOf(viewSet.getQ(3, 4));
			break;
		case "rarity":
			colorStat[0] = "-";
			colorStat[1] = "-";
			colorStat[2] = "-";
			colorStat[3] = "-";
			colorStat[4] = "-";
			break;
		case "winrate":
			colorStat[0] = String.valueOf(viewSet.getQ(5, 0));
			colorStat[1] = String.valueOf(viewSet.getQ(5, 1));
			colorStat[2] = String.valueOf(viewSet.getQ(5, 2));
			colorStat[3] = String.valueOf(viewSet.getQ(5, 3));
			colorStat[4] = String.valueOf(viewSet.getQ(5, 4));
			break;
		default:
			colorStat[0] = "Error";
			colorStat[1] = "Error";
			colorStat[2] = "Error";
			colorStat[3] = "Error";
			colorStat[4] = "Error";
		}
	}
	public static int getImageWidth() {
		return (int)(imageWidth / diviser);
	}
	public static int getImageHeight() {
		return (int)(imageHeight / diviser);
	}
}
