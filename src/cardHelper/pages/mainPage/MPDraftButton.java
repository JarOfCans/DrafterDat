package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MPDraftButton extends ClickableButton {

	public MPDraftButton() {
		super("Draft",MainClass.BASICFONT,MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth*2/3;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight*1/3 + 100;
	}

	@Override
	public int getX1() {
		return 120;
	}

	@Override
	public int getY1() {
		return 60;
	}

}
