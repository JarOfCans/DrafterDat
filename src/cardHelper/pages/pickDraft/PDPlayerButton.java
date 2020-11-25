package cardHelper.pages.pickDraft;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class PDPlayerButton extends ClickableButton {
	int index;

	public PDPlayerButton(int i) {
		super("Player "+i, MainClass.BASICFONT, MainClass.DDTEAL);
		index = i;;
	}

	@Override
	public int getX() {
		return 100*index+30;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight - 130;
	}

	@Override
	public int getX1() {
		return 90;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
