package com.panel;

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

public class CompanyAddDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_address;
	private JTextField t_telephone;
	CompanyDao companyDao=new CompanyDao();

	/**
	 * Create the frame.
	 */
	public CompanyAddDialog() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 476, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(34, 45, 54, 15);
		contentPane.add(lblNewLabel);
		
		t_id = new JTextField();
		t_id.setBounds(98, 42, 82, 21);
		contentPane.add(t_id);
		t_id.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("name");
		lblNewLabel_1.setBounds(250, 45, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		t_name = new JTextField();
		t_name.setBounds(316, 41, 82, 21);
		contentPane.add(t_name);
		t_name.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("address");
		lblNewLabel_2.setBounds(34, 133, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		t_address = new JTextField();
		t_address.setBounds(98, 130, 82, 21);
		contentPane.add(t_address);
		t_address.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("telephone");
		lblNewLabel_3.setBounds(237, 133, 67, 15);
		contentPane.add(lblNewLabel_3);
		
		t_telephone = new JTextField();
		t_telephone.setBounds(316, 129, 82, 21);
		contentPane.add(t_telephone);
		t_telephone.setColumns(10);
		
		JButton btn_confirm = new JButton("Confirm");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name=t_name.getText();
				int id=0;
				if(StringUtil.isNotEmpty(t_id.getText())&&StringUtil.isNumeric(t_id.getText()))
				id=Integer.parseInt(t_id.getText());
				String address=t_address.getText();
				String telephone=t_telephone.getText();
				if(id==0){
					JOptionPane.showMessageDialog(null, "Please input correct id");
					t_id.setText("");
					return;
				}
				Company company=new Company(id, name, address, telephone);
				try{
					int addNum=companyDao.companyAdd(company);
					if(addNum==1){
						JOptionPane.showMessageDialog(null, "Add Success");
						resetValue();
					}else{
						JOptionPane.showMessageDialog(null, "Wrong");
					}
				}catch(Exception ex){
					//ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Company id or name already exist");
				}
			}
		});
		btn_confirm.setBounds(102, 213, 93, 23);
		contentPane.add(btn_confirm);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancel.setBounds(245, 213, 93, 23);
		contentPane.add(btn_cancel);
	}
	
	private void resetValue(){
		t_name.setText("");
		t_id.setText("");
		t_address.setText("");
		t_telephone.setText("");
		}

}
