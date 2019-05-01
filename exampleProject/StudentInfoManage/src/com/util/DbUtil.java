package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	private String dbUrl="jdbc:mysql://localhost:3306/studentinfo?useSSL=false";
	private String dbUsername="root";
	private String dbPassword="123456";
	private String jdbcName="com.mysql.jdbc.Driver";
	/**
	 * get sql connection
	 * @return
	 */
	public Connection getCon() throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
		return con;
	}
	
}
