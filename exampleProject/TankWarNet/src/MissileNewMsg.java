import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class MissileNewMsg implements Msg {
	int msgType=Msg.MISSILE_NEW_MSG;
	TankFrame tf;
	Missile m;
	
	public MissileNewMsg(Missile m){
		this.m=m;
	}
	
	public MissileNewMsg(TankFrame tf){
		this.tf=tf;
	}
	
	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(m.tankId);
			dos.writeInt(m.id);
			dos.writeInt(m.getX());
			dos.writeInt(m.getY());
			dos.writeInt(m.getDir().ordinal());
			dos.writeBoolean(m.isGood());
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

	@Override
	public void parse(DataInputStream dis) {
		try {
			int tankId=dis.readInt();
			if(tankId==tf.myTank.id)
				return;
			int id=dis.readInt();
			int x=dis.readInt();
			int y=dis.readInt();
			Direction dir=Direction.values()[dis.readInt()];
			boolean good=dis.readBoolean();
			
			Missile m=new Missile(tankId,x, y, good, dir, tf);
			m.id=id;
			tf.missiles.add(m);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
