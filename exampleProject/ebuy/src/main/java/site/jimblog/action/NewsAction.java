package site.jimblog.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.News;
import site.jimblog.entity.PageBean;
import site.jimblog.service.NewsService;
import site.jimblog.util.DateJsonValueProcessor;
import site.jimblog.util.NavUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: NewsAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 6, 2018  
 * 
 */
public class NewsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	@Resource
	private NewsService newsService;
	
	private News news;
	private int newsId;
	private String pageCode;
	private String mainPage;
	private String navCode;
	
	private String page;
	private String rows;
	private String ids;
	
	public void setNews(News news) {
		this.news = news;
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

	public News getNews() {
		return news;
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

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String showNews(){
		news=newsService.getNewsById(newsId);
		mainPage="news/newsDetails.jsp";
		navCode=NavUtil.genNavCode("新闻信息");
		return SUCCESS;
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<News> newsList = newsService.findNewsList(news, pageBean);
		Long total = newsService.getNewsCount(news);
		JsonConfig config=new JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(newsList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void save() throws Exception{
		if(news.getId()==0){
			news.setCreateTime(new Date());
		}
		newsService.saveNews(news);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			News newsById = newsService.getNewsById(Integer.parseInt(id));
			newsService.deleteNews(newsById);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
}
