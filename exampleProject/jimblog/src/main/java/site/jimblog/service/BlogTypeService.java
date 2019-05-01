package site.jimblog.service;

import java.util.List;
import java.util.Map;

import site.jimblog.entity.BlogType;

/**
 * 
 * <p>Title: BlogTypeService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
public interface BlogTypeService {

	public List<BlogType> countList();
	
	public List<BlogType> list(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public Integer add(BlogType blogType);
	
	public Integer update(BlogType blogType);
	
	public Integer delete(Integer id);
}
