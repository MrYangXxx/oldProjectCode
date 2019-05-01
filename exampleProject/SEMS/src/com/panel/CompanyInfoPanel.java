package com.panel;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bean.Company;
import com.dao.CompanyDao;
import com.util.StringUtil;

public class CompanyInfoPanel extends JPanel {
	private JTextField t_id;
	private JTextField t_name;
	private JTable table;
	private JTextField t_address;
	int selectid;
	CompanyDao companyDao=new CompanyDao();

	/**
	 * Create the panel.
	 */
	public CompanyInfoPanel() {
		setBackground(SystemColor.activeCaptionBorder);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				selectid=0;
				int row=table.getSelectedRow();
				selectid=((Integer)table.getValueAt(row, 0));
			}
		});
		scrollPane.setBounds(63, 112, 583, 371);
		add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectid=0;
				int row=table.getSelectedRow();
				selectid=((Integer)table.getValueAt(row, 0));
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "name", "address", "telephone"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		fillTable(new Company());
		
		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id=0;
				if(StringUtil.isNotEmpty(t_id.getText())&&StringUtil.isNumeric(t_id.getText()))
					id=Integer.parseInt(t_id.getText());
				String name=t_name.getText();
				String address=t_address.getText();
				Company company=new Company(id, name, address);
				fillTable(company);
			}
		});
		btn_search.setBounds(613, 42, 79, 25);
		add(btn_search);
		
		t_id = new JTextField();
		t_id.setBounds(117, 43, 79, 22);
		add(t_id);
		t_id.setColumns(10);
		
		t_name = new JTextField();
		t_name.setBounds(282, 43, 116, 22);
		add(t_name);
		t_name.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(78, 46, 27, 16);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("name");
		lblNewLabel_1.setBounds(233, 46, 37, 16);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("address");
		lblNewLabel_2.setBounds(421, 47, 54, 15);
		add(lblNewLabel_2);
		
		t_address = new JTextField();
		t_address.setBounds(487, 43, 90, 21);
		add(t_address);
		t_address.setColumns(10);
		
		JButton btn_add = new JButton("Add");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CompanyAddDialog companyAdd=new CompanyAddDialog();
				companyAdd.setLocationRelativeTo(null);   //set center
				companyAdd.setVisible(true);
			}
		});
		btn_add.setBounds(653, 149, 80, 23);
		add(btn_add);
		
		JButton btn_modify = new JButton("Modify");
		btn_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selectid==0) {
					JOptionPane.showMessageDialog(null, "select one Node");
					return;
					}
				Company company=companyDao.getCompanyById(selectid);
					
				CompanyUpdateDialog company_update=new CompanyUpdateDialog(company);
				company_update.setLocationRelativeTo(null);
				company_update.setVisible(true);
			}
		});
		btn_modify.setBounds(653, 210, 80, 23);
		add(btn_modify);
		
		JButton btn_del = new JButton("Delete");
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selectid==0) {
					JOptionPane.showMessageDialog(null, "select one Node");
					return;
					}
				int n=JOptionPane.showConfirmDialog(null, "Delete?");
				if(n==0){
						boolean del=companyDao.companyDelete(selectid);
						if(del){
							JOptionPane.showMessageDialog(null, "Delete Success");
							fillTable(new Company());
						}
				}
			}
				
		});
		btn_del.setBounds(653, 278, 80, 23);
		add(btn_del);

	}
	
	public void fillTable(Company company){
		DefaultTableModel dtm=(DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
			List<Company> listCompany=companyDao.companyList(company);
			
			for (Company company2 : listCompany) {
				Vector<Comparable> v=new Vector<Comparable>();
				v.add(company2.getId());
				v.add(company2.getName());
				v.add(company2.getAddress());
				v.add(company2.getTelephone());
				dtm.addRow(v);
			}
			
	}
}
	
