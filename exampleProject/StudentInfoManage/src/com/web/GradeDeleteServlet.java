package com.web;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GradeDao;
import com.dao.StudentDao;
import com.util.DbUtil;
import com.util.ResponseUtil;

import net.sf.json.JSONObject;
@WebServlet(urlPatterns="/gradeDelete")
public class GradeDeleteServlet extends HttpServlet {
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	StudentDao studentDao=new StudentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String delIds=request.getParameter("delIds");
		try(Connection con=dbUtil.getCon();){
			String str[]=delIds.split(",");
			JSONObject result=new JSONObject();
			for(int i=0;i<str.length;i++){
				boolean f=studentDao.getStudentByGradeId(con, str[i]);
				if(f){
					result.put("errorIndex",i);
					result.put("errorMsg","�༶����ѧ��������ɾ��");
					ResponseUtil.write(response, result);
					return;
				}
			}
			int delNums=gradeDao.gradeDelete(con, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "ɾ��ʧ��");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
}
