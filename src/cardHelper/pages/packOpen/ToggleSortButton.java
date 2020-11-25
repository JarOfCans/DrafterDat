package cardHelper.pages.packOpen;

import java.awt.Color;
import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class ToggleSortButton extends ClickableButton {

	public ToggleSortButton() {
		super("Toggle Sort", MainClass.BASICFONT, Color.green);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth - 165;
	}

	@Override
	public int getY() {
		return 720;
	}

	@Override
	public int getX1() {
		return 155;
	}

	@Override
	public int getY1() {
		return 40;
	}

}
