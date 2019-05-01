package site.jimblog.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import site.jimblog.entity.News;
import site.jimblog.entity.Notice;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductBigType;
import site.jimblog.entity.Tag;
import site.jimblog.service.NewsService;
import site.jimblog.service.NoticeService;
import site.jimblog.service.ProductBigTypeService;
import site.jimblog.service.ProductService;
import site.jimblog.service.TagService;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: SysAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 5, 2018  
 * 
 */
public class SysAction extends ActionSupport implements ApplicationAware{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> application;
	
	@Resource
	private ProductBigTypeService productBigTypeService;
	@Resource
	private TagService tagService;
	@Resource
	private NoticeService noticeService;
	@Resource
	private NewsService newsService;
	@Resource
	private ProductService productService;
	
	@Override
	public void setApplication(Map<String, Object> application) {
		this.application=application;
	}

	public void refreshSystem() throws Exception{
		List<ProductBigType> bigTypeList=productBigTypeService.findAllBigTypeList();
		application.put("bigTypeList", bigTypeList);
		
		List<Tag> tagList=tagService.findTagList(null,null);
		application.put("tagList", tagList);
		
		List<Notice> noticeList=noticeService.findNoticeList(null,new PageBean(1, 7));
		application.put("noticeList", noticeList);
		
		List<News> newsList=newsService.findNewsList(null,new PageBean(1, 7));
		application.put("newsList", newsList);
		
		Product product=new Product();
		product.setSpecialPrice(1);
		List<Product> specialPriceProductList = productService.findProductList(product, new PageBean(1, 8));
		application.put("specialPriceProductList", specialPriceProductList);
		
		product=new Product();
		product.setHot(1);
		List<Product> hotProductList = productService.findProductList(product, new PageBean(1, 8));
		application.put("hotProductList", hotProductList);
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
}
