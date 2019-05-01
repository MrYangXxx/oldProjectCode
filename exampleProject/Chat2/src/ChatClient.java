import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClient extends Frame{
	
	TextField tfTxt=new TextField();
	TextArea taContent=new TextArea();
	String str=null;
	Socket s=null;
	DataOutputStream dos = null;
	DataInputStream dis=null;
	boolean bConnected=false;
	
	Thread client=new Thread(new ClientThread());
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	public void connect(){
		try {
			s=new Socket("127.0.0.1",8888);
			System.out.println("Connect!");
			dos = new DataOutputStream(s.getOutputStream());
			dis=new DataInputStream(s.getInputStream());
			bConnected=true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Host error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Server error");
		}
	}
	
	public void disClose(){
		try {
			if(dos!=null)dos.close();
			if(dis!=null)dis.close();
			if(s!=null)s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void launchFrame(){
		setLocation(400, 300);
		this.setSize(400, 600);
		add(tfTxt,BorderLayout.SOUTH);
		add(taContent,BorderLayout.NORTH);
		taContent.setEditable(false);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				disClose();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		client.start();
	}
	
	private class TFListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String temp=tfTxt.getText();
			if(!temp.trim().equals("")&&temp!=null)
				str=temp;
			tfTxt.setText("");
			
			try {
				if(dos!=null&&str!=null){
					dos.writeUTF(str);
					dos.flush();
					str=null;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class ClientThread implements Runnable{
		public void run(){
			while(bConnected){
				try {
					String str=dis.readUTF();
					taContent.setText(taContent.getText()+str+"\n");
				} catch (IOException e) {
					System.out.println("you out");
					return;
				}
				
			}
		}
	}
	
}
