package com.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.panel.AboutPanel;
import com.panel.CompanyInfoPanel;
import com.panel.Quick_R_Frame;
import com.panel.Quick_SW_Frame;
import com.panel.Quick_S_Frame;
import com.panel.RecruitPanel;
import com.panel.StudentInfoPanel;

@SuppressWarnings("serial")
public class MainFrm extends JFrame {

	JPanel mainPanel;
	
	
	/**
	 * Create the frame.
	 */
	public MainFrm() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().createImage("./img/sem.png")); 
		getContentPane().setBackground(SystemColor.controlDkShadow);
		setFont(new Font("Dialog", Font.PLAIN, 20));
		setTitle("SEMS");
		setBounds(100, 100, 939, 682);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(0, 26, 98, 543);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(SystemColor.menu);
		mainPanel.setBounds(129, 26, 779, 543);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("                                            WELCOME");
		lblNewLabel_1.setFont(new Font("Wide Latin", Font.ITALIC, 20));
		mainPanel.add(lblNewLabel_1);
		
		JButton bt_StuInfo = new JButton("S_INFO");
		bt_StuInfo.setFont(new Font("Tekton Pro", Font.ITALIC, 16));
		bt_StuInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.removeAll();
				mainPanel.add(new StudentInfoPanel());
				mainPanel.updateUI();
			}
		});
		bt_StuInfo.setBounds(1, 35, 97, 65);
		panel.add(bt_StuInfo);
		
		JButton bt_CompInfo = new JButton("C_INFO");
		bt_CompInfo.setFont(new Font("Tekton Pro", Font.ITALIC, 16));
		bt_CompInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.removeAll();
				mainPanel.add(new CompanyInfoPanel());
				mainPanel.updateUI();
			}
		});
		bt_CompInfo.setBounds(0, 104, 97, 65);
		panel.add(bt_CompInfo);
		
		JButton bt_Recruit = new JButton("R_INFO");
		bt_Recruit.setFont(new Font("Tekton Pro", Font.ITALIC, 16));
		bt_Recruit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.removeAll();
				mainPanel.add(new RecruitPanel());
				mainPanel.updateUI();
			}
		});
		bt_Recruit.setBounds(1, 171, 97, 74);
		panel.add(bt_Recruit);
		
		JButton btnNewButton = new JButton("ABOUT");
		btnNewButton.setFont(new Font("Tekton Pro", Font.ITALIC, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.removeAll();
				mainPanel.add(new AboutPanel());
				mainPanel.updateUI();
			}
		});
		btnNewButton.setBounds(1, 249, 97, 65);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("   Menu");
		lblNewLabel.setFont(new Font("Tekton Pro Cond", Font.ITALIC, 24));
		lblNewLabel.setBounds(1, 0, 97, 36);
		panel.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("QUIT");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n=JOptionPane.showConfirmDialog(null, "QUIT?");
				if(n==0){
				dispose();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tekton Pro", Font.ITALIC, 16));
		btnNewButton_1.setBounds(1, 315, 97, 65);
		panel.add(btnNewButton_1);
		
		JLabel copyleft = new JLabel("Copyleft@\u5E7F\u5DDE\u822A\u6D77\u5B66\u9662\u8F6F\u4EF616");
		copyleft.setFont(new Font("STFangsong", Font.BOLD, 15));
		copyleft.setBounds(340, 582, 337, 29);
		getContentPane().add(copyleft);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("QUICK_SERCH");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("S_INFO");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Quick_S_Frame quick_s=new Quick_S_Frame();
				quick_s.setLocationRelativeTo(null);   //set center
				quick_s.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("SW_INFO");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Quick_SW_Frame quick_sw=new Quick_SW_Frame();
				quick_sw.setLocationRelativeTo(null);   //set center
				quick_sw.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("R_INFO");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Quick_R_Frame quick_r=new Quick_R_Frame();
				quick_r.setLocationRelativeTo(null);   //set center
				quick_r.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("HELP");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("CONTACT ME");
		mnNewMenu_1.add(mntmNewMenuItem_3);

	}
}
