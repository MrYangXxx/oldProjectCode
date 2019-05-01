package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.model.User;
import com.util.DbUtil;
import com.util.StringUtil;

import net.sf.json.JSONObject;

@javax.servlet.annotation.WebServlet(urlPatterns="/login")
public class WebServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String action = request.getParameter("action");
		String skip = request.getParameter("skip");
		boolean choose=false;
		if (skip!=null&&skip.equals("login")) {
			login(request, response);
		}else if ("checkUserName".equals(action)) {
			choose=this.checkUserName(request, response);
		}
		else if (skip!=null&&skip.equals("register")) {
				if(!choose){
					register(request, response);
				}else{
					request.getRequestDispatcher("register.jsp");
				}
			}

		}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			request.setAttribute("error", "用户名或密码为空！");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		User user = new User(username, password);
		DbUtil db = new DbUtil();
		UserDao ud = new UserDao();
		try (Connection con = db.getCon();){
			User currentUser = ud.login(con, user);
			if (currentUser == null) {
				request.setAttribute("error", "用户名或密码错误！");
				// 服务器跳转
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				// 获取Session
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", currentUser);
				// 客户端跳转
				response.sendRedirect("main.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordAgain = request.getParameter("passwordAgain");
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			request.setAttribute("error", "用户名或密码为空！");
			request.getRequestDispatcher("register.jsp").forward(request, response);;
			return;
		} else if (password.equals(passwordAgain)) {
			User user = new User(username, password);
			DbUtil db = new DbUtil();
			UserDao ud = new UserDao();
			try(Connection con = db.getCon();) {
				int num = ud.register(con, user);
				if (num > 0) {
					response.sendRedirect("index.jsp");
				} else {
					request.setAttribute("error", "注册失败！");
					// 服务器跳转
					request.getRequestDispatcher("register.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("error", "输入密码不一致！");
			// 服务器跳转
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}

	private boolean checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		boolean ret=false;
		JSONObject resultJson = new JSONObject();

		DbUtil db = new DbUtil();
		UserDao ud = new UserDao();
		Connection con = null;
		try {
			con = db.getCon();
			if (ud.checkUser(con, username)) {
				resultJson.put("exist", true);
				ret=true;
			} else {
				resultJson.put("exist", false);
				ret=false;
			}
			out.println(resultJson);
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
