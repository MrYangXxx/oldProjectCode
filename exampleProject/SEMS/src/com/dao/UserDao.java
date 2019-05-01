package com.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.bean.User;
import com.util.HibernateUtil;

public class UserDao {
	
	Session session=HibernateUtil.getSession();

	public User login(User user){
		String sql="from User where userName=? and password=?";
		Query<User> query = session.createQuery(sql);
		query.setParameter(0, user.getUserName());
		query.setParameter(1, user.getPassWord());
		return query.uniqueResult();
	}

}
