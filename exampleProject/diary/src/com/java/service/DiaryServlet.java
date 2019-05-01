package com.java.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.java.model.Diary;
import com.java.util.DbConn;
import com.java.util.StringUtil;

/**
 * Servlet implementation class DiaryServlet
 */
@WebServlet(urlPatterns = "/diary")
public class DiaryServlet extends BaseServlet {
	public static final long serialVersionUID = 1L;
	QueryRunner qr = new QueryRunner(DbConn.getDataSource());

	public String show(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String diaryId = request.getParameter("diaryId");
		Diary diary = qr.query(
				"select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=" + diaryId,
				new BeanHandler<Diary>(Diary.class));
		request.setAttribute("diary", diary);
		request.setAttribute("mainPage", "diary/diaryShow.jsp");
		return "mainTemp.jsp";
	}

	public String preSave(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String diaryId = request.getParameter("diaryId");
		if (StringUtil.isNotEmpty(diaryId)) {
			Diary diary = qr.query(
					"select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=" + diaryId,
					new BeanHandler<Diary>(Diary.class));
			request.setAttribute("diary", diary);
		}
		request.setAttribute("mainPage", "diary/diarySave.jsp");
		return "mainTemp.jsp";
	}

	public String save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String typeId = request.getParameter("typeId");
		String diaryId = request.getParameter("diaryId");

		int num;
		if (StringUtil.isNotEmpty(diaryId)) {
			num = qr.update("update t_diary set title=?,content=?,typeId=? where diaryId=?", title, content, typeId,
					diaryId);
		} else {
			num = qr.update("insert into t_diary values(null,?,?,?,now())", title, content, typeId);
		}
		if (num > 0) {
			request.getRequestDispatcher("main?all=true").forward(request, response);
		} else {
			Diary diary = new Diary();
			diary.setTitle(title);
			diary.setContent(content);
			diary.setTypeId(Integer.parseInt(typeId));
			request.setAttribute("diary", diary);
			request.setAttribute("error", "����ʧ��");
			request.setAttribute("mainPage", "diary/diarySave.jsp");
		}
		return "mainTemp.jsp";
	}

	public String delete(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String diaryId = request.getParameter("diaryId");
		qr.update("delete from t_diary where diaryId=" + diaryId);
		return "main?all=true";
	}

}
