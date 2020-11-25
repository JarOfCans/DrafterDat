package cardHelper.pages.pickDraft;

import cardHelper.MainClass;
import cardHelper.pages.PickDraft;
import cardHelper.toolBox.ClickableButton;

public class PDRecordWinButton extends ClickableButton {

	public PDRecordWinButton() {
		super("Record Win", MainClass.BASICFONT, MainClass.DDTEAL);
	}

	@Override
	public int getX() {
		return 190;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight - PickDraft.BUTTON_ROW_FROM_BOTTOM;
	}

	@Override
	public int getX1() {
		return 120;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
