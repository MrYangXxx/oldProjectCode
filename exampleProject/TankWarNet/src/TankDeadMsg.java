import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class TankDeadMsg implements Msg {
	int id;
	int life;
	int msgType=Msg.TANK_DEAF_MSG;
	private TankFrame tf;
	
	public TankDeadMsg(int id,int life){
		this.id=id;
		this.life=life;
	}
	
	public TankDeadMsg(TankFrame tf){
		this.tf=tf;
	}
	
	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
			dos.writeInt(life);
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
			int id=dis.readInt();
			int life=dis.readInt();
			if(tf.myTank.id==id){
				return;
			}
			
			for(int i=0;i<tf.tanks.size();i++){
				Tank t=tf.tanks.get(i);
				if(t.id==id&&life<=0){
					t.setLive(false);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
