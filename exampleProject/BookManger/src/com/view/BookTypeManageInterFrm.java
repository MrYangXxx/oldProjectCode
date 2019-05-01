package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import com.model.BookType;
import com.util.StringUtil;

@SuppressWarnings("serial")
public class BookTypeManageInterFrm extends JInternalFrame {
	@Autowired
	private BookTypeDao bookTypeDao;
	@Autowired
	private BookDao bookDao;
	
	private JTable bookTypeTable;
	private JTextField s_TypeNametext;
	private JTextField idText;
	private JTextField bookTypeNameText;

	public void fillTable(BookType bookType){
		DefaultTableModel dtm=(DefaultTableModel) bookTypeTable.getModel();
		dtm.setRowCount(0);
			List<Map<String, Object>> bookTypeList=bookTypeDao.bookTypeList(bookType);
				for (Map<String, Object> map : bookTypeList) {
					Vector<String> v=new Vector<String>();
					v.add(map.get("id")+"");
					v.add(map.get("bookTypeName")+"");
					v.add(map.get("bookTypeDesc")+"");
					dtm.addRow(v);
				}
		}
	
	/**
	 * Create the frame.
	 */
	public BookTypeManageInterFrm() {
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 100, 715, 608);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("TypeName");
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s_bookTypeName=s_TypeNametext.getText();
				BookType bookType=new BookType();
				bookType.setBookTypeName(s_bookTypeName);
				fillTable(bookType);
			}
		});
		btnNewButton.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\search.png"));
		
		s_TypeNametext = new JTextField();
		s_TypeNametext.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Edit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(84)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(35)
							.addComponent(s_TypeNametext, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
							.addComponent(btnNewButton)))
					.addGap(108))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(s_TypeNametext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		
		idText = new JTextField();
		idText.setEditable(false);
		idText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("TypeName");
		
		JLabel lblNewLabel_3 = new JLabel("AboutType");
		
		bookTypeNameText = new JTextField();
		bookTypeNameText.setColumns(10);
		
		final JTextArea aboutTypeText = new JTextArea();
		
		JButton jb_modify = new JButton("Modify");
		jb_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=idText.getText();
				String bookTypeName=bookTypeNameText.getText();
				String aboutType=aboutTypeText.getText();
				if(StringUtil.isEmpty(id)){
					JOptionPane.showMessageDialog(null, "select one row");
					return;
				}
				BookType bookType=new BookType(Integer.parseInt(id), bookTypeName, aboutType);
				int n=JOptionPane.showConfirmDialog(null, "Modify?");
				if(n==0){
						int modifyNum=bookTypeDao.modifyBookType(bookType);
						if(modifyNum==1){
							//JOptionPane.showMessageDialog(null, "Modify Success");
							fillTable(new BookType());
						}else{
							JOptionPane.showMessageDialog(null, "Wrong");
						}
					}
				}
			});
		jb_modify.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\modify.png"));
		
		JButton jb_delete = new JButton("Delete");
		jb_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=idText.getText();
				if(StringUtil.isEmpty(id)){
					JOptionPane.showMessageDialog(null, "select one row");
					return;
				}
				int n=JOptionPane.showConfirmDialog(null, "Delete?");
				if(n==0){
						boolean flag=bookDao.getBookByBookTypeId(id);
						if(flag){
							JOptionPane.showMessageDialog(null, "Delete Wrong,This BookType have some book!");
							return;
						}
						int deleteNum=bookTypeDao.deleteBookType(id);
						if(deleteNum==1){
							JOptionPane.showMessageDialog(null, "Delete Success");
							resetValue();
							fillTable(new BookType());
						}else{
							JOptionPane.showMessageDialog(null, "Wrong");
						}
					}
				}
			
			private void resetValue(){
				idText.setText("");
				bookTypeNameText.setText("");
				aboutTypeText.setText("");
			}
		});
		jb_delete.setIcon(new ImageIcon("D:\\JAVA\\Source\\BookManger\\images\\delete.png"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_3)
									.addGap(18)
									.addComponent(aboutTypeText, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addGap(18)
									.addComponent(idText, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addGap(50)
									.addComponent(lblNewLabel_2)
									.addGap(18)
									.addComponent(bookTypeNameText, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(46)
							.addComponent(jb_modify)
							.addGap(29)
							.addComponent(jb_delete)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(idText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(bookTypeNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(38)
							.addComponent(aboutTypeText, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(53)
							.addComponent(lblNewLabel_3)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jb_modify)
						.addComponent(jb_delete))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		bookTypeTable = new JTable();
		bookTypeTable.addMouseListener(new MouseAdapter() {
			@Override
			//get select row
			public void mousePressed(MouseEvent arg0) {
				int row=bookTypeTable.getSelectedRow();
				idText.setText((String) bookTypeTable.getValueAt(row, 0));
				bookTypeNameText.setText((String) bookTypeTable.getValueAt(row, 1));
				aboutTypeText.setText((String) bookTypeTable.getValueAt(row, 2));
			}
		});
		bookTypeTable.setFillsViewportHeight(true);
		bookTypeTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "TypeName", "AboutType"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(bookTypeTable);
		getContentPane().setLayout(groupLayout);

	}
}
