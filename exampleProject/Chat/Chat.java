import java.awt.*;
import java.awt.event.*;
import java.net.*;
public class Chat 
{
	Frame f=new Frame("�ҵ�������");
	TextField tfIP=new TextField(15);
	List lst=new List(6);
	DatagramSocket ds;
/*����DatagramSocket�Ĺ��캯�����������׳��쳣�����ǵĳ�����Ҫ��try��catch���
�����쳣���������Բ���ֱ�����������DatagramSocket�Ĺ��캯����ds���г�ʼ����
������Ҫ��ds�ĳ�ʼ������Chat��Ĺ��캯����ȥ��ɡ�*/
	public Chat()
	{
	        try
	        {
	            ds=new DatagramSocket(3000);
	        }catch(Exception ex){ex.printStackTrace();}
	}
	public static void main(String [] args)
	{
		Chat chat=new Chat();
		chat.init();
	}
	public void init()
	{
		f.setSize(300,300);
		f.add(lst);

		Panel p=new Panel();
		p.setLayout(new BorderLayout());
		p.add("West",tfIP);
		TextField tfData=new TextField(20);
		p.add("East",tfData);
		f.add("South",p);
		f.setVisible(true);
		f.setResizable(false);//�����û��ı䴰�ڵĴ�С
		
		//���ӹرմ��ڵ��¼��������
		f.addWindowListener(new WindowAdapter()
{
	public void windowClosing(WindowEvent e)
	{
		ds.close();//�����˳�ʱ���ر�Socket���ͷ������Դ
		f.setVisible(false);
		f.dispose();
		System.exit(0);
	}
});	
		//��������Ϣ�ı����а��»س������¼��������
		tfData.addActionListener(new ActionListener()
{
    			public void actionPerformed(ActionEvent e)
    			{
		//ȡ���ı����е���Ϣ�ַ�����������ת�����ֽ�����
				byte[] buf;
				buf = e.getActionCommand().getBytes();
	try
	{
		DatagramPacket dp= new DatagramPacket(buf,buf.length,
		InetAddress.getByName(tfIP.getText()),3000);
		ds.send(dp);
}catch(Exception ex){ex.printStackTrace();}
/*�����Exception�����ñ���������Ϊe�����Ǹ�д����ex����Ϊe�Ѿ���
actionPerformed��������Ϊ��ʽ������������������ˡ�*/
            		((TextField)e.getSource()).setText("");
    			}	
});
	}
}