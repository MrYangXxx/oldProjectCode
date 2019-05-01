package site.jimblog.action;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.mchange.v2.lang.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductBigType;
import site.jimblog.entity.ProductSmallType;
import site.jimblog.service.ProductService;
import site.jimblog.util.DateJsonValueProcessor;
import site.jimblog.util.DateUtil;
import site.jimblog.util.NavUtil;
import site.jimblog.util.ObjectJsonValueProcessor;
import site.jimblog.util.PageUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: ProductAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 1, 2018  
 * 
 */
@Controller
public class ProductAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	@Resource
	private ProductService productService;
	
	private List<Product> productList;
	private Product product;
	
	private String page;
	private String rows;
	private Long total;
	private String pageCode;
	private String mainPage;
	private String navCode;
	private int productId;
	
	private File proPic;
	private String proPicFileName;
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setProPic(File proPic) {
		this.proPic = proPic;
	}

	public void setProPicFileName(String proPicFileName) {
		this.proPicFileName = proPicFileName;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
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

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public String execute() throws Exception {
		if(!StringUtils.nonWhitespaceString(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page), 8);
		productList=productService.findProductList(product, pageBean);
		total=productService.getProductCount(product);
		StringBuffer param=new StringBuffer();
		if(product!=null){
			if(product.getBigType()!=null){
				param.append("product.bigType.id="+product.getBigType().getId());
			}
			if(product.getSmallType()!=null){
				param.append("product.smallType.id="+product.getSmallType().getId());
			}
			if(StringUtils.nonWhitespaceString(product.getName())){
				param.append("product.name="+product.getName());
			}
		}
		pageCode=PageUtil.genPagination(ServletActionContext.getRequest().getContextPath()+"/product.action", total,pageBean, param.toString());
		navCode=NavUtil.genNavCode("商品列表");
		mainPage="product/productList.jsp";
		return SUCCESS;
	}
	
	public String showProduct(){
		product=productService.getProductById(productId);
		saveCurrentBrowse(product);
		navCode=NavUtil.genNavCode("商品详情");
		mainPage="product/productDetails.jsp";
		return SUCCESS;
	}

	private void saveCurrentBrowse(Product product){
		HttpSession session = ServletActionContext.getRequest().getSession();
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>) session.getAttribute("currentBrowse");
		if(list==null){
			list=new LinkedList<>();
		}
		
		boolean flag=true;
		for (Product p : list) {
			if(p.getId()==product.getId()){
				flag=false;
				break;
			}
		}
		
		if(flag){
			list.add(0,product);
		}
		
		if(list.size()==5){
			list.remove(4);
		}
		session.setAttribute("currentBrowse",list);
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		productList = productService.findProductList(product, pageBean);
		total = productService.getProductCount(product);
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"orderProductList"});
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		config.registerJsonValueProcessor(ProductBigType.class,new ObjectJsonValueProcessor(new String[]{"id","name"}, ProductBigType.class));
		config.registerJsonValueProcessor(ProductSmallType.class,new ObjectJsonValueProcessor(new String[]{"id","name"}, ProductSmallType.class));
		JSONArray rows = JSONArray.fromObject(productList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void save() throws Exception{
		if(proPic!=null){
			String imageName=DateUtil.getCurrentDateStr();
			String realPath = ServletActionContext.getServletContext().getRealPath("/images/product");
			String imageFile=imageName+"."+proPicFileName.split("\\.")[1];
			File saveFile=new File(realPath,imageFile);
			FileUtils.copyFile(proPic, saveFile);
			product.setProPic("images/product/"+imageFile);
		}else if(product.getProPic()==null){
			product.setProPic("");
		}
		productService.saveProduct(product);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			Product productById = productService.getProductById(Integer.parseInt(id));
			productService.deleteProduct(productById);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void setProductWithHot() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			productService.setProductWithHot(Integer.parseInt(id));
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void setProductWithSpecialPrice() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			productService.setProductWithSpecialPrice(Integer.parseInt(id));
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
}
