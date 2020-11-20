package cardHelper.toolBox;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Clickable {
	public Image image;
	//public int x,x1,y,y1;
	public String word;
	public boolean visible=true;
	public int hoverhold=0;
	public abstract void draw(Graphics g);
	public String type="Clickable";
	public abstract int getX();
	public abstract int getY();
	public abstract int getX1();
	public abstract int getY1();
	public boolean Check(int x2,int y2) {
		int x = getX();
		int y = getY();
		int x1 = getX1();
		int y1 = getY1();
		if (x2>=x&&x2<=x+x1&&y2>=y&&y2<=y+y1) {
			return true;
		}
		return false;
	}
}
