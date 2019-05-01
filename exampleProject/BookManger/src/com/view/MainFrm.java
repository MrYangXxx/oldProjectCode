package com.view;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.model.Book;
import com.model.BookType;

@SuppressWarnings("serial")
public class MainFrm extends JFrame {
	@Autowired
	private BookAddInterFrm bookAddInterFrm;
	@Autowired
	private BookTypeManageInterFrm bookTypeManageInterFrm;
	@Autowired
	private BookTypeInterFrm bookTypeInterFrm;
	@Autowired
	private BookManageInterFrm bookManageInterFrm;
	
	/**
	 * Create the frame.
	 */
	public MainFrm() {
		 final JDesktopPane table = new JDesktopPane();
		setFont(new Font("Dialog", Font.PLAIN, 20));
		setTitle("MainForm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setEnabled(false);
		menuBar.setBorderPainted(false);
		menuBar.setFont(new Font("Segoe UI", Font.ITALIC, 23));
		menuBar.setMargin(new Insets(2, 1, 1, 2));
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Edit");
		mnNewMenu.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\base.png"));
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_2 = new JMenu("BookType");
		mnNewMenu_2.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\bookTypeManager.png"));
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem jmiBookType = new JMenuItem("AddType");
		jmiBookType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookTypeInterFrm.setVisible(true);
				table.add(bookTypeInterFrm);
			}
		});
		jmiBookType.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\add.png"));
		mnNewMenu_2.add(jmiBookType);
		
		JMenuItem jmiBookTypeManage = new JMenuItem("EditType");
		jmiBookTypeManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookTypeManageInterFrm.setVisible(true);
				table.add(bookTypeManageInterFrm);
				bookTypeManageInterFrm.fillTable(new BookType());
			}
		});
		jmiBookTypeManage.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\edit.png"));
		mnNewMenu_2.add(jmiBookTypeManage);
		
		JMenu mnNewMenu_3 = new JMenu("Book");
		mnNewMenu_3.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\bookManager.png"));
		mnNewMenu.add(mnNewMenu_3);
		
		JMenuItem jmi_BookAdd = new JMenuItem("AddBook");
		jmi_BookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookAddInterFrm.setVisible(true);
				table.add(bookAddInterFrm);
				bookAddInterFrm.fillBookType();
			}
		});
		jmi_BookAdd.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\add.png"));
		mnNewMenu_3.add(jmi_BookAdd);
		
		JMenuItem jmiBookManage = new JMenuItem("EditBook");
		jmiBookManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookManageInterFrm.setVisible(true);
				table.add(bookManageInterFrm);
				bookManageInterFrm.fillTable(new Book());
				bookManageInterFrm.fillBookType("search");
				bookManageInterFrm.fillBookType("edit");
			}
		});
		jmiBookManage.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\edit.png"));
		mnNewMenu_3.add(jmiBookManage);
		
		JMenuItem jmiQuit = new JMenuItem("Quit");
		jmiQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result=JOptionPane.showConfirmDialog(null, "Want to Quit?");
				if(result==0){
					dispose();
				}
			}
		});
		jmiQuit.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\exit.png"));
		mnNewMenu.add(jmiQuit);
		
		JMenu mnNewMenu_1 = new JMenu("Help");
		mnNewMenu_1.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\about.png"));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("AboutMe");
		mntmNewMenuItem_5.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\userName.png"));
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);

	}

}
