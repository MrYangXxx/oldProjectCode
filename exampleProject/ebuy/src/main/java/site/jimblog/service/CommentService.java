package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.Comment;
import site.jimblog.entity.PageBean;

/**
 * <p>Title: CommentService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 9, 2018  
 * 
 */
public interface CommentService {
	List<Comment> findCommentList(Comment comment,PageBean pageBean);
	
	Long getCommentCount(Comment comment);
	
	void saveComment(Comment comment);
	
	Comment getCommentById(int id);
	
	void deleteComment(Comment comment);
}
