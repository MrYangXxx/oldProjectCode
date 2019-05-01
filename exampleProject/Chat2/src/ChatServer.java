import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {
	
	static boolean bConnected;
	List<ServerThread> serverList=new ArrayList<>();
	ServerSocket ss=null;
	Socket s=null;
	public static void main(String[] args) {
		new ChatServer().start();
}
	
	public void start(){
		while(true){
			if(ss==null){
				try {
					ss=new ServerSocket(8888);
					System.out.println("Wait for Client");
				}catch(BindException e){
					System.out.println("Port is used");
					return;
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(ss!=null){
				try {
					s=ss.accept();
					System.out.println("Client Connected");
				} catch (IOException e) {
				}
			}
			if(s!=null){
				ServerThread st=new ServerThread(s);
				new Thread(st).start();
				serverList.add(st);
			}
		}
	}


	
	class ServerThread implements Runnable{
		Socket socket;
		DataInputStream dis=null;
		DataOutputStream dos=null;
		boolean bConnect=false;
		public ServerThread(Socket t) {
			socket=t;
			try {
				dis=new DataInputStream(socket.getInputStream());
				dos=new DataOutputStream(socket.getOutputStream());
				bConnect=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void send(String str){
			if(dos!=null){
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			}
		}
		
		public void run(){
			try {
				while(bConnect){
					String str = dis.readUTF();
					System.out.println("For Client"+socket.getInetAddress()+":"+str);
					for(int i=0;i<serverList.size();i++){
						ServerThread st=serverList.get(i);
						st.send(str);
					}
				}
			} catch (IOException e) {
				System.out.println("Client gone");
				serverList.remove(this);
				return;
			}finally{
				try {
					if(dis!=null)dis.close();
					if(dos!=null)dos.close();
					if(socket!=null)socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}
}
