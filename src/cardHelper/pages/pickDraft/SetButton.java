package cardHelper.pages.pickDraft;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class SetButton extends ClickableButton {
	private int i;

	public SetButton(String setName, int index) {
		super(setName, MainClass.BASICFONT, MainClass.DDTEAL);
		i = index;
	}

	@Override
	public int getX() {
		return 30+100*(i%9);
	}

	@Override
	public int getY() {
		return 60+80*(i/9);
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
