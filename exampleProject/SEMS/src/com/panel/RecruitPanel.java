package com.panel;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bean.Company;
import com.bean.Recruit;
import com.dao.CompanyDao;
import com.dao.RecruitDao;
import com.dao.StudentDao;
import com.util.DbUtil;

public class RecruitPanel extends JPanel {
	private JTextField t_position;
	private JTable table;
	int selectid;
	JComboBox cb_company;
	JComboBox cb_degree;
	RecruitDao recruitDao=new RecruitDao();
	CompanyDao companyDao=new CompanyDao();
	StudentDao studentDao=new StudentDao();

	/**
	 * Create the panel.
	 */
	public RecruitPanel() {
		setBackground(SystemColor.activeCaptionBorder);
		setLayout(null);
		
		cb_degree = new JComboBox();
		cb_degree.setBounds(506, 43, 76, 22);
		add(cb_degree);
		fillDegree();
		
		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String position=t_position.getText();
				String company = null;
				if(cb_company.getItemCount()>0){
					company=(String) cb_company.getSelectedItem();
				}
				String degree = null;
				if(cb_degree.getItemCount()>0){
					degree=(String) cb_degree.getSelectedItem();
				}
				Recruit recruit=new Recruit(position, company, degree);
				fillTable(recruit);
			}
		});
		btn_search.setBounds(615, 42, 76, 25);
		add(btn_search);
		
		t_position = new JTextField();
		t_position.setBounds(121, 43, 85, 22);
		add(t_position);
		t_position.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("posititon");
		lblNewLabel.setBounds(55, 46, 54, 16);
		add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 115, 588, 339);
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
				"id", "company", "position", "degree", "salary", "deadline", "other"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("company");
		lblNewLabel_1.setBounds(239, 47, 54, 15);
		add(lblNewLabel_1);
		
		cb_company = new JComboBox();
		cb_company.setBounds(314, 44, 91, 21);
		add(cb_company);
		fillCompany();
		
		JLabel lblNewLabel_2 = new JLabel("degree");
		lblNewLabel_2.setBounds(444, 47, 54, 15);
		add(lblNewLabel_2);
		
		JButton btn_add = new JButton("Add");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RecruitAddDialog recruitAdd=new RecruitAddDialog();
				recruitAdd.setLocationRelativeTo(null);   //set center
				recruitAdd.setVisible(true);
				
			}
		});
		btn_add.setBounds(655, 137, 75, 23);
		add(btn_add);
		
		JButton btn_modify = new JButton("Modify");
		btn_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selectid==0) {
					JOptionPane.showMessageDialog(null, "select one Node");
					return;
					}
				Recruit recruit=recruitDao.getRecruitById(selectid);
				RecruitUpdateDialog reUpdateFrame=new RecruitUpdateDialog(recruit);
				reUpdateFrame.setLocationRelativeTo(null);
				reUpdateFrame.setVisible(true);
			}
		});
		btn_modify.setBounds(655, 198, 75, 23);
		add(btn_modify);
		
		JButton btn_delete = new JButton("Delete");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selectid==0) {
					JOptionPane.showMessageDialog(null, "select one Node");
					return;
					}
				int n=JOptionPane.showConfirmDialog(null, "Delete?");
				if(n==0){
						boolean del=recruitDao.recruitDelete(selectid);
						if(del){
							JOptionPane.showMessageDialog(null, "Delete Success");
							fillTable(new Recruit());
						}
				}
			}
		});
		btn_delete.setBounds(655, 269, 75, 23);
		add(btn_delete);
		fillTable(new Recruit());
	}
	
	public void fillCompany(){
			List<Company> listCompany=companyDao.companyList(new Company());
			cb_company.addItem("");
			for (Company company : listCompany) {
				this.cb_company.addItem(company.getName());
			}
	}
	
	public void fillDegree(){
		try(Connection con=DbUtil.getCon();){
			ResultSet rs=studentDao.degreeList(con);
			cb_degree.addItem("");
			while(rs.next()){
				this.cb_degree.addItem(rs.getString(2));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void fillTable(Recruit recruit){
		DefaultTableModel dtm=(DefaultTableModel) table.getModel();
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
