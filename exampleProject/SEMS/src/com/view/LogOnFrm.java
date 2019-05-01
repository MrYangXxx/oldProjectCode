package com.view;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
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

import com.bean.User;
import com.dao.UserDao;
import com.util.StringUtil;

@SuppressWarnings("serial")
public class LogOnFrm extends JFrame {
	
	UserDao userDao=new UserDao();
	
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
					LogOnFrm frame = new LogOnFrm();
					frame.setUndecorated(true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);   //set center
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public LogOnFrm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogOnFrm.class.getResource("/img/graybg.jpg")));
		JLabel imgLabel=new JLabel(new ImageIcon("src/img/graybg.jpg"));
		imgLabel.setBounds(0,0,new ImageIcon("src/img/graybg.jpg").getIconWidth(),new ImageIcon("src/img/graybg.jpg").getIconHeight());
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 517, 395);
		this.getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student EmployMent Manager");
		lblNewLabel.setIcon(new ImageIcon("src/img/sem2.png"));
		lblNewLabel.setFont(new Font("Sitka Subheading", Font.PLAIN, 26));
		lblNewLabel.setBounds(39, 36, 457, 54);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("AdminName");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setIcon(new ImageIcon("src/img/userName.png"));
		lblNewLabel_1.setBounds(130, 145, 105, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PassWord");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setIcon(new ImageIcon("src/img/password.png"));
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
		jb_Text.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jb_Text.setIcon(new ImageIcon("src/img/login.png"));
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
				User currentUser=userDao.login(user);
				if(currentUser!=null){
					dispose();
					MainFrm main=new MainFrm();
					main.setVisible(true);
					main.setLocationRelativeTo(null);
				}
				else{
					JOptionPane.showMessageDialog(null, "AdminName or Password Wrong");
				}
			}
		});
		jb_Text.setBounds(118, 288, 105, 27);
		contentPane.add(jb_Text);
		
		JButton jb_Reset = new JButton("Cancel");
		jb_Reset.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jb_Reset.setIcon(new ImageIcon("src/img/reset.png"));
		jb_Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		jb_Reset.setBounds(287, 288, 105, 27);
		contentPane.add(jb_Reset);
	}

}
