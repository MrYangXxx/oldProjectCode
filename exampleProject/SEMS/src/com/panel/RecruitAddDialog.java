package com.panel;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.bean.Company;
import com.bean.Recruit;
import com.dao.CompanyDao;
import com.dao.RecruitDao;
import com.dao.StudentDao;
import com.util.DbUtil;
import com.util.StringUtil;

public class RecruitAddDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_position;
	private JTextField t_salary;
	private JTextField t_deadline;
	JComboBox cb_company;
	JComboBox cb_degree;
	JTextArea ta_other ;
	RecruitDao recruitDao=new RecruitDao();
	StudentDao studentDao=new StudentDao();
	CompanyDao companyDao=new CompanyDao();

	/**
	 * Create the frame.
	 */
	public RecruitAddDialog() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 528, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(53, 43, 33, 15);
		contentPane.add(lblNewLabel);
		
		t_id = new JTextField();
		t_id.setEditable(false);
		t_id.setBounds(124, 40, 93, 21);
		contentPane.add(t_id);
		t_id.setColumns(10);
		t_id.setText(getId()+"");
		
		JLabel lblNewLabel_1 = new JLabel("company");
		lblNewLabel_1.setBounds(300, 43, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("position");
		lblNewLabel_2.setBounds(43, 99, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		t_position = new JTextField();
		t_position.setBounds(124, 96, 93, 21);
		contentPane.add(t_position);
		t_position.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("degree");
		lblNewLabel_3.setBounds(300, 99, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		t_salary = new JTextField();
		t_salary.setBounds(124, 159, 93, 21);
		contentPane.add(t_salary);
		t_salary.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("salary");
		lblNewLabel_4.setBounds(43, 162, 46, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("deadline");
		lblNewLabel_5.setBounds(300, 162, 54, 15);
		contentPane.add(lblNewLabel_5);
		
		t_deadline = new JTextField();
		t_deadline.setBounds(364, 159, 93, 21);
		contentPane.add(t_deadline);
		t_deadline.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("other:");
		lblNewLabel_6.setBounds(43, 215, 54, 15);
		contentPane.add(lblNewLabel_6);
		
		ta_other = new JTextArea();
		ta_other.setBounds(107, 211, 183, 84);
		contentPane.add(ta_other);
		
		JButton btn_confirm = new JButton("confirm");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(StringUtil.isEmpty(cb_company.getSelectedItem().toString())||StringUtil.isEmpty(t_id.getText())){
					JOptionPane.showMessageDialog(null, "id And name Could Not Empty");
					return;
				}
				
				int id=Integer.parseInt(t_id.getText());
				String salary=t_salary.getText();
				String position=t_position.getText();
				String other=ta_other.getText();
				String deadline=t_deadline.getText();
				String company = null;
				if(cb_company.getItemCount()>0){
					company=(String) cb_company.getSelectedItem();
				}
				String degree = null;
				if(cb_degree.getItemCount()>0){
					degree=(String) cb_degree.getSelectedItem();
				}
				
				Recruit recruit=new Recruit(id, company, position, degree, salary, deadline);
					int addNum=recruitDao.recruitAdd(recruit);
					if(addNum==1){
						JOptionPane.showMessageDialog(null, "Add Success");
						resetValue();
					}else{
						JOptionPane.showMessageDialog(null, "Wrong");
					}
			}
		});
		btn_confirm.setBounds(82, 322, 93, 23);
		contentPane.add(btn_confirm);
		
		JButton btn_cancel = new JButton("cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btn_cancel.setBounds(225, 322, 93, 23);
		contentPane.add(btn_cancel);
		
		cb_company = new JComboBox();
		cb_company.setBounds(364, 40, 93, 21);
		contentPane.add(cb_company);
		
		cb_degree = new JComboBox();
		cb_degree.setBounds(364, 96, 93, 21);
		contentPane.add(cb_degree);
		fillCompany();
		fillDegree();
	}
	
	
	public void fillCompany(){
			List<Company> listCompany=companyDao.companyList( new Company());
			for (Company company : listCompany) {
				this.cb_company.addItem(company.getName());
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
	
	private void resetValue(){
		t_deadline.setText("");
		t_id.setText("");
		t_position.setText("");
		t_salary.setText("");
		ta_other.setText("");
		if(this.cb_company.getItemCount()>0){
			cb_company.setSelectedIndex(0);
		}
		if(this.cb_degree.getItemCount()>0){
			cb_degree.setSelectedIndex(0);
		}
		}
	
	public int getId(){
		return recruitDao.getMaxId()+1;
	}
}
