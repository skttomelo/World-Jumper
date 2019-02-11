package com.mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class charhealth {
	BufferedImage heart;
	BufferedImage[] bar = new BufferedImage[2];
	int x, y, scale, w, h,	w2, h2, diff;
	boolean visible = true;
	private final int width = 14, height = 14;
	
	public void initImage(){
		bar[0] = heart.getSubimage(145, 50, 14, 14);
		bar[1] = heart.getSubimage(131, 50, 14, 14);
	}
	
	public charhealth(int startx, int starty, int hp, int size, BufferedImage sheet){
		scale = size;
		heart = sheet;
		initImage();
		w = (width * hp) * 2;
		w2 = (width * hp) * 2;
		h2 = (height * scale) * scale;
		h = (height * scale) * scale;
		x = startx;
		y = starty;
	}
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.drawImage(bar[1], x, y, w2, h2, null);
		if(w > 0){
			g.drawImage(bar[0], x, y, w, h, null);
		}
	}
}
