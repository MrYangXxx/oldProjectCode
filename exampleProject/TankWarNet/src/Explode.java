import java.awt.Color;
import java.awt.Graphics;


public class Explode {

	int x;
	int y;
	private boolean live=true;
	int []diameter={4,7,12,18,23,26,32,38,44,49,35,24,15,5};
	int step=0;
	TankFrame tf;
	
	public Explode(int x, int y, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	
	public void draw(Graphics g){
		if(!live){
			tf.explodes.remove(this);
			return;
		}
		if(step==diameter.length){
			live=false;
			step=0;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		
		step++;
	}
	
}
