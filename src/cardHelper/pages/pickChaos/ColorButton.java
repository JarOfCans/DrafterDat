package cardHelper.pages.pickChaos;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class ColorButton extends ClickableButton {
	private int index;

	public ColorButton(String color, int i) {
		super(color, MainClass.BASICFONT, MainClass.DDTEAL);
		index = i;
	}

	@Override
	public int getX() {
		return 30+120*index;
	}

	@Override
	public int getY() {
		return 700;
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
