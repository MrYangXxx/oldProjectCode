import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class TankNewMsg implements Msg{
	Tank tank;
	TankFrame tf;
	int msgType=Msg.TANK_NEW_MSG;
	
	public TankNewMsg(Tank t){
		this.tank=t;
	}
	
	
	public TankNewMsg(TankFrame tf) {
		this.tf=tf;
	}


	public void send(DatagramSocket ds,String IP,int udpPort){
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(tank.id);
			dos.writeInt(tank.getX());
			dos.writeInt(tank.getY());
			dos.writeInt(tank.getDir().ordinal());
			dos.writeBoolean(tank.isGood());
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[]buf=baos.toByteArray();
		DatagramPacket dp=new DatagramPacket(buf,buf.length,new InetSocketAddress(IP, udpPort));
		try {
			if(ds!=null){
				ds.send(dp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void parse(DataInputStream dis) {
		try {
			int id=dis.readInt();
			if(tf.myTank.id==id){
				return;
			}
			int x=dis.readInt();
			int y=dis.readInt();
			Direction dir=Direction.values()[dis.readInt()];
			boolean good=dis.readBoolean();
			boolean exist=false;
			for(int i=0;i<tf.tanks.size();i++){
				Tank t=tf.tanks.get(i);
				if(t.id==id){
					exist=true;
					break;
				}
			}
			if(!exist){
				TankNewMsg tnMsg=new TankNewMsg(tf.myTank);
				tf.tc.send(tnMsg);
				
				Tank t=new Tank(x, y, good, dir, tf);
				t.id=id;
				tf.tanks.add(t);
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
