package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.ShopCategory;

/**
 * <p>Title: ShopCategoryService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 17, 2018  
 * 
 */
public interface ShopCategoryService {
	List<ShopCategory> listShopCategory(ShopCategory shopCategory);
	
}
