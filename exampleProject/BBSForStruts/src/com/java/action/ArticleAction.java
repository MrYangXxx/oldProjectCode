package com.java.action;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.struts2.ServletActionContext;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionSupport;

public class ArticleAction extends ActionSupport{

	QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());
	int count=-1;
	private void tree(Set<Article> articles,int id,int grade){
		String sql="select * from article where pid="+id;
		List<Article> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Article>(Article.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Article a : list) {
			a.setGrade(grade);
			articles.add(a);
			count++;
			if(!a.isLeaf()){
				tree(articles,a.getId(),grade+1);
			}
		}
	}
	
	@Override
	public String execute() throws Exception {
		count=-1;
		Set<Article> articles=new LinkedHashSet<Article>();
		tree(articles,  0, 0);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("articles", articles);
		request.setAttribute("count", count);
		return SUCCESS;
	}
}