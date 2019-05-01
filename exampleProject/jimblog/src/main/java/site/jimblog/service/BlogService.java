package site.jimblog.service;

import java.util.List;
import java.util.Map;

import site.jimblog.entity.Blog;


/**
 * <p>Title: BlogService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 * 
 */
public interface BlogService {
	public List<Blog> countList();
	
	public List<Blog> list(Map<String,Object> map);
	
	public Long getTotal(Map<String,Object> map);
	
	public Blog findById(Integer id);
	
	public Integer update(Blog blog); 
	
	public Blog getLastBlog(Integer id);
	
	public Blog getNextBlog(Integer id);
	
	public Integer add(Blog blog);
	
	public Integer delete(Integer id);
	
	public Integer getBlogByTypeId(Integer typeId);
}
