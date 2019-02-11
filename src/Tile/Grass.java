package Tile;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Grass implements TileSet{
	int x, y, w = 50, h = 50, scale;
	BufferedImage obj;
	
	public Grass(int x, int y, int w, int h, int scale){
		try{
			obj = ImageIO.read(getClass().getResource("Grass Tile.gif"));
		}catch(IOException e){
			e.printStackTrace();
		}
		this.scale = scale;
		this.w = w * scale;
		this.h = h;
		if(this.scale == 1){
			this.x = x;
			this.y = y;
		}
		if(this.scale > 1){
			this.x = x - (w / 2);
			this.y = y;
		}
	}
	
	public Rectangle getBound() {
		return new Rectangle( x, y, w, h);
	}

	public void draw(Graphics g) {
		g.drawImage(obj, x, y, w, h, null);
	}

}
