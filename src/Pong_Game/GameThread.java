package Pong_Game;

import java.awt.*;
import javax.swing.JLabel;

public class GameThread implements Runnable{
	JLabel p1, p2;				//p1, p2의 막대바를 가리킴
	Pressed prstate;			//키보드 입력 정보
	int mapheight;				//맵의 높이
	
	Game g;
	
	private long msecond_prev_bar;
	private long msecond_prev_gs;
	private long msecond_prev_itemstate;
	private long BARSPEED = 25;			//플레이어 막대의 속도를 결정, 낮을수록 빨라짐
	private long msecond_prev_ball;
	private long BALLSPEED = 20;
	
	private final long BALL_PERIOD = 1000 * 15;
	private final long ITEM_PERIOD = 1000;
	static long itemuse_prev[] = {0, 0, 0, 0};
	
	ItemBar it_p1, it_p2;
	
	static boolean gaming = false;							//게임중 여부
	Ball b;
	
	GameThread(Game g){
		if((p1 = g.getLabel(1)) == null){				//p1의 막대바 설정
			System.out.println("Get to `p1 JLabel` was Failed");
			System.exit(0);
		}
		if((p2 = g.getLabel(2)) == null){				//p2의 막대바설정
			System.out.println("Get to `p2 JLabel` was Failed");
			System.exit(0);
		}
		prstate = g.getPressed();					//키눌려짐여부객체 설정
		mapheight = g.getSize().height;				//맵높이 설정
		
		msecond_prev_bar = msecond_prev_ball = System.currentTimeMillis();
		b = (Ball)g.getLabel(0);
		
		this.g = g;
	}
	
	void setItemBar(ItemBar p1, ItemBar p2){
		this.it_p1 = p1;
		this.it_p2 = p2;
	}
	
	public void run(){
		while(true){
			
			if(System.currentTimeMillis() > msecond_prev_bar + BARSPEED){
				if(prstate.getW()){				//1p 위로
					if(p1.getLocation().y - 10 > 0)
						p1.setLocation(p1.getLocation().x, p1.getLocation().y -= 10);
				}
				if(prstate.getS()){				//1p 아래로
					if(p1.getLocation().y + Game.GAMESIZE_BAR.height + 20 < mapheight)
						p1.setLocation(p1.getLocation().x, p1.getLocation().y += 10);
				}
				if(prstate.getUp()){			//2p 위로
					if(p2.getLocation().y - 10 > 0)
						p2.setLocation(p2.getLocation().x, p2.getLocation().y -= 10);
				}
				if(prstate.getDn()){			//2p 아래로
					if(p2.getLocation().y + Game.GAMESIZE_BAR.height + 20 < mapheight)
						p2.setLocation(p2.getLocation().x, p2.getLocation().y += 10);
				}
				msecond_prev_bar = System.currentTimeMillis();
			}
			if(gaming && System.currentTimeMillis() > msecond_prev_ball + BALLSPEED){
				b.Gaming();
				msecond_prev_ball = System.currentTimeMillis();
			}
			
			///////////////
			
			if(System.currentTimeMillis() > msecond_prev_gs + BALL_PERIOD){
				b.Speedy();
				b.item_speed = false;
				msecond_prev_gs = System.currentTimeMillis();
			}			//BALL_PERIODms마다 속도증가
			
			///////////////아이템 스레드 쓸곳
			if(prstate.getQ()){
				it_p1.UseItem(1);
				itemuse_prev[0] = System.currentTimeMillis();
				prstate.setQ();
			}
			if(prstate.getA()){
				it_p1.UseItem(2);
				itemuse_prev[1] = System.currentTimeMillis();
				prstate.setA();
			}
			if(prstate.getSemi()){
				it_p2.UseItem(1);
				itemuse_prev[2] = System.currentTimeMillis();
				prstate.setSemi();
			}
			if(prstate.getDot()){
				it_p2.UseItem(2);
				itemuse_prev[3] = System.currentTimeMillis();
				prstate.setDot();
			}
		}
	}
}
