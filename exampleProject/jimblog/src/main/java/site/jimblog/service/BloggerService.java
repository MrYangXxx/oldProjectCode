package site.jimblog.service;

import site.jimblog.entity.Blogger;

/**
 * 
 * <p>Title: BloggerService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
public interface BloggerService {

	public Blogger find();
	
	public Blogger getByUserName(String userName);
	
	public Integer update(Blogger blogger);
	
	public Blogger login(String userName,String password);
}
