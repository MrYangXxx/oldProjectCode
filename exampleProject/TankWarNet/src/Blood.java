import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Blood {
	int x;
	int y;
	private boolean live=true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	
	
	public Blood() {
		x=500;
		y=505;
	}

	public void draw(Graphics g,Tank t){
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.drawRect(10, 80, 100, 20);
		g.fillRect(10, 80, t.getLife(), 20);
		g.setColor(c);
	}
	
	public void addBlood(Graphics g,int hc){
		if(!live)return;
		Color c=g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, 20, 20);
		g.setColor(c);
		move(hc);
	}
	
	private void move(int hc){
		if(hc==10||hc==25){
			x=350;
			y=300;
		}else if(hc==15||hc==32){
			x=800;
			y=500;
		}else if(hc==40){
			x=600;
			y=450;
		}else{
			x=0;
			y=0;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,20,20);
	}
	
}
