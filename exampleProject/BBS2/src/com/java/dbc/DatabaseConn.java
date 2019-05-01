package com.java.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConn {

	private String dbDriver = "com.mysql.jdbc.Driver";
	private String dbUrl = "jdbc:mysql://localhost:3306/bbs?useSSL=false";
	private String dbUser = "root";
	private String dbPassword = "123456";
	private Connection conn = null;

	public Connection getConn() {
		try {
			Class.forName(dbDriver);
			this.conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接失败");
		}
		return conn;
	}

	public Statement createStmt(Connection conn){
		Statement stmt=null;
		try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	public PreparedStatement preparStmt(Connection conn,String sql){
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public PreparedStatement preparStmt(Connection conn,String sql,int autoGenerateKey){
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql,autoGenerateKey);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public ResultSet executeQuery(Statement stmt,String sql){
		ResultSet rs=null;
		try {
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void executeUpdate(Connection conn,String sql){
		try {
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
