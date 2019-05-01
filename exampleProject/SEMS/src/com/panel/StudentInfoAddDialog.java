package com.panel;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.bean.Student;
import com.dao.StudentDao;
import com.util.DbUtil;
import com.util.StringUtil;

public class StudentInfoAddDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_sid;
	JComboBox cb_degree;
	JComboBox cb_state;
	JRadioButton rb_male;
	JRadioButton rb_female;
	JFormattedTextField t_age;
	StudentDao studentDao=new StudentDao();


	/**
	 * Create the frame.
	 */
	public StudentInfoAddDialog() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 599, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(33, 50, 56, 16);
		contentPane.add(lblNewLabel);
		
		t_id = new JTextField();
		t_id.setBounds(123, 47, 116, 22);
		contentPane.add(t_id);
		t_id.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("name");
		lblNewLabel_1.setBounds(33, 115, 56, 16);
		contentPane.add(lblNewLabel_1);
		
		t_name = new JTextField();
		t_name.setBounds(123, 112, 116, 22);
		contentPane.add(t_name);
		t_name.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("s_id");
		lblNewLabel_2.setBounds(318, 50, 56, 16);
		contentPane.add(lblNewLabel_2);
		
		t_sid = new JTextField();
		t_sid.setBounds(400, 47, 116, 22);
		contentPane.add(t_sid);
		t_sid.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("age");
		lblNewLabel_3.setBounds(318, 115, 56, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("gender");
		lblNewLabel_4.setBounds(33, 195, 56, 16);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("state");
		lblNewLabel_5.setBounds(266, 195, 56, 16);
		contentPane.add(lblNewLabel_5);
		
		cb_state = new JComboBox();
		cb_state.setModel(new DefaultComboBoxModel(new String[] {"\u5F85\u4E1A", "\u5DE5\u4F5C"}));
		cb_state.setBounds(334, 192, 66, 22);
		contentPane.add(cb_state);
		
		JLabel lblNewLabel_6 = new JLabel("degree");
		lblNewLabel_6.setBounds(445, 195, 56, 16);
		contentPane.add(lblNewLabel_6);
		
		cb_degree = new JComboBox();
		cb_degree.setBounds(513, 192, 56, 22);
		contentPane.add(cb_degree);
		
		JButton btn_enter = new JButton("Confirm");
		btn_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(StringUtil.isEmpty(t_name.getText())||StringUtil.isEmpty(t_sid.getText())||StringUtil.isEmpty(t_id.getText())){
					JOptionPane.showMessageDialog(null, "id,sid And name Could Not Empty");
					return;
				}
				String name=t_name.getText();
				int id=Integer.parseInt(t_id.getText());
				int sid=Integer.parseInt(t_sid.getText());
				int age=Integer.parseInt(t_age.getText());
				
				String gender="";
				if(rb_male.isSelected()){
					gender="male";
				}else if(rb_female.isSelected()){
					gender="female";
				}
				
				String state = null;
				if(cb_state.getItemCount()>0){
					state=(String) cb_state.getSelectedItem();
				}
				String degree = null;
				if(cb_degree.getItemCount()>0){
					degree=(String) cb_degree.getSelectedItem();
				}
				
				Student student=new Student(id, sid, name, gender, age, state, degree);
				
					int addNum=studentDao.studentAdd(student);
					if(addNum==1){
						JOptionPane.showMessageDialog(null, "Add Success");
						resetValue();
					}else{
						JOptionPane.showMessageDialog(null, "Wrong,Please check id or sid whether exist");
					}
			}
		});
		btn_enter.setBounds(142, 286, 97, 25);
		contentPane.add(btn_enter);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!t_id.getText().trim().equals("")||!t_name.getText().trim().equals("")||!t_sid.getText().trim().equals("")){
					int n=JOptionPane.showConfirmDialog(null, "YOU HAVE DATA,QUIT?");
					if(n==0){
					dispose();
					}
				}else{
					dispose();
				}
			}
		});
		btn_cancel.setBounds(303, 286, 97, 25);
		contentPane.add(btn_cancel);
		
		rb_male = new JRadioButton("Male");
		rb_male.setSelected(true);
		rb_male.setBounds(83, 191, 56, 25);
		contentPane.add(rb_male);
		
		rb_female = new JRadioButton("Female");
		rb_female.setBounds(153, 191, 71, 25);
		contentPane.add(rb_female);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(rb_male);
		bg.add(rb_female);
		
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter(' ');
		t_age = new JFormattedTextField(formatter);

					
		t_age.setBounds(400, 115, 116, 22);
		contentPane.add(t_age);
		fillDegree();
		
	}

	
	
	public void fillDegree(){
		try(Connection con=DbUtil.getCon();){
			ResultSet rs=studentDao.degreeList(con);
			while(rs.next()){
				this.cb_degree.addItem(rs.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void resetValue(){
		t_name.setText("");
		t_id.setText("");
		t_sid.setText("");
		t_age.setText("");
		rb_male.setSelected(true);
		if(this.cb_state.getItemCount()>0){
			cb_state.setSelectedIndex(0);
		}
		if(this.cb_degree.getItemCount()>0){
			cb_degree.setSelectedIndex(0);
		}
		}
	
}
