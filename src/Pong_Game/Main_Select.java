package Pong_Game;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.awt.*;

public class Main_Select extends JFrame {
	Main_ m;
	
	Main_Select(Main_ m) 
	{
		this.m = m;
		setTitle("Mode");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		Container ctn = getContentPane(); 

		ImageIcon Itemimage = new ImageIcon("iitem.png");
		JLabel Itemimage_jl = new JLabel(Itemimage);
//		Itemimage_jl.setLocation(1, 1);
		Itemimage_jl.setSize(300,220);

		JButton Itembutton = new JButton("아이템 전");
		Itembutton.setSize(100,30);
		Itembutton.setLocation(100, 50);
		Itembutton.addActionListener(new Listener(this));

		JButton nomalbutton= new JButton("일반 게임");
		nomalbutton.setSize(100, 30);
		nomalbutton.setLocation(100, 100);
		nomalbutton.addActionListener(new NoItemListener(this));

		JLayeredPane display = new JLayeredPane();
		display.add(Itemimage_jl, JLayeredPane.DEFAULT_LAYER);
		display.add(Itembutton, JLayeredPane.POPUP_LAYER);
		display.add(nomalbutton, JLayeredPane.POPUP_LAYER);

		display.setSize(300, 220);
		ctn.add(display);
		//  setResizable(false);
		setSize(300,220);
		setLocation(450,200);
		setVisible(true);
	}

	private class Listener implements ActionListener
	{
		Main_Select ms;
		
		Listener(Main_Select ms){
			this.ms = ms;
		}
		public void actionPerformed(ActionEvent e){
			Game g = new Game(ms.m, true);
			ms.setVisible(false);
		}
	}
	
	private class NoItemListener implements ActionListener{
		Main_Select ms;
		
		NoItemListener(Main_Select ms){
			this.ms = ms;
		}
		
		public void actionPerformed(ActionEvent e){
			Game g = new Game(ms.m, false);
			ms.setVisible(false);
		}
	}
}

