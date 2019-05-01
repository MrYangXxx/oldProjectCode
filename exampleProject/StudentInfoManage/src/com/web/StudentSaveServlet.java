package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.StudentDao;
import com.model.Student;
import com.util.DateUtil;
import com.util.DbUtil;
import com.util.ResponseUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@WebServlet(urlPatterns="/studentSave")
public class StudentSaveServlet extends HttpServlet {
	DbUtil dbUtil=new DbUtil();
	StudentDao studentDao=new StudentDao();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String stuId=request.getParameter("stuId");
		String stuNo=request.getParameter("stuNo");
		String stuName=request.getParameter("stuName");
		String gender=request.getParameter("gender");
		String birthday=request.getParameter("birthday");
		String gradeId=request.getParameter("gradeId");
		String email=request.getParameter("email");
		String stuDesc=request.getParameter("stuDesc");
		Student student=null;
		try {
			student = new Student(stuNo, stuName, gender, DateUtil.formatString(birthday, "yyyy-MM-dd"), Integer.parseInt(gradeId), email, stuDesc);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuId))
			student.setStuId(Integer.parseInt(stuId));
		try(Connection con=dbUtil.getCon();){
			int saveNums=0;
			
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
			}else{
				saveNums=studentDao.studentAdd(con, student);
			}
			if(saveNums>0){
				result.put("errorMsg", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "±£¥Ê ß∞‹");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
}
