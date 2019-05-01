package site.jimblog.service;

import java.util.List;

import site.jimblog.dto.ProductCategoryExecution;
import site.jimblog.entity.ProductCategory;

/**
 * <p>Title: ProductCategoryService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Aug 21, 2018  
 * 
 */
public interface ProductCategoryService {
	List<ProductCategory> listProductCategory(Long shopId);
	
	ProductCategoryExecution saveProductCategorys(List<ProductCategory> categories);
	
	ProductCategoryExecution deleteProductCategory(long productCategoryId);
}
