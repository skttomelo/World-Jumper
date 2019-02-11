package com.mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class floor{
	//Height must be 65
	//width at the moment doesn't matter
	int x;
	public int y;
	int scale;
	int w;
	public int h;
	private final int width = 1280, height = 50;
	Image Tile;
	ImageIcon g = new ImageIcon(getClass().getResource("Resources/Tiles/Ground.gif"));
	public floor(int sx, int sy, int w, int h, int size){
		Tile = g.getImage();
		scale = size;
		this.w = w;
		this.h = h * scale;
		if(scale == 1){
			x = sx;
			y = sy;
		}
	}
	
	public Rectangle getBound(){
		return new Rectangle(x, y, w, h);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.drawImage(Tile, x, y, w, h, null);
		//g.drawRect(x,y,w,h);
	}
	
}
