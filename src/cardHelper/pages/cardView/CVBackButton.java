package cardHelper.pages.cardView;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class CVBackButton extends ClickableButton {

	public CVBackButton() {
		super("Back", MainClass.BASICFONT, MainClass.DDTEAL);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 1100;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 80;
	}

	@Override
	public int getX1() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public int getY1() {
		// TODO Auto-generated method stub
		return 50;
	}

}
