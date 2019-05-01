package com.java.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.java.model.User;
import com.java.util.DateUtil;
import com.java.util.DbConn;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns="/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	QueryRunner qr=new QueryRunner(DbConn.getDataSource());   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("preSave".equals(action)){
			userPreSave(request, response);
		}else if("save".equals(action)){
			userSave(request, response);
		}
	}

	private void userSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		
		Iterator<FileItem> iterator=items.iterator();
		
		HttpSession session=request.getSession();
		
		User user=(User) session.getAttribute("queryUser");
		while(iterator.hasNext()){
			FileItem item=iterator.next();
			if(item.isFormField()){
				String fieldName=item.getFieldName();
				if("nickName".equals(fieldName)){
					user.setNickName(item.getString());
				}
				if("mood".equals(fieldName)){
					user.setMood(item.getString());
				}
			}else if(!"".equals(item.getName())){
				try {
					String imageName=DateUtil.getCurrentDateStr();
					user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
					String filePath="D:\\JAVA\\Source\\diary\\WebContent\\userImages\\"+imageName+"."+item.getName().split("\\.")[1];
					item.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			int num=qr.update("update t_user set nickName=?,imageName=?,mood=? where userId=?",user.getNickName(),user.getImageName().split("/")[1],user.getMood(),user.getUserId());
			if(num>0){
				session.setAttribute("queryUser", user);
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else{
				request.setAttribute("queryUser", user);
				request.setAttribute("error", "±£¥Ê ß∞‹");
				request.setAttribute("mainPage", "user/userSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void userPreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mainPage", "user/userSave.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
