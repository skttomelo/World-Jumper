package com.mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

public class aiRandomSpawn{
	//Transform Images to BufferedImage
	
	int x, y, dx, w, h, yscale, xscale, max = 1269, sx, intx;
	public int originalframe;
	public int hp, inthp;
	private final int width = 5, height = 10;
	boolean resting = false, shouldSetRandDir = true, noWaitTime, noTime, onplayer = true;
	public boolean alive = true, changex = false, hurt, canbehurt = true;
	boolean timechosen;
	long timehurt, hurtframe;
	long timeWait;
	long Timer;
	long delay;
	long start;
	long end;
	Random rand;
	BufferedImage AI;
	BufferedImage[] ai = new BufferedImage[4];
	public int currentFrame;
	public boolean canmove = true;
	player p;
	
	public void initImageArray(){
		//still
		ai[0] = AI.getSubimage(10, 59, 10, 5);
		//Moving Right
		ai[1] = AI.getSubimage(20, 59, 10, 5);
		//Moving Left
		ai[2] = AI.getSubimage(0, 59, 10, 5);
		//Hurt
		ai[3] = AI.getSubimage(30, 59, 10, 5);
	}
	
	public aiRandomSpawn(int rs, int sy, int xsize, int ysize, int hp, player p, BufferedImage sheet){
		this.p = p;
		AI = sheet;
		initImageArray();
		xscale = xsize;
		yscale = ysize;
		intx = rs;
		w = width * xscale;
		h = height * yscale;
		this.hp = hp;
		inthp = hp;
		if(xscale > 1 && yscale > 1){
			max = ((max - w) + width) - 5;
		}
		Random r = new Random();
		x = r.nextInt(rs);
		if(x >= p.x && x <= p.cw){
			onplayer = true;
			while(onplayer == true){
				if(x >= p.x && x <= p.cw){
					x = r.nextInt(rs);
				}else{
					onplayer = false;
				}
			}
		}
		if(xscale > 1){
			x = (r.nextInt(rs) - w) + width;
			if(x >= p.x && x <= p.cw){
				onplayer = true;
				while(onplayer == true){
					if(x >= p.x && x <= p.cw){
						x = r.nextInt(rs);
					}else{
						onplayer = false;
					}
				}
			}
		}
		int halfx = rs/2;
		if(x < halfx && xscale == 1){
			halfx = halfx + 1;
			x = r.nextInt(halfx);
			if(x >= p.x && x <= p.cw){
				x = r.nextInt(halfx);
			}
		}
		if(x < halfx && xscale > 1){
			halfx = halfx + 1;
			x = (r.nextInt(halfx) - w) + width;
		}
		if(yscale >= 1){
			y = sy - h;
		}
		noTime = true;
		currentFrame = 0;
	}
	public void draw(Graphics g){
		if(alive == true){
			g.drawImage(ai[currentFrame], x, y, w, h, null);
		}
	}
	//Random Time Stamp for movement
	public int randTime(){
		Random r = new Random();
		int[] timeStamp = new int[3];
		timeStamp[0] = 1000;
		timeStamp[1] = 2000;
		timeStamp[2] = 3000;
		int randChoice = r.nextInt(3);
		return timeStamp[randChoice];
	}
	//choose Random direction
	public int chooseRandomDirection(){
		Random r = new Random();
		int[] randDirections = new int[3];
		randDirections[0] = 0;
		randDirections[1] = 1;
		randDirections[2] = -1;
		int randChoice = r.nextInt(3);
		return randDirections[randChoice];
	}
	//move in that direction
	public void move(){
		if(canmove == true){
			if(dx == -1){
				currentFrame = 2;
			}
			if(dx == 1){
				currentFrame = 1;
			}
			if(dx == 0){
				currentFrame = 0;
			}
			x += dx;
		}
		if(x >= max){
			x = max;
		}
		if(x <= 2){
			x = 2;
		}
	}
	
	public void update(){
		if(changex == true){
			Random r = new Random();
			x = r.nextInt(intx);
			if(x >= p.x && x <= p.cw){
				onplayer = true;
				while(onplayer == true){
					if(x >= p.x && x <= p.cw){
						x = r.nextInt(intx);
					}else{
						onplayer = false;
					}
				}
			}
			changex = false;
		}
		if(hurt == true){
			canbehurt = false;
			AIhurt();
		}
		timeGrayed();
		if(alive == true){
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
				end = start + randTime();
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
				timeWait =  System.currentTimeMillis() + randTime();
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
	
	public void AIhurt(){
		hurt = false;
		p.canhurt = false;
		hp--;
		originalframe = currentFrame;
		currentFrame = 3;
		timechosen = false;
		canmove = false;
		if(hp <= 0){
			alive = false;
		}
	}
	

	public void timeGrayed(){
		timehurt = System.currentTimeMillis();
		if(timechosen == false && currentFrame == 3){
			hurtframe = timehurt + 1500;
			timechosen = true;
		}
		if(timehurt >= hurtframe){
			currentFrame = originalframe;
			timechosen = false;
			canmove = true;
			canbehurt = true;
		}
	}

	
	public Rectangle getBound(){
		return new Rectangle(x, y, w, h);
	}
}
