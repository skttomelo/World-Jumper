package Tile;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface TileSet {
	public Rectangle getBound();
	public void draw(Graphics g);
}
