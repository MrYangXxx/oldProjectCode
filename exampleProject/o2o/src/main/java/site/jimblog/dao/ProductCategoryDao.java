package site.jimblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.ProductCategory;

/**
 * <p>Title: ProductCategoryDao</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Aug 21, 2018  
 * 
 */
public interface ProductCategoryDao {
	List<ProductCategory> listProductCategory(Long shopId);
	
	int saveProductCategorys(List<ProductCategory> categories);
	
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId);
}
