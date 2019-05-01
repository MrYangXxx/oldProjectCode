import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class TankFrame extends Frame{
	
	public static final int GAME_WIDTH=1024;
	public static final int GAME_HEIGHT=768;
	Image offScreenImage=null;
	Tank myTank=new Tank(400, 300,true,Direction.STOP,this);
	TankClient tc=new TankClient(this);
	
	List<Missile> missiles=new ArrayList<>();
	List<Explode> explodes=new ArrayList<>();
	List<Tank> tanks=new ArrayList<>();
	ConnDialog dialog=new ConnDialog();
	Blood b=new Blood();
	
	public void paint(Graphics g) {
		//g.drawString("Missiles Count:"+missiles.size(), 10, 50);
		Font f=g.getFont();
		g.setFont(new Font("ËÎÌו",Font.BOLD,15));
		g.drawString("Hit Count:"+Missile.hitCount, 10, 65);
		g.setFont(f);
		//g.drawString("Tank Life:"+myTank.getLife(), 10, 80);
		b.draw(g, myTank);
		
//		if(tanks.size()<=0){
//			for(int i=0;i<6;i++){
//				tanks.add(new Tank(50+75*(i+1),60+80*(i+1),false,Direction.STOP,this));
//			}
//		}
		
		if(!myTank.isLive()){
			g.setFont(new Font("ËÎÌו",Font.BOLD,50));
			g.drawString("Game Over", 400, 350);
			g.setFont(new Font("ËÎÌו",Font.BOLD,20));
			g.drawString("key F2 Restart", 460, 370);
		}
		
		for(int i=0;i<missiles.size();i++){
			Missile m=missiles.get(i);
			//m.hitTanks(tanks);
			if(m.hitTank(myTank)){
					TankDeadMsg msg=new TankDeadMsg(myTank.id,myTank.getLife());
					tc.send(msg);
					MissileDeadMsg mdmMsg=new MissileDeadMsg(m.tankId, m.id);
					tc.send(mdmMsg);
			}
			m.draw(g);
		}
		
		for(int i=0;i<explodes.size();i++){
			explodes.get(i).draw(g);
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.draw(g);
		Color c=g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.setColor(c);
	}
	
	public void update(Graphics g){
		if(offScreenImage==null){
			offScreenImage=this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.LIGHT_GRAY);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public static void main(String[] args) {
		new TankFrame().lauchFrame();
	}
	
	public void lauchFrame(){
		
//		for(int i=0;i<10;i++){
//			tanks.add(new Tank(50+50*(i+1),40+60*(i+1),false,Direction.STOP,this));
//		}
		
		this.setLocation(300, 200);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		dialog.setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.addKeyListener(new KeyMonitor());
		setResizable(false);
		setVisible(true);
		new Thread(new PaintThread()).start();
		//tc.connect("127.0.0.1", TankServer.TCP_PORT);
	}
	
	private class PaintThread implements Runnable{
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			
			myTank.keyPressed(e);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		
	}
	
	class ConnDialog extends Dialog{
		Button b=new Button("Enter");
		TextField tfIP=new TextField("127.0.0.1",12);
		TextField tfPort=new TextField(""+TankServer.TCP_PORT,4);
		TextField tfUDP=new TextField("2222",4);
		
		public ConnDialog(){
			super(TankFrame.this,true);
			this.setLayout(new FlowLayout());
			this.add(new Label("IP:"));
			this.add(tfIP);
			this.add(new Label("Port:"));
			this.add(tfPort);
			this.add(new Label("UDP Port:"));
			this.add(tfUDP);
			this.add(b);
			this.setLocation(500, 500);
			this.pack();
			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					if(tfIP.getText().trim()!=""&&tfPort.getText().trim()!=""&&tfUDP.getText().trim()!="")
					setVisible(false);
				}
				
			});
			b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(tfIP.getText().trim()!=""&&tfPort.getText().trim()!=""&&tfUDP.getText().trim()!=""){
					String ip=tfIP.getText().trim();
					int port=Integer.parseInt(tfPort.getText().trim());
					int udpPort=Integer.parseInt(tfUDP.getText().trim());
					tc.setUdpPort(udpPort);
					tc.connect(ip, port);
					setVisible(false);
					}
				}
			});
		}
		
	}
	
	

}
