package site.jimblog.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import site.jimblog.entity.User;

public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session=((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("currentUser");
		String path=((HttpServletRequest) request).getServletPath();
		
		if(user!=null&&user.getStatus()==2||path.indexOf("login")>0||path.indexOf("image")>0||path.indexOf("index")>0){
			chain.doFilter(request, response);
		}else{
			((HttpServletResponse) response).sendRedirect("login.jsp");
		}
	}

	@Override
	public void destroy() {
	}

}
