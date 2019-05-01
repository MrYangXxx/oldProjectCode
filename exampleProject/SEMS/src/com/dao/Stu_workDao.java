package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bean.Stu_work;
import com.util.HibernateUtil;
import com.util.StringUtil;

public class Stu_workDao {

	Session session=HibernateUtil.getSession();
	
	public int stu_workAdd(Stu_work stu_work) {
		Transaction tx = session.beginTransaction();
		Integer num=(Integer) session.save(stu_work);
		tx.commit();
		if(num>0){
			return 1;
		}
		return 0;
		
	}
	
	public List<Stu_work> stu_workList(Stu_work stu_work,String operation){
		StringBuffer sb=new StringBuffer("from Stu_work ");
		if(StringUtil.isNotEmpty(stu_work.getName())){
			sb.append(" and name like '%"+stu_work.getName()+"%'");
		}
		
		if(StringUtil.isNotEmpty(stu_work.getCompany())){
			sb.append(" and company = '"+stu_work.getCompany()+"'");
		}
		if(StringUtil.isNotEmpty(stu_work.getId())){
			sb.append(" and id = "+stu_work.getId());
		}
		if(StringUtil.isNotEmpty(stu_work.getPosition())){
			sb.append(" and position like '%"+stu_work.getPosition()+"%'");
		}
		if(StringUtil.isNotEmpty(stu_work.getSalary())&&StringUtil.isNotEmpty(operation)){
			sb.append(" and salary "+operation+" "+stu_work.getSalary());
		}
		session.clear();
		Query query = session.createQuery(sb.toString().replaceFirst("and", "where"));
		return query.list();
	}
	
	public boolean stu_workDelete(int sid){
		try{
			Transaction tx = session.beginTransaction();
			Stu_work stu_work=session.get(Stu_work.class, sid);
			session.delete(stu_work);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
	}
	
	public boolean stu_workModify(Stu_work stu_work){
		try{
			Transaction tx = session.beginTransaction();
			session.update(stu_work);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
	}
	
	public Stu_work getStu_workById(int id){
		String sql="from Stu_work where id=?";
		
		Query<Stu_work> query = session.createQuery(sql);
		query.setParameter(0,id);
		return query.uniqueResult();
	}
	
}
