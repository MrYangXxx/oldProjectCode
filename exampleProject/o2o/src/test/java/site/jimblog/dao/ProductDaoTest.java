package site.jimblog.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductCategory;
import site.jimblog.entity.ProductImg;
import site.jimblog.entity.Shop;
import site.jimblog.util.BaseTest;

/**
 * <p>
 * Title: ProductDaoTest
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Sep 14, 2018
 * 
 */
public class ProductDaoTest extends BaseTest {
	@Autowired
	ProductDao productDao;

	@Test
	public void testASaveProduct() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(15L);
		Shop shop2 = new Shop();
		shop2.setShopId(16L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(9L);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryId(10L);
		ProductCategory pc3 = new ProductCategory();
		pc3.setProductCategoryId(11L);
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(0);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(0);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc2);
		Product product3 = new Product();
		product3.setProductName("测试3");
		product3.setProductDesc("测试Desc3");
		product3.setImgAddr("test3");
		product3.setPriority(0);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop2);
		product3.setProductCategory(pc3);
		int effectedNum = productDao.saveProduct(product1);
		assertEquals(1, effectedNum);
		effectedNum = productDao.saveProduct(product2);
		assertEquals(1, effectedNum);
		effectedNum = productDao.saveProduct(product3);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testListProduct() throws Exception {
		Product product = new Product();
		List<Product> productList = productDao.listProduct(product,new PageBean(0, 3));
		//assertEquals(3, productList.size());
		System.out.println(productList.size());
		
		int count = productDao.countProduct(product);
		//assertEquals(4, count);
		System.out.println(count);
		
		product.setProductName("美式咖啡");
		productList = productDao.listProduct(product,new PageBean(0, 3));
		//assertEquals(1, productList.size());
		System.out.println(productList.size());
		
		count = productDao.countProduct(product);
		//assertEquals(3, count);
		System.out.println(count);
		
		product=new Product();
		Shop shop = new Shop();
		shop.setShopId(15L);
		product.setShop(shop);
		productList = productDao.listProduct(product,new PageBean(0, 3));
		for (Product product2 : productList) {
			System.out.println(product2.getProductName());
		}
		count = productDao.countProduct(product);
		System.out.println(count);
//		assertEquals(1, count);
	}

	@Test
	public void testCQueryProductByProductId() throws Exception {
		long productId = 1;
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productDao.saveProductImgs(productImgList);
		assertEquals(2, effectedNum);
		Product product = productDao.getProduct(productId);
		assertEquals(2, product.getProductImgList().size());
		effectedNum = productDao.deleteProductImgs(productId);
		assertEquals(2, effectedNum);
	}

	@Test
	public void testDUpdateProduct() throws Exception {
		Product product = new Product();
		product.setProductId(1L);
		product.setProductName("第一个产品");
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testEDeleteShopAuthMap() throws Exception {
		int effectedNum = productDao.deleteProduct(1);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testSaveProductImgs() {
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productDao.saveProductImgs(productImgList);
		assertEquals(2, effectedNum);
	}

	@Test
	public void testCDeleteProductImgByProductId() throws Exception {
		long productId = 1;
		int effectedNum = productDao.deleteProductImgs(productId);
		assertEquals(2, effectedNum);
	}

}
