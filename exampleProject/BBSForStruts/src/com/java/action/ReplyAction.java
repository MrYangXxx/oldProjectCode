package com.java.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.struts2.ServletActionContext;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ReplyAction extends ActionSupport implements ModelDriven<Article>{
	private Article a=new Article();

	public Article getA() {
		return a;
	}

	@Override
	public Article getModel() {
		return a;
	}

	@Override
	public String execute(){
		QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());
		String sql = "select * from article where id=" + a.getId();
		Article a = null;
		try {
			a = qr.query(sql, new BeanHandler<Article>(Article.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (a == null) {
			return ERROR;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("id", a.getId());
		request.setAttribute("rootId", a.getRootId());
		return SUCCESS;
	}

}
