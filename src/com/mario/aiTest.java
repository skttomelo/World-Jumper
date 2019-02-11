package com.mario;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;



import javax.management.timer.Timer;
import javax.swing.ImageIcon;

public class aiTest{
	//Transform Images to BufferedImage
	int x, y, dx, scale, w, h, max = 1264, sx = 3;
	private final int width = 15, height = 29;
	boolean resting = false;
	boolean shouldSetRandDir = true;
	Image AI;
	ImageIcon s = new ImageIcon(getClass().getResource("Resources/Player/Default AI Down.gif"));
	ImageIcon l = new ImageIcon(getClass().getResource("Resources/Player/Default AI Left.gif"));
	ImageIcon r = new ImageIcon(getClass().getResource("Resources/Player/Default AI Right.gif"));
	
	boolean noTime, noWaitTime;
	long timeWait;
	long Timer;
	long delay;
	long start;
	long end;
	
	public aiTest(int startx, int starty, int size, BufferedImage sheet){
		scale = size;
		w = width * scale;
		h = height * scale;
		AI = s.getImage();
		if(scale == 1){
			x = startx;
			y = starty;
		}
		if(scale > 1){
			max = ((max - w) + width) - 5;
			x = (startx - w) + width;
			y = starty - h;
		}
		noTime = true;
	}
	public void draw(Graphics g){
		g.drawImage(AI, x, y, w, h, null);
	}
	//choose Random direction
	public int chooseRandomDirection(){
		Random r = new Random();
		int[] randDirections = new int[3];
		randDirections[0] = 0;
		randDirections[1] = sx;
		randDirections[2] = -sx;
		int randChoice = r.nextInt(3);
		return randDirections[randChoice];
	}
	//move in that direction
	public void move(){
		if(dx == -sx){
			AI = l.getImage();
		}
		if(dx == sx){
			AI = r.getImage();
		}
		if(dx == 0){
			AI = s.getImage();
		}
		x += dx;
		if(x >= max){
			x = max;
		}
		if(x <= 1){
			x = 1;
		}
	}
	
	
	public void update(){
		if(resting == false){
			if(shouldSetRandDir == true){
			dx = chooseRandomDirection();
			shouldSetRandDir = false;
			noWaitTime = true;
			}
			if(noTime == true){
				noTime = false;
				Timer = System.currentTimeMillis();
				start = Timer;
				end = start + 1 * 1000;
			}
			if(System.currentTimeMillis() < end){
				move();
			}
		}
		if(System.currentTimeMillis() >= end){
			resting = true;
		}
		if(resting == true){
			if(noWaitTime){
				timeWait =  System.currentTimeMillis() + 1 * 1000;
				noWaitTime = false;
			}
			if(System.currentTimeMillis() > timeWait){
				resting = false;
				shouldSetRandDir = true;
				noWaitTime = true;
				noTime = true;
			}
		}
	}
}
