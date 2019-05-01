import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Egg {

	int row;
	int col;
	int w=Yard.BLOCK_SIZE;
	int h=Yard.BLOCK_SIZE;
	private static Random r=new Random();
	private Color color=Color.GREEN;
	
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Egg(){
		this(r.nextInt(Yard.ROWS-2)+2,r.nextInt(Yard.COLS-2)+2);
	}
	
	public void reAppear(){
		this.row=r.nextInt(Yard.ROWS-2)+2;
		this.col=r.nextInt(Yard.COLS-2)+2;
	}
	
	public Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE*col, Yard.BLOCK_SIZE*row,w,h);
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(color);
		g.fillRect(Yard.BLOCK_SIZE*col, Yard.BLOCK_SIZE*row, w,h);
		g.setColor(c);
		if(color==Color.GREEN)color=Color.RED;
		else color=Color.GREEN;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
}
