package site.jimblog.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import site.jimblog.entity.News;
import site.jimblog.entity.Notice;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductBigType;
import site.jimblog.entity.Tag;
import site.jimblog.service.NewsService;
import site.jimblog.service.NoticeService;
import site.jimblog.service.ProductBigTypeService;
import site.jimblog.service.ProductService;
import site.jimblog.service.TagService;

/**
 * <p>Title: InitAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
@Component
public class InitAction implements ServletContextListener,ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext sc=servletContextEvent.getServletContext();
		ProductBigTypeService productBigTypeService = (ProductBigTypeService) applicationContext.getBean("productBigTypeService");
		List<ProductBigType> bigTypeList=productBigTypeService.findAllBigTypeList();
		sc.setAttribute("bigTypeList", bigTypeList);
		
		TagService tagService = (TagService) applicationContext.getBean("tagService");
		List<Tag> tagList=tagService.findTagList(null,null);
		sc.setAttribute("tagList", tagList);
		
		NoticeService noticeService = (NoticeService) applicationContext.getBean("noticeService");
		List<Notice> noticeList=noticeService.findNoticeList(null,new PageBean(1, 7));
		sc.setAttribute("noticeList", noticeList);
		
		NewsService newsService = (NewsService) applicationContext.getBean("newsService");
		List<News> newsList=newsService.findNewsList(null,new PageBean(1, 7));
		sc.setAttribute("newsList", newsList);
		
		Product product=new Product();
		product.setSpecialPrice(1);
		ProductService productService=(ProductService) applicationContext.getBean("productService");
		List<Product> specialPriceProductList = productService.findProductList(product, new PageBean(1, 8));
		sc.setAttribute("specialPriceProductList", specialPriceProductList);
		
		product=new Product();
		product.setHot(1);
		List<Product> hotProductList = productService.findProductList(product, new PageBean(1, 8));
		sc.setAttribute("hotProductList", hotProductList);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		InitAction.applicationContext=applicationContext;
	}

	
}
