package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.Comment;
import site.jimblog.entity.PageBean;
import site.jimblog.service.CommentService;

/**
 * <p>Title: CommentServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 9, 2018  
 * 
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
	
	@Resource
	private BaseDao<Comment> baseDao;

	@Override
	public List<Comment> findCommentList(Comment comment,PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from Comment");
		if(comment!=null){
			if(comment.getContent()!=null){
				hql.append(" and content like ?");
				param.add("%"+comment.getContent()+"%");
			}
		}
		hql.append(" order by createTime desc ");
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}
		return null;
	}

	@Override
	public Long getCommentCount(Comment comment) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from Comment");
		if(comment!=null){
			if(comment.getContent()!=null){
				hql.append(" and content like ?");
				param.add("%"+comment.getContent()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"),param);
	}

	@Override
	public void saveComment(Comment comment) {
		baseDao.merge(comment);
	}

	@Override
	public Comment getCommentById(int id) {
		return baseDao.get(Comment.class, id);
	}

	@Override
	public void deleteComment(Comment comment) {
		baseDao.delete(comment);
	}
	
}
