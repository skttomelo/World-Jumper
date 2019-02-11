package com.mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class FactorTree {
	int hp = 6, inthp = hp, x, y, w, h, intw = 9, inth = 13, scale; // automatic if the hp isnt given
	player p;
	BufferedImage AI;
	BufferedImage[] Tree = new BufferedImage[4];
	public int currentframe, originalframe = 0;
	boolean inrange = false, hurt = false, hit = false, canhit = true, alive = true, canbehit = true, canmove = true, timechosen = true, ilos = false;
	long timehurt, hurtframe; 
	//set up rest of frame work for Factor Tree ...in progress
	
	public void initImageArray(){
		//still
		Tree[0] = AI.getSubimage(9, 46, 9, 13);
		//moving left
		Tree[1] = AI.getSubimage(0, 46, 9, 13);
		//moving right
		Tree[2] = AI.getSubimage(18, 46, 9, 13);
		//hurt
		Tree[3] = AI.getSubimage(27, 46, 9, 13);
	}
	
	public FactorTree(int x, int y, int hp, player p, int scale, BufferedImage sheet){
		AI = sheet;
		initImageArray();
		currentframe = 0;
		this.scale = scale;
		w = intw * scale;
		h = inth * scale;
		this.x = x - (w/2);
		this.y = y - h;
		this.hp = hp;
		inthp = hp;
		this.p = p;
	}
	
	public void update(){
		if(alive == true){
			attack();
			HP();
			if(canmove == true){
				sideLook();
				frenzy();
			}
		}
	}
	
	//hp system
	public void HP(){
		if(Body().intersects(p.getPBoundLeft()) == true && canbehit == true && p.punching == true || Body().intersects(p.getPBoundRight()) == true && canbehit == true && p.punching == true){
			canbehit = false;
			p.canhurt = false;
			canhit = false;
			hp--;
			originalframe = currentframe;
			currentframe = 3;
			canmove = false;
			timechosen = false;
			if(hp <= 0){
				alive = false;
			}
		}
		timehurt = System.currentTimeMillis();
		if(timechosen == false && currentframe == 3){
			hurtframe = timehurt + 1500;
			timechosen = true;
		}
		if(timehurt >= hurtframe){
			currentframe = originalframe;
			timechosen = false;
			canmove = true;
			canbehit = true;
			canhit = true;
		}
	}
	//attack System for Factor Tree
	public void attack(){
		if(canbehit == true){
			if(p.canbehurt == true){
				canhit = true;
			}
			if(p.canbehurt == false){
				canhit = false;
			}
		}
		if(Body().intersects(p.getBound())  == true && canhit == true || Body().contains(p.getBound()) == true && canhit == true){
			hit = true;
			p.hurt = true;
			canhit = false;
		}
		if(Body().intersects(p.getBound())  == false || Body().contains(p.getBound()) == false){
			hit = false;
		}
	}
	
	//side the tree looks at when player walks by
	public void sideLook(){
		if(Chase().intersects(p.getBound()) == true || Chase().contains(p.getBound()) == true){
			ilos = true;
			if(ilos = true){
				if(p.x < x){
					currentframe = 1;
				}
				if(p.x > x ){
					currentframe = 2;
				}
			}
		}
		if(Chase().intersects(p.getBound()) == false || Chase().contains(p.getBound()) == false){
			ilos = false;
			currentframe = 0;
		}
	}
	
	//triggers the tree's frenzy chase
	public void frenzy(){
		//trigger frenzy
		if(Range().intersects(p.getBound()) == true || Range().contains(p.getBound()) == true){
			inrange = true;
		}
		//continue the chase
		if(Chase().intersects(p.getBound()) == true && inrange == true|| Chase().contains(p.getBound()) == true && inrange == true){
			//move right
			if(p.x > x + w){
				x += 2;
			}
			//move left
			if(p.x < x){
				x -= 2;
			}
		}
		//stop the chase
		if(Chase().intersects(p.getBound()) == false && inrange == true|| Chase().contains(p.getBound()) == false && inrange == true){
			inrange = false;
		}
		if(x >= 1280 - w){
			x = 1280 - w;
		}
		if(x <= 0){
			x = 0;
		}
	}
	
	public Rectangle Body(){
		return new Rectangle(x, y, w, h);
	}
	
	//zone that the creature sees
	public Rectangle Range(){
		return new Rectangle(x - ((w / 2) / 2), y, w + (w / 2), h);
	}
	
	//area that the creature chases the player
	public Rectangle Chase(){
		return new Rectangle(x - (w + (w/2)), y, w * 4, h);
	}
	
	public void draw(Graphics g){
		if(alive == true){
			g.drawImage(Tree[currentframe], x, y, w, h, null);
			g.setColor(Color.RED);
			//g.drawRect(x - ((w / 2) / 2), y, w + (w / 2), h);
			//g.drawRect(x - (w + (w/2)), y, w * 4, h);
		}
	}
	
}
