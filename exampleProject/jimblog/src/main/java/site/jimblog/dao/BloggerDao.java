package site.jimblog.dao;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.Blogger;

/**
 * 
 * <p>Title: BloggerDao</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
public interface BloggerDao {

	 Blogger find();
	
	 Blogger getByUserName(String userName);
	
	 Integer update(Blogger blogger);
	
	 Blogger login(@Param("userName")String userName,@Param("password")String password);
}
