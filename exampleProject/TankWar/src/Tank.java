import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Tank {
	public static final int SPEED=3;
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	public static final int TWIDTH=50;
	public static final int THEIGHT=50;
	TankFrame tf;
	
	private boolean good;
	private boolean live=true;
	private static Random r=new Random();
	private int life=100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	private boolean bL=false,bU=false,bR=false,bD=false;
	private Direction dir=Direction.STOP;
	private Direction missileDir=Direction.D;
	
	private int step=r.nextInt(12)+3;
	
	public Tank(int x,int y,boolean good){
		this.x=x;
		this.y=y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
	}
	
	public Tank() {
		super();
	}

	public Tank(int x,int y,boolean good,Direction dir,TankFrame tf){
		this(x,y,good);
		this.dir=dir;
		this.tf=tf;
	}
	
	
	private static Image[] tankImgs=null;
	private static Map<String,Image> imgs=new HashMap<>();
	
		static{	
		tankImgs=new Image[]{
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif")),
			};
		imgs.put("L", tankImgs[0]);
		imgs.put("LU", tankImgs[1]);
		imgs.put("U", tankImgs[2]);
		imgs.put("RU", tankImgs[3]);
		imgs.put("R", tankImgs[4]);
		imgs.put("RD", tankImgs[5]);
		imgs.put("D", tankImgs[6]);
		imgs.put("LD", tankImgs[7]);
		}
	
	public void draw(Graphics g){
		if(!live){
			if(!good){
				tf.tanks.remove(this);
			}
			return;
		}
		
		switch(missileDir){
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
	
	void move(){
		this.oldX=x;
		this.oldY=y;
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
			break;
		}
		if(this.dir!=Direction.STOP){
			this.missileDir=this.dir;
		}
		if(x<0)x=0;
		if(y<30)y=30;
		if(x+Tank.TWIDTH>TankFrame.GAME_WIDTH)x=TankFrame.GAME_WIDTH-Tank.TWIDTH;
		if(y+Tank.THEIGHT>TankFrame.GAME_HEIGHT)y=TankFrame.GAME_HEIGHT-Tank.THEIGHT;
		
		if(!good){
			if(step==0){
				step=r.nextInt(12)+3;
				Direction[] dirs=Direction.values();
				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			step--;
			
			if(r.nextInt(40)>38)
				this.fire();
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F2:
			if(!this.live){
				this.live=true;
				this.life=100;
				Missile.hitCount=0;
			}
			break;
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		}
		locateDirection();
	}
	
	public void locateDirection(){
		if(bL&&!bU&&!bR&&!bD) dir=Direction.L;
		else if(bL&&bU&&!bR&&!bD) dir=Direction.LU;
		else if(!bL&&bU&&!bR&&!bD) dir=Direction.U;
		else if(!bL&&bU&&bR&&!bD) dir=Direction.RU;
		else if(!bL&&!bU&&bR&&!bD) dir=Direction.R;
		else if(!bL&&!bU&&bR&&bD) dir=Direction.RD;
		else if(!bL&&!bU&&!bR&&bD) dir=Direction.D;
		else if(bL&&!bU&&!bR&&bD) dir=Direction.LD;
		else if(!bL&&!bU&&!bR&&!bD) dir=Direction.STOP;
	}
	
	public void keyReleased(KeyEvent e){
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();
	}
	
	private void stay(){
		x=oldX;
		y=oldY;
	}
	
	public Missile fire(){
		if(!live)return null;
		int x=this.x+Tank.TWIDTH/2-Missile.MWIDTH/2;
		int y=this.y+Tank.THEIGHT/2-Missile.MHEIGHT/2;
		Missile m=new Missile(x, y, good,missileDir,tf);
		tf.missiles.add(m);
		return m;
	}
	public Missile fire(Direction dir){
		if(!live)return null;
		int x=this.x+Tank.TWIDTH/2-Missile.MWIDTH/2;
		int y=this.y+Tank.THEIGHT/2-Missile.MHEIGHT/2;
		Missile m=new Missile(x, y, good,dir,tf);
		tf.missiles.add(m);
		return m;
	}
	
	public void superFire(){
		Direction[] dirs=Direction.values();
		for(int i=0;i<dirs.length-1;i++){
			fire(dirs[i]);
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,TWIDTH,THEIGHT);
	}
	
	public boolean collidesWithWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			if(this!=t){
				if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean eat(Blood b){
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
			this.life=100;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
}
