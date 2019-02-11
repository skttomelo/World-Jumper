package Level_State;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Entity.Level;
import Tile.Grass;

import com.mario.Display;
import com.mario.FactorTree;
import com.mario.aiRandomSpawn;
import com.mario.aiTest;
import com.mario.floor;
import com.mario.player;

public class Test_World implements Level{
	//TODO List
	//Work on collisions on map
	//add a hurt Image to the slime and player for when they're hit
	//add collision for if the slime touches the player he'll lose health
	
	boolean palive = true;
	player p;
	//Parts of Ground and other base parts for level
	floor[] platform = new floor[3];
	public void lvlParts(){
		platform[0] = new floor(50, 577, 200, 20, 1);
		platform[1] = new floor(640, 550, 150, 50, 1);
		platform[2] = new floor(0, 667, 1280, 50, 1);
	}
	
	//NPCS for level population;
	//Testing performance with about 10 ai running around as slimes
	aiTest human;
	int slimeCount;
	aiRandomSpawn[] slime;
	FactorTree tree;
	BufferedImage sheet;
	public void NPCS(){
		p = new player(640, 667, 4, sheet);
		human = new aiTest(555, 667, 2, sheet);
		tree = new FactorTree(300, 667, 6, p, 7, sheet);
		for(int i = 0; i < slimeCount; i++){
			slime[i] = new aiRandomSpawn(1280, 667, 10, 3, 2, p, sheet);
		}
	}
	
	public Test_World(player p, int slimeCount, BufferedImage sheet){
		this.p = p;
		this.sheet = sheet;
		this.slimeCount = slimeCount;
		slime = new aiRandomSpawn[slimeCount];
		lvlParts();
		NPCS();
	}
	
	public void update(){
		p.update();
		tree.update();
		if(p.reset == true){
			for(int i = 0; i < slimeCount;i++){
				slime[i].alive = true;
				slime[i].hp = slime[i].inthp;
				slime[i].changex = true;
				slime[i].currentFrame = slime[i].originalframe;
				slime[i].canmove = true;
			}
			p.reset();
		}
		human.update();
		for(int i = 0; i < slimeCount; i++){
			slime[i].update();
		}
		Collision();
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < 3; i++){
			platform[i].draw(g);
		}
		tree.draw(g);
		for(int i = 0; i < slimeCount; i++){
			slime[i].draw(g);
		}
		human.draw(g);
		if(palive == true){
			p.draw(g);
		}
	}
	
	public void Collision(){
		//collisions with lvl parts
		for(int i = 0; i < 3; i++){
			if(platform[i].getBound().contains(p.pointTopLeft()) == true || platform[i].getBound().contains(p.pointTopRight()) == true){
				p.done = true;
				if(p.jumping == true){
					p.jy = platform[i].y + platform[i].h;
					p.jumping = false;
				}
			}
			if(platform[i].getBound().contains(p.pointBottomLeft()) == false && p.jumping == false || platform[i].getBound().contains(p.pointBottomRight()) == false && p.jumping == false){
				p.done = true;
				p.falling = true;
				p.collide = false;
			}
			if(platform[i].getBound().contains(p.pointBottomLeft()) == true && p.jumping == false || platform[i].getBound().contains(p.pointBottomRight()) == true && p.jumping == false){
				p.done = true;
			}
			//make better collisions
			if(platform[i].getBound().contains(p.pointBottomLeft()) == true && p.jumping == true || platform[i].getBound().contains(p.pointBottomRight()) == true && p.jumping == true){
				p.setXDir(0);
			}
			if(platform[i].getBound().contains(p.pointBottomLeft()) == true && p.done == true || platform[i].getBound().contains(p.pointBottomRight()) && p.done == true){
				if(p.jumping == false){
					p.jy = platform[i].y - p.ch;
				}
				p.falling = false;
				p.collide = true;
			}
		}
		
		//collisions with npcs
		for(int i = 0; i < slimeCount; i++){
			if(p.alive == true && slime[i].getBound().intersects(p.getPBoundRight()) == true && p.punching == true && slime[i].alive == true && p.done == true && p.canhurt == true && slime[i].canbehurt == true && slime[i].hurt == false || p.alive == true && slime[i].getBound().intersects(p.getPBoundLeft()) == true && p.punching == true && slime[i].alive == true && p.done == true && p.canhurt == true && slime[i].canbehurt == true && slime[i].hurt == false){
				slime[i].hurt = true;
			}
			if(slime[i].getBound().intersects(p.getBound()) == true && p.canbehurt == true && p.alive == true && slime[i].canbehurt == true && slime[i].alive == true){
				p.hurt = true;
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		p.keyPressed(e);
	}
	public void keyReleased(KeyEvent e){
		p.keyReleased(e);
	}
}
