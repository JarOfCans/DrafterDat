package cardHelper.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.Settings;
import cardHelper.card.Card;
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
	private int imageHeight = (int)(523/1.5);
	int imageWidth = (int)(375/1.5);
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
		click.add(new ClickableTri("Right",new int [] {screenWidth-100,screenWidth-100,screenWidth-20},new int [] {screenHeight/2+40,screenHeight/2-40,screenHeight/2},Color.decode("#07D600")));
		click.add(new ClickableTri("Left",new int [] {screenWidth-120,screenWidth-120,screenWidth-200},new int [] {screenHeight/2+40,screenHeight/2-40,screenHeight/2},Color.decode("#07D600")));
		click.add(new ClickableButton("Change Sort", screenWidth - 150, 80, 120, 40, MainClass.BASICFONT, Color.decode("#07D600")));
	}
	@Override
	public void draw(Graphics cellar) {
			//int startx = 20;
			//int starty = 20;
			cellar.setColor(Color.decode("#000000"));
			cellar.setFont(new Font("Arial",Font.PLAIN,20));
			//cellar.drawString("Packs: "+Integer.toString(viewedSet.getPackTotal()), screenWidth-200, 300);
			//for (int i = 1;i <= 8;i++){
				//if (i+(8*main.viewPage)<=main.viewedSet.getSetSize()){
			//TB.fulladdclick(main.viewedSet.getCard(i+(8*main.viewPage)-1).getPic(main.flip),20+(imageWidth*((i-1)%4)),(int)(20+(imageHeight+30)*Math.floor((i-1)/4)),imageWidth, imageHeight);
			//cellar.drawString(Integer.toString(getEditValue(main.viewedSet.getCard(i+(8*main.viewPage)-1))), 20+imageWidth/2+(imageWidth*((i-1)%4)), (int)(10+(imageHeight+30)*Math.floor((i+3)/4)));
			//}
			//}
			int m = 0;
			for (Clickable yep:click) {
				if (yep.type.equals("card")) {
					Card thatcard = setCards.get(Integer.parseInt(yep.word));
					cellar.drawString( thatcard.q1+", "+thatcard.q2+", "+thatcard.q3+", "+thatcard.q4+" = "+thatcard.getqt() + "  " + thatcard.winrateString(),
							22+(imageWidth*((m%8)%4)),(int)(40+(imageHeight+30)*Math.floor((m++%8)/4)+imageHeight));
				}
			}
			//cellar.drawString(main.editView, 1200, 240);
			/*if (main.editnum-1<5&&main.editnum>0) {
				cellar.drawString("White: "+main.viewedSet.qstats[0][main.editnum-1], 1200, 100);
				cellar.drawString("Blue: "+main.viewedSet.qstats[1][main.editnum-1], 1200, 120);
				cellar.drawString("Black: "+main.viewedSet.qstats[2][main.editnum-1], 1200, 140);
				cellar.drawString("Red: "+main.viewedSet.qstats[3][main.editnum-1], 1200, 160);
				cellar.drawString("Green: "+main.viewedSet.qstats[4][main.editnum-1], 1200, 180);
			}*/
			cellar.drawString("White:"+colorStat[0], 1200, 100);
			cellar.drawString("Blue:"+colorStat[1], 1200, 120);
			cellar.drawString("Black:"+colorStat[2], 1200, 140);
			cellar.drawString("Red:"+colorStat[3], 1200, 160);
			cellar.drawString("Green:"+colorStat[4], 1200, 180);
			cellar.drawString(SORT_STYLES[sortIndex], screenWidth - 150, 140);
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
			click.add(new ClickableCard(i, setCards.get(i).getPic(false), 20+(imageWidth*((i%8)%4)),(int)(20+(imageHeight+30)*Math.floor((i%8)/4)),imageWidth, imageHeight));
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
}
