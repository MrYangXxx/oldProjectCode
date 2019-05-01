package com.java.action;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionSupport;

public class ArticleDetailAction extends ActionSupport{
	QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());
	private int id;
	private Article a=new Article();

	public Article getA() {
		return a;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		DBUtilConn databaseConn = new DBUtilConn();
		String sql = "select * from article where id=" + id;
		 a=qr.query(sql, new BeanHandler<Article>(Article.class));
		 if(a==null){
			return ERROR;
		 }else{
		   return SUCCESS;
		 }
	}
}