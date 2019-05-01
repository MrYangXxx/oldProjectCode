import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class TankMoveMsg implements Msg{
	int id;
	int x;
	int y;
	Direction ptDir;
	Direction dir;
	int msgType=Msg.TANK_MOVE_MSG;
	TankFrame tf;
	
	public TankMoveMsg(int id,int x,int y, Direction dir,Direction ptDir) {
		super();
		this.id = id;
		this.x=x;
		this.y=y;
		this.dir = dir;
		this.ptDir=ptDir;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(ptDir.ordinal());
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
			if(tf.myTank.id==id){
				return;
			}
			int x=dis.readInt();
			int y=dis.readInt();
			Direction dir=Direction.values()[dis.readInt()];
			Direction ptDir=Direction.values()[dis.readInt()];
			
			boolean exist=false;
			for(int i=0;i<tf.tanks.size();i++){
				Tank t=tf.tanks.get(i);
				if(t.id==id){
					t.setX(x);
					t.setY(y);
					t.setDir(dir);
					t.setPtDir(ptDir);
					exist=true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TankMoveMsg(TankFrame tf){
		this.tf=tf;
	}
	
}
