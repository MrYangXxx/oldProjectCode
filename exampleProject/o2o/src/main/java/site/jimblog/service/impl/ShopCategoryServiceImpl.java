package site.jimblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.ShopCategoryDao;
import site.jimblog.entity.ShopCategory;
import site.jimblog.service.ShopCategoryService;

/**
 * <p>Title: ShopCategoryServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 17, 2018  
 * 
 */
@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Resource
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> listShopCategory(ShopCategory shopCategory) {
		return shopCategoryDao.listShopCategory(shopCategory);
	}

}
