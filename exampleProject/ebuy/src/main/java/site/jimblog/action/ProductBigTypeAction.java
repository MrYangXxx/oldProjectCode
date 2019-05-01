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
import site.jimblog.service.ProductBigTypeService;
import site.jimblog.service.ProductSmallTypeService;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: ProductBigTypeAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 3, 2018  
 * 
 */
public class ProductBigTypeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Resource
	ProductBigTypeService productBigTypeService;
	@Resource
	ProductSmallTypeService productSmallTypeService;
	
	private ProductBigType productBigType;
	private String page;
	private String rows;
	private String ids;
	
	public void setPage(String page) {
		this.page = page;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setProductBigType(ProductBigType productBigType) {
		this.productBigType = productBigType;
	}
	public ProductBigType getProductBigType() {
		return productBigType;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void comboList() throws Exception{
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", "");
		jsonObject.put("name", "请选择...");
		jsonArray.add(jsonObject);
		List<ProductBigType> bigTypeList = productBigTypeService.findAllBigTypeList();
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"productList","smallTypeList"});
		JSONArray rows = JSONArray.fromObject(bigTypeList,config);
		jsonArray.addAll(rows);
		ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<ProductBigType> productBigTypeList = productBigTypeService.findProductBigTypeList(productBigType, pageBean);
		Long total = productBigTypeService.getProductBigTypeCount(productBigType);
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"productList","smallTypeList"});
		JSONArray rows = JSONArray.fromObject(productBigTypeList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void save() throws Exception{
		productBigTypeService.saveProductBigType(productBigType);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void delete() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			if(productSmallTypeService.existSmallTypeWithBigTypeId(Integer.parseInt(id))){
				result.put("exist", "商品大类含有小类，不能删除");
			}else{
				ProductBigType productBigTypeById = productBigTypeService.getProductBigTypeById(Integer.parseInt(id));
				productBigTypeService.deleteProductBigType(productBigTypeById);
			}
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
}
