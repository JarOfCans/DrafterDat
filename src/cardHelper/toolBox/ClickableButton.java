package cardHelper.toolBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public abstract class ClickableButton extends Clickable {
	Color color;
	Font font;
	int stringx,stringy;
	public ClickableButton(String words, Font font,Color color) {
		this.word = words;
		this.color = color;
		this.font = font;
		type="button";
	}
	public void draw(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.fillRect(getX(),getY(),getX1(),getY1());
		g.setColor(Color.black);
		FontMetrics metrics = g.getFontMetrics(font);
		int stringx =getX()+((getX1()-metrics.stringWidth(word))/2);
		int stringy =getY()+((getY1()-metrics.getHeight())/2)+metrics.getAscent();
		g.drawString(word, stringx, stringy);
		//System.out.println(String.format("%s drawn", word));
	}
}
