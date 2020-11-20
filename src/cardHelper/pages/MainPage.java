package cardHelper.pages;

import java.awt.Graphics;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.pages.mainPage.*;
import cardHelper.toolBox.Clickable;

public class MainPage extends Page {

	public MainPage(MainClass main) {
		super(main, Pages.main);
		click.add(new MainPageOpenPackButton());
		click.add(new MPDraftButton());
		click.add(new MPCollectionButton());
		click.add(new MPSealedButton());
		click.add(new MPChaosButton());
		click.add(new MPResetDataButton());
		click.add(new MPForceErrorButton());
	}

	@Override
	public void draw(Graphics cellar) {
		
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		String action="none";
		for (Clickable ping:click) {
			if (ping.Check(x, y)) {
				action=ping.word;
			}
		}
		if (!action.equals("none")) {
			switch (action) {
			case "Open Pack":
				main.setPage(Pages.pickOpen);
				main.repaint();
				break;
			case "Draft":
				main.setPage(Pages.pickDraft);
				main.repaint();
				break;
			case "Collection":
				main.setPage(Pages.pickCollection);
				main.repaint();
				break;
			case "Sealed":
				main.setPage(Pages.pickSealed);
				main.repaint();
				break;
			case "Chaos":
				main.setPage(Pages.pickChaos);
				main.repaint();
				break;
			case "Reset Data":
				main.CalibrateData(true);
				break;
			case "Force Error":
				int hoi = 1/0;
				break;
			default:
				System.out.println(action);
				break;
			}
		}
	}
}
