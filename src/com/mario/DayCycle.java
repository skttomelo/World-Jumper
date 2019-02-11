package com.mario;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DayCycle {
	boolean noTime;
	Long Start;
	Long DNTime;
	Long End;
	boolean Day;
	BufferedImage[] image = new BufferedImage[5];
	int DN;
	BufferedImage dn;
	
	public void init(){
		Day = true;
		noTime = true;
		try{
			dn = ImageIO.read(getClass().getResource("Resources/Tiles/Time of Day.gif"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		image[0] = dn.getSubimage(0, 0, 1, 1);
		image[1] = dn.getSubimage(1, 0, 1, 1);
		image[2] = dn.getSubimage(2, 0, 1, 1);
		image[3] = dn.getSubimage(3, 0, 1, 1);
		image[4] = dn.getSubimage(4, 0, 1, 1);

	}
	
	public DayCycle(int DN, long DNTime){
		init();
		this.DN = DN;
		this.DNTime = ((DNTime * 1000) * 60);
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		g.drawImage(image[DN], 0, 0, 1280, 720,null);
	}
}
