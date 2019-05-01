package com.java.action;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.struts2.ServletActionContext;

import com.java.db.DBUtilConn;
import com.java.model.Article;
import com.opensymphony.xwork2.ActionSupport;

public class ArticleFlatAction extends ActionSupport{
	final int PAGE_SIZE = 5;
	private int pageNo;
	private String search;
	QueryRunner qr=new QueryRunner( DBUtilConn.getDataSource());

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String execute() throws Exception {
		if (pageNo <= 0)
			pageNo = 1;

		int totalPages = 0;

		Set<Article> articles = new LinkedHashSet<Article>();
		StringBuffer countSql = new StringBuffer("select count(0) from article where pid=0");
		if (search != null && !search.trim().equals("")) {
			countSql.append(" and title like '%" + search + "%'");
		}
		int totalRecords =Integer.parseInt(qr.query(countSql.toString(), new ScalarHandler<Long>())+"");
		
		totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
		if (pageNo > totalPages)
			pageNo = totalPages;

		int startPos = (pageNo - 1) * PAGE_SIZE;
		StringBuffer sql = new StringBuffer("select * from article where");
		if (search != null && !search.trim().equals("")) {
			sql.append(" title like '%" + search + "%' and");
		}
		sql.append(" pid=0 order by pdate desc");
		if(totalRecords>0){
			sql.append( " limit " + startPos + "," + PAGE_SIZE);
		}
		List<Article> list = qr.query(sql.toString(), new BeanListHandler<Article>(Article.class));
		for (Article a : list) {
			articles.add(a);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("search", search);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("articles", articles);
		return SUCCESS;
	}
	
}