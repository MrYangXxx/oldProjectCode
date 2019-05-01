package com.java.action;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ReplyDealAction extends ActionSupport implements ModelDriven<Article>{
	 private Article a=new Article();
	 QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());

	 @Override
		public Article getModel() {
			return a;
		}
		
	@Override
	public String execute() throws Exception {
		 
		 if(a.getTitle()!=null||a.getTitle().trim()!=""||a.getCont()!=null||a.getCont().trim()!=""){
		 Connection conn=DBUtilConn.getConn();
		 conn.setAutoCommit(false);
		 
		 String sql="insert into article values(null,?,?,?,?,now(),?)";
		 qr.update(sql,a.getpId(),a.getRootId(), a.getTitle(),a.getCont(),0);
		 qr.update("update article set isleaf=1 where id="+a.getpId());
		 
		 conn.commit();
		 conn.setAutoCommit(true);
		 return SUCCESS;
		 }
		 return ERROR;
	}
}
