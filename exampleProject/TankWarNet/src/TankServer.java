import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class TankServer {
	public static final int TCP_PORT=8888;
	public static final int UDP_PORT=6666;
	private static int ID=100;
	ServerSocket ss;
	Socket s=null;
	List<Client>clients=new ArrayList<>();
	
	public static void main(String[] args) {
		new TankServer().start();
	}
	
	public void start(){
		new Thread(new UDPThread()).start();
		try {
			ss = new ServerSocket(TCP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true){
			try{
				s=ss.accept();
				DataInputStream dis=new DataInputStream(s.getInputStream());
				String IP=s.getInetAddress().getHostAddress();
				int udpPort=dis.readInt();
				System.out.println("Client Connect Addr:"+s.getInetAddress()+":"+s.getPort()+" DUP Addr:"+udpPort);
				Client c=new Client(IP, udpPort);
				clients.add(c);
				DataOutputStream dos=new DataOutputStream(s.getOutputStream());
				dos.writeInt(ID++);
			}catch(IOException e){
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
		}
	}
	
	private class Client{
		String IP;
		int udpPort;
		
		public Client(String IP,int udpPort){
			this.IP=IP;
			this.udpPort=udpPort;
		}
	}
	
	private class UDPThread implements Runnable{
		byte[]buf=new byte[1024];
		public void run(){
			DatagramSocket ds=null;
			try{
				ds=new DatagramSocket(UDP_PORT);
			}catch(SocketException e){
				e.printStackTrace();
		}
			while(ds!=null){
				DatagramPacket dp=new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					for(int i=0;i<clients.size();i++){
						dp.setSocketAddress(new InetSocketAddress(clients.get(i).IP,clients.get(i).udpPort));
						ds.send(dp);
					}
					System.out.println("Receive Packet From Client");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
}
