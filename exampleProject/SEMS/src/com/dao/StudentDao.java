package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bean.Student;
import com.util.HibernateUtil;
import com.util.StringUtil;

public class StudentDao {
	
	Session session=HibernateUtil.getSession();

	public int studentAdd(Student student){
		
		Transaction tx = session.beginTransaction();
		Integer num=(Integer) session.save(student);
		tx.commit();
		if(num>0){
			return 1;
		}
		return 0;
		
	}
	
	public List<Student> studentList(Student student) {
		StringBuffer sb=new StringBuffer("from Student ");
		if(StringUtil.isNotEmpty(student.getName())){
			sb.append(" and name like '%"+student.getName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getDegree())){
			sb.append(" and degree = '"+student.getDegree()+"'");
		}
		if(StringUtil.isNotEmpty(student.getId())&&student.getId()!=0){
			sb.append(" and id = "+student.getId());
		}
		if(StringUtil.isNotEmpty(student.getSid())&&student.getSid()!=0){
			sb.append(" and sid = "+student.getSid());
		}
		if(StringUtil.isNotEmpty(student.getAge())&&student.getAge()!=0){
			sb.append(" and age = "+student.getAge());
		}
		if(StringUtil.isNotEmpty(student.getGender())){
			sb.append(" and gender = '"+student.getGender()+"'");
		}
		if(StringUtil.isNotEmpty(student.getState())){
			sb.append(" and state = '"+student.getState()+"'");
		}
		session.clear();
		Query query = session.createQuery(sb.toString().replaceFirst("and", "where"));
		return query.list();
	}
	
	public boolean studentDelete(int id){
		try{
			Transaction tx = session.beginTransaction();
			Student student=session.get(Student.class, id);
			session.delete(student);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean studentModify(Student student){
		try{
			Transaction tx = session.beginTransaction();
			session.update(student);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
	}
	
	public Student getStudentById(int id) {
		String sql="from Student where id=?";
		Query<Student> query = session.createQuery(sql);
		query.setParameter(0,id);
		return query.uniqueResult();
	}
	public Student getStudentByName(String name) {
		String sql="from Student where name=?";
		Query<Student> query = session.createQuery(sql);
		query.setParameter(0,name);
		return query.uniqueResult();
	}
	
	public ResultSet degreeList(Connection con) throws Exception{
		String sb="select * from degree order by id";
		PreparedStatement pstmt=con.prepareStatement(sb);
		
		return pstmt.executeQuery();
	}
	
}
