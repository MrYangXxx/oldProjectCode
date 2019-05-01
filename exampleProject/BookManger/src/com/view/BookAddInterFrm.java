package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.BookDao;
import com.dao.BookTypeDao;
import com.model.Book;
import com.model.BookType;
import com.util.StringUtil;

@SuppressWarnings("serial")
public class BookAddInterFrm extends JInternalFrame {
	@Autowired
	private BookTypeDao bookTypeDao;
	
	@Autowired
	private BookDao bookDao;
	
	private JTextField bookNametext;
	private JTextField authorNameText;
	private JTextField priceText;
	@SuppressWarnings("rawtypes")
	JComboBox jcb_BookType;
	JRadioButton jrb_male;
	JTextArea aboutBookText;
	JRadioButton jrb_female;
	@SuppressWarnings("unchecked")
	public void fillBookType(){
			List<Map<String, Object>> bookTypeList=bookTypeDao.bookTypeList(new BookType());
			for (Map<String, Object> map : bookTypeList) {
				this.jcb_BookType.addItem(map.get("bookTypeName"));
			}
		}
	
private void resetValue(){
	bookNametext.setText("");
	authorNameText.setText("");
	jrb_male.setSelected(true);
	priceText.setText("");
	if(this.jcb_BookType.getItemCount()>0){
		jcb_BookType.setSelectedIndex(0);
	}
	aboutBookText.setText("");
	}


	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public BookAddInterFrm() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 714, 590);
		
		JLabel lblNewLabel = new JLabel("BookName");
		
		JLabel lblNewLabel_1 = new JLabel("Gender");
		
		bookNametext = new JTextField();
		bookNametext.setColumns(10);
		
		jrb_male = new JRadioButton("male");
		jrb_male.setSelected(true);
		jrb_female = new JRadioButton("female");
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(jrb_male);
		bg.add(jrb_female);
		
		JLabel lblNewLabel_3 = new JLabel("Author");
		
		authorNameText = new JTextField();
		authorNameText.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Price");
		
		priceText = new JTextField();
		priceText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("BookType");
		
		jcb_BookType = new JComboBox();
		
		if(this.jcb_BookType.getItemCount()>0){
			this.jcb_BookType.setSelectedIndex(0);
		}
		JLabel lblNewLabel_5 = new JLabel("AboutBook");
		
		aboutBookText = new JTextArea();
		
		JButton jb_AddBook = new JButton("Add");
		jb_AddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookName=bookNametext.getText();
				String author=authorNameText.getText();
				String price=priceText.getText();
				String aboutBook=aboutBookText.getText();
				
				if(StringUtil.isEmpty(bookName)||StringUtil.isEmpty(author)||StringUtil.isEmpty(price)){
					JOptionPane.showMessageDialog(null, "BookName,Author And Price Could Not Empty");
					return;
				}
				
				if(!StringUtil.isNumeric(price)){
					JOptionPane.showMessageDialog(null, "Price have to number");
					return;
				}
				
				String sex="";
				if(jrb_male.isSelected()){
					sex="male";
				}else if(jrb_female.isSelected()){
					sex="female";
				}
				
				int bookTypeId=0;
			if(jcb_BookType.getItemCount()>0){
				bookTypeId=bookTypeDao.getBookTypeId(jcb_BookType.getSelectedItem()+"");
			}
			
			Book book=new Book(bookName,author,sex,Float.parseFloat(price),aboutBook,bookTypeId);
				int addNum=bookDao.addBook(book);
				if(addNum==1){
					JOptionPane.showMessageDialog(null, "Add Success");
					resetValue();
				}else{
					JOptionPane.showMessageDialog(null, "Wrong");
				}
			}
		});
		
		
		
		jb_AddBook.setIcon(new ImageIcon("D:\\JAVA\\Swing\\1\\images\\add.png"));
		
		JButton jb_bookReset = new JButton("Reset");
		jb_bookReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetValue();
			}
		});
		jb_bookReset.setIcon(new ImageIcon("D:\\JAVA\\Swing\\1\\images\\reset.png"));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(77)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_5)
							.addGap(57)
							.addComponent(aboutBookText, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(bookNametext, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jrb_male, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(jrb_female, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
								.addComponent(jcb_BookType, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jb_bookReset, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
									.addGap(59)))
							.addGap(47)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_3)
									.addGap(14)
									.addComponent(authorNameText, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_4)
									.addGap(18)
									.addComponent(priceText, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
							.addContainerGap(90, Short.MAX_VALUE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(63)
					.addComponent(jb_AddBook, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(572, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(51)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(bookNametext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(authorNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(66)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(jrb_male)
						.addComponent(jrb_female)
						.addComponent(lblNewLabel_4)
						.addComponent(priceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(67)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(jcb_BookType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(67)
							.addComponent(lblNewLabel_5))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(46)
							.addComponent(aboutBookText, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jb_AddBook)
						.addComponent(jb_bookReset))
					.addGap(24))
		);
		getContentPane().setLayout(groupLayout);
	}
}
