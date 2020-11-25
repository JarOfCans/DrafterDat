package cardHelper.pages.pickChaos;

import java.awt.Color;
import cardHelper.MainClass;
import cardHelper.toolBox.ClickableTextBox;

public class ArchetypeBox extends ClickableTextBox {

	public ArchetypeBox() {
		super("Archetype Box", MainClass.BASICFONT, Color.BLACK);
	}

	@Override
	public int getX() {
		return 700;
	}

	@Override
	public int getY() {
		return 825;
	}

	@Override
	public int getX1() {
		return 500;
	}

	@Override
	public int getY1() {
		return 30;
	}

}
