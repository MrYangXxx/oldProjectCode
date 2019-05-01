package site.jimblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.Shop;

/**
 * <p>Title: ShopDao</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 13, 2018  
 * 
 */
public interface ShopDao {
	int saveShop(Shop shop);
	
	int updateShop(Shop shop);
	
	Shop getShopById(long id);
	
	List<Shop> listShop(@Param("shopCondition")Shop shopCondition,@Param("pageBean")PageBean pageBean);
	
	int countShop(@Param("shopCondition")Shop shopCondition);
}
