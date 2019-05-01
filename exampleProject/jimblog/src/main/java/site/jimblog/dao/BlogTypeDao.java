package site.jimblog.dao;

import java.util.List;
import java.util.Map;

import site.jimblog.entity.BlogType;

/**
 * 
 * <p>Title: BlogTypeDao</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
public interface BlogTypeDao {

	 List<BlogType> countList();
	
	 BlogType findById(Integer id);
	
	 List<BlogType> list(Map<String,Object> map);
	
	 Long getTotal(Map<String,Object> map);
	
	 Integer add(BlogType blogType);
	
	 Integer update(BlogType blogType);
	
	 Integer delete(Integer id);
}
