package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bean.Company;
import com.bean.User;
import com.util.HibernateUtil;
import com.util.StringUtil;

public class CompanyDao {
	
	Session session=HibernateUtil.getSession();
	
	public int companyAdd(Company company){
		Transaction tx = session.beginTransaction();
		Integer num=(Integer) session.save(company);
		tx.commit();
		if(num>0){
			return 1;
		}
		return 0;
	}
	
	public List<Company> companyList(Company company){
		StringBuffer sb=new StringBuffer("from Company ");
		if(StringUtil.isNotEmpty(company.getName())){
			sb.append(" and name like '%"+company.getName()+"%'");
		}
		if(StringUtil.isNotEmpty(company.getId())){
			sb.append(" and id = "+company.getId());
		}
		if(StringUtil.isNotEmpty(company.getAddress())){
			sb.append(" and address like '%"+company.getAddress()+"%'");
		}
		session.clear();
		Query query = session.createQuery(sb.toString().replaceFirst("and", "where"));
		return query.list();
	}
	
	public boolean companyDelete(int id){
		try{
			Transaction tx = session.beginTransaction();
			Company company=session.get(Company.class, id);
			session.delete(company);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
	}
	
	public boolean companyModify(Company company){
		try{
			Transaction tx = session.beginTransaction();
			session.update(company);
			tx.commit();
			return true;
		}catch(RuntimeException e){
			return false;
		}
		
	}
	
	public Company getCompanyById(int id){
		String sql="from Company where id=?";
		Query<Company> query = session.createQuery(sql);
		query.setParameter(0,id);
		return query.uniqueResult();
	}
}
