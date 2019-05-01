package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.BookTypeDao;
import com.model.BookType;
import com.util.StringUtil;

@SuppressWarnings("serial")
public class BookTypeInterFrm extends JInternalFrame {
	@Autowired
	private BookTypeDao bookTypeDao;

	private JTextField bookTypeNametext;
	/**
	 * Create the frame.
	 */
	public BookTypeInterFrm() {
		setClosable(true);
		setIconifiable(true);
		setEnabled(false);
		setTitle("AddBookType");
		setBounds(100, 100, 675, 488);
		
		JLabel lblNewLabel = new JLabel("TypeName");
		lblNewLabel.setBounds(76, 83, 64, 18);
		
		JLabel lblNewLabel_1 = new JLabel("AboutType");
		lblNewLabel_1.setBounds(76, 230, 72, 18);
		
		bookTypeNametext = new JTextField();
		bookTypeNametext.setBounds(226, 76, 320, 32);
		bookTypeNametext.setColumns(10);
		
		final JTextArea aboutTypeText = new JTextArea();
		aboutTypeText.setWrapStyleWord(true);
		aboutTypeText.setTabSize(4);
		aboutTypeText.setLineWrap(true);
		aboutTypeText.setBounds(226, 160, 320, 184);
		
		JButton jb_AddType = new JButton("Add");
		jb_AddType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String bookTypeName=bookTypeNametext.getText();
				String aboutType=aboutTypeText.getText();
				if(StringUtil.isEmpty(bookTypeName)){
					JOptionPane.showMessageDialog(null, "Book Type could not empty");
					return;
				}
				BookType bookType=new BookType(bookTypeName,aboutType);
					int n=bookTypeDao.addBookType(bookType);
					if(n==1){
						JOptionPane.showMessageDialog(null, "Add Success");
					}else{
						JOptionPane.showMessageDialog(null, "Sorry,Add Wrong");
					}
			}
		});
		jb_AddType.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\add.png"));
		jb_AddType.setBounds(195, 398, 96, 27);
		
		JButton jb_ResetType = new JButton("Reset");
		jb_ResetType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bookTypeNametext.setText("");
				aboutTypeText.setText("");
			}
		});
		jb_ResetType.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\reset.png"));
		jb_ResetType.setBounds(343, 398, 105, 27);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(99, 51, 1, 1);
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel);
		getContentPane().add(bookTypeNametext);
		getContentPane().add(lblNewLabel_1);
		getContentPane().add(aboutTypeText);
		getContentPane().add(jb_AddType);
		getContentPane().add(jb_ResetType);
		getContentPane().add(desktopPane);

	}
}
