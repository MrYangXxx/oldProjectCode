package com.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.bean.Company;
import com.bean.Recruit;
import com.dao.CompanyDao;
import com.dao.RecruitDao;
import com.dao.StudentDao;
import com.util.DbUtil;

public class Quick_R_Frame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField t_position;
	private JTextField t_deadline;
	JComboBox cb_company;
	JComboBox cb_degree;
	RecruitDao recruitDao = new RecruitDao();
	StudentDao studentDao = new StudentDao();
	CompanyDao companyDao = new CompanyDao();

	/**
	 * Create the frame.
	 */
	public Quick_R_Frame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 686, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 650, 205);
		contentPane.add(panel);

		JLabel label_1 = new JLabel("company");
		label_1.setBounds(93, 39, 54, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("position");
		label_2.setBounds(323, 40, 54, 15);
		panel.add(label_2);

		t_position = new JTextField();
		t_position.setColumns(10);
		t_position.setBounds(411, 39, 93, 21);
		panel.add(t_position);

		JLabel label_3 = new JLabel("degree");
		label_3.setBounds(102, 105, 47, 17);
		panel.add(label_3);

		JLabel label_5 = new JLabel("deadline");
		label_5.setBounds(323, 104, 54, 15);
		panel.add(label_5);

		t_deadline = new JTextField();
		t_deadline.setColumns(10);
		t_deadline.setBounds(408, 95, 93, 21);
		panel.add(t_deadline);

		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String position = t_position.getText();
				String deadline = t_deadline.getText();
				String company = null;
				if (cb_company.getItemCount() > 0) {
					company = (String) cb_company.getSelectedItem();
				}
				String degree = null;
				if (cb_degree.getItemCount() > 0) {
					degree = (String) cb_degree.getSelectedItem();
				}
				Recruit recruit = new Recruit(company, position, degree, deadline);
				fillTable(recruit);

			}
		});
		btn_search.setBounds(191, 164, 93, 23);
		panel.add(btn_search);

		JButton btn_cancel = new JButton("cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn_cancel.setBounds(336, 164, 93, 23);
		panel.add(btn_cancel);

		cb_company = new JComboBox();
		cb_company.setEditable(true);
		cb_company.setBounds(183, 39, 93, 21);
		panel.add(cb_company);

		cb_degree = new JComboBox();
		cb_degree.setEditable(true);
		cb_degree.setBounds(182, 101, 93, 21);
		panel.add(cb_degree);

		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("#####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter(' ');

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 219, 646, 221);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id", "company", "position", "degree", "salary", "deadline", "other" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		fillCompany();
		fillDegree();

	}

	public void fillCompany() {
		List<Company> listCompany = companyDao.companyList(new Company());
		cb_company.addItem("");
		for (Company company : listCompany) {
			this.cb_company.addItem(company.getName());
		}
	}

	public void fillDegree() {
		try (Connection con = DbUtil.getCon();) {
			ResultSet rs = studentDao.degreeList(con);
			cb_degree.addItem("");
			while (rs.next()) {
				this.cb_degree.addItem(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillTable(Recruit recruit) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);

		List<Recruit> listRecruit = recruitDao.recruitList(recruit);

		for (Recruit recruit2 : listRecruit) {
			Vector<Comparable> v = new Vector<>();
			v.add(recruit2.getId());
			v.add(recruit2.getCompany());
			v.add(recruit2.getPosition());
			v.add(recruit2.getDegree());
			v.add(recruit2.getSalary());
			v.add(recruit2.getDeadline());
			v.add(recruit2.getOther());
			dtm.addRow(v);
		}
	}
}
