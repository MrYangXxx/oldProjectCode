package site.jimblog.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.mchange.v2.lang.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.Comment;
import site.jimblog.entity.PageBean;
import site.jimblog.service.CommentService;
import site.jimblog.util.DateJsonValueProcessor;
import site.jimblog.util.PageUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: CommentAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 13, 2018  
 * 
 */
public class CommentAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private List<Comment> commentList;
	private Comment comment;
	
	@Resource
	private CommentService commentService;
	
	private String page;
	private Long total;
	private String pageCode;
	private String mainPage;
	private String navCode;
	private String rows;
	private String commentId;
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public Long getTotal() {
		return total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public String getMainPage() {
		return mainPage;
	}

	public String getNavCode() {
		return navCode;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Comment getComment() {
		return comment;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String list(){
		if(!StringUtils.nonWhitespaceString(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page), 5);
		commentList=commentService.findCommentList(comment, pageBean);
		total=commentService.getCommentCount(comment);
		pageCode = PageUtil.genPaginationNoParam(ServletActionContext.getRequest().getContextPath()+"/comment_list.action", total, pageBean);
		return SUCCESS;
	}
	
	public String save(){
		if(comment.getCreateTime()==null){
			comment.setCreateTime(new Date());
		}
		commentService.saveComment(comment);
		return "save";
	}
	
	public void listComment() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		commentList = commentService.findCommentList(comment, pageBean);
		total = commentService.getCommentCount(comment);
		JsonConfig config=new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(commentList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void loadCommentById() throws Exception{
		Comment commentById = commentService.getCommentById(Integer.parseInt(commentId));
		JsonConfig config=new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject result=JSONObject.fromObject(commentById,config);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void reply() throws Exception{
		comment.setReplyTime(new Date());
		commentService.saveComment(comment);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			Comment commentById = commentService.getCommentById(Integer.parseInt(id));
			commentService.deleteComment(commentById);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
}
