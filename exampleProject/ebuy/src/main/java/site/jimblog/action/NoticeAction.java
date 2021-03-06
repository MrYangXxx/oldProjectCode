package site.jimblog.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.Notice;
import site.jimblog.entity.PageBean;
import site.jimblog.service.NoticeService;
import site.jimblog.util.DateJsonValueProcessor;
import site.jimblog.util.NavUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: NoticeAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 6, 2018  
 * 
 */
public class NoticeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	@Resource
	private NoticeService noticeService;
	
	private Notice notice;
	private int noticeId;
	private String pageCode;
	private String mainPage;
	private String navCode;
	private String page;
	private String rows;
	private String ids;
	
	public void setPage(String page) {
		this.page = page;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Notice getNotice() {
		return notice;
	}
	
	public void setNotice(Notice notice) {
		this.notice = notice;
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

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String showNotice(){
		notice=noticeService.getNoticeById(noticeId);
		mainPage="notice/noticeDetails.jsp";
		navCode=NavUtil.genNavCode("公告信息");
		return SUCCESS;
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<Notice> noticeList = noticeService.findNoticeList(notice, pageBean);
		Long total = noticeService.getNoticeCount(notice);
		JsonConfig config=new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(noticeList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void save() throws Exception{
		if(notice.getId()==0){
			notice.setCreateTime(new Date());
		}
		noticeService.saveNotice(notice);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			Notice noticeById = noticeService.getNoticeById(Integer.parseInt(id));
			noticeService.deleteNotice(noticeById);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
}
