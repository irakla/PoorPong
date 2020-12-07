package Pong_Game;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.awt.*;

public class Main_ extends JFrame {

	Main_ m;
	
	Main_() 
	{
	setTitle("퐁게임(What)");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLayout(null);
	
	Container ctn = getContentPane(); 
	
	ImageIcon image = new ImageIcon("mainname.png");
	JLabel image_jl = new JLabel(image);
	image_jl.setLocation(1,1);
	image_jl.setSize(800,500);
	
	JLabel menutext = new JLabel("메뉴목록");
	menutext.setSize(100,100);
	menutext.setLocation(675, 10);
	menutext.setForeground(Color.white);
	
	JButton startbutton = new JButton("START");
	startbutton.setSize(100, 30);
	startbutton.setLocation(650, 80);
	startbutton.addActionListener(new Listener(this));
	
	JButton gamehelpbutton = new JButton("게임설명");
	gamehelpbutton.setSize(100, 30);
	gamehelpbutton.setLocation(650, 120);
	
	gamehelpbutton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			ImageIcon image_1 = new ImageIcon("helpmain.png"); //imageicon 으로 이미지 불러오기 이미지 경로
			JLabel image_j2 = new JLabel(image_1);  // jlabel 로 이미지 설정
			image_j2.setLocation(1, 1); //back 위치
			image_j2.setSize(100,100);// 위치
			JFrame help = new JFrame("게임설명(Help)");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 
			help.add(image_j2); //헬프페이지에 라벨 추가
			help.setSize(690,600);
			help.setLocation(350,100);
			help.setResizable(true);
			help.setVisible(true);
			} 
		});
		
		  
		JButton exitbutton = new JButton("exit");
		exitbutton.setSize(100, 30);
		exitbutton.setLocation(650, 160);
	  
	
		exitbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			} 
		});
	
		JLabel CREDITtext = new JLabel("CREDIT");
		CREDITtext.setSize(100,30);
		CREDITtext.setLocation(657, 320);
		CREDITtext.setForeground(Color.white);
		
		JLabel CREDITNAME1 = new JLabel("김성수");
		CREDITNAME1.setSize(100,30);
		CREDITNAME1.setLocation(657, 340);
		CREDITNAME1.setForeground(Color.white);
		 
		JLabel CREDITNAME2 = new JLabel("박경훈");
		CREDITNAME2.setSize(100,30);
		CREDITNAME2.setLocation(657, 360);
		CREDITNAME2.setForeground(Color.white);
		
		JLayeredPane display = new JLayeredPane();
		display.add(image_jl, JLayeredPane.DEFAULT_LAYER);
		display.add(menutext, JLayeredPane.POPUP_LAYER);
		display.add(startbutton, JLayeredPane.POPUP_LAYER);
		display.add(gamehelpbutton, JLayeredPane.POPUP_LAYER);
		display.add(exitbutton, JLayeredPane.POPUP_LAYER);
		display.add(CREDITtext, JLayeredPane.POPUP_LAYER);
		display.add(CREDITNAME1, JLayeredPane.POPUP_LAYER);
		display.add(CREDITNAME2, JLayeredPane.POPUP_LAYER);
	
		display.setSize(800, 500);
		ctn.add(display);
		setResizable(false);
		setSize(800,500);
		setLocation(450,200);
		setVisible(true);
	
	}
	/////////////////////


	private class Listener implements ActionListener{
		Main_ m;
		
		Listener(Main_ m){
			this.m = m;
		}
		
		public void actionPerformed(ActionEvent e){
			Main_Select ms = new Main_Select(m);
			m.setVisible(false);
		}
	}
	
	void Return(Game g){
		this.setVisible(true);
		g.setVisible(false);
		g.itbar_p1.setVisible(false);
		g.itbar_p2.setVisible(false);
		g.itbar_p1 = null;
		g.itbar_p2 = null;
		g = null;
	}

	public static void main(String [] args){
		Main_ m = new Main_();
	} 
}

