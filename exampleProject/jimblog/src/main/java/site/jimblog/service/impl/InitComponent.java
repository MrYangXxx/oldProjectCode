package site.jimblog.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import site.jimblog.entity.Blog;
import site.jimblog.entity.BlogType;
import site.jimblog.entity.Blogger;
import site.jimblog.service.BlogService;
import site.jimblog.service.BlogTypeService;
import site.jimblog.service.BloggerService;

/**
 * 
 * <p>Title: InitComponent</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		InitComponent.applicationContext=applicationContext;
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext application=servletContextEvent.getServletContext();
		BloggerService bloggerService=(BloggerService) applicationContext.getBean("bloggerService");
		Blogger blogger=bloggerService.find(); 
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		BlogTypeService blogTypeService=(BlogTypeService) applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList=blogTypeService.countList(); 
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		BlogService blogService=(BlogService) applicationContext.getBean("blogService");
		List<Blog> blogCountList=blogService.countList(); 
		application.setAttribute("blogCountList", blogCountList);
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
