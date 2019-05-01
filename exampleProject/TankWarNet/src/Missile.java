import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;


public class Missile {
	private int x;
	private int y;
	public static final int SPEED=6;
	public static final int MWIDTH=10;
	public static final int MHEIGHT=10;
	public static int hitCount=0;
	
	private boolean live=true;
	private boolean good;
	
	public boolean isLive() {
		return live;
	}
	
	public int getX() {
		return x;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	private static int ID=1;

	Direction dir;
	TankFrame tf;
	int tankId;
	int id;
	
	public Missile(int tankId,int x,int y,Direction dir){
		this.tankId=tankId;
		this.x=x;
		this.y=y;
		this.dir=dir;
		this.id=ID++;
	}
	
	public Missile() {
		super();
	}

	public Missile(int tankId,int x, int y,boolean good, Direction dir, TankFrame tf) {
		this(tankId,x,y,dir);
		this.good=good;
		this.tf = tf;
	}

	public void draw(Graphics g){
		if(!live){
			tf.missiles.remove(this);
			return;
		}
		
		Color c=g.getColor();
		if(good)
			g.setColor(Color.BLACK);
		else 
			g.setColor(Color.RED);
		g.fillOval(x, y, MWIDTH, MHEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
		switch(dir){
		case L:
			x-=SPEED;
			break;
		case LU:
			x-=SPEED;
			y-=SPEED;
			break;
		case U:
			y-=SPEED;
			break;
		case RU:
			x+=SPEED;
			y-=SPEED;
			break;
		case R:
			x+=SPEED;
			break;
		case RD:
			x+=SPEED;
			y+=SPEED;
			break;
		case D:
			y+=SPEED;
			break;
		case LD:
			x-=SPEED;
			y+=SPEED;
			break;
		case STOP:
		}
		if(x<0||y<0||x>TankFrame.GAME_WIDTH||y>TankFrame.GAME_HEIGHT){
			live=false;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,MWIDTH,MHEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()){
			
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0){
					t.setLive(false);
					hitCount++;
				}
			
				this.live=false;
			Explode e=new Explode(x, y, tf);
			tf.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i)))
				return true;
		}
		return false;
	}
	
	
}
