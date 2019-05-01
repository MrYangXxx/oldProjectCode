package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.News;
import site.jimblog.entity.PageBean;

/**
 * <p>Title: NoticeService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
public interface NewsService {
	List<News> findNewsList(News news,PageBean pageBean);
	
	News getNewsById(int newsId);
	
	Long getNewsCount(News news);
	
	void saveNews(News news);
	
	void deleteNews(News news);
}
