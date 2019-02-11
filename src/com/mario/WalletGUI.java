package com.mario;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class WalletGUI {
	Image ws;
	int x = 512, y = 360;
	public WalletGUI(){
		ImageIcon i = new ImageIcon(getClass().getResource("Resources/GUI/Wallet GUI Basesheet.gif"));
		ws = i.getImage();
	}
	public void draw(Graphics g){
		g.drawImage(ws, x, y, null);
	}
}
