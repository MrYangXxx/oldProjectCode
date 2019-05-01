import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class MissileDeadMsg implements Msg {
	int msgType=Msg.MISSILE_DEAF_MSG;
	TankFrame tf;
	int id;
	int tankId;

	public MissileDeadMsg(int tankId,int id) {
		super();
		this.tankId=tankId;
		this.id = id;
	}

	public MissileDeadMsg(TankFrame tf) {
		super();
		this.tf = tf;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(tankId);
			dos.writeInt(id);
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
//			if(tf.myTank.id==tankId){
//				return;
//			}
			int id=dis.readInt();
			
			for(int i=0;i<tf.missiles.size();i++){
				Missile m=tf.missiles.get(i);
				if(m.tankId==tankId&&m.id==id){
					m.setLive(false);
					tf.explodes.add(new Explode(m.getX(), m.getY(), tf));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
