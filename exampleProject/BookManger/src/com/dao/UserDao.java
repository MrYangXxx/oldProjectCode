package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.model.User;

public class UserDao {
	/**
	 * Logon verification
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User login(User user){
		String sql="select * from t_user where userName=? and password=?";
		return jdbcTemplate.queryForObject(sql,new Object[]{user.getUserName(),user.getPassword()},new BeanPropertyRowMapper<>(User.class) );
	}
}
