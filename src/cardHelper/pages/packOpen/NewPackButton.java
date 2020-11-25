package cardHelper.pages.packOpen;

import java.awt.Color;
import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class NewPackButton extends ClickableButton {

	public NewPackButton() {
		super("New Pack", MainClass.BASICFONT, Color.green);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth-450;
	}

	@Override
	public int getY() {
		return 700;
	}

	@Override
	public int getX1() {
		return 100;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
