package site.jimblog.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.dto.ShopExecution;
import site.jimblog.entity.Area;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Shop;
import site.jimblog.enums.ShopStateEnum;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: ShopServiceTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 14, 2018  
 * 
 */
public class ShopServiceTest extends BaseTest{
	@Resource
	private ShopService shopService;
	
	@Test
	public void testListShop(){
		Shop shopCondition=new Shop();
		shopCondition.setShopName("二");
		ShopExecution shopExecution = shopService.listShop(shopCondition, new PageBean(0, 5));
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop.getShopName());
		}
		System.out.println("count:"+shopExecution.getCount());
	}
	
	@Test
	public void testSaveShop(){
		Shop shop=new Shop();
		Area area=new Area();
		area.setAreaId(3);
		
		shop.setOwnerId(8L);
		shop.setArea(area);
		shop.setShopCategoryId(10L);
		shop.setShopName("test3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setShopImg("test3");
		shop.setPriority(10);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
	//	File shopImg=new File("C:\\Users\\HSAEE\\Pictures\\Saved Pictures\\1.jpg");
		
	//	ShopExecution shopExecution = shopService.saveShop(shop,shopImg);
	//	assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getShop().getEnableStatus().intValue());
	}
	
	@Test
	public void testUpdateShop(){
		Shop shop=new Shop();
		shop.setShopId(31L);
		shop.setOwnerId(8L);
		shop.setShopCategoryId(10L);
		shop.setShopName("test3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setShopImg("test3");
		shop.setPriority(10);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg=new File("C:\\Users\\HSAEE\\Pictures\\Saved Pictures\\4.jpg");
	
		ShopExecution shopExecution = shopService.updateShop(shop, shopImg);
		assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getShop().getEnableStatus().intValue());
	}
	
}
