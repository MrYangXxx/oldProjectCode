package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bean.Recruit;
import com.util.HibernateUtil;
import com.util.StringUtil;

public class RecruitDao {
	
	Session session=HibernateUtil.getSession();
	
	public int recruitAdd(Recruit recruit){
		Transaction tx = session.beginTransaction();
		Integer num=(Integer) session.save(recruit);
		tx.commit();
		if(num>0){
			return 1;
		}
		return 0;
		
	}
	
	public List<Recruit> recruitList(Recruit recruit){
		StringBuffer sb=new StringBuffer("from Recruit ");
		if(StringUtil.isNotEmpty(recruit.getCompany())){
			sb.append(" and company = '"+recruit.getCompany()+"'");
		}
		if(StringUtil.isNotEmpty(recruit.getDegree())){
			sb.append(" and degree = '"+recruit.getDegree()+"'");
		}
		if(StringUtil.isNotEmpty(recruit.getDeadline())){
			sb.append(" and deadline like '%"+recruit.getDeadline()+"%'");
		}
		if(StringUtil.isNotEmpty(recruit.getPosition())){
			sb.append(" and position like '%"+recruit.getPosition()+"%'");
		}
		session.clear();
		Query query = session.createQuery(sb.toString().replaceFirst("and", "where"));
		return query.list();
	}
	
	public boolean recruitDelete(int id){
		try{
			Transaction tx = session.beginTransaction();
			Recruit recruit=session.get(Recruit.class, id);
			session.delete(recruit);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
		
	}
	
	public boolean recruitModify(Recruit recruit){
		try{
			Transaction tx = session.beginTransaction();
			session.update(recruit);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
		
	}
	
	public Recruit getRecruitById(int id){
		String sql="from Recruit where id=?";
		Query<Recruit> query = session.createQuery(sql);
		query.setParameter(0,id);
		return query.uniqueResult();
	}
	
	public int getMaxId(){
		Query query = session.createQuery("select max(id) from Recruit");
		String max = query.uniqueResult()+"";
		return Integer.parseInt(max);
	}
}
