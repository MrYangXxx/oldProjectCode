package com.panel;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.bean.Stu_work;
import com.dao.CompanyDao;
import com.dao.Stu_workDao;
import com.util.StringUtil;

public class Stu_workUpdateDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_position;
	private JTextField t_salary;
	JComboBox cb_company;
	CompanyDao companyDao=new CompanyDao();
	Stu_workDao stu_workDao=new Stu_workDao();
	
	/**
	 * Create the frame.
	 */
	public Stu_workUpdateDialog(Stu_work stu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 624, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("id");
		label.setBounds(45, 39, 56, 16);
		panel.add(label);
		
		JLabel label_1 = new JLabel("name");
		label_1.setBounds(299, 39, 56, 16);
		panel.add(label_1);
		
		t_id = new JTextField();
		t_id.setEditable(false);
		t_id.setColumns(10);
		t_id.setBounds(113, 36, 116, 22);
		panel.add(t_id);
		
		t_name = new JTextField();
		t_name.setColumns(10);
		t_name.setBounds(387, 33, 116, 22);
		panel.add(t_name);
		
		JLabel label_2 = new JLabel("company");
		label_2.setBounds(32, 121, 56, 16);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("position");
		label_3.setBounds(299, 121, 56, 16);
		panel.add(label_3);
		
		t_position = new JTextField();
		t_position.setColumns(10);
		t_position.setBounds(387, 118, 116, 22);
		panel.add(t_position);
		
		JLabel label_4 = new JLabel("salary");
		label_4.setBounds(45, 193, 56, 16);
		panel.add(label_4);
		
		t_salary = new JTextField();
		t_salary.setColumns(10);
		t_salary.setBounds(113, 193, 116, 22);
		panel.add(t_salary);
		
		JLabel label_5 = new JLabel("other:");
		label_5.setBounds(299, 193, 56, 16);
		panel.add(label_5);
		
		JTextArea ta_other = new JTextArea();
		ta_other.setLineWrap(true);
		ta_other.setBounds(309, 222, 231, 118);
		panel.add(ta_other);
		
		JButton btn_confirm = new JButton("Confirm");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(StringUtil.isEmpty(t_name.getText())||StringUtil.isEmpty(t_id.getText())){
					JOptionPane.showMessageDialog(null, "id And name Could Not Empty");
					return;
				}
				
				String name=t_name.getText();
				int id=Integer.parseInt(t_id.getText());
				String company = null;
				if(cb_company.getItemCount()>0){
					company=(String) cb_company.getSelectedItem();
				}
				String position=t_position.getText();
				String salary=t_salary.getText();
				String other=ta_other.getText();
				
				Stu_work stu=new Stu_work(id, name, company, position, salary, other);
				
					boolean mod=stu_workDao.stu_workModify(stu);
					if(mod){
						JOptionPane.showMessageDialog(null, "Modify Success");
					}else{
						JOptionPane.showMessageDialog(null, "Wrong");
					}
			}
		});
		btn_confirm.setBounds(72, 343, 97, 25);
		panel.add(btn_confirm);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancel.setBounds(200, 343, 97, 25);
		panel.add(btn_cancel);
		
		cb_company = new JComboBox();
		cb_company.setBounds(114, 118, 115, 22);
		panel.add(cb_company);
		fillCompany();
		
		t_id.setText(stu.getId()+"");
		t_name.setText(stu.getName());
		t_position.setText(stu.getPosition());
		t_salary.setText(stu.getSalary());
		ta_other.setText(stu.getOther());
		
		for(int i=0;i<cb_company.getItemCount();i++){
			if(stu.getCompany().equals(cb_company.getItemAt(i))){
				cb_company.setSelectedItem(stu.getCompany());
			}
		}
	}
	
	public void fillCompany(){
		List<Company> listCompany=companyDao.companyList(new Company());
			for (Company company : listCompany) {
				this.cb_company.addItem(company.getName());
			}
	}

}
