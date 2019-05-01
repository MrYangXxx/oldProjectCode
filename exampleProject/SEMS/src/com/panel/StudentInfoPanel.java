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
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bean.Stu_work;
import com.bean.Student;
import com.dao.Stu_workDao;
import com.dao.StudentDao;
import com.util.StringUtil;

public class StudentInfoPanel extends JPanel {
	private JTextField t_id;
	private JTable table;
	String studentObject;
	private JTextField t_name;
	int selectid;
	Stu_workDao stu_workDao = new Stu_workDao();
	StudentDao studentDao = new StudentDao();

	/**
	 * Create the panel.
	 */
	public StudentInfoPanel() {
		setBackground(SystemColor.activeCaptionBorder);
		setLayout(null);

		JTree tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent arg0) {
				if (!tree.isSelectionEmpty()) {
					TreePath selectionPaths = tree.getSelectionPath();
					Object path = selectionPaths.getLastPathComponent();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path;
					studentObject = (String) node.getUserObject();
					repaint();
					if (studentObject.equals("学生普通信息")) {
						selectid = 0;
						table.setModel(new DefaultTableModel(new Object[][] {},
								new String[] { "id", "s_id", "name", "age", "gender", "state", "degree" }) {
							boolean[] columnEditables = new boolean[] { false, false, false, false, false, false,
									false };

							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
						table.setFillsViewportHeight(true);
						fillTable(new Student());
					} else if (studentObject.equals("学生工作信息")) {
						selectid = 0;
						table.setModel(new DefaultTableModel(new Object[][] {},
								new String[] { "id", "name", "company", "position", "salary", "other" }) {
							boolean[] columnEditables = new boolean[] { false, false, false, false, false, false,
									false };

							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
						table.setFillsViewportHeight(true);
						fillTable(new Stu_work());

					}
				}
			}
		});

		tree.setBackground(SystemColor.menu);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("\u5B66\u751F\u4FE1\u606F") {
			{
				add(new DefaultMutableTreeNode("\u5B66\u751F\u666E\u901A\u4FE1\u606F"));
				add(new DefaultMutableTreeNode("\u5B66\u751F\u5DE5\u4F5C\u4FE1\u606F"));
			}
		}));
		tree.setBounds(22, 104, 120, 362);
		add(tree);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(160, 104, 497, 362);
		add(scrollPane);

		table = new JTable();

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				selectid = 0;
				int row = table.getSelectedRow();
				if (row > -1)
					selectid = ((Integer) table.getValueAt(row, 0));
			}
		});
		scrollPane.setViewportView(table);

		JButton btn_search = new JButton("SEARCH");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = 0;
				if (StringUtil.isNotEmpty(t_id.getText()) && StringUtil.isNumeric(t_id.getText()))
					id = Integer.parseInt(t_id.getText());
				String name = t_name.getText();
				if (studentObject == null) {
					JOptionPane.showMessageDialog(null, "select one Node");
				} else if (studentObject.equals("学生普通信息")) {
					Student student = new Student(id, name);
					fillTable(student);
				} else {
					Stu_work stu_work = new Stu_work(id, name);
					fillTable(stu_work);
				}
			}
		});
		btn_search.setBounds(529, 54, 81, 25);
		add(btn_search);

		t_id = new JTextField();
		t_id.setBounds(199, 55, 88, 22);
		add(t_id);
		t_id.setColumns(10);

		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(160, 58, 27, 16);
		add(lblNewLabel);

		JButton btn_add = new JButton("ADD");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (studentObject == null) {
					JOptionPane.showMessageDialog(null, "select one Node");
				} else if (studentObject.equals("学生普通信息")) {
					StudentInfoAddDialog stuAdd = new StudentInfoAddDialog();
					stuAdd.setLocationRelativeTo(null); // set center
					stuAdd.setVisible(true);
					// fillTable(new Student());
				} else if (studentObject.equals("学生工作信息")) {
					Stu_workAddDialog stu_workAdd = new Stu_workAddDialog();
					stu_workAdd.setLocationRelativeTo(null);
					stu_workAdd.setVisible(true);
					// fillTable(new Stu_work());
				}
			}

		});
		btn_add.setBounds(669, 117, 77, 25);
		add(btn_add);

		JButton btn_modify = new JButton("MODIFY");
		btn_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentObject == null || selectid == 0) {
					JOptionPane.showMessageDialog(null, "select one Node");
				} else if (studentObject.equals("学生普通信息")) {
					Student student = studentDao.getStudentById(selectid);
					StudentInfoUpdateDialog studentInfo_update = new StudentInfoUpdateDialog(student);
					studentInfo_update.setLocationRelativeTo(null);
					studentInfo_update.setVisible(true);
					// fillTable(new Student());
				} else if (studentObject.equals("学生工作信息")) {
					Stu_work stu = stu_workDao.getStu_workById(selectid);
					Stu_workUpdateDialog stu_workUpdate = new Stu_workUpdateDialog(stu);
					stu_workUpdate.setLocationRelativeTo(null);
					stu_workUpdate.setVisible(true);
					// fillTable(new Stu_work());
				}
			}
		});
		btn_modify.setBounds(669, 189, 77, 25);
		add(btn_modify);

		JButton btn_del = new JButton("DEL");
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (studentObject == null || selectid == 0) {
					JOptionPane.showMessageDialog(null, "select one Node");
				} else if (studentObject.equals("学生普通信息")) {
					int n = JOptionPane.showConfirmDialog(null, "Delete?");
					if (n == 0) {
							boolean del = studentDao.studentDelete(selectid);
							if (del) {
								JOptionPane.showMessageDialog(null, "Delete Success");
								fillTable(new Student());
							}else{
								JOptionPane.showMessageDialog(null, "wrong");
							}
					}
				} else if (studentObject.equals("学生工作信息")) {
					int n = JOptionPane.showConfirmDialog(null, "Delete?");
					if (n == 0) {
						boolean del = stu_workDao.stu_workDelete(selectid);
						if (del) {
							JOptionPane.showMessageDialog(null, "Delete Success");
							fillTable(new Stu_work());
						}
					}
				}
			}
		});
		btn_del.setBounds(669, 257, 77, 25);
		add(btn_del);

		JLabel lblNewLabel_1 = new JLabel("name");
		lblNewLabel_1.setBounds(324, 58, 43, 16);
		add(lblNewLabel_1);

		t_name = new JTextField();
		t_name.setBounds(379, 55, 88, 22);
		add(t_name);
		t_name.setColumns(10);

	}

	public void fillTable(Object obj) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		try{
			if (Class.forName("com.bean.Student").isInstance(obj)) {
				Student student = (Student) obj;
				List<Student> studentList = studentDao.studentList(student);
				for (Student student2 : studentList) {
					Vector<Comparable> v=new Vector<Comparable>();
					v.add(student2.getId());
					v.add(student2.getSid());
					v.add(student2.getName());
					v.add(student2.getAge());
					v.add(student2.getGender());
					v.add(student2.getState());
					v.add(student2.getDegree());
					dtm.addRow(v);
				}
			} else if (Class.forName("com.bean.Stu_work").isInstance(obj)) {
				Stu_work stu_work = (Stu_work) obj;
				List<Stu_work> listStu_work = stu_workDao.stu_workList(stu_work, new String());
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
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

}
