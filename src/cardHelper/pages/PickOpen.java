package cardHelper.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.set.Set;
import cardHelper.toolBox.Clickable;
import cardHelper.toolBox.ClickableButton;

public class PickOpen extends Page {

	public PickOpen(MainClass main) {
		super(main, Pages.pickOpen);
		for (int i = 0; i < main.setList.size();i++) {
			if (main.setList.get(i).getSetSize()>0) {
				click.add(new ClickableButton(main.setList.get(i).getName(), 30+100*(i%8), 60+80*(i/8), 90, 50, new Font("Arial",Font.PLAIN,20), Color.decode("#00F0EC")));
			}
		}
	}

	@Override
	public void draw(Graphics cellar) {
		cellar.setColor(Color.decode("#000000"));
		cellar.drawString("Pick a Set", screenWidth*4/9, 50);
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		String action="none";
		for (Clickable ping:click) {
			if (ping.Check(x, y)) {
				action= ping.word;
			}
		}
		switch(action) {
		case "none":
			break;
		default:
			Set check = main.setList.get(main.getidSet(action));
			main.setPageSet(Pages.openPack, check);
			main.setPage(Pages.openPack);
			main.repaint();
		}
	}
}
