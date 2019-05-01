package com.view;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.UserDao;
import com.model.User;
import com.util.StringUtil;

@SuppressWarnings("serial")
public class LogOnFrm extends JFrame {
	@Autowired
	private UserDao userDao;
	@Autowired
	private MainFrm mainFrm;
	
	private JPanel contentPane;
	private JTextField userNameText;
	private JPasswordField passwordText;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//改变系统默认字体
					Font font = new Font("Dialog", Font.PLAIN, 12);
					@SuppressWarnings("rawtypes")
					java.util.Enumeration keys = UIManager.getDefaults().keys();
					while (keys.hasMoreElements()) {
						Object key = keys.nextElement();
						Object value = UIManager.get(key);
						if (value instanceof javax.swing.plaf.FontUIResource) {
							UIManager.put(key, font);
						}
					}
					ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
					LogOnFrm frame=(LogOnFrm) context.getBean("logOnFrm");
					((ClassPathXmlApplicationContext)context).close();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);   //set center
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public LogOnFrm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Manager System");
		lblNewLabel.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\logo.png"));
		lblNewLabel.setFont(new Font("Sitka Subheading", Font.PLAIN, 26));
		lblNewLabel.setBounds(97, 38, 353, 54);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("AdminName");
		lblNewLabel_1.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\userName.png"));
		lblNewLabel_1.setBounds(130, 145, 105, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PassWord");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\password.png"));
		lblNewLabel_2.setBounds(130, 208, 93, 18);
		contentPane.add(lblNewLabel_2);
		
		userNameText = new JTextField();
		userNameText.setBounds(261, 142, 136, 24);
		contentPane.add(userNameText);
		userNameText.setColumns(10);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(261, 205, 136, 24);
		contentPane.add(passwordText);
		
		JButton jb_Text = new JButton("LogOn");
		jb_Text.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\login.png"));
		jb_Text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName=userNameText.getText();
				String passWord=new String(passwordText.getPassword());
				if(StringUtil.isEmpty(userName)){
					JOptionPane.showMessageDialog(null, "Name could not empty");
					return;
				}
				if(StringUtil.isEmpty(passWord)){
					JOptionPane.showMessageDialog(null, "PassWord could not empty");
					return;
				}
				User user=new User(userName,passWord);
			
				if(userDao.login(user)!=null){
					dispose();
					mainFrm.setSize(1200, 800);
					mainFrm.setLocationRelativeTo(null);
					mainFrm.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "AdminName or Password Wrong");
				}
			}
		});
		jb_Text.setBounds(130, 309, 93, 27);
		contentPane.add(jb_Text);
		
		JButton jb_Reset = new JButton("Reset");
		jb_Reset.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\reset.png"));
		jb_Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userNameText.setText("");
				passwordText.setText("");
			}
		});
		jb_Reset.setBounds(304, 309, 93, 27);
		contentPane.add(jb_Reset);
	}

}
