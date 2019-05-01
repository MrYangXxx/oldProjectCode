package com.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GradeDao;
import com.model.Grade;
import com.model.Page;
import com.util.DbUtil;
import com.util.JsonUtil;
import com.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@WebServlet(urlPatterns="/gradeList")
public class GradeListServlet extends HttpServlet {
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String gradeName = request.getParameter("gradeName");
		if (gradeName == null) {
			gradeName = "";
		}
		Grade grade = new Grade();
		grade.setGradeName(gradeName);
		Page pageBean = new Page(Integer.parseInt(page), Integer.parseInt(rows));

		try (Connection con = dbUtil.getCon();) {
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, pageBean, grade));
			int total = gradeDao.gradeCount(con, grade);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
