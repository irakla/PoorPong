package Pong_Game;

import java.awt.*;

import javax.swing.*;

public class Ball extends JLabel{
	boolean moving = false;		//공의 움직임 여부 설정
	static boolean item_speed = false;	//배속아이템 사용여부
	
	ItemBar it_p1, it_p2;
	
	int limitwidth_left, limitwidth_right,
		limitheight_upside, limitheight_downside;			//공이 가질 수 있는 위치 제한
	
	double x = 0, y = 0;
	
	Game gameclass;
	
	byte angle;										//공의 각도
	static final int ANGLELENGTH = 10;						//각도를 구별할 막대의 길이단위
	boolean direction_left;									//공의 방향 - true : 왼쪽, false : 오른쪽
	static int SPEED_DEFAULT; 							//기본 게임 속도
	private int speed;						//현재 게임 속도
	
	boolean item;
	
	Ball(ImageIcon img){
		super(img);
	}
	
	JLabel p1_bar, p2_bar;
	
	void Set(Game g, boolean item){										//컨테이너 입력순서상 생성자역할을 객체 생성 후에 하려는 목적
		speed = SPEED_DEFAULT = 8;
		angle = 5;
		gameclass = g;
		limitwidth_left = g.getLabel(1).getX() + Game.GAMESIZE_BAR.width;
		limitwidth_right = g.getLabel(2).getX();
		limitheight_upside = 0;
		limitheight_downside = Game.GAMESIZE_WP.height - 30;
		
		p1_bar = g.getLabel(1);
		p2_bar = g.getLabel(2);
		
		int rand = (int)(Math.random() * 2);
		if(rand == 0)				//게임 초기 방향설정 랜덤
			direction_left = true;
		else
			direction_left = false;
		
		this.item = item;
	}
	
	void setItemBar(ItemBar p1, ItemBar p2){
		this.it_p1 = p1;
		this.it_p2 = p2;
	}
	
	void Speedy(){
		SPEED_DEFAULT += 2;
		speed = SPEED_DEFAULT;
	}
	
	void AngleModify(){
		if(angle == 0)
			angle = 10;
		else if(angle == 1)
			angle = 9;
		else if(angle == 2)
			angle = 8;
		else if(angle == 3)
			angle = 7;
		else if(angle == 4)
			angle = 6;
		else if(angle == 10)
			angle = 0;
		else if(angle == 9)
			angle = 1;
		else if(angle == 8)
			angle = 2;
		else if(angle == 7)
			angle = 3;
		else if(angle == 6)
			angle = 4;
	}
	
	void Gaming(){
		Moving();	//움직이고
		Sensing();	//감지
	}				//게임 동작
	
	void Sensing(){			//공 위치 감지&처리
		int bar_y;
		
		if(getX() < limitwidth_left && getX() > limitwidth_left - speed * 2
				&& (getY() <= p1_bar.getY() + p1_bar.getHeight() && getY() >= p1_bar.getY() - getHeight())){
						//p1막대에 부딪혔을때, 공의 x좌표가 p1막대의 x좌표와 일치했을때 && 공의 y축길이가 p1막대y축길이 안쪽에 있을때 
			bar_y = p1_bar.getY();
			angle = (byte)((getY() + getHeight() - bar_y) / ANGLELENGTH);
			if(angle > 10)
				angle = 10;
			direction_left = false;
			
			if(item)
				it_p1.Smashed();
		}
		else if(getX() > limitwidth_right - getWidth() && getX() < limitwidth_right - getWidth() + speed * 2
				&& (getY() <= p2_bar.getY() + p2_bar.getHeight() && getY() >= p2_bar.getY() - getHeight())){
						//p2막대에 부딪혓을때, 공의 x좌표가 p1막대의 x좌표 + 공의가로길이와 일치했을때 && 공의 y축길이가 p1막대y축길이 안쪽에 있을때
			bar_y = p2_bar.getY();
			angle = (byte)((getY() + getHeight() - bar_y) / ANGLELENGTH);
			if(angle > 10)
				angle = 10;
			direction_left = true;
			
			if(item)
				it_p2.Smashed();
		}
		
		if(getY() < limitheight_upside + 10){
					//공이 맵 위에 닿았을때
			AngleModify();
			setLocation(getX(), 10);
		}
		
		if(getY() > limitheight_downside - getHeight()){
					//공이 아래에 닿앗을때
			AngleModify();
			setLocation(getX(), Game.GAMESIZE_WP.height - 30 - getHeight());
		}
		
		if(getX() < limitwidth_left - 80){
			gameclass.Score(Game.SCORE_P2WIN);///////////////////////////// 스코어처리!!
			gameclass.GameStart();
		}
		if(getX() > limitwidth_right + 80){
			gameclass.Score(Game.SCORE_P1WIN);
			gameclass.GameStart();
		}
	}
	
	void Moving(){				//위치 감지에 따른 행동
		int direction;
		
		if(direction_left)
			direction = -1;
		else
			direction = 1;
		
		switch(angle){				//각도 처리, 0;가장윗부분, 10;가장아랫부분
		case 0:
			x += speed * direction * Math.cos((75.0 / 180.0) * Math.PI);
			y += speed * Math.sin(-(75.0 / 180.0) * Math.PI);
			break;
		case 1:
			x += speed * direction * Math.cos((45.0 / 180.0) * Math.PI);
			y += speed * Math.sin(-(45.0 / 180.0) * Math.PI);
			break;
		case 2:
			x += speed * direction * Math.cos((20.0 / 180.0) * Math.PI);
			y += speed * Math.sin(-(20.0 / 180.0) * Math.PI);
			break;
		case 3:
			x += speed * direction * Math.cos((10.0 / 180.0) * Math.PI);
			y += speed * Math.sin(-(10.0 / 180.0) * Math.PI);
			break;
		case 4:
			x += speed * direction * Math.cos((5.0 / 180.0) * Math.PI);
			y += speed * Math.sin(-(5.0 / 180.0) * Math.PI);
			break;
		case 5:
			x += speed * direction;
			break;
		case 6:
			x += speed * direction * Math.cos((5.0 / 180.0) * Math.PI);
			y += speed * Math.sin((5.0 / 180.0) * Math.PI);
			break;
		case 7:
			x += speed * direction * Math.cos((10.0 / 180.0) * Math.PI);
			y += speed * Math.sin((10.0 / 180.0) * Math.PI);
			break;
		case 8:
			x += speed * direction * Math.cos((20.0 / 180.0) * Math.PI);
			y += speed * Math.sin((20.0 / 180.0) * Math.PI);
			break;
		case 9:
			x += speed * direction * Math.cos((45.0 / 180.0) * Math.PI);
			y += speed * Math.sin((45.0 / 180.0) * Math.PI);
			break;
		case 10:
			x += speed * direction * Math.cos((75.0 / 180.0) * Math.PI);
			y += speed * Math.sin((75.0 / 180.0) * Math.PI);
			break;
		}
		if(x > 1.0 || x < -1.0){
			setLocation(getX() + (int)x, getY());
			x -= (int)x;
		}
		if(y > 1.0 || y < -1.0){
			setLocation(getX(), getY() + (int)y);
			y -= (int)y;
		}
	}
	
	
	/**********************************************
	 * 
	 * 			아이템 메소드
	 * 
	 * ********************************************/
	void SetSpeedItem(){
		if(!item_speed){
			this.speed *= 2;
			item_speed = true;
		}
	}
	
	void SetDirect(){
		if(getX() > limitwidth_left && getX() < limitwidth_right)
			this.direction_left = !this.direction_left;
	}
	
	void SetDirect_Backward(){
		if(getX() < limitwidth_left || getX() > limitwidth_right)
			this.direction_left = !this.direction_left;
	}
}
