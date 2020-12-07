package Pong_Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Item extends JLabel{
	protected Ball b;
	
	static enum itemnum{FASTER, ANGLEMODIFIER, DIRECTRELEASE, GUSAILSANG, NULL};
	
	Item(Game g, ImageIcon i){
		super(i);
		b = (Ball)(g.getLabel(0));
	}
	
	abstract void ItemAction();
}

class Faster extends Item{
	static final String img = "Item_Fast.png";
	
	Faster(Game g) {
		super(g, new ImageIcon(img));
	}

	void ItemAction(){
		b.SetSpeedItem();
	}
}

class AngleModifier extends Item{
	static final String img = "Item_Angle.png";
	
	AngleModifier(Game g){
		super(g, new ImageIcon(img));
	}
	
	void ItemAction(){
		b.AngleModify();
	}
}

class DirectRelease extends Item{
	static final String img = "Item_director.png";
	
	DirectRelease(Game g){
		super(g, new ImageIcon(img));
	}
	
	void ItemAction(){
		b.SetDirect();
	}
}

class Gusailsang extends Item{
	static final String img = "Item_941.png";
	
	Gusailsang(Game g){
		super(g, new ImageIcon(img));
	}
	
	void ItemAction(){
		b.SetDirect_Backward();
	}
}