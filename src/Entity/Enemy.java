package Entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface Enemy {
	public void initImageArray();
	public void ImageCycle();
	public void move();
	public void setXDir(int xdir);
	public void setYDir(int ydir);
	public void update();
	public Point pointTopLeft();
	public Point pointTopRight();
	public Point pointBottomLeft();
	public Point pointBottomRight();
	public Rectangle getBound();
	public void draw(Graphics g);
}
