package com.panel;

import java.awt.BorderLayout;
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

public class RecruitUpdateDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_position;
	private JTextField t_salary;
	private JTextField t_deadline;
	JComboBox cb_company;
	JComboBox cb_degree;
	RecruitDao recruitDao=new RecruitDao();
	StudentDao studentDao=new StudentDao();
	CompanyDao companyDao=new CompanyDao();

	/**
	 * Create the frame.
	 */
	public RecruitUpdateDialog(Recruit recruit) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 556, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("id");
		label.setBounds(53, 43, 33, 15);
		panel.add(label);
		
		t_id = new JTextField();
		t_id.setEditable(false);
		t_id.setColumns(10);
		t_id.setBounds(124, 40, 93, 21);
		panel.add(t_id);
		
		JLabel label_1 = new JLabel("company");
		label_1.setBounds(300, 43, 54, 15);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("position");
		label_2.setBounds(43, 99, 54, 15);
		panel.add(label_2);
		
		t_position = new JTextField();
		t_position.setColumns(10);
		t_position.setBounds(124, 96, 93, 21);
		panel.add(t_position);
		
		JLabel label_3 = new JLabel("degree");
		label_3.setBounds(300, 99, 54, 15);
		panel.add(label_3);
		
		t_salary = new JTextField();
		t_salary.setColumns(10);
		t_salary.setBounds(124, 159, 93, 21);
		panel.add(t_salary);
		
		JLabel label_4 = new JLabel("salary");
		label_4.setBounds(43, 162, 46, 15);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("deadline");
		label_5.setBounds(300, 162, 54, 15);
		panel.add(label_5);
		
		t_deadline = new JTextField();
		t_deadline.setColumns(10);
		t_deadline.setBounds(364, 159, 93, 21);
		panel.add(t_deadline);
		
		JLabel label_6 = new JLabel("other:");
		label_6.setBounds(43, 215, 54, 15);
		panel.add(label_6);
		
		JTextArea ta_other = new JTextArea();
		ta_other.setBounds(107, 211, 183, 84);
		panel.add(ta_other);
		
		JButton btn_confirm = new JButton("confirm");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				
					boolean mod=recruitDao.recruitModify(recruit);
					if(mod){
						JOptionPane.showMessageDialog(null, "Modify Success");
					}else{
						JOptionPane.showMessageDialog(null, "Wrong");
					}
			}
		});
		btn_confirm.setBounds(82, 322, 93, 23);
		panel.add(btn_confirm);
		
		JButton btn_cancel = new JButton("cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancel.setBounds(225, 322, 93, 23);
		panel.add(btn_cancel);
		
		cb_company = new JComboBox();
		cb_company.setBounds(364, 40, 93, 21);
		panel.add(cb_company);
		
		cb_degree = new JComboBox();
		cb_degree.setBounds(364, 96, 93, 21);
		panel.add(cb_degree);
		
		fillCompany();
		fillDegree();
		
		t_id.setText(recruit.getId()+"");
		t_deadline.setText(recruit.getDeadline());
		t_position.setText(recruit.getPosition());
		t_salary.setText(recruit.getSalary());
		ta_other.setText(recruit.getOther());
		for(int i=0;i<cb_degree.getItemCount();i++){
			if(recruit.getDegree().equals(cb_degree.getItemAt(i))){
				cb_degree.setSelectedItem(recruit.getDegree());
			}
		}
		for(int i=0;i<cb_company.getItemCount();i++){
			if(recruit.getCompany().equals(cb_company.getItemAt(i))){
				cb_company.setSelectedItem(recruit.getCompany());
			}
		}
	}
	
	public void fillCompany(){
		
			List<Company> listCompany=companyDao.companyList(new Company());
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

}
