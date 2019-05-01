package com.panel;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.bean.Company;
import com.bean.Stu_work;
import com.bean.Student;
import com.dao.CompanyDao;
import com.dao.Stu_workDao;
import com.dao.StudentDao;
import com.util.StringUtil;

public class Stu_workAddDialog extends JDialog {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_position;
	JComboBox cb_company;
	JTextArea ta_other;
	JFormattedTextField tf_salary;
	StudentDao studentDao = new StudentDao();
	String state;
	Stu_workDao stu_workDao = new Stu_workDao();
	CompanyDao companyDao = new CompanyDao();

	/**
	 * Create the frame.
	 */
	public Stu_workAddDialog() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 602, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(45, 39, 56, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("name");
		lblNewLabel_1.setBounds(299, 39, 56, 16);
		contentPane.add(lblNewLabel_1);

		t_id = new JTextField();
		t_id.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Stu_work stu = new Stu_work();
				if (!t_id.getText().trim().equals("")) {
					try {
						stu.setId(Integer.parseInt(t_id.getText()));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "PLEASE INPUT NUMBER");
					}
				}
				Student student = studentDao.getStudentById(stu.getId());
				if (student != null) {
					state = student.getState();
					t_name.setText(student.getName());
				} else {
					JOptionPane.showMessageDialog(null, "DON'T HAVE THIS STUDENT");
					t_id.setText("");
				}
			}
		});
		t_id.setBounds(113, 36, 116, 22);
		contentPane.add(t_id);
		t_id.setColumns(10);

		t_name = new JTextField();
		t_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Stu_work stu = new Stu_work();
				if (!t_name.getText().trim().equals("")) {
					stu.setName(t_name.getText());
				}
					Student student= studentDao.getStudentByName(stu.getName());
					if (student!=null) {
							state = student.getState();
						t_id.setText(student.getId()+"");
					} else {
						JOptionPane.showMessageDialog(null, "DON'T HAVE THIS STUDENT");
						t_name.setText("");
					}
			}
		});
		t_name.setBounds(387, 33, 116, 22);
		contentPane.add(t_name);
		t_name.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("company");
		lblNewLabel_2.setBounds(32, 121, 56, 16);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("position");
		lblNewLabel_3.setBounds(299, 121, 56, 16);
		contentPane.add(lblNewLabel_3);

		t_position = new JTextField();
		t_position.setBounds(387, 118, 116, 22);
		contentPane.add(t_position);
		t_position.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("salary");
		lblNewLabel_4.setBounds(45, 193, 56, 16);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("other:");
		lblNewLabel_5.setBounds(299, 193, 56, 16);
		contentPane.add(lblNewLabel_5);

		ta_other = new JTextArea();
		ta_other.setLineWrap(true);
		ta_other.setBounds(309, 222, 231, 118);
		contentPane.add(ta_other);

		JButton btn_confirm = new JButton("Confirm");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (StringUtil.isEmpty(t_name.getText()) || StringUtil.isEmpty(t_id.getText())
						|| t_id.getText().equals(0)) {
					JOptionPane.showMessageDialog(null, "id And name Could Not Empty");
					return;
				}
				if (state != null && state.equals("นคื๗")) {
					JOptionPane.showMessageDialog(null, "ALREADY HAVE,IF YOU WANT TO CAHNGE DATA,PLEASE UPDATE");
					return;
				}
				String name = t_name.getText();
				int id = 0;
				if (StringUtil.isNotEmpty(t_id.getText()))
					id = Integer.parseInt(t_id.getText());
				String company = null;
				if (cb_company.getItemCount() > 0) {
					company = (String) cb_company.getSelectedItem();
				}
				String position = t_position.getText();
				String salary = tf_salary.getText();
				String other = ta_other.getText();

				Stu_work stu = new Stu_work(id, name, company, position, salary, other);
				if (stu.getId() == 0) {
					JOptionPane.showMessageDialog(null, "Student not exist");
					return;
				}

				int addNum = stu_workDao.stu_workAdd(stu);
				if (addNum == 1) {
					JOptionPane.showMessageDialog(null, "Add Success");
					resetValue();
				} else {
					JOptionPane.showMessageDialog(null, "Wrong");
				}
			}

		});
		btn_confirm.setBounds(72, 343, 97, 25);
		contentPane.add(btn_confirm);

		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancel.setBounds(200, 343, 97, 25);
		contentPane.add(btn_cancel);

		cb_company = new JComboBox();
		cb_company.setBounds(114, 118, 115, 22);
		contentPane.add(cb_company);

		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("#####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter(' ');
		tf_salary = new JFormattedTextField(formatter);
		tf_salary.setBounds(113, 190, 116, 22);
		contentPane.add(tf_salary);
		fillCompany();
	}

	public void fillCompany() {
		List<Company> listCompany = companyDao.companyList(new Company());
		for (Company company : listCompany) {
			this.cb_company.addItem(company.getName());
		}
	}

	private void resetValue() {
		t_name.setText("");
		t_id.setText("");
		if (this.cb_company.getItemCount() > 0) {
			cb_company.setSelectedIndex(0);
		}
		t_position.setText("");
		tf_salary.setText("");
		ta_other.setText("");
	}
}
