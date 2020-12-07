package Pong_Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Game extends JFrame{
	private int score_p1 = 0, score_p2 = 0;
	
	static final String GAMETEXT_DISPLAY = "Display";		//게임화면 지칭 키워드
	static final String GAMETEXT_PLAYBAR1 = "P1";			//1p 막대 지칭 키워드
	static final String GAMETEXT_PLAYBAR2 = "P2";			//2p 막대 지칭 키워드
	static final String GAMETEXT_BALL = "BALL";				//공 지칭 키워드
	static final String GAMETEXT_SCOREBOARD = "ScoreBoard";	//점수판 패널 지칭 키워드
	static final String GAMETEXT_SCORE = "Score";	//점수판 지칭 키워드
			
	static final Dimension GAMESIZE_WP = new Dimension(640, 480);		//게임화면 크기
	static final Dimension GAMESIZE_BALL = new Dimension(30, 30);		//공 크기
	static final Point GAMELOCA_BALL = new Point(GAMESIZE_WP.width / 2 - GAMESIZE_BALL.width / 2, GAMESIZE_WP.height / 2 - GAMESIZE_BALL.height / 2);
	static final Dimension GAMESIZE_BAR = new Dimension(20, 80);		//플레이어막대 크기
	
	static final int SCORE_P1WIN = 1;
	static final int SCORE_P2WIN = 2;
	
	ItemBar itbar_p1;
	ItemBar itbar_p2;
	
	static final int MATCHPOINT = 5;
	
	private Pressed prstate = new Pressed();		//키의 눌린 상태 객체
	
	private Point GAMELOCA_P1BAR;
	private Point GAMELOCA_P2BAR;
	
	private Main_ m;
	
	private boolean item;
	
	public Game(Main_ m, boolean item){
		this.item = item;
		prstate.setItem(item);
		
		///////////////////////////////////////////
		setTitle("Project What?!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 200);
		setSize(GAMESIZE_WP);
		///////////////////////////////////////////창 기본 설정
		
		Container ctn = getContentPane();
		
		///////////////////////////////////////////
		ImageIcon wp_ii = new ImageIcon("wp.png");
		JLabel wp_jl = new JLabel(wp_ii);
		wp_jl.setSize(GAMESIZE_WP);
		wp_jl.setLocation(0, 0);
		//////////////////////////////////////////배경화면
		
		/////////////////////////////////////////////////
		Ball ball_jl = new Ball(new ImageIcon("ball.png"));		ball_jl.setName(GAMETEXT_BALL);
		ball_jl.setSize(GAMESIZE_BALL);
		ball_jl.setLocation(GAMELOCA_BALL.x, GAMELOCA_BALL.y);
		/////////////////////////////////////////////////공
		
		////////////////////////////////////////
		Font score_f = new Font("Score", Font.PLAIN, 15);
		Dimension score_d = new Dimension(getWidth() * 3 / 10, getHeight() / 5);	//192, 96
		
		JLayeredPane score = new JLayeredPane();			score.setName(GAMETEXT_SCOREBOARD);
		score.setLayout(null);
		score.setSize(score_d);
		
		JLabel score_img = new JLabel(new ImageIcon("score.png"));
		score_img.setSize(score_d);
		score_img.setLocation(0, 0);
		
		JLabel score_text = new JLabel("Scoreboard");
		score_text.setSize(120, 20);
		score_text.setLocation(score.getWidth() / 2 - score_text.getWidth() / 2, 0);
		score_text.setFont(score_f);
		
		JLabel score_jl = new JLabel(Integer.toString(score_p1) + "   :   " + Integer.toString(score_p2));		score_jl.setName(GAMETEXT_SCORE);
		score_jl.setSize(50, 20);
		score_jl.setLocation(score.getWidth() / 2 - score_jl.getWidth() / 2, score.getHeight() / 2 - score_jl.getHeight() / 2);
		score_jl.setFont(score_f);
		
		score.add(score_text, JLayeredPane.POPUP_LAYER);
		score.add(score_jl, JLayeredPane.POPUP_LAYER);
		score.add(score_img, JLayeredPane.DEFAULT_LAYER);
		score.setLocation(getWidth() / 2 - score.getWidth() / 2, getHeight() / 10 - score.getHeight() / 2);
		//////////////////////////////////////////////////스코어보드
		
		//////////////////////////////////////////////////////////
		JLabel p1 = new JLabel(new ImageIcon("p1.png"));	p1.setName(GAMETEXT_PLAYBAR1);
		p1.setSize(GAMESIZE_BAR);
		GAMELOCA_P1BAR = new Point(100, getHeight() / 2 - p1.getHeight() / 2);
		p1.setLocation(GAMELOCA_P1BAR);
		
		JLabel p2 = new JLabel(new ImageIcon("p2.png"));	p2.setName(GAMETEXT_PLAYBAR2);
		p2.setSize(GAMESIZE_BAR);
		GAMELOCA_P2BAR = new Point(getWidth() - 100 - GAMESIZE_BAR.width, getHeight() / 2 - p2.getHeight() / 2);
		p2.setLocation(GAMELOCA_P2BAR);
		//////////////////////////////////////////////////////////p1, p2
		
		/////////////////////////////////////////////////////
		JLayeredPane display = new JLayeredPane();	display.setName(GAMETEXT_DISPLAY);
		ctn.add(display);
		//ctn.add(itbar_p1);
		display.add(wp_jl, JLayeredPane.DEFAULT_LAYER);
		display.add(score, JLayeredPane.PALETTE_LAYER);
		display.add(p1, JLayeredPane.POPUP_LAYER);
		display.add(p2, JLayeredPane.POPUP_LAYER);
		ball_jl.Set(this, item);
		display.add(ball_jl, JLayeredPane.POPUP_LAYER);
		display.setSize(getWidth(), getHeight());
		/////////////////////////////////////////////////패널, 컨테이너 컨트롤
		
		/////////////////////////////////////////////////////
		if(item){
			itbar_p1 = new ItemBar(this, new ImageIcon("itembar1.png"), 1);
			itbar_p2 = new ItemBar(this, new ImageIcon("itembar1.png"), 2);
			ball_jl.setItemBar(itbar_p1, itbar_p2);
		}
		/////////////////////////////////////////////////////

		/////////////////////////////////////////////////
		setVisible(true);
		setResizable(false);
		this.addKeyListener(new EventHandler());
		////////////////////////////////////////////////창기본설정

		this.m = m;
		
		/////////////////////////////////////////////
		Runnable game_r = new GameThread(this);
		if(item)
			((GameThread)game_r).setItemBar(itbar_p1, itbar_p2);
		Thread game_t = new Thread(game_r);
		GameStart();
		game_t.start();
		////////////////////////////////////////////스레드 구현부  
	}
	
	/**************************************************************************
	 * 
	 *				게임 동작 메소드
	 *
	 **************************************************************************/
	
	void Score(int cond){
		if(cond == SCORE_P1WIN){			//1플레이어가 승리한 상황
			score_p1++;
			getScore().setText(Integer.toString(score_p1) + "   :   " + Integer.toString(score_p2));
			if(item){
				itbar_p1.GameSet();
				itbar_p2.GameSet();
			}
		}
		else if(cond == SCORE_P2WIN){		//2플레이어가 승리한 상황
			score_p2++;
			getScore().setText(Integer.toString(score_p1) + "   :   " + Integer.toString(score_p2));
			if(item){
				itbar_p1.GameSet();
				itbar_p2.GameSet();
			}
		}
		
		if(score_p1 == MATCHPOINT || score_p2 == MATCHPOINT){			//누군가 한명이 승리한 상황
			JLabel victory = null;
			if(score_p1 == MATCHPOINT){			//1p가 매치포인트 달성으로 이겼을때
				victory = new JLabel("P1's Victory!");
				victory.setFont(new Font("vicfont", Font.PLAIN, 30));
				victory.setForeground(Color.magenta);
				victory.setSize(250, 30);
				victory.setLocation(Game.GAMESIZE_WP.width / 2 - victory.getWidth() / 2, Game.GAMESIZE_WP.height / 2 - victory.getHeight() / 2);
				victory.setVisible(true);
				getDisplay().add(victory, JLayeredPane.POPUP_LAYER);	//승리텍스트 출력
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			//5초정지
				//System.exit(0);
				m.Return(this);
			}
			if(score_p2 == MATCHPOINT){			//2p가 매치포인트 달성으로 이겼을때
				victory = new JLabel("P2's Victory!");
				victory.setFont(new Font("vicfont", Font.PLAIN, 30));
				victory.setSize(250, 30);
				victory.setForeground(Color.magenta);
				victory.setLocation(Game.GAMESIZE_WP.width / 2 - victory.getWidth() / 2, Game.GAMESIZE_WP.height / 2 - victory.getHeight() / 2);
				victory.setVisible(true);
				getDisplay().add(victory, JLayeredPane.POPUP_LAYER);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			//5초 정지
				//System.exit(0);
				m.Return(this);
			}
		}
	}
	
	public void GameStart(){
		GameThread.gaming = false;
		
		JLabel num;
		JLayeredPane display = this.getDisplay();
		Font f = new Font("number", Font.PLAIN, 70);		//글자 크기
		Ball ball = (Ball)this.getLabel(0);
		ball.setLocation(GAMELOCA_BALL.x, GAMELOCA_BALL.y);
		getLabel(1).setLocation(GAMELOCA_P1BAR);
		getLabel(2).setLocation(GAMELOCA_P2BAR);
		ball.Set(this, item);
		
		for(int i = 3; i > 0; i--){							//3, 2, 1
			num = new JLabel(Integer.toString(i));
			num.setFont(f);
			display.add(num);								//display 레이어드패널에
			display.setLayer(num, JLayeredPane.DRAG_LAYER);	//num추가
			num.setSize(70, 70);
			num.setLocation(getSize().width / 30, 120);
			for(int j = 0; j < 30; j++){
				long frame = System.currentTimeMillis();
				while(true){
					if(System.currentTimeMillis() > frame + (long)(500 / 30))
						break;
				}					//숫자 띄움
				num.setLocation(getSize().width * j / 30, 120);
			}
			display.remove(num);							//display 레이어드 패널에서 삭제
			num = null;						//num 반환
		}
		
		GameThread.gaming = true;
	}
	
	/***************************************************************************
	 * 
	 * 				게임 기본 설정 메소드
	 * 
	 **************************************************************************/
	Pressed getPressed(){
		return prstate;
	}
	
	JLabel getLabel(int source){
		String ptext = "EMPTY";
		//////////////////////////////////////////////
		if(source != 1 && source != 2 && source != 0){
			System.out.println("Incorrect Input - sourceNumber");
			return null;
		}
		else if(source == 1)				//1p의 막대를 반환받고자 할때
			ptext = GAMETEXT_PLAYBAR1;
		else if(source == 2)
			ptext = GAMETEXT_PLAYBAR2;		//2p의 막대를 반환받고자 할때
		else if(source == 0)
			ptext = GAMETEXT_BALL;
		//////////////////////////////////////////////반환가능 JLabel목록
		
		///////////////////////////
		Component[] cmpnts = getContentPane().getComponents();	//찾을 대상 : Game객체 컨테이너 안의 컴포넌트들 
		JLayeredPane display = null;			//
		for(int i = 0; i < cmpnts.length; i++)
			if(cmpnts[i].getName() == null)
				continue;
			else if(cmpnts[i].getName().equals(GAMETEXT_DISPLAY)){		//display 컴포넌트를 찾았으면
				display = (JLayeredPane)cmpnts[i];
				break;
			}
		if(display == null){			//display 컴포넌트를 찾지 못했다면
			System.out.println("Error - Search to 'display' was failed" + source);
			return null;
		}
		///////////////////////////// 컨테이너상 검색
		
		//////////////////////////////
		cmpnts = display.getComponents();						//찾을 대상 : display 컴포넌트 안의 컴포넌트들
		for(int i = 0; i < cmpnts.length; i++){
			if(cmpnts[i].getName() == null)
				continue;
			else if(cmpnts[i].getName().equals(ptext))
				return (JLabel)cmpnts[i];
		}
		///////////////////////////////display 컴포넌트상 검색
		return null;			//플레이어 JLabel컴포넌트를 찾지 못했다면
	}
	
	JLabel getScore(){
		JLayeredPane display = getDisplay(), scoreboard = null;
		Component[] cmpnts = display.getComponents();
		for(int i = 0; i < cmpnts.length; i++){
			if(cmpnts[i].getName() == null)
				continue;
			else if(cmpnts[i].getName().equals(GAMETEXT_SCOREBOARD)){
				scoreboard = (JLayeredPane)cmpnts[i];
				break;
			}
		}			//점수판 패널찾기
		
		if(scoreboard == null){
			System.out.println(GAMETEXT_SCOREBOARD + " is Not Exist!");
			System.exit(0);
		}
		
		cmpnts = scoreboard.getComponents();
		for(int i = 0; i < cmpnts.length; i++)
			if(cmpnts[i].getName() == null)
				continue;
			else if(cmpnts[i].getName().equals(GAMETEXT_SCORE))
				return (JLabel)cmpnts[i];
		
		//if(없으면)
		return null;
	}			//점수표 라벨 반환 메소드
	
	JLayeredPane getDisplay(){
		Container ctn = getContentPane();
		Component[] cmpts = ctn.getComponents();
		for(int i = 0; i < cmpts.length; i++)
			if(cmpts[i].getName() == null)
				continue;
			else if(cmpts[i].getName().equals(GAMETEXT_DISPLAY))
				return (JLayeredPane)cmpts[i];
		return null;
	}
	
	class EventHandler implements KeyListener{
		public void keyTyped(KeyEvent e){
		}
		public void keyPressed(KeyEvent e){
			prstate.set(e, true);			//해당 키 눌린것으로 처리
		}
		public void keyReleased(KeyEvent e){
			prstate.set(e, false);			//해당 키 안눌린것으로 처리
		}
	}
	
	//public static void main(String[] args) {
	//	Game f = new Game();
	//}
}

