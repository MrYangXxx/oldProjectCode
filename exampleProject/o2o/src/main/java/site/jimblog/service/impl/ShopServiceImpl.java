package site.jimblog.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import site.jimblog.dao.ShopDao;
import site.jimblog.dto.ShopExecution;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Shop;
import site.jimblog.enums.ShopStateEnum;
import site.jimblog.service.ShopService;
import site.jimblog.util.FileUtils;
import site.jimblog.util.ImageUtil;
import site.jimblog.util.PathUtil;

/**
 * <p>
 * Title: ShopServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Jun 14, 2018
 * 
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {
	@Resource
	ShopDao shopDao;

	@Override
	public ShopExecution saveShop(Shop shop, CommonsMultipartFile shopImg) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		shop.setEnableStatus(0);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());

		int effectedNum = shopDao.saveShop(shop);
		if (effectedNum <= 0) {
			throw new RuntimeException("店铺添加异常");
		} else {
			if (shopImg != null) {
				saveShopImg(shop, shopImg);
				effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					throw new RuntimeException("更新图片地址失败");
				}
			}
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void saveShopImg(Shop shop, CommonsMultipartFile shopImg) {
		// get shop path
		String dest=PathUtil.getShopImgPath(shop.getShopId());
		String shopImgAddr=ImageUtil.generateThumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}
	
	/**
	 * use for test
	 */
	private void saveShopImg(Shop shop, File shopImg) {
		// get shop path
		String dest=PathUtil.getShopImgPath(shop.getShopId());
		String shopImgAddr=ImageUtil.generateThumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public ShopExecution updateShop(Shop shop, CommonsMultipartFile shopImg) {
		if(shop==null||shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		
		if(shopImg!=null){
			Shop temp=shopDao.getShopById(shop.getShopId());
			if(temp!=null){
				FileUtils.deleteFileOrPath(temp.getShopAddr());
			}
			saveShopImg(shop, shopImg);
		}
		shop.setLastEditTime(new Date());
		int updateShop = shopDao.updateShop(shop);
		if(updateShop<=0){
			return new ShopExecution(ShopStateEnum.INNER_ERROR);
		}else{
			shop=shopDao.getShopById(shop.getShopId());
		}
		return new ShopExecution(ShopStateEnum.SUCCESS,shop);
	}
	@Override
	public ShopExecution updateShop(Shop shop, File shopImg) {
		if(shop==null||shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		
		if(shopImg!=null){
			Shop temp=shopDao.getShopById(shop.getShopId());
			if(temp!=null){
				FileUtils.deleteFileOrPath(temp.getShopAddr());
			}
			saveShopImg(shop, shopImg);
		}
		shop.setLastEditTime(new Date());
		int updateShop = shopDao.updateShop(shop);
		if(updateShop<=0){
			return new ShopExecution(ShopStateEnum.INNER_ERROR);
		}/*else{
			shop=shopDao.getShopById(shop.getShopId());
		}*/
		return new ShopExecution(ShopStateEnum.SUCCESS,shop);
	}

	@Override
	public Shop getByShopId(long id) {
		return shopDao.getShopById(id);
	}

	@Override
	public ShopExecution listShop(Shop shopCondition, PageBean pageBean) {
		List<Shop> shopList = shopDao.listShop(shopCondition, pageBean);
		int count = shopDao.countShop(shopCondition);
		ShopExecution se=new ShopExecution();
		if(shopList != null){
			se.setShopList(shopList);
			se.setCount(count);
		}else{
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
