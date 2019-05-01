package com.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.bean.Student;
import com.dao.StudentDao;
import com.util.DbUtil;
import com.util.StringUtil;

public class Quick_S_Frame extends JFrame {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_sid;
	private JTable table;
	JFormattedTextField t_age;
	JComboBox cb_degree;
	JComboBox cb_gender;
	JComboBox cb_state;
	StudentDao studentDao=new StudentDao();
	

	/**
	 * Create the frame.
	 */
	public Quick_S_Frame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 737, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 719, 210);
		contentPane.add(panel);
		
		JLabel label = new JLabel("id");
		label.setBounds(33, 31, 32, 16);
		panel.add(label);
		
		t_id = new JTextField();
		t_id.setColumns(10);
		t_id.setBounds(83, 28, 116, 22);
		panel.add(t_id);
		
		JLabel label_1 = new JLabel("name");
		label_1.setBounds(479, 31, 56, 16);
		panel.add(label_1);
		
		t_name = new JTextField();
		t_name.setColumns(10);
		t_name.setBounds(548, 28, 116, 22);
		panel.add(t_name);
		
		JLabel label_2 = new JLabel("s_id");
		label_2.setBounds(241, 31, 40, 16);
		panel.add(label_2);
		
		t_sid = new JTextField();
		t_sid.setColumns(10);
		t_sid.setBounds(294, 28, 116, 22);
		panel.add(t_sid);
		
		JLabel label_3 = new JLabel("age");
		label_3.setBounds(241, 92, 32, 16);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("gender");
		label_4.setBounds(33, 92, 40, 16);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("state");
		label_5.setBounds(415, 92, 40, 16);
		panel.add(label_5);
		
		cb_state = new JComboBox();
		cb_state.setModel(new DefaultComboBoxModel(new String[] {"", "\u5F85\u4E1A", "\u5DE5\u4F5C"}));
		cb_state.setBounds(464, 92, 71, 22);
		panel.add(cb_state);
		
		JLabel label_6 = new JLabel("degree");
		label_6.setBounds(561, 92, 56, 16);
		panel.add(label_6);
		
		cb_degree = new JComboBox();
		cb_degree.setBounds(624, 89, 71, 22);
		panel.add(cb_degree);
		
		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=t_name.getText();
				int id = 0;
				int sid = 0;
				int age = 0;
				if(StringUtil.isNotEmpty(t_id.getText())&&StringUtil.isNumeric(t_id.getText()))
					id=Integer.parseInt(t_id.getText());
				if(StringUtil.isNotEmpty(t_sid.getText())&&StringUtil.isNumeric(t_sid.getText()))
					sid=Integer.parseInt(t_sid.getText());
				if(StringUtil.isNotEmpty(t_age.getText()))
					age=Integer.parseInt(t_age.getText());
				String state = null;
				if(cb_state.getItemCount()>0){
					state=(String) cb_state.getSelectedItem();
				}
				String degree = null;
				if(cb_degree.getItemCount()>0){
					degree=(String) cb_degree.getSelectedItem();
				}
				String gender = null;
				if(cb_gender.getItemCount()>0){
					gender=(String) cb_gender.getSelectedItem();
				}
				Student student=new Student(id, sid, name, gender, age, state, degree);
				fillTable(student);
			}
		});
		btn_search.setBounds(241, 161, 97, 25);
		panel.add(btn_search);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btn_cancel.setBounds(399, 161, 97, 25);
		panel.add(btn_cancel);
		
		ButtonGroup bg=new ButtonGroup();
		
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter(' ');
		t_age = new JFormattedTextField(formatter);
		
		t_age.setBounds(294, 89, 87, 22);
		panel.add(t_age);
		
		cb_gender = new JComboBox();
		cb_gender.setModel(new DefaultComboBoxModel(new String[] {"", "male", "female"}));
		cb_gender.setBounds(100, 89, 71, 22);
		panel.add(cb_gender);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 223, 697, 251);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"id", "s_id", "name", "age", "gender", "state", "degree"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		table.setFillsViewportHeight(true);
		
		fillDegree();
	}
	
	public void fillDegree(){
		try(Connection con=DbUtil.getCon();){
			ResultSet rs=studentDao.degreeList(con);
			cb_degree.addItem("");
			while(rs.next()){
				this.cb_degree.addItem(rs.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void fillTable(Student student){
		DefaultTableModel dtm=(DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		
			List<Student> studentList = studentDao.studentList(student);
			for (Student student2 : studentList) {
				Vector<Comparable> v=new Vector<Comparable>();
				v.add(student2.getId());
				v.add(student2.getSid());
				v.add(student2.getName());
				v.add(student2.getAge());
				v.add(student2.getGender());
				v.add(student2.getState());
				v.add(student2.getDegree());
				dtm.addRow(v);
			}
			
	}
}
