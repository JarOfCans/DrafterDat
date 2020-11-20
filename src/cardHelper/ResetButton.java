package cardHelper;

import java.awt.Color;
import java.awt.Font;

import cardHelper.toolBox.ClickableButton;

public class ResetButton extends ClickableButton {

	public ResetButton() {
		super("Main Menu", new Font("Arial",Font.PLAIN,20), Color.decode("#07D600"));
	}

	@Override
	public int getX() {
		return 1200;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight - 120;
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
