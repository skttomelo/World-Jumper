package com.mario;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;







import Entity.Player;


public class player implements Player{
	//if facing = 0, then facing left
	//if facing = 1, then facing right
	BufferedImage[] Player = new BufferedImage[14];
	//punch component x and y
	int pw = 3, ph = 8, PR, PL;
	BufferedImage c;
	
	int frameSwitch = 8;
	int frameTime = 0;
	int x, dx, scale, cw;
	public int ch;
	int dy;
	public int facing;
	public int jy;
	int my;
	int maxh;
	int max = 1280;
	int currentFrame;
	int sx = 3;
	public int sy = sx * 3;
	int scoll = 2;
	int intx;
	int inty;
	private final int width = 10, height = 16;
	//health
	public int hp, inthp;
	//object width = ow, and object height = oh
	int ow, oh;
	boolean jb = false, js = true;
	public boolean jumping = false;
	boolean h = false;
	public boolean done = true;
	public boolean alive = true;
	public boolean moving = false;
	boolean keyPressedDownLeft = false;
	boolean keyPressedDownRight = false;
	boolean sprinting = false;
	public boolean falling = false;
	boolean fall = false;
	public boolean cmflight = false;
	public boolean punching = false;
	public boolean hurt = false;
	public boolean collide = true;
	public boolean reset = false;
	public boolean canhurt;
	boolean bpressed = false;
	boolean charTurn = true;
	
	//information used for making the hp system
	public boolean canbehurt = true;
	long timetilhurt, timecount = 1000;
	
	//health bar
	charhealth chp1;
	
	//Wallet
	Wallet wallet = new Wallet(1259, 27, 4);
	
	
	
	//image cycle
	public void ImageCycle(){
		if(moving == false){
			charTurn = true;
		}
		//Jumping
		if(facing == 1 && jumping == true && done == false){
			currentFrame = 6;
		}
		if(facing == 0 && jumping == true && done == false){
			currentFrame = 7;
		}
		//timer for animation
		if(keyPressedDownLeft == true || keyPressedDownRight == true){
				if(jumping == false && done == true){
				if(frameTime >= frameSwitch){
					frameTime = 0;
				}
				frameTime++;
			}
		}
		//standing still
		if(facing == 1 && moving == false && jumping == false){
			currentFrame = 3;
		}
		if(facing == 0 && moving == false && jumping == false){
			currentFrame = 0;
		}
		//moving right
		if(facing == 1 && moving == true){
			if(jumping == true && done == false){
				currentFrame = 6;
				charTurn = true;
			}
			if(jumping == false && done == true){
				if(charTurn == true && facing == 1){
					charTurn = false;
					currentFrame = 3;
				}
				if(frameTime >= frameSwitch && done == true){
					currentFrame++;
					if(currentFrame > 5){
						currentFrame = 4;
					}
				}
			}
		}
		//moving left
		if(facing == 0 && moving == true){
			if(jumping == true && done == false){
				currentFrame = 7;
				charTurn = true;
			}
			if(charTurn == true && done == true){
				charTurn = false;
				currentFrame = 0;
			}
			if(frameTime >= frameSwitch && done == true){
				currentFrame++;
				if(currentFrame > 2){
					currentFrame = 1;
				}
			}
		}
		if(punching == true && done == true && moving == false){
			if(facing == 1){
				currentFrame = 12;
			}
			if(facing == 0){
				currentFrame = 13;
			}
		}
	}
	
	//initialize the image array completely here
	public void initImageArray(){
		//Images		
		Player[0] = c.getSubimage(40, 16, 10, 16);
		Player[1] = c.getSubimage(30, 16, 10, 16);
		Player[2] = c.getSubimage(20, 16, 10, 16);
		Player[3] = c.getSubimage(0, 0, 10, 16);
		Player[4] = c.getSubimage(10, 0, 10, 16);
		Player[5] = c.getSubimage(20, 0, 10, 16);
		//Jumping
		Player[6] = c.getSubimage(30, 0, 10, 16);
		Player[7] = c.getSubimage(10, 16, 10, 16);
		//Punching
		Player[8] = c.getSubimage(40, 0, 10, 16);
		Player[9] = c.getSubimage(0, 16, 10, 16);
		//punching components
		Player[10] = c.getSubimage(50, 5, 2, 8);
		Player[11] = c.getSubimage(52, 5, 2, 8);
		
		//UNUSED AT THE MOMENT
		Player[12] =  c.getSubimage(40, 0, 10, 16);
		Player[13] =  c.getSubimage(0, 16, 10, 16);
	}
	
	public player(int startx, int starty, int size, BufferedImage sheet){
		c = sheet;
		chp1 = new charhealth(9, 27, 5, 2, c);
		initImageArray();
		currentFrame = 3;
		facing = 1;
		scale = size;
		cw = width * scale;
		ch = height * scale;
		pw = pw * scale;
		ph = ph * scale;
		maxh = ch + (ch / 2);
		hp = 5;
		inthp = hp;
		if(scale > 1){
			x = startx - (cw / 2);
			jy = starty - ch;
			intx = x;
			inty = jy;
		}
		if(scale == 1){
			x = startx;
			jy = starty;
			intx = x;
			inty = jy;
		}
		PR = x + cw;
		PL = x - pw;
		scoll = scoll * scale;
		my = jy;
		max += -cw;
		falling = false;
		//x = 640;
		//jy = 667;
	}
	public void update(){
		damage();
		ImageCycle();
		jump();
		move();
		Fall();
	}
	public void move(){
		if(sprinting == true && moving == true && alive == true){
			frameSwitch = 6;
			if(facing == 1){
				x += (dx + 2);
			}
			if(facing == 0){
				x += (dx - 2);
			}
		}
		if(sprinting == false && moving == true && alive == true){
			frameSwitch = 8;
			x += dx;
		}
		if(x >= max){
			x = max;
		}
		if(x <= 1){
			x = 1;
		}
		if(falling == true && collide == false){
			fall = true;
		}
		if(falling == false && collide == true){
			fall = false;
		}
		if(falling == true && collide == true){
			fall = false;
		}
	}
	
	public void setXDir(int xdir){
		
		dx = xdir;
		
	}
	public void setYDir(int ydir){
		
		dy = ydir;
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == e.VK_S){
			sprinting = true;
		}
		if(key == e.VK_F){
			punching = true;
			canhurt = true;
		}
		if(key == e.VK_RIGHT){
			keyPressedDownRight = true;
			facing = 1;
			moving = true;
			setXDir(sx);
		}
		if(key == e.VK_LEFT){
			keyPressedDownLeft = true;
			facing = 0;
			moving = true;
			setXDir(-sx);
		}
		if(key == e.VK_D){
			//jumping section
			if(jb == true){
				jumping = true;
				ImageCycle();
				jb = false;
			}
		}
		if(key == e.VK_R){
			if(bpressed == false){
				reset = true;
				bpressed = true;
			}
		}
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == e.VK_S){
			sprinting = false;
		}
		if(key == e.VK_RIGHT){
			keyPressedDownRight = false;
			frameTime = 0;
			moving = false;
			setXDir(0);
		}
		if(key == e.VK_LEFT){
			keyPressedDownLeft = false;
			frameTime = 0;
			moving = false;
			setXDir(0);
		}
		if(key == e.VK_D){
			jumping = false;
			if(facing == 1 && jumping == false){
				currentFrame = 3;
			}
			if(facing == 0 && jumping == false){
				currentFrame = 0;
			}
			jb = true;
		}
		if(key == e.VK_F){
			punching = false;
			canhurt = false;
		}
		if(key == e.VK_R){
			reset = false;
			bpressed = false;
		}
	}
	
	public void reset(){
		if(reset == true){
			reset = false;
			hp = inthp;
			alive = true;
			chp1.w = chp1.w2;
		}
	}
	
	//fall mechanism
	public void Fall(){
		if(fall == true && collide == false){
			jy += sy;
		}
	}
	
	//Boolean for testing if not jumping
	public boolean isDone(){
		return done;
	}
	
	//Jumping
	public void jump(){
		if(jumping == true && js == true && falling == false){
			js = false;
			done = false;
			my = jy;
			jy -= 2;
		}
		jumpCycle();
		if(done == true){
			h = false;
			js = true;
			cmflight = false;
		}
	}
	public void jumpCycle(){
		if(falling == false && done == false){
			if(cmflight == true){
				done = true;
			}
			if(falling == false && h == false){
				jy -= sy;
			}
			if(falling == false && jy <= my - maxh){
				h = true;
				jumping = false;
			}
		}
	}
	public void Done(){
		if(done == true){
			h = false;
			js = true;
			cmflight = false;
		}
	}
	//hp rolled into one
	public void hp(Graphics g){
		chp1.draw(g);
	}
	public void damage(){
		if(hurt == true){
			chp1.w -= 28;
			hp--;
			hurt = false;
			canbehurt = false;
			timetilhurt = timecount + System.currentTimeMillis();
			if(hp <= 0){
				alive = false;
			}
		}
		if(System.currentTimeMillis() > timetilhurt && canbehurt == false){
			canbehurt = true;
		}
	}
	
	//Receives Player Points
	public Point pointTopLeft(){
		return new Point(x,jy);
	}
	public Point pointTopRight(){
		return new Point(x+cw,jy);
	}
	public Point pointBottomLeft(){
		return new Point(x + scoll,jy+ch);
	}
	public Point pointBottomRight(){
		return new Point((x+cw) - scoll,jy+ch);
	}
	
	//Receives Player Bounds
	public Rectangle getBound(){
		return new Rectangle(x, jy, cw, ch);
	}
	public Rectangle getPBoundRight(){
		return new Rectangle(x + cw + scale, jy + (6 * scale), pw, ph);
	}
	public Rectangle getPBoundLeft(){
		return new Rectangle(x - (scale * 5), jy + (6 * scale), pw, ph);
	}
	
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		if(alive == true){
			g.drawImage(Player[currentFrame], x, jy, cw, ch, null);
			if(punching == true && facing == 0 && done == true){
				PL = x - pw - (scale * 2);
				g.drawImage(Player[11],PL, jy + (6 * scale), pw, ph, null);
				//g.drawRect(x - (scale * 5), jy + (6 * scale), pw, ph);
			}
			if(punching == true && facing == 1 && done == true){
				PR = x + cw + (scale * 2);
				g.drawImage(Player[10],PR, jy + (6 * scale), pw, ph, null);
				//g.drawRect(x + cw + (scale * 2), jy + (6 * scale), pw, ph);
			}
		}
		hp(g);
		wallet.draw(g);
	}
}
