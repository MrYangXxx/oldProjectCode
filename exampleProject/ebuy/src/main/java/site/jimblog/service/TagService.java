package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.Tag;

/**
 * <p>Title: TagService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
public interface TagService {
	List<Tag> findTagList(Tag tag,PageBean pageBean);
	
	Long getTagCount(Tag tag);
	
	void saveTag(Tag tag);
	
	void deleteTag(Tag tag);
	
	Tag getTagById(int id);
}
