package com.java.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.java.model.Diary;
import com.java.model.DiaryType;
import com.java.model.PageBean;
import com.java.util.DbConn;
import com.java.util.StringUtil;
@WebServlet(urlPatterns="/main")
public class MainServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	QueryRunner qr = new QueryRunner(DbConn.getDataSource());
	final int PAGE_SIZE = 5;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		PageBean pageBean = new PageBean();
		String page = req.getParameter("page");
		String s_typeId = req.getParameter("s_typeId");
		String s_releaseDate = req.getParameter("s_releaseDate");
		String s_title = req.getParameter("s_title");
		String all = req.getParameter("all");

		// diary type
		HttpSession session = req.getSession();
		try {
			List<DiaryType> typeList = qr.query(
					"select diaryTypeId,typeName,count(diaryId) as diaryCount from t_diary right join t_diaryType on t_diary.typeId=t_diaryType.diaryTypeId group by typeName",
					new BeanListHandler<>(DiaryType.class));
			session.setAttribute("diaryTypeCountList", typeList);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// diary date type
		try {
			List<DiaryType> timeList = qr.query(
					"select date_format(releaseDate,'发布日期') as releaseDate,count(*) as diaryCount from t_diary group by date_format(releaseDate,'发布日期') order by date_format(releaseDate,'发布日期') desc",
					new BeanListHandler<DiaryType>(DiaryType.class));
			session.setAttribute("diaryCountList", timeList);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if ("true".equals(all)) {
			if (StringUtil.isEmpty(s_title)) {
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_releaseDate");
				session.removeAttribute("s_title");

			}
		}

		// about pagination
		try {
			StringBuffer sql = new StringBuffer("select * from t_diary");
			StringBuffer countSql = new StringBuffer("select count(*) from t_diary");

			if (StringUtil.isNotEmpty(s_typeId)) {
				sql.append(" and t_diary.typeId=" + s_typeId);
				countSql.append(" and t_diary.typeId=" + s_typeId);
				session.setAttribute("s_typeId", s_typeId);
				session.removeAttribute("s_releaseDate");
				session.removeAttribute("s_title");
			}

			if (StringUtil.isNotEmpty(s_releaseDate)) {
				sql.append(" and date_format(t_diary.releaseDate,'发布日期')='" + s_releaseDate + "'");
				countSql.append(" and date_format(t_diary.releaseDate,'发布日期')='" + s_releaseDate + "'");
				session.setAttribute("s_releaseDate", s_releaseDate);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}

			if (StringUtil.isNotEmpty(s_title)) {
				sql.append(" and t_diary.title like '%" + s_title + "%'");
				countSql.append(" and t_diary.title like '%" + s_title + "%'");
				session.setAttribute("s_title", s_title);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_releaseDate");
			}

			if (session.getAttribute("s_typeId") != null) {
				Object o = session.getAttribute("s_typeId");
				sql.append(" and t_diary.typeId=" + o);
				countSql.append(" and t_diary.typeId=" + o);
			}

			if (session.getAttribute("s_title") != null) {
				Object o = session.getAttribute("s_title");
				sql.append(" and t_diary.title like '%" + o + "%'");
				countSql.append(" and t_diary.title like '%" + o + "%'");
			}

			if (session.getAttribute("s_releaseDate") != null) {
				Object o = session.getAttribute("s_releaseDate");
				sql.append(" and date_format(t_diary.releaseDate,'发布日期')='" + o + "'");
				countSql.append(" and date_format(t_diary.releaseDate,'发布日期')='" + o + "'");
			}

			int totalRecords = Integer.parseInt(
					qr.query(countSql.toString().replaceFirst("and", "where"), new ScalarHandler<Long>()) + "");
			pageBean.setTotalRecords(totalRecords);
			pageBean.setTotalPages(totalRecords);
			if (StringUtil.isEmpty(page)) {
				pageBean.setPage(1);
			} else {
				pageBean.setPage(Integer.parseInt(page));
			}

			if (totalRecords > 0) {
				sql.append(" limit " + pageBean.getStart() + "," + pageBean.getPAGE_SIZE());
			}

			String pagination = pagination(pageBean);

			List<Diary> diaryList = qr.query(sql.toString().replaceFirst("and", "where"),
					new BeanListHandler<Diary>(Diary.class));

			req.setAttribute("diaryList", diaryList);
			req.setAttribute("pagination", pagination);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.setAttribute("mainPage", "diary/diaryList.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
	}

	public String pagination(PageBean pageBean) {
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>首页</a></li>");
		if (pageBean.getPage() == 1) {
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (pageBean.getPage() - 1) + "'>上一页</a></li>");
		}
		for (int i = pageBean.getPage() - 2; i <= pageBean.getPage() + 2; i++) {
			if (i < 1 || i > pageBean.getTotalPages()) {
				continue;
			}
			if (i == pageBean.getPage()) {
				pageCode.append("<li class='active'><a href='#'>" + i + "</a></li>");
			} else {
				pageCode.append("<li><a href='main?page=" + i + "'>" + i + "</a></li>");
			}
		}
		if (pageBean.getPage() == pageBean.getTotalPages()) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (pageBean.getPage() + 1) + "'>下一页</a></li>");
		}
		pageCode.append("<li><a href='main?page=" + pageBean.getTotalPages() + "'>尾页</a></li>");
		return pageCode.toString();
	}

}
