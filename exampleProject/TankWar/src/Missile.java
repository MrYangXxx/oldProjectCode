import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Missile {
	int x;
	int y;
	public static final int SPEED=6;
	public static final int MWIDTH=11;
	public static final int MHEIGHT=11;
	public static int hitCount=0;
	
	private boolean live=true;
	private boolean good;
	
	public boolean isLive() {
		return live;
	}

	Direction dir;
	TankFrame tf;
	
	public Missile(int x,int y,Direction dir){
		this.x=x;
		this.y=y;
		this.dir=dir;
	}
	
	public Missile() {
		super();
	}

	public Missile(int x, int y,boolean good, Direction dir, TankFrame tf) {
		this(x,y,dir);
		this.good=good;
		this.tf = tf;
	}
	
	private static Image[] missileImgs=null;
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Map<String,Image> imgs=new HashMap<>();
	
		static{	
		missileImgs=new Image[]{
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif")),
			};
		imgs.put("L", missileImgs[0]);
		imgs.put("LU", missileImgs[1]);
		imgs.put("U", missileImgs[2]);
		imgs.put("RU", missileImgs[3]);
		imgs.put("R", missileImgs[4]);
		imgs.put("RD", missileImgs[5]);
		imgs.put("D", missileImgs[6]);
		imgs.put("LD", missileImgs[7]);
		}

	public void draw(Graphics g){
		if(!live){
			tf.missiles.remove(this);
			return;
		}
		switch(dir){
		case L:
			g.drawImage(imgs.get("L"), x,y,null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x,y,null);			
			break;
		case U:
			g.drawImage(imgs.get("U"), x,y,null);			
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x,y,null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x,y,null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x,y,null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x,y,null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x,y,null);
			break;
		case STOP:
		}
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
			if(t.isGood()){
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0)
					t.setLive(false);
			}
			else {
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
	
	public boolean hitWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			this.live=false;
			return true;
		}
		return false;
	}
	
}
