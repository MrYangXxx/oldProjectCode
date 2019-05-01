package com.java.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.java.model.User;
import com.java.util.DbConn;
import com.java.util.MD5Util;

@WebServlet(urlPatterns="/login")
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	QueryRunner qr = new QueryRunner(DbConn.getDataSource());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		PrintWriter out=resp.getWriter();
		try {
			User queryUser = qr.query("select * from t_user where userName=? and password=?",
					new BeanHandler<>(User.class), userName, MD5Util.encoderPwdByMD5(password));
			if (queryUser != null) {
				session.setAttribute("queryUser", queryUser);
				out.print(true);//返回登录信息
		        out.flush();
		        out.close();
			//	resp.sendRedirect("main.jsp");
			}else{
			        out.print(false);//返回登录信息
			        out.flush();
			        out.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	}

}
