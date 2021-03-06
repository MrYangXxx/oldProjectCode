package com.java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns="/*")
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session=((HttpServletRequest) request).getSession();
		Object obj = session.getAttribute("queryUser");
		String path=((HttpServletRequest) request).getServletPath();
		
		if(obj!=null||path.indexOf("login")>0||path.toString().contains("html_files")||path.toString().contains("$")){
			chain.doFilter(request, response);
		}else{
			((HttpServletResponse) response).sendRedirect("login.html");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
