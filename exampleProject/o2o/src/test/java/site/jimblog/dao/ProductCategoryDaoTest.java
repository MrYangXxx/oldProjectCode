package site.jimblog.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.entity.ProductCategory;
import site.jimblog.util.BaseTest;

public class ProductCategoryDaoTest extends BaseTest {

	@Resource
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testListProductCategory() {
		List<ProductCategory> listProductCategory = productCategoryDao.listProductCategory(20L);
		for (ProductCategory productCategory : listProductCategory) {
			System.out.println(productCategory.getProductCategoryName());
		}
	}
	
	@Test
	public void testSaveProductCategorys(){
		List<ProductCategory> categories=new ArrayList<>();
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setProductCategoryDesc("测试商品类别");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setLastEditTime(new Date());
		productCategory.setShopId(15L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setProductCategoryDesc("测试商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setLastEditTime(new Date());
		productCategory2.setShopId(15L);
		categories.add(productCategory);
		categories.add(productCategory2);
		int saveProductCategorys = productCategoryDao.saveProductCategorys(categories);
		assertEquals(2, saveProductCategorys);
	}
	
	@Test
	public void testDeleteProductCategory(){
		int effectNum = productCategoryDao.deleteProductCategory(22);
		assertEquals(1, effectNum);
	}

}
