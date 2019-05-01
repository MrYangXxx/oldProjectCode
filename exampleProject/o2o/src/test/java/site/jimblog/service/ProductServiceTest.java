package site.jimblog.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.entity.ProductCategory;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: ShopServiceTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 14, 2018  
 * 
 */
public class ProductServiceTest extends BaseTest{
	@Resource
	private ProductCategoryService productCategoryService;
	
	@Test
	public void testListproductCategory(){
		List<ProductCategory> listProductCategory = productCategoryService.listProductCategory(15L);
		for (ProductCategory productCategory : listProductCategory) {
			System.out.println(productCategory.getProductCategoryName());
		}
	}
}
