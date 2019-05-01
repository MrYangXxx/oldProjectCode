package site.jimblog.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.ProductBigType;
import site.jimblog.entity.ProductSmallType;
import site.jimblog.service.ProductService;
import site.jimblog.service.ProductSmallTypeService;
import site.jimblog.util.ObjectJsonValueProcessor;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: ProductSmallTypeAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 3, 2018  
 * 
 */
public class ProductSmallTypeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Resource
	ProductSmallTypeService productSmallTypeService;
	@Resource
	ProductService productService;
	
	private ProductSmallType productSmallType;
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

	public void setProductSmallType(ProductSmallType productSmallType) {
		this.productSmallType = productSmallType;
	}
	public ProductSmallType getProductSmallType() {
		return productSmallType;
	}

	public void comboList() throws Exception{
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", "");
		jsonObject.put("name", "请选择...");
		jsonArray.add(jsonObject);
		List<ProductSmallType> smallTypeList = productSmallTypeService.findProductSmallTypeList(productSmallType,null);
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"bigType","productList"});
		JSONArray rows = JSONArray.fromObject(smallTypeList,config);
		jsonArray.addAll(rows);
		ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<ProductSmallType> productSmallTypeList = productSmallTypeService.findProductSmallTypeList(productSmallType, pageBean);
		Long total = productSmallTypeService.getProductSmallTypeCount(productSmallType);
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"productList"});
		config.registerJsonValueProcessor(ProductBigType.class,new ObjectJsonValueProcessor(new String[]{"id","name"}, ProductBigType.class));
		JSONArray rows = JSONArray.fromObject(productSmallTypeList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void save() throws Exception{
		productSmallTypeService.saveProductSmallType(productSmallType);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			if(productService.existProductWithSmallTypeId(Integer.parseInt(id))){
				result.put("exist", "商品小类含有商品，不能删除");
			}else{
				ProductSmallType productSmallTypeById = productSmallTypeService.getProductSmallTypeById(Integer.parseInt(id));
				productSmallTypeService.deleteProductSmallType(productSmallTypeById);
			}
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}

}
