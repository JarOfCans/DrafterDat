package cardHelper.pages.cardView;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class CVDraftWithCardButton extends ClickableButton {

	public CVDraftWithCardButton() {
		super("Draft with Card", MainClass.BASICFONT, MainClass.DDTEAL);
	}

	@Override
	public int getX() {
		return 1300;
	}

	@Override
	public int getY() {
		return 80;
	}

	@Override
	public int getX1() {
		return 150;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
