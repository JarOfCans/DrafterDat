package cardHelper.pages;

import java.awt.Color;
import java.awt.Graphics;

import cardHelper.MainClass;
import cardHelper.Settings;
import cardHelper.MainClass.Pages;
import cardHelper.card.Card;
import cardHelper.pack.Pack;
import cardHelper.set.Set;
import cardHelper.toolBox.Clickable;
import cardHelper.pages.packOpen.*;

public class Packopen extends Page {
	public Set theSet;
	public Pack apack;
	private boolean sortValue;
	private String lastSort = "";
	public Packopen(MainClass main) {
		super(main, Pages.openPack);
		sortValue = Boolean.parseBoolean(Settings.settingValue("SortLoosePack", "true"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics cellar) {
		int m = 0;
		for (int i = 0; i <apack.numOfCards();i++,m++){
			int width = 0;
			Color newColor = Color.black;
			cellar.setColor(Color.black);
			Card hoi = apack.getCard(i);
			cellar.drawString((m+1) +"."+hoi.getName()+": "+hoi.getq1()+","+hoi.getq2()+","+hoi.getq3()+","+hoi.getq4()+"= "+hoi.getqt(),MainClass.screenWidth-450,i*40+100);
			int value = hoi.getqt();
			if (hoi.getq1() == 4 && hoi.getq2() == 4 && hoi.getq3() == 4 && hoi.getq4() == 4)
			{
				newColor = Color.darkGray;
				width = 35 + 25 * (value - 10);
			}
			else
			{
				if (value <= 10) {
					width = 30;
					newColor = Color.gray;
				}
				else
				{
					width = 30 + 25 * (value - 10);
					if (value < 16)
					{
						newColor = Color.orange;
					}
					else if (value < 20)
					{
						width += 10 * (value - 15);
						newColor = Color.yellow;
					}
					else
					{
						width += 10 * (value - 19) + 10 * (value - 15);
						newColor = Color.magenta;
					}
				}
			}
			cellar.setColor(newColor);
			cellar.fillRect(MainClass.screenWidth-500-width, i*40+90, width, 15);
		}
		cellar.setColor(Color.black);
		cellar.drawString("Currently: " + sortValue, MainClass.screenWidth - 165, 780);
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
		case "New Pack":
			setPack(theSet.buildpack());
			break;
		case "Toggle Sort":
			sortValue = !sortValue;
			Settings.setSettingValue("SortLoosePack", String.valueOf(sortValue));
			properSort();
			break;
		default:
			int check = Integer.parseInt(action);
			if (check>=0) {
				main.setPage(new CardView(main,apack.getCard(check), this));
			}
		}
		}
	}
	public void setSet(Set set) {
		Pack pack = set.buildpack();
		theSet=set;
		setPack(pack);
	}
	public void enter() {
		
	}
	public void setPack(Pack pack) {
		apack = pack;
		pack.sortPackId();
		apack.setIds();
		properSort();
	}
	private void setClickables()
	{
		click.clear();
		int i = 0;
		for (Card hoi: apack.getCards()) {
			click.add(new PackCard(i++, hoi.getPic(false)));
		}
		click.add(new NewPackButton());
		click.add(new ToggleSortButton());
	}
	/**
	 * Sorts the pack based on the sortValue value and resets clickables.
	 */
	private void properSort()
	{
		if (sortValue)
		{
			apack.sortPackQt();
			lastSort = "sortQt";
		}
		else if (!lastSort.equals("sortId"))
		{
			apack.sortPackId();
			lastSort = "sortId";
		}
		setClickables();
	}
	private void curveMin(int input)
	{
		
	}
}
