package com.java.action;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DeleteAction extends ActionSupport implements ModelDriven<Article>{
	private Article a=new Article();
	private String url;
	DBUtilConn databaseConn=new DBUtilConn();
	QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public Article getModel() {
		return a;
	}

	private void delete(Connection conn,int id,boolean isLeaf){
		if(!isLeaf){
			try{
				Article a=qr.query("select * from article where pid="+id, new BeanHandler<Article>(Article.class));
				while(a!=null){
					delete(conn, a.getId(), a.isLeaf());
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		try {
			qr.update( "delete from article where id="+id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String execute(){
			Connection conn=null;
		try{
			conn=DBUtilConn.getConn();
			conn.setAutoCommit(false);
			
			delete(conn, a.getId(),a.isLeaf());
			
			String sql = "select count(*) from article where pid="+a.getpId();
			int count=Integer.parseInt(qr.query(sql, new ScalarHandler<Long>())+"");
			if(count<=0){
				qr.update("update article set isleaf=0 where id="+a.getpId());
				}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
}
