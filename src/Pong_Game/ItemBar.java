package Pong_Game;

import java.awt.*;
import java.awt.Event.*;
import javax.swing.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class ItemBar extends JFrame{
	Game g;
	
	private int smashed = 0;
	
	static final Dimension SIZE = new Dimension(80, 320);	//������â ������
	static final Dimension ITEMSIZE = new Dimension(40, 40);	//�����۾����� ������
	static final int DISPLAYNUM = 4;						//ǥ���� �������� ����
	private int display_now = 0;
	static final int NULLITEMNUM = 4;
	
	static final Point[] localist = new Point[DISPLAYNUM];		//�����۵��� ��ġ
	
	static final int ITEMCONDITION = 4;						//��� �ľ� �������� ������
	
	LinkedList<Item> itemline = new LinkedList<Item>();
		
	JLayeredPane lp = new JLayeredPane();
	
	ItemBar(Game g, ImageIcon img, int p){
		//////////////////////////////////////
		for(int i = 0; i < localist.length; i++){
		localist[i] = new Point(20, (int) (110 + (i - 1) * (ITEMSIZE.getHeight() + 10)));
		}
		//////////////////////////////////////��������ġlocalist ����
		
		this.g = g;
		///////////////////////////////////
		JLabel bg_label = new JLabel(img);
		bg_label.setSize(SIZE);
		bg_label.setOpaque(true);
		///////////////////////////////////����ȭ��
		
		///////////////////////////////////
		JLabel text_label = new JLabel("ITEM");
		Font text_font = new Font("text", Font.PLAIN, 15);
		text_label.setFont(text_font);
		text_label.setForeground(Color.cyan);
		text_label.setSize(40, 15);
		text_label.setLocation(20, 40);
		///////////////////////////////////
		
		///////////////////////////////////
		lp.setName("display");
		lp.add(bg_label, JLayeredPane.DEFAULT_LAYER);
		lp.add(text_label, JLayeredPane.PALETTE_LAYER);
		///////////////////////////////////
		
		///////////////////////////////////
		switch(p){
		case 1:
			setLocation(g.getX() - SIZE.width, g.getY());
			break;
		case 2:
			setLocation(g.getX() + Game.GAMESIZE_WP.width, g.getY());
			break;
		}
		///////////////////////////////////��ġ ����
		
		//////////////////////////////////////
		getContentPane().add(lp);
		
		setUndecorated(true);
		setSize(SIZE);
		setResizable(false);
		setVisible(true);
		
		///////////////////////////////////////������â ��������
	}
	
	void Smashed(){
		if(smashed < ITEMCONDITION)
			smashed++;
		else{
			int itemnum = (int)(Math.random() * (double)(Item.itemnum.values().length + NULLITEMNUM));
				//�����ϰ� �������� ����
			
			if(itemnum < Item.itemnum.values().length)
				CreateItem(Item.itemnum.values()[itemnum]);
			else
				CreateItem(Item.itemnum.NULL);
			smashed = 0;
		}
	}				//�÷��̾� ����ٸ� ������ ����
	
	void CreateItem(Item.itemnum key){
		Item item = null;
		
		switch(key){
		case FASTER:
			item = new Faster(g);
			break;
		case ANGLEMODIFIER:
			item = new AngleModifier(g);
			break;
		case DIRECTRELEASE:
			item = new DirectRelease(g);
			break;
		case GUSAILSANG:
			item = new Gusailsang(g);
			break;
		case NULL:
			return;
		}
		
		if(display_now < DISPLAYNUM){
			item.setSize(40, 40);
			item.setLocation(localist[display_now++]);
			item.setVisible(true);
			lp.add(item, JLayeredPane.POPUP_LAYER);
		}
		
		itemline.addLast(item);
	}				//������ ���� �޼ҵ�
	
	void UseItem(int keycode){
		if(keycode == 1){
			if(!itemline.isEmpty()){
				itemline.getFirst().setVisible(false);
				itemline.getFirst().ItemAction();
				itemline.removeFirst();
			}
			else
				return;
		}
		else if(keycode == 2){
			if(itemline.size() > 1){
				itemline.get(1).setVisible(false);
				itemline.get(1).ItemAction();
				itemline.remove(1);
			}
			else
				return;
		}
		int i;
		
		for(i = 0, display_now = 0; i < itemline.size() && i < DISPLAYNUM - 1; i++){
			itemline.get(i).setLocation(localist[i]);
			display_now++;
		}
		
		if(i == DISPLAYNUM - 1 && itemline.size() >= DISPLAYNUM){
			itemline.get(DISPLAYNUM - 1).setSize(40, 40);
			itemline.get(DISPLAYNUM - 1).setLocation(localist[3]);
			itemline.get(DISPLAYNUM - 1).setVisible(true);
			lp.add(itemline.get(DISPLAYNUM - 1), JLayeredPane.POPUP_LAYER);
			display_now++;
		}
	}
	
	void GameSet(){
		int i;
		for(i = 0; i < display_now; i++){
			itemline.get(0).setVisible(false);
			itemline.remove(0);
		}
		itemline.clear();
		display_now = 0;
		smashed = 0;
	}
}
