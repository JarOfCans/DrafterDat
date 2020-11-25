package cardHelper.pages.collection;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class CLSortButton extends ClickableButton {

	public CLSortButton() {
		//Old color was #07D600
		super("Change Sort", MainClass.BASICFONT, MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth-150;
	}

	@Override
	public int getY() {
		return 80;
	}

	@Override
	public int getX1() {
		return 120;
	}

	@Override
	public int getY1() {
		return 40;
	}

}
