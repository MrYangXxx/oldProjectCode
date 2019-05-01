package site.jimblog.web.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import site.jimblog.dto.ProductCategoryExecution;
import site.jimblog.dto.Result;
import site.jimblog.entity.Person;
import site.jimblog.entity.ProductCategory;
import site.jimblog.enums.ProductCategoryStateEnum;
import site.jimblog.service.ProductCategoryService;
import site.jimblog.util.HttpServletRequestUtil;

/**
 * <p>Title: ProductCategoryManagementController</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Aug 26, 2018  
 * 
 */
@Controller
@RequestMapping("/shop")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping("/productcategorys")
	@ResponseBody
	private Result<List<ProductCategory>> listProductCategorys(HttpServletRequest request) {
		Long shopId=HttpServletRequestUtil.getLong(request, "shopid");
		Person user = new Person();
		user.setUserId(8L);
		user.setName("Jim");
		List<ProductCategory> productCategory = productCategoryService.listProductCategory(shopId);
		Result<List<ProductCategory>> result=new Result<>();
		result.setSuccess(true);
		result.setData(productCategory);
		result.setPerson(user);
		return result;
	}
	
	@RequestMapping("/addproductcategorys")
	@ResponseBody
	private Map<String, Object> addProductCategorys(HttpServletRequest request,@RequestBody List<ProductCategory> productCategorys){
		Map<String, Object> modelMap=new HashMap<>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopid");
		if(productCategorys!=null&&productCategorys.size()>0 && shopId!=-1){
			for (ProductCategory productCategory : productCategorys) {
				productCategory.setShopId(shopId);
			}
			ProductCategoryExecution productCategoryExecution = productCategoryService.saveProductCategorys(productCategorys);
			if(productCategoryExecution.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", productCategoryExecution.getStateInfo());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入数据");
		}
		return modelMap;
	}
	
	@RequestMapping("/removeproductcategory")
	@ResponseBody
	private Map<String, Object> removeproductcategory(HttpServletRequest request,Long productCategoryId){
		Map<String, Object> modelMap=new HashMap<>();
		if(productCategoryId>0){
			ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId);
			if(productCategoryExecution.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", productCategoryExecution.getStateInfo());
			}
		}
		return modelMap;
	}
}
