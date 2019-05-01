package site.jimblog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BloggerDao;
import site.jimblog.entity.Blogger;
import site.jimblog.service.BloggerService;

/**
 * 
 * <p>Title: BloggerServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

	@Resource
	private BloggerDao bloggerDao;

	public Blogger find() {
		return bloggerDao.find();
	}

	public Blogger getByUserName(String userName) {
		return bloggerDao.getByUserName(userName);
	}

	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}

	public Blogger login(String userName, String password) {
		return bloggerDao.login(userName, password);
	}
	
	
}
