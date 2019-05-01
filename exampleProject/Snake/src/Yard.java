import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Yard extends Frame{

	PaintThread paintThread=new PaintThread();
	private boolean gameOver=false;
	
	public static final int ROWS=30;
	public static final int COLS=30;
	public static final int BLOCK_SIZE=15;
	
	private int score=0;
	
	Snake s=new Snake(this);
	Egg e=new Egg();
	
	Image offScreenImage=null;
	
	public void launch(){
		this.setLocation(300, 300);
		this.setSize(COLS*BLOCK_SIZE, ROWS*BLOCK_SIZE);
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.setVisible(true);
		
		this.addKeyListener(new keyMonitor());
		new Thread(paintThread).start();
	}
	
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if(offScreenImage==null){
			offScreenImage=this.createImage(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		}
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0,0, null);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void stop(){
		gameOver=true;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Color c=g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS*BLOCK_SIZE, ROWS*BLOCK_SIZE);
		g.setColor(Color.DARK_GRAY);
		for(int i=1;i<ROWS;i++){
			g.drawLine(0, BLOCK_SIZE*i, COLS*BLOCK_SIZE, BLOCK_SIZE*i);
		}
		for(int i=0;i<COLS;i++){
			g.drawLine(BLOCK_SIZE*i, 0, BLOCK_SIZE*i,ROWS*BLOCK_SIZE );
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("score:"+score, 10, 60);
		if(gameOver){
			g.setFont(new Font("ËÎÌו",Font.BOLD,50));
			g.drawString("Game Over", 90, 220);
			g.setFont(new Font("ËÎÌו",Font.BOLD,20));
			g.drawString("key F2 Restart", 150, 240);
			paintThread.pause();
		}
		g.setColor(c);
		
		s.eat(e);
		e.draw(g);
		s.draw(g);
		
		
	}
	public static void main(String[] args) {
		new Yard().launch();
	}
	
	private class PaintThread implements Runnable{
		private boolean running=true;
		private boolean pause=false;
		public void run(){
			while(running){
				if(pause) ;
				else repaint();
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		public void gameOver(){
			running=false;
		}
		public void pause(){
			this.pause=true;
		}
		public void reStart(){
			this.pause=false;
			s=new Snake(Yard.this);
			gameOver=false;
			score=0;
		}
	}
	
	private class keyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int key=e.getKeyCode();
			if(key==KeyEvent.VK_F2){
				paintThread.reStart();
			}
			s.keyPressed(e);
		}
		
	}
}
