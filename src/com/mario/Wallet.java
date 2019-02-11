package com.mario;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Wallet {
	BufferedImage wallet;
	final int width = 14, height = 15;
	int xloc, yloc, w, h,scale;
	public Wallet(int x, int y, int size){
		scale = size;
		w = width * scale;
		h = height * scale;
		if(scale > 1){
			xloc  = (x - w) + width;
		}
		if(scale == 1){
			xloc = x + width;
		}
		yloc = y;
		try {
			wallet = ImageIO.read(getClass().getResource("Resources/GUI/Wallet2.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics g){
		g.drawImage(wallet, xloc, yloc, w, h,null);
	}
}
