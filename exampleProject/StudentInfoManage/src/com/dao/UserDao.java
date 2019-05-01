package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.User;

public class UserDao {
	/**
	 * Logon verification
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */

	public User login(Connection con,User user)throws Exception{
		User resultUser=null;
		String sql="select * from t_user where userName=? and password=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new User();
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
		}
		return resultUser;
	}
	
	public int register(Connection con,User user)throws Exception{
		String sql="insert into t_user values(0,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		int num=pstmt.executeUpdate();
		return num;
	}
	
	public boolean checkUser(Connection con,String username) throws SQLException{
		String sql="select * from t_user where username=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, username);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}
		return false;
	}
	
}
