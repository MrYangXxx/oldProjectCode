package site.jimblog.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.entity.Area;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Shop;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: ShopDaoTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 13, 2018  
 * 
 */
public class ShopDaoTest extends BaseTest{
	@Resource
	ShopDao shopDao;
	
	@Test
	public void testListShop(){
		Shop shopCondition=new Shop();
		//shopCondition.setShopName("二");
		List<Shop> list = shopDao.listShop(shopCondition, new PageBean(0, 5));
		for (Shop shop2 : list) {
			System.out.println(shop2.getShopId());
		}
		int countShop = shopDao.countShop(shopCondition);
		System.out.println("count:"+countShop);
	}
	
	@Test
	public void testSaveShop(){
		Shop shop=new Shop();
		Area area=new Area();
		area.setAreaId(3);
		
		shop.setOwnerId(8L);
		shop.setArea(area);
		shop.setShopCategoryId(10L);
		shop.setShopName("test");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setPriority(10);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		
		int saveShop = shopDao.saveShop(shop);
		assertEquals(1,saveShop);
	}
	
	@Test
	public void testUpdateShop(){
		Shop shop=new Shop();
		shop.setShopId(31L);
		
		shop.setShopName("test2");
		shop.setShopDesc("test2");
		shop.setShopAddr("test2");
		shop.setPhone("test2");
		shop.setShopImg("test2");
		shop.setPriority(20);
		shop.setLastEditTime(new Date());
		
		int updateShop = shopDao.updateShop(shop);
		assertEquals(1,updateShop);
	}
	
	@Test
	public void testQueryShop(){
		Long shopId=15L;
		Shop shop = shopDao.getShopById(shopId);
		System.out.println(shop.getShopName());
	}
}
