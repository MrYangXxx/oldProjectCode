package com.panel;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.bean.Company;
import com.dao.CompanyDao;
import com.util.StringUtil;

public class CompanyUpdateDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_address;
	private JTextField t_telephone;
	CompanyDao companyDao=new CompanyDao();


	/**
	 * Create the frame.
	 */
	public CompanyUpdateDialog(Company company) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 457, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("id");
		label.setBounds(34, 45, 54, 15);
		panel.add(label);
		
		t_id = new JTextField();
		t_id.setEditable(false);
		t_id.setColumns(10);
		t_id.setBounds(98, 42, 82, 21);
		panel.add(t_id);
		
		JLabel label_1 = new JLabel("name");
		label_1.setBounds(237, 45, 54, 15);
		panel.add(label_1);
		
		t_name = new JTextField();
		t_name.setColumns(10);
		t_name.setBounds(323, 42, 82, 21);
		panel.add(t_name);
		
		JLabel label_2 = new JLabel("address");
		label_2.setBounds(34, 133, 54, 15);
		panel.add(label_2);
		
		t_address = new JTextField();
		t_address.setColumns(10);
		t_address.setBounds(98, 130, 82, 21);
		panel.add(t_address);
		
		JLabel label_3 = new JLabel("telephone");
		label_3.setBounds(237, 133, 74, 15);
		panel.add(label_3);
		
		t_telephone = new JTextField();
		t_telephone.setColumns(10);
		t_telephone.setBounds(323, 130, 82, 21);
		panel.add(t_telephone);
		
		t_address.setText(company.getAddress());
		t_id.setText(company.getId()+"");
		t_name.setText(company.getName());
		t_telephone.setText(company.getTelephone());
		
		JButton btn_confirm = new JButton("Confirm");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StringUtil.isEmpty(t_name.getText())||StringUtil.isEmpty(t_id.getText())){
					JOptionPane.showMessageDialog(null, "id And name Could Not Empty");
					return;
				}
				String name=t_name.getText();
				int id=Integer.parseInt(t_id.getText());
				String address=t_address.getText();
				String telephone=t_telephone.getText();
				Company company=new Company(id, name, address, telephone);
					boolean mod=companyDao.companyModify(company);
					if(mod){
						JOptionPane.showMessageDialog(null, "Modify Success");
					}else{
						JOptionPane.showMessageDialog(null, "Wrong,please check Company name whether exist");
					}
			}
		});
		btn_confirm.setBounds(102, 213, 93, 23);
		panel.add(btn_confirm);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancel.setBounds(245, 213, 93, 23);
		panel.add(btn_cancel);
	}

}
