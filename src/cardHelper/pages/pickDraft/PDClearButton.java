package cardHelper.pages.pickDraft;

import cardHelper.MainClass;
import cardHelper.pages.PickDraft;
import cardHelper.toolBox.ClickableButton;

public class PDClearButton extends ClickableButton {

	public PDClearButton() {
		super("Clear", MainClass.BASICFONT, MainClass.DDTEAL);
	}

	@Override
	public int getX() {
		return 510;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight - PickDraft.BUTTON_ROW_FROM_BOTTOM;
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
