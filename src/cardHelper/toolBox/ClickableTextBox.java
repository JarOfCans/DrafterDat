package cardHelper.toolBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class ClickableTextBox extends ClickableButton {
	private boolean selected;
	private String text;
	/**
	 * Constructor for a clickable text box object.
	 * @param words Initial name.
	 * @param x initial x value of upper right corner
	 * @param y initial y value of upper right corner
	 * @param x1 Width of the Text box
	 * @param y1 Height of the Text box
	 * @param font Font of the Text box.
	 * @param color Color of the border of the Text box.
	 */
	public ClickableTextBox(String words, int x, int y, int x1, int y1, Font font, Color color) {
		super(words, x, y, x1, y1, font, color);
		type = "button";
		selected = false;
		text = "";
	}
	/**
	 * Swaps whether or not it is selected.
	 * @return
	 */
	public boolean select()
	{
		return (selected = !selected);
	}
	public void typed(char input)
	{
		if (selected && Character.isAlphabetic(input))
		{
			text += input;
		}
		else if (selected && input == KeyEvent.VK_BACK_SPACE && text.length() > 0)
		{
			text = text.substring(0, text.length() - 1);
		}
	}
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.drawRect(x, y, x1, y1);
		if (selected) {
			g.drawRect(x + 1, y + 1, x1 - 2, y1 - 2);
			g.setColor(Color.BLACK);
		} 
		else {
			g.setColor(Color.GRAY);
		}
		g.drawString(text, x+5, y+20);
	}
	public String getText()
	{
		return text;
	}
}
