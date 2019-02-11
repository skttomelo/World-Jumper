	package com.mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

import Level_State.Test_World;

public class Display extends JFrame implements Runnable{
	//http://www.ludumdare.com/compo/ludum-dare-28/?action=preview&uid=8717
	int tickCount = 0;
	int frames, ticks;
	String version = "0.0.8";
	
	BufferedImage spritesheet;
	public DayCycle DN = new DayCycle(0, 12);
	public player mc;
	
	public Test_World tw;
	
	public Display(){
		setTitle("World Jumper BY: Trevor Crow || Build:" + version + " || FPS:" + frames + " Ticks:" + ticks);
		setSize(1280, 720);
		setResizable(false);
		setVisible(true);
		setFocusable(true);
		addKeyListener(new keyboard());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
			spritesheet = ImageIO.read(getClass().getResource("Resources/Player/Spritesheet.gif"));
		}catch(IOException e){
			e.printStackTrace();
		}
		//main y should be 667 and x should be 640
		tw = new Test_World(mc, 6, spritesheet);
	}
	public void run(){
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		
		frames = 0;
		ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime)/ nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while(delta >= 1){
				ticks++;
				update();
				delta -= 1;
				shouldRender = true;
			}
			//if(shouldRender){
				frames++;
				render();
			//}
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				setTitle("World Jumper BY: Trevor Crow || Build:" + version + " || FPS:" + frames + " Ticks:" + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}
	//DRAWING IMAGES TO SCREEN
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(4);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		DN.draw(g);
		tw.draw(g);
		g.dispose();
		bs.show();
	}
	public void update(){
		tw.update();
	}
	
	private class keyboard extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			tw.keyPressed(e);
		}
		public void keyReleased(KeyEvent e){
			tw.keyReleased(e);
		}
	}
	public static void main(String[] args){
		Display game = new Display();
		//Threading
		Thread Game = new Thread(game);
		Game.start();
	}
}