package Pong_Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Game extends JFrame{
	private int score_p1 = 0, score_p2 = 0;
	
	static final String GAMETEXT_DISPLAY = "Display";		//����ȭ�� ��Ī Ű����
	static final String GAMETEXT_PLAYBAR1 = "P1";			//1p ���� ��Ī Ű����
	static final String GAMETEXT_PLAYBAR2 = "P2";			//2p ���� ��Ī Ű����
	static final String GAMETEXT_BALL = "BALL";				//�� ��Ī Ű����
	static final String GAMETEXT_SCOREBOARD = "ScoreBoard";	//������ �г� ��Ī Ű����
	static final String GAMETEXT_SCORE = "Score";	//������ ��Ī Ű����
			
	static final Dimension GAMESIZE_WP = new Dimension(640, 480);		//����ȭ�� ũ��
	static final Dimension GAMESIZE_BALL = new Dimension(30, 30);		//�� ũ��
	static final Point GAMELOCA_BALL = new Point(GAMESIZE_WP.width / 2 - GAMESIZE_BALL.width / 2, GAMESIZE_WP.height / 2 - GAMESIZE_BALL.height / 2);
	static final Dimension GAMESIZE_BAR = new Dimension(20, 80);		//�÷��̾�� ũ��
	
	static final int SCORE_P1WIN = 1;
	static final int SCORE_P2WIN = 2;
	
	ItemBar itbar_p1;
	ItemBar itbar_p2;
	
	static final int MATCHPOINT = 5;
	
	private Pressed prstate = new Pressed();		//Ű�� ���� ���� ��ü
	
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
		///////////////////////////////////////////â �⺻ ����
		
		Container ctn = getContentPane();
		
		///////////////////////////////////////////
		ImageIcon wp_ii = new ImageIcon("wp.png");
		JLabel wp_jl = new JLabel(wp_ii);
		wp_jl.setSize(GAMESIZE_WP);
		wp_jl.setLocation(0, 0);
		//////////////////////////////////////////���ȭ��
		
		/////////////////////////////////////////////////
		Ball ball_jl = new Ball(new ImageIcon("ball.png"));		ball_jl.setName(GAMETEXT_BALL);
		ball_jl.setSize(GAMESIZE_BALL);
		ball_jl.setLocation(GAMELOCA_BALL.x, GAMELOCA_BALL.y);
		/////////////////////////////////////////////////��
		
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
		//////////////////////////////////////////////////���ھ��
		
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
		/////////////////////////////////////////////////�г�, �����̳� ��Ʈ��
		
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
		////////////////////////////////////////////////â�⺻����

		this.m = m;
		
		/////////////////////////////////////////////
		Runnable game_r = new GameThread(this);
		if(item)
			((GameThread)game_r).setItemBar(itbar_p1, itbar_p2);
		Thread game_t = new Thread(game_r);
		GameStart();
		game_t.start();
		////////////////////////////////////////////������ ������  
	}
	
	/**************************************************************************
	 * 
	 *				���� ���� �޼ҵ�
	 *
	 **************************************************************************/
	
	void Score(int cond){
		if(cond == SCORE_P1WIN){			//1�÷��̾ �¸��� ��Ȳ
			score_p1++;
			getScore().setText(Integer.toString(score_p1) + "   :   " + Integer.toString(score_p2));
			if(item){
				itbar_p1.GameSet();
				itbar_p2.GameSet();
			}
		}
		else if(cond == SCORE_P2WIN){		//2�÷��̾ �¸��� ��Ȳ
			score_p2++;
			getScore().setText(Integer.toString(score_p1) + "   :   " + Integer.toString(score_p2));
			if(item){
				itbar_p1.GameSet();
				itbar_p2.GameSet();
			}
		}
		
		if(score_p1 == MATCHPOINT || score_p2 == MATCHPOINT){			//������ �Ѹ��� �¸��� ��Ȳ
			JLabel victory = null;
			if(score_p1 == MATCHPOINT){			//1p�� ��ġ����Ʈ �޼����� �̰�����
				victory = new JLabel("P1's Victory!");
				victory.setFont(new Font("vicfont", Font.PLAIN, 30));
				victory.setForeground(Color.magenta);
				victory.setSize(250, 30);
				victory.setLocation(Game.GAMESIZE_WP.width / 2 - victory.getWidth() / 2, Game.GAMESIZE_WP.height / 2 - victory.getHeight() / 2);
				victory.setVisible(true);
				getDisplay().add(victory, JLayeredPane.POPUP_LAYER);	//�¸��ؽ�Ʈ ���
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			//5������
				//System.exit(0);
				m.Return(this);
			}
			if(score_p2 == MATCHPOINT){			//2p�� ��ġ����Ʈ �޼����� �̰�����
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
				}			//5�� ����
				//System.exit(0);
				m.Return(this);
			}
		}
	}
	
	public void GameStart(){
		GameThread.gaming = false;
		
		JLabel num;
		JLayeredPane display = this.getDisplay();
		Font f = new Font("number", Font.PLAIN, 70);		//���� ũ��
		Ball ball = (Ball)this.getLabel(0);
		ball.setLocation(GAMELOCA_BALL.x, GAMELOCA_BALL.y);
		getLabel(1).setLocation(GAMELOCA_P1BAR);
		getLabel(2).setLocation(GAMELOCA_P2BAR);
		ball.Set(this, item);
		
		for(int i = 3; i > 0; i--){							//3, 2, 1
			num = new JLabel(Integer.toString(i));
			num.setFont(f);
			display.add(num);								//display ���̾���гο�
			display.setLayer(num, JLayeredPane.DRAG_LAYER);	//num�߰�
			num.setSize(70, 70);
			num.setLocation(getSize().width / 30, 120);
			for(int j = 0; j < 30; j++){
				long frame = System.currentTimeMillis();
				while(true){
					if(System.currentTimeMillis() > frame + (long)(500 / 30))
						break;
				}					//���� ���
				num.setLocation(getSize().width * j / 30, 120);
			}
			display.remove(num);							//display ���̾�� �гο��� ����
			num = null;						//num ��ȯ
		}
		
		GameThread.gaming = true;
	}
	
	/***************************************************************************
	 * 
	 * 				���� �⺻ ���� �޼ҵ�
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
		else if(source == 1)				//1p�� ���븦 ��ȯ�ް��� �Ҷ�
			ptext = GAMETEXT_PLAYBAR1;
		else if(source == 2)
			ptext = GAMETEXT_PLAYBAR2;		//2p�� ���븦 ��ȯ�ް��� �Ҷ�
		else if(source == 0)
			ptext = GAMETEXT_BALL;
		//////////////////////////////////////////////��ȯ���� JLabel���
		
		///////////////////////////
		Component[] cmpnts = getContentPane().getComponents();	//ã�� ��� : Game��ü �����̳� ���� ������Ʈ�� 
		JLayeredPane display = null;			//
		for(int i = 0; i < cmpnts.length; i++)
			if(cmpnts[i].getName() == null)
				continue;
			else if(cmpnts[i].getName().equals(GAMETEXT_DISPLAY)){		//display ������Ʈ�� ã������
				display = (JLayeredPane)cmpnts[i];
				break;
			}
		if(display == null){			//display ������Ʈ�� ã�� ���ߴٸ�
			System.out.println("Error - Search to 'display' was failed" + source);
			return null;
		}
		///////////////////////////// �����̳ʻ� �˻�
		
		//////////////////////////////
		cmpnts = display.getComponents();						//ã�� ��� : display ������Ʈ ���� ������Ʈ��
		for(int i = 0; i < cmpnts.length; i++){
			if(cmpnts[i].getName() == null)
				continue;
			else if(cmpnts[i].getName().equals(ptext))
				return (JLabel)cmpnts[i];
		}
		///////////////////////////////display ������Ʈ�� �˻�
		return null;			//�÷��̾� JLabel������Ʈ�� ã�� ���ߴٸ�
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
		}			//������ �г�ã��
		
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
		
		//if(������)
		return null;
	}			//����ǥ �� ��ȯ �޼ҵ�
	
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
			prstate.set(e, true);			//�ش� Ű ���������� ó��
		}
		public void keyReleased(KeyEvent e){
			prstate.set(e, false);			//�ش� Ű �ȴ��������� ó��
		}
	}
	
	//public static void main(String[] args) {
	//	Game f = new Game();
	//}
}

