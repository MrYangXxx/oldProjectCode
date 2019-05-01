import java.io.DataInputStream;
import java.net.DatagramSocket;


public interface Msg {
	int TANK_NEW_MSG=1;
	int TANK_MOVE_MSG=2;
	int MISSILE_NEW_MSG=3;
	int TANK_DEAF_MSG=4;
	int MISSILE_DEAF_MSG=5;
	public void send(DatagramSocket ds,String IP,int udpPort);
	public void parse(DataInputStream dis);
}
