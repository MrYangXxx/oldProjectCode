package site.jimblog.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Tag;
import site.jimblog.service.TagService;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: TagAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 4, 2018  
 * 
 */
public class TagAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	@Resource
	private TagService tagService;
	
	private Tag tag;
	private String pageCode;
	private String mainPage;
	private String navCode;
	
	private String page;
	private String rows;
	private String ids;
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Tag getTag() {
		return tag;
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


	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<Tag> tagList = tagService.findTagList(tag, pageBean);
		Long total = tagService.getTagCount(tag);
		JsonConfig config=new JsonConfig();
		JSONArray rows = JSONArray.fromObject(tagList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void save() throws Exception{
		tagService.saveTag(tag);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			Tag tagById = tagService.getTagById(Integer.parseInt(id));
			tagService.deleteTag(tagById);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
}
