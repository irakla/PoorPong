package Pong_Game;

import java.awt.*;

import javax.swing.*;

public class Ball extends JLabel{
	boolean moving = false;		//���� ������ ���� ����
	static boolean item_speed = false;	//��Ӿ����� ��뿩��
	
	ItemBar it_p1, it_p2;
	
	int limitwidth_left, limitwidth_right,
		limitheight_upside, limitheight_downside;			//���� ���� �� �ִ� ��ġ ����
	
	double x = 0, y = 0;
	
	Game gameclass;
	
	byte angle;										//���� ����
	static final int ANGLELENGTH = 10;						//������ ������ ������ ���̴���
	boolean direction_left;									//���� ���� - true : ����, false : ������
	static int SPEED_DEFAULT; 							//�⺻ ���� �ӵ�
	private int speed;						//���� ���� �ӵ�
	
	boolean item;
	
	Ball(ImageIcon img){
		super(img);
	}
	
	JLabel p1_bar, p2_bar;
	
	void Set(Game g, boolean item){										//�����̳� �Է¼����� �����ڿ����� ��ü ���� �Ŀ� �Ϸ��� ����
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
		if(rand == 0)				//���� �ʱ� ���⼳�� ����
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
		Moving();	//�����̰�
		Sensing();	//����
	}				//���� ����
	
	void Sensing(){			//�� ��ġ ����&ó��
		int bar_y;
		
		if(getX() < limitwidth_left && getX() > limitwidth_left - speed * 2
				&& (getY() <= p1_bar.getY() + p1_bar.getHeight() && getY() >= p1_bar.getY() - getHeight())){
						//p1���뿡 �ε�������, ���� x��ǥ�� p1������ x��ǥ�� ��ġ������ && ���� y����̰� p1����y����� ���ʿ� ������ 
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
						//p2���뿡 �ε�������, ���� x��ǥ�� p1������ x��ǥ + ���ǰ��α��̿� ��ġ������ && ���� y����̰� p1����y����� ���ʿ� ������
			bar_y = p2_bar.getY();
			angle = (byte)((getY() + getHeight() - bar_y) / ANGLELENGTH);
			if(angle > 10)
				angle = 10;
			direction_left = true;
			
			if(item)
				it_p2.Smashed();
		}
		
		if(getY() < limitheight_upside + 10){
					//���� �� ���� �������
			AngleModify();
			setLocation(getX(), 10);
		}
		
		if(getY() > limitheight_downside - getHeight()){
					//���� �Ʒ��� �������
			AngleModify();
			setLocation(getX(), Game.GAMESIZE_WP.height - 30 - getHeight());
		}
		
		if(getX() < limitwidth_left - 80){
			gameclass.Score(Game.SCORE_P2WIN);///////////////////////////// ���ھ�ó��!!
			gameclass.GameStart();
		}
		if(getX() > limitwidth_right + 80){
			gameclass.Score(Game.SCORE_P1WIN);
			gameclass.GameStart();
		}
	}
	
	void Moving(){				//��ġ ������ ���� �ൿ
		int direction;
		
		if(direction_left)
			direction = -1;
		else
			direction = 1;
		
		switch(angle){				//���� ó��, 0;�������κ�, 10;����Ʒ��κ�
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
	 * 			������ �޼ҵ�
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
