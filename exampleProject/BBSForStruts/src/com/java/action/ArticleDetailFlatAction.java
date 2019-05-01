package com.java.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.struts2.ServletActionContext;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

public class ArticleDetailFlatAction extends ActionSupport{
	private int id = 0;
	private Article a = new Article();
	private String title;
	QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());

	public String getTitle() {
		return title;
	}

	public Article getA() {
		return a;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String execute() throws Exception {
		ActionContext context=ActionContext.getContext();
		//usually use root(inside is a list),not use context(inside is a map)
		ValueStack valueStack = context.getValueStack();

		String sql = "select * from article where rootid=" + id + " order by pdate asc";
		List<Article> articles =qr.query(sql, new BeanListHandler<Article>(Article.class));
		title=qr.query("select title from article where id=" + id, new ScalarHandler<String>());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("articles", articles);
		return SUCCESS;
	}
}