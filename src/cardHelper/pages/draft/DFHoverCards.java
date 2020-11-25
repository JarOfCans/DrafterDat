package cardHelper.pages.draft;

import cardHelper.MainClass;
import cardHelper.card.Card;
import cardHelper.toolBox.HoverableCardName;

public class DFHoverCards extends HoverableCardName {
	int index;
	public DFHoverCards(Card cardIn, int i) {
		super(cardIn);
		index = i;
	}

	@Override
	public int getX() {
		return MainClass.screenWidth-245;
	}

	@Override
	public int getY() {
		return index*20+10;
	}

	@Override
	public int getX1() {
		return 245;
	}

	@Override
	public int getY1() {
		return 20;
	}

}
