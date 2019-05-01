package com.java.action;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.java.db.DBUtilConn;
import com.opensymphony.xwork2.ActionSupport;

public class PostAction extends ActionSupport{
	private String action;
	private String title;
	private String cont;
	 QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());
	
	public void setAction(String action) {
		this.action = action;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	@Override
	public String execute() throws Exception {
		if(action!=null&&action.trim().equals("post")){
			Connection conn=DBUtilConn.getConn();
			conn.setAutoCommit(false);
			
			String sql="insert into article values(null,0,?,?,?,now(),?)";
			qr.update(sql, -1, title,cont,0);
			int rootId=Integer.parseInt(qr.query("select @@identity", new ScalarHandler<Long>(1))+"");
			qr.update("update article set rootid="+rootId+" where id="+rootId);
			
			conn.commit();
			conn.setAutoCommit(true);
			return SUCCESS;
		}
		return ERROR;
	}
}