package Pong_Game;

import java.awt.event.KeyEvent;

class Pressed{
	 volatile boolean pressed_w = false, pressed_s = false,		//1p 위로, 1p 아래로
			pressed_up = false, pressed_dn = false;				//2p 위로, 2p 아래로
	 
	 volatile boolean pressed_q = false, pressed_a = false,		//1p 아이템1, 아이템2
			 pressed_semi = false, pressed_dot = false;			//2p 아이템1, 아이템2
	 
	 boolean item;
	 
	 void setItem(boolean item){
		 this.item = item;
	 }
	
	 void set(KeyEvent e, boolean state){				//state : true일때 Pressed, false일때 Released
		switch(e.getKeyCode()){
		case 87:
			pressed_w = state;
			break;
		case 83:
			pressed_s = state;
			break;
		case 38:
			pressed_up = state;
			break;
		case 40:
			pressed_dn = state;
			break;
		}
		if(state && item)
			switch(e.getKeyCode()){
			case 81:
				pressed_q = true;
				break;
			case 65:
				pressed_a = true;
				break;
			case 59:
				pressed_semi = true;
				break;
			case 47:
				pressed_dot = true;
				break;
			}
	}
	
	boolean getW(){
		return pressed_w;
	}
	
	boolean getS(){
		return pressed_s;
	}
	
	boolean getUp(){
		return pressed_up;
	}
	
	boolean getDn(){
		return pressed_dn;
	}
	
	boolean getQ(){
		return pressed_q;
	}
	
	boolean getA(){
		return pressed_a;
	}
	
	boolean getSemi(){
		return pressed_semi;
	}
	
	boolean getDot(){
		return pressed_dot;
	}
	
	void setQ(){
		pressed_q = false;
	}
	
	void setA(){
		pressed_a = false;
	}
	
	void setSemi(){
		pressed_semi = false;
	}
	
	void setDot(){
		pressed_dot = false;
	}
}