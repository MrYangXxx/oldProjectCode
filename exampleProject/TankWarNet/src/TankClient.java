import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class TankClient {

	private int udpPort;
	private String ip;
	
	TankFrame tf;
	Socket s=null;
	DatagramSocket ds=null;
	
	public TankClient(TankFrame tf){
		this.tf=tf;
	}
	
	public void connect(String IP,int port){
		this.ip=IP;
		
		try {
			ds=new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			s=new Socket(IP, port);
			System.out.println("Connect Server");
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			int id=dis.readInt();
			tf.myTank.id=id;
			
			if(id%2==0)
				tf.myTank.setGood(false);
			else
				tf.myTank.setGood(true);
			
			System.out.println("Get ID from Server:"+id);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		TankNewMsg msg=new TankNewMsg(tf.myTank);
		send(msg);
		
		new Thread(new UDPRecvThread()).start();
	}
	
	
	
	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	public void send(Msg msg){
		msg.send(ds, ip, TankServer.UDP_PORT);
	}
	
	
	private class UDPRecvThread implements Runnable{
		byte[] buf=new byte[1024];
		public void run(){
			
			while(ds!=null){
				DatagramPacket dp=new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					parse(dp);
					System.out.println("Receive Packet from Server");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void parse(DatagramPacket dp){
			ByteArrayInputStream bais=new ByteArrayInputStream(buf,0,dp.getLength());
			DataInputStream dis=new DataInputStream(bais);
			int msgType = 0;
			try {
				msgType = dis.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Msg msg=null;
			switch(msgType){
			case Msg.TANK_NEW_MSG:
				msg=new TankNewMsg(TankClient.this.tf);
				msg.parse(dis);
				break;
			case Msg.TANK_MOVE_MSG:
				msg=new TankMoveMsg(TankClient.this.tf);
				msg.parse(dis);
				break;
			case Msg.MISSILE_NEW_MSG:
				msg=new MissileNewMsg(TankClient.this.tf);
				msg.parse(dis);
				break;
			case Msg.TANK_DEAF_MSG:
				msg=new TankDeadMsg(tf);
				msg.parse(dis);
				break;
			case Msg.MISSILE_DEAF_MSG:
				msg=new MissileDeadMsg(tf);
				msg.parse(dis);
				break;
			}
		}
	}
}
	
