package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Tag;
import site.jimblog.service.TagService;

/**
 * <p>Title: ProductBigTypeServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
@Service("tagService")
public class TagServiceImpl implements TagService {
	
	@Resource
	private BaseDao<Tag> baseDao;

	@Override
	public List<Tag> findTagList(Tag tag, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from Tag");
		if(tag!=null){
			if(tag.getName()!=null){
				hql.append(" and name like ?");
				param.add("%"+tag.getName()+"%");
			}
		}
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"),param,pageBean);
		}
		return baseDao.find(hql.toString().replaceFirst("and", "where"),param);
	}

	@Override
	public Long getTagCount(Tag tag) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from Tag");
		if(tag!=null){
			if(tag.getName()!=null){
				hql.append(" and name like ?");
				param.add("%"+tag.getName()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"));
	}

	@Override
	public void saveTag(Tag tag) {
		baseDao.merge(tag);
	}

	@Override
	public void deleteTag(Tag tag) {
		baseDao.delete(tag);
	}

	@Override
	public Tag getTagById(int id) {
		return baseDao.get(Tag.class, id);
	}
	
	

}
