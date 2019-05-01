package site.jimblog.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.entity.ShopCategory;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: ShopCategoryDaoTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 17, 2018  
 * 
 */
public class ShopCategoryDaoTest extends BaseTest{
	
	@Resource
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testListShopCategory(){
//		List<ShopCategory> listShopCategory = shopCategoryDao.listShopCategory(new ShopCategory());
//		assertEquals(18,listShopCategory.size());
//		ShopCategory shopCategory=new ShopCategory();
//		shopCategory.setParentId(10L);
//		List<ShopCategory> listShopCategory2 = shopCategoryDao.listShopCategory(shopCategory);
//		assertEquals(2,listShopCategory2.size());
		List<ShopCategory> listShopCategory = shopCategoryDao.listShopCategory(null);
		assertEquals(6,listShopCategory.size());
	}
	
	
}
