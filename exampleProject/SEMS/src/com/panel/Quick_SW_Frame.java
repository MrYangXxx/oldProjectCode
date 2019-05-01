package com.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

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

import com.bean.Company;
import com.bean.Stu_work;
import com.dao.CompanyDao;
import com.dao.Stu_workDao;
import com.util.StringUtil;

public class Quick_SW_Frame extends JFrame {

	private JPanel contentPane;
	private JTextField t_id;
	private JTextField t_name;
	private JTextField t_position;
	JComboBox cb_company;
	JComboBox cb_operation;
	JFormattedTextField ft_salary;
	private JTable table;
	Stu_workDao stu_workDao=new Stu_workDao();
	CompanyDao companyDao = new CompanyDao();

	/**
	 * Create the frame.
	 */
	public Quick_SW_Frame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 711, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 693, 203);
		contentPane.add(panel);

		JLabel label = new JLabel("id");
		label.setBounds(45, 39, 10, 16);
		panel.add(label);

		JLabel label_1 = new JLabel("name");
		label_1.setBounds(240, 39, 40, 16);
		panel.add(label_1);

		t_id = new JTextField();
		t_id.setColumns(10);
		t_id.setBounds(86, 36, 116, 22);
		panel.add(t_id);

		t_name = new JTextField();
		t_name.setColumns(10);
		t_name.setBounds(299, 36, 116, 22);
		panel.add(t_name);

		JLabel label_2 = new JLabel("company");
		label_2.setBounds(463, 39, 56, 16);
		panel.add(label_2);

		JLabel label_3 = new JLabel("position");
		label_3.setBounds(30, 96, 49, 16);
		panel.add(label_3);

		t_position = new JTextField();
		t_position.setColumns(10);
		t_position.setBounds(86, 93, 116, 22);
		panel.add(t_position);

		JLabel label_4 = new JLabel("salary");
		label_4.setBounds(254, 96, 40, 16);
		panel.add(label_4);

		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = t_name.getText();
				int id = 0;
				if (StringUtil.isNotEmpty(t_id.getText()) && StringUtil.isNumeric(t_id.getText()))
					id = Integer.parseInt(t_id.getText());
				String company = null;
				if (cb_company.getItemCount() > 0) {
					company = (String) cb_company.getSelectedItem();
				}
				String position = t_position.getText();
				String salary = null;
				String operation = null;
				String other = null;
				if (cb_operation.getItemCount() > 0) {
					operation = (String) cb_operation.getSelectedItem();
					salary = ft_salary.getText();
				}
				Stu_work stu = new Stu_work(id, name, company, position, salary, other);
				fillTable(stu, operation);
			}
		});
		btn_search.setBounds(197, 163, 97, 25);
		panel.add(btn_search);

		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.setBounds(338, 163, 97, 25);
		panel.add(btn_cancel);

		cb_company = new JComboBox();
		cb_company.setEditable(true);
		cb_company.setBounds(531, 36, 115, 22);
		panel.add(cb_company);
		fillCompany();

		cb_operation = new JComboBox();
		cb_operation.setModel(new DefaultComboBoxModel(new String[] { "", ">", "<", "=" }));
		cb_operation.setBounds(329, 93, 56, 22);
		panel.add(cb_operation);

		ft_salary = new JFormattedTextField();
		ft_salary.setBounds(427, 92, 128, 25);
		panel.add(ft_salary);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 216, 671, 243);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id", "name", "company", "position", "salary", "other" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setFillsViewportHeight(true);
	}

	public void fillCompany() {
		List<Company> listCompany = companyDao.companyList(new Company());
		cb_company.addItem("");
		for (Company company : listCompany) {
			this.cb_company.addItem(company.getName());
		}
	}

	public void fillTable(Stu_work stu_work, String operation) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);

		List<Stu_work> listStu_work = stu_workDao.stu_workList(stu_work, operation);
		for (Stu_work stu_work2 : listStu_work) {
			Vector<Comparable> v = new Vector<Comparable>();
			v.add(stu_work2.getId());
			v.add(stu_work2.getName());
			v.add(stu_work2.getCompany());
			v.add(stu_work2.getPosition());
			v.add(stu_work2.getSalary());
			v.add(stu_work2.getOther());
			dtm.addRow(v);
		}
	}
}
