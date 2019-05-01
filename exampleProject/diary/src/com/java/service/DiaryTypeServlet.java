package com.java.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.java.model.DiaryType;
import com.java.util.DbConn;
import com.java.util.StringUtil;

/**
 * Servlet implementation class DiaryTypeServlet
 */
@WebServlet(urlPatterns="/diaryType")
public class DiaryTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QueryRunner qr=new QueryRunner(DbConn.getDataSource());   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiaryTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("list".equals(action)){
			diaryTypeList(request, response);
		}else if("preSave".equals(action)){
			diaryTypePreSave(request, response);
		}else if("save".equals(action)){
			diaryTypeSave(request, response);
		}else if("delete".equals(action)){
			diaryTypeDelete(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void diaryTypeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			List<DiaryType>diaryTypeList = qr.query("select * from t_diaryType", new BeanListHandler<DiaryType>(DiaryType.class));
			request.setAttribute("diaryTypeList", diaryTypeList);
			request.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void diaryTypePreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		try {
			if(StringUtil.isNotEmpty(diaryTypeId)){
			DiaryType diaryType = qr.query("select * from t_diaryType where diaryTypeId="+diaryTypeId, new BeanHandler<DiaryType>(DiaryType.class));
			request.setAttribute("diaryType", diaryType);
			}
			request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void diaryTypeSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		String typeName = request.getParameter("typeName");
		
		try {
			int num;
			if(StringUtil.isNotEmpty(diaryTypeId)){
				num=qr.update("update t_diaryType set typeName=? where diaryTypeId=?",typeName,diaryTypeId);
			}else{
				num=qr.update("insert into t_diaryType values(null,?)",typeName);
			}
			if(num>0){
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);
			}else{
				DiaryType diaryType=new DiaryType();
				diaryType.setTypeName(typeName);
				request.setAttribute("diaryType", diaryType);
				request.setAttribute("error", "保存失败");
				request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	


	private void diaryTypeDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		try {
			Object num=qr.query("select * from t_diaryType where diaryTypeId="+diaryTypeId, new ScalarHandler<>());
			if(Integer.parseInt(num.toString())>0){
				request.setAttribute("error", "日志类别下有日记，不能删除");
			}else{
				qr.update("delete from t_diaryType where diaryTypeId="+diaryTypeId);
			}
			request.getRequestDispatcher("diaryType?action=list").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
