package cardHelper.pages.cardView;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class CVModifyButton extends ClickableButton {
	private int index;

	public CVModifyButton(String word, int i) {
		super(word, MainClass.BASICFONT, MainClass.DDTEAL);
		index = i;
	}

	@Override
	public int getX() {
		return 1050+150*(index%2);
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 200+70*(index/2);
	}

	@Override
	public int getX1() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public int getY1() {
		// TODO Auto-generated method stub
		return 50;
	}

}
