package com.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.StudentDao;
import com.model.Page;
import com.model.Student;
import com.util.DbUtil;
import com.util.JsonUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@WebServlet(urlPatterns="/studentList")
public class StudentListServlet extends HttpServlet {
	DbUtil dbUtil=new DbUtil();
	StudentDao studentDao=new StudentDao();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String gender=request.getParameter("gender");
		String gradeId=request.getParameter("gradeId");
		String bbirthday=request.getParameter("bbirthday");
		String ebirthday=request.getParameter("ebirthday");
		
		Student student=new Student();
		if(stuNo!=null){
			student.setStuNo(stuNo);
			student.setStuName(stuName);
			student.setGender(gender);
			if(StringUtil.isNotEmpty(gradeId)){
				student.setGradeId(Integer.parseInt(gradeId));
			}
		}
		
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		Page pageBean=new Page(Integer.parseInt(page), Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(studentDao.studentList(con, pageBean,student,bbirthday,ebirthday));
			int total=studentDao.gradeCount(con,student,bbirthday,ebirthday);
			result.put("rows",jsonArray);
			result.put("total",total);
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
}
