package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.BookDao;
import com.dao.BookTypeDao;
import com.model.Book;
import com.model.BookType;
import com.util.StringUtil;

@SuppressWarnings("serial")
public class BookManageInterFrm extends JInternalFrame {
	@Autowired
	private BookTypeDao bookTypeDao;
	@Autowired
	private BookDao bookDao;
	
	private JTable bookTable;
	private JTextField s_BookNameText;
	private JTextField s_AuthorText;
	@SuppressWarnings("rawtypes")
	JComboBox s_jcbBookType;
	private JTextField e_idText;
	private JTextField e_bookNameText;
	private JTextField e_priceText;
	private JTextField e_authorText;
	JRadioButton e_jrbFemale;
	JRadioButton e_jrbmale;
	JTextArea e_aboutBook;
	@SuppressWarnings("rawtypes")
	JComboBox e_cbType;
	@SuppressWarnings("rawtypes")
	JComboBox s_cbGender;
	
	@SuppressWarnings("rawtypes")
	public void fillTable(Book book){
		DefaultTableModel dtm=(DefaultTableModel) bookTable.getModel();
		dtm.setRowCount(0);
			List<Map<String,Object>> bookList=bookDao.bookList(book);
			for (Map<String, Object> map : bookList) {
				Vector<Comparable> v=new Vector<Comparable>();
				v.add(map.get("id")+"");
				v.add(map.get("bookName")+"");
				v.add(map.get("author")+"");
				v.add(map.get("sex")+"");
				v.add(map.get("price")+"");
				v.add(map.get("bookDesc")+"");
				v.add(map.get("bookTypeName")+"");
				dtm.addRow(v);
			}
	}
	
	
	@SuppressWarnings("unchecked")
	public void fillBookType(String type){
		BookType bookType=null;
			List<Map<String, Object>> bookTypeList=bookTypeDao.bookTypeList(new BookType());
			if("search".equalsIgnoreCase(type)){
				bookType=new BookType();
				bookType.setBookTypeName("Select on");
				bookType.setId(-1);
				this.s_jcbBookType.addItem(bookType);
			}
			if(bookTypeList!=null)
				for (Map<String, Object> map : bookTypeList) {
					bookType=new BookType();
					bookType.setId((Integer)map.get("id"));
					bookType.setBookTypeName(map.get("bookTypeName")+"");
					if("search".equalsIgnoreCase(type)){
						this.s_jcbBookType.addItem(bookType);
					}else if("edit".equalsIgnoreCase(type)){
						this.e_cbType.addItem(bookType);
					}
				}
	}
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BookManageInterFrm() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 1030, 695);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Edit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE))
							.addGap(23))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_4 = new JLabel("ID");
		
		e_idText = new JTextField();
		e_idText.setEditable(false);
		e_idText.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("BookName");
		
		e_bookNameText = new JTextField();
		e_bookNameText.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Gender");
		
		e_jrbmale = new JRadioButton("Male");
		
		e_jrbFemale = new JRadioButton("Female");
		ButtonGroup bg=new ButtonGroup();
		bg.add(e_jrbFemale);
		bg.add(e_jrbmale);
		
		JLabel lblNewLabel_7 = new JLabel("Price");
		
		e_priceText = new JTextField();
		e_priceText.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Author");
		
		e_authorText = new JTextField();
		e_authorText.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Type");
		
		e_cbType = new JComboBox();
		
		JLabel lblNewLabel_10 = new JLabel("AboutBook:");
		
		e_aboutBook = new JTextArea();
		
		JButton e_btModify = new JButton("Modify");
		e_btModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=e_idText.getText();
				BookType bookType=(BookType) e_cbType.getSelectedItem();
				int bookTypeId=bookType.getId();
				String author=e_authorText.getText();
				String bookName=e_bookNameText.getText();
				String sex=null;
				if(e_jrbmale.isSelected()){
					sex="male";
				}else if(e_jrbFemale.isSelected()){
					sex="female";
				}
				String price=e_priceText.getText();
				String bookDesc=e_aboutBook.getText();
				if(StringUtil.isEmpty(id)){
					JOptionPane.showMessageDialog(null, "select one row");
					return;
				}
				Book book=new Book(Integer.parseInt(id), bookName, author, sex, Float.parseFloat(price), bookDesc, bookTypeId);
				int n=JOptionPane.showConfirmDialog(null, "Modify?");
				if(n==0){
						int modifyNum=bookDao.modifyBook(book);
						if(modifyNum==1){
							//JOptionPane.showMessageDialog(null, "Modify Success");
							fillTable(new Book());
						}else{
							JOptionPane.showMessageDialog(null, "Wrong");
						}
					}
				}
			});
		
		e_btModify.setIcon(new ImageIcon("D:\\JAVA\\Swing\\1\\images\\modify.png"));
		
		JButton e_jbDelete = new JButton("Delete");
		e_jbDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=e_idText.getText();
				if(StringUtil.isEmpty(id)){
					JOptionPane.showMessageDialog(null, "select one row");
					return;
				}
				int n=JOptionPane.showConfirmDialog(null, "Delete?");
				if(n==0){
						int deleteNum=bookDao.deleteBook(id);
						if(deleteNum==1){
							JOptionPane.showMessageDialog(null, "Delete Success");
							resetValue();
							fillTable(new Book());
						}else{
							JOptionPane.showMessageDialog(null, "Wrong");
						}
				}
			}
			
			private void resetValue(){
				e_idText.setText("");
				e_bookNameText.setText("");
				e_authorText.setText("");
				e_aboutBook.setText("");
				e_jrbmale.setSelected(true);
				e_priceText.setText("");
				if(e_cbType.getItemCount()>0){
					e_cbType.setSelectedItem(0);
				}
				
			}
		});
		e_jbDelete.setIcon(new ImageIcon("D:\\JAVA\\Swing\\1\\images\\delete.png"));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(lblNewLabel_7)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(e_priceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(lblNewLabel_4)
									.addGap(18)
									.addComponent(e_idText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(38)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_8))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(e_authorText)
								.addComponent(e_bookNameText, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
							.addGap(34)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_9, Alignment.TRAILING)
								.addComponent(lblNewLabel_6, Alignment.TRAILING))
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(18)
									.addComponent(e_jrbmale, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(e_jrbFemale, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(34)
									.addComponent(e_cbType, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(109)
							.addComponent(e_btModify)
							.addGap(48)
							.addComponent(e_jbDelete)))
					.addGap(10)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_10)
						.addComponent(e_aboutBook, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(e_jrbmale)
						.addComponent(e_jrbFemale)
						.addComponent(e_bookNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5)
						.addComponent(e_idText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4)
						.addComponent(lblNewLabel_10))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(49)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_7)
								.addComponent(e_priceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(e_authorText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_8)
								.addComponent(e_cbType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_9))
							.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(e_btModify)
								.addComponent(e_jbDelete))
							.addContainerGap())
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(e_aboutBook, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Name");
		
		JLabel lblNewLabel_1 = new JLabel("Gender");
		
		s_BookNameText = new JTextField();
		s_BookNameText.setColumns(10);
		
		
		JLabel lblNewLabel_2 = new JLabel("Author");
		
		s_AuthorText = new JTextField();
		s_AuthorText.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Type");
		
		s_jcbBookType = new JComboBox();
		
		JButton s_Search = new JButton("Search");
		s_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String bookName=s_BookNameText.getText();
				String author=s_AuthorText.getText();
				String sex=null;
				if(s_cbGender.getSelectedIndex()==1){
					sex="Male";
				}else if(s_cbGender.getSelectedIndex()==2){
					sex="Female";
				}else{
					sex="";
				}
				BookType bookType=(BookType) s_jcbBookType.getSelectedItem();
				int bookTypeId=bookType.getId();
				
				Book book=new Book(bookName, author, sex, bookTypeId);
				
				fillTable(book);
			}
			
		});
		s_Search.setIcon(new ImageIcon("D:\\JAVA\\Swing\\1\\images\\search.png"));
		
		s_cbGender = new JComboBox();
		s_cbGender.setModel(new DefaultComboBoxModel(new String[] {"", "Male", "Female"}));
		s_cbGender.setToolTipText("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(35)
							.addComponent(lblNewLabel)
							.addGap(18)
							.addComponent(s_BookNameText, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_2)
							.addGap(18)
							.addComponent(s_AuthorText, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
							.addGap(52)
							.addComponent(lblNewLabel_1)
							.addGap(29)
							.addComponent(s_cbGender, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addGap(88)
							.addComponent(lblNewLabel_3)
							.addGap(18)
							.addComponent(s_jcbBookType, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(389)
							.addComponent(s_Search)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(s_jcbBookType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel)
							.addComponent(s_BookNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_2)
							.addComponent(s_AuthorText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_1)
							.addComponent(lblNewLabel_3)
							.addComponent(s_cbGender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(32)
					.addComponent(s_Search)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		bookTable = new JTable();
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				int row=bookTable.getSelectedRow();
				e_idText.setText(bookTable.getValueAt(row, 0)+"");
				e_bookNameText.setText((String) bookTable.getValueAt(row, 1));
				e_authorText.setText((String) bookTable.getValueAt(row, 2));
				String sex=(String)bookTable.getValueAt(row, 3);
				if("Male".equalsIgnoreCase(sex)){
					e_jrbmale.setSelected(true);
				}else if("Female".equalsIgnoreCase(sex)){
					e_jrbFemale.setSelected(true);
				}
				e_priceText.setText(bookTable.getValueAt(row, 4)+"");
				e_aboutBook.setText((String) bookTable.getValueAt(row, 5));
				String bookTypeName=(String)bookTable.getValueAt(row, 6);
				int n=e_cbType.getItemCount();
				for(int i=0;i<n;i++){
					BookType item=(BookType)e_cbType.getItemAt(i);
					if(item.getBookTypeName().equals(bookTypeName)){
						e_cbType.setSelectedItem(item);
					}
				}
			}
		});
		bookTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Author", "Gender", "Price", "AboutBook", "Type"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(bookTable);
		getContentPane().setLayout(groupLayout);

		e_jrbmale.setSelected(true);

	}
}
