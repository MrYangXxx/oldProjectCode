package com.panel;

import java.awt.BorderLayout;
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

public class StudentInfoUpdateDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_sid;
	JComboBox cb_state;
	JComboBox cb_degree;
	JRadioButton rb_male;
	JRadioButton rb_female;
	JFormattedTextField t_age;
	StudentDao studentDao=new StudentDao();

	/**
	 * Create the frame.
	 */
	public StudentInfoUpdateDialog(Student student) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 629, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("id");
		label.setBounds(33, 50, 56, 16);
		panel.add(label);
		
		t_id = new JTextField();
		t_id.setEditable(false);
		t_id.setColumns(10);
		t_id.setBounds(123, 47, 116, 22);
		panel.add(t_id);
		
		JLabel label_1 = new JLabel("name");
		label_1.setBounds(33, 115, 56, 16);
		panel.add(label_1);
		
		t_name = new JTextField();
		t_name.setColumns(10);
		t_name.setBounds(123, 112, 116, 22);
		panel.add(t_name);
		
		JLabel label_2 = new JLabel("s_id");
		label_2.setBounds(318, 50, 56, 16);
		panel.add(label_2);
		
		t_sid = new JTextField();
		t_sid.setColumns(10);
		t_sid.setBounds(400, 47, 116, 22);
		panel.add(t_sid);
		
		JLabel label_3 = new JLabel("age");
		label_3.setBounds(318, 115, 56, 16);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("gender");
		label_4.setBounds(33, 195, 56, 16);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("state");
		label_5.setBounds(266, 195, 56, 16);
		panel.add(label_5);
		
		cb_state = new JComboBox();
		cb_state.setModel(new DefaultComboBoxModel(new String[] {"\u5F85\u4E1A", "\u5DE5\u4F5C"}));
		cb_state.setEnabled(false);
		cb_state.setBounds(334, 192, 66, 22);
		panel.add(cb_state);
		
		JLabel label_6 = new JLabel("degree");
		label_6.setBounds(445, 195, 56, 16);
		panel.add(label_6);
		
		cb_degree = new JComboBox();
		cb_degree.setBounds(513, 192, 56, 22);
		panel.add(cb_degree);
		
		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(StringUtil.isEmpty(t_name.getText())||StringUtil.isEmpty(t_id.getText())){
					JOptionPane.showMessageDialog(null, "id And name Could Not Empty");
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
				
					boolean mod=studentDao.studentModify(student);
					if(mod){
						JOptionPane.showMessageDialog(null, "Modify Success");
					}else{
						JOptionPane.showMessageDialog(null, "Wrong,please check id whether exist");
					}
				
			}
		});
		button.setBounds(142, 286, 97, 25);
		panel.add(button);
		
		JButton button_1 = new JButton("Cancel");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_1.setBounds(303, 286, 97, 25);
		panel.add(button_1);
		
		rb_male = new JRadioButton("Male");
		rb_male.setSelected(true);
		rb_male.setBounds(83, 191, 56, 25);
		panel.add(rb_male);
		
		rb_female = new JRadioButton("Female");
		rb_female.setBounds(153, 191, 71, 25);
		panel.add(rb_female);
		
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
		t_age = new JFormattedTextField(formatter);
		
		t_age.setBounds(400, 115, 122, 22);
		panel.add(t_age);
		
		fillDegree();
		
		t_id.setText(student.getId()+"");
		t_sid.setText(student.getSid()+"");
		t_name.setText(student.getName()+"");
		t_age.setText(student.getAge()+"");
		
		if(student.getGender().equals("female")){
			rb_female.setSelected(true);
		}else{
			rb_male.setSelected(true);
		}
		
		if(student.getState().equals("工作")){
			cb_state.setSelectedItem("工作");
		}else{
			cb_state.setSelectedItem("待业");
		}
		
		for(int i=0;i<cb_degree.getItemCount();i++){
			if(student.getDegree().equals(cb_degree.getItemAt(i))){
				cb_degree.setSelectedItem(student.getDegree());
			}
		}

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
	
}
