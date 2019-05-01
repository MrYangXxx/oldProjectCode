import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Explode {

	int x;
	int y;
	private boolean live=true;
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[]imgs={
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	int step=0;
	TankFrame tf;
	
	private static boolean init=false;
	
	public Explode(int x, int y, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	
	public void draw(Graphics g){
		if(!init){
			for(int i=0;i<imgs.length;i++)
			g.drawImage(imgs[i], -100, -100, null);
			init=true;
		}
		if(!live){
			tf.explodes.remove(this);
			return;
		}
		if(step==imgs.length){
			live=false;
			step=0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		
		step++;
	}
	
}
