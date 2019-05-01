package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.News;
import site.jimblog.entity.PageBean;
import site.jimblog.service.NewsService;

/**
 * <p>Title: NewsServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {
	
	@Resource
	private BaseDao<News> baseDao;
	
	@Override
	public List<News> findNewsList(News news, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from News");
		if(news!=null){
			if(news.getTitle()!=null){
				hql.append(" and title like ?");
				param.add("%"+news.getTitle()+"%");
			}
		}
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}
		return null;
	}

	@Override
	public News getNewsById(int newsId) {
		return baseDao.get(News.class, newsId);
	}

	@Override
	public Long getNewsCount(News news) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from News");
		if(news!=null){
			if(news.getTitle()!=null){
				hql.append(" and title like ?");
				param.add("%"+news.getTitle()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveNews(News news) {
		baseDao.merge(news);
	}

	@Override
	public void deleteNews(News news) {
		baseDao.delete(news);
	}

}
