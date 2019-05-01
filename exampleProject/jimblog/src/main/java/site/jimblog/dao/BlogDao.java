package site.jimblog.dao;

import java.util.List;
import java.util.Map;

import site.jimblog.entity.Blog;

/**
 * <p>
 * Title: BlogDao
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Feb 22, 2018
 * 
 */
public interface BlogDao {
	
	List<Blog> countList();

	List<Blog> list(Map<String, Object> map);

	Long getTotal(Map<String, Object> map);

	Blog findById(Integer id);

	Integer update(Blog blog);

	Blog getLastBlog(Integer id);

	Blog getNextBlog(Integer id);

	Integer add(Blog blog);

	Integer delete(Integer id);

	Integer getBlogByTypeId(Integer typeId);
}
