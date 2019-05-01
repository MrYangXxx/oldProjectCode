package site.jimblog.service;


import java.io.File;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import site.jimblog.dto.ShopExecution;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Shop;

/**
 * <p>Title: ShopService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 14, 2018  	
 * 
 */
public interface ShopService {
	ShopExecution saveShop(Shop shop,CommonsMultipartFile shopImg);
	
	//use for test
	//ShopExecution saveShop(Shop shop,File shopImg);
	
	ShopExecution updateShop(Shop shop,CommonsMultipartFile shopImg);
	
	ShopExecution updateShop(Shop shop,File shopImg);
	
	Shop getByShopId(long id);
	
	ShopExecution listShop(Shop shopCondition,PageBean pageBean);
}
