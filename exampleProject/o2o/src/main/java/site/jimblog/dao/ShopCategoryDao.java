package site.jimblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.ShopCategory;

/**
 * <p>Title: ShopCategoryDao</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 17, 2018  
 * 
 */
public interface ShopCategoryDao {
	List<ShopCategory> listShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
