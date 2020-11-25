package cardHelper.toolBox;

import java.awt.Color;
import java.awt.Graphics;

public abstract class ClickableTri extends Clickable {
	//public 
	public Color color;
	public ClickableTri(String function, Color color) {
		word=function;
		this.color=color;
		type="tri";
	}
	public void draw(Graphics cellar) {
		cellar.setColor(color);
		cellar.fillPolygon(getXS(),getYS(),3);
	}
	public boolean Check(int x,int y) {
		int [] xs = getXS(), ys = getYS();
		if ((x>=xs[0]||x>=xs[1]||x>=xs[2])&&(x<=xs[0]||x<=xs[1]||x<=xs[2])&&(y>=ys[0]||y>=ys[1]||y>=ys[2])&&(y<=ys[0]||y<=ys[1]||y<=ys[2])) {
			return true;
		}
		return false;
	}
	protected void testintarray(int[] hoi) {
		for (int hoi1:hoi) {
			System.out.println(hoi1);
		}
	}
	public abstract int[] getXS();
	public abstract int[] getYS();
	@Override
	public int getX() {
		return getXS()[0];
	}
	@Override
	public int getX1() {
		return getXS()[0];
	}
	@Override
	public int getY() {
		return getYS()[0];
	}
	@Override
	public int getY1() {
		return getYS()[0];
	}
}
