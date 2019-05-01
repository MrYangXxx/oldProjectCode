package site.jimblog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import site.jimblog.dao.ProductDao;
import site.jimblog.dto.Execution;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductImg;
import site.jimblog.enums.StateEnum;
import site.jimblog.service.ProductService;
import site.jimblog.util.FileUtils;
import site.jimblog.util.ImageUtil;
import site.jimblog.util.PathUtil;

/**
 * <p>
 * Title: ProductServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Sep 15, 2018
 * 
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	@Override
	public Execution<Product> saveProduct(Product product, CommonsMultipartFile productImg,
			List<CommonsMultipartFile> productImgs) {
		if (product == null || product.getShop() == null || product.getShop().getShopId() == -1) {
			return new Execution<>(StateEnum.NULL);
		}
		product.setEnableStatus(1);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		int effectedNum = productDao.saveProduct(product);
		if (effectedNum <= 0) {
			return new Execution<>(StateEnum.INNER_ERROR);
		} else {
			if (productImg != null) {
				saveProductImg(product, productImg);
				effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					return new Execution<>(StateEnum.INNER_ERROR);
				}
			}
			if (productImgs != null) {
				saveProductImgs(product, productImgs);
			}
		}
		return new Execution<>(StateEnum.CHECK, product);
	}

	// 批量产品详情图
	private void saveProductImgs(Product product, List<CommonsMultipartFile> productImgs) {
		String dest = PathUtil.getShopImgPath(product.getProductId());
		List<ProductImg> productImgList = new ArrayList<>();
		for (CommonsMultipartFile commonsMultipartFile : productImgs) {
			String productImgAddr = ImageUtil.generateNormalImg(commonsMultipartFile, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(productImgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		if (productImgList.size() > 0) {
			int effectNum = productDao.saveProductImgs(productImgList);
			if (effectNum <= 0) {
				throw new RuntimeException("保存详情图错误");
			}
		}
	}

	// 产品缩略图
	private void saveProductImg(Product product, CommonsMultipartFile productImg) {
		// get shop path
		String dest = PathUtil.getShopImgPath(product.getProductId());
		String productImgAddr = ImageUtil.generateThumbnail(productImg, dest);
		product.setImgAddr(productImgAddr);
	}

	@Override
	public Product getOne(long id) {
		return productDao.getProduct(id);
	}

	@Override
	public Execution<Product> listProduct(Product productCondition, PageBean pageBean) {
		List<Product> products = productDao.listProduct(productCondition, pageBean);
		int count = productDao.countProduct(productCondition);
		Execution<Product> pe = new Execution<>();
		if (products != null) {
			pe.setList(products);
			pe.setCount(count);
		} else {
			pe.setState(StateEnum.INNER_ERROR.getState());
		}
		return pe;
	}

	@Override
	public Execution<Product> updateProduct(Product product, CommonsMultipartFile productImg,
			List<CommonsMultipartFile> productImgs) {
		if (product == null || product.getShop() == null || product.getShop().getShopId() == -1) {
			return new Execution<>(StateEnum.NULL);
		}
		if (productImg != null) {
			Product temp = productDao.getProduct(product.getProductId());
			if (temp != null) {
				FileUtils.deleteFileOrPath(temp.getImgAddr());
			}
			saveProductImg(product, productImg);
		}
		if (productImgs != null && productImgs.size() > 0) {
			deleteProductImgList(product.getProductId());
			saveProductImgs(product, productImgs);
		}
		product.setLastEditTime(new Date());
		int effectedNum = productDao.updateProduct(product);
		if (effectedNum <= 0) {
			return new Execution<>(StateEnum.INNER_ERROR);
		} /*
			 * else { product=productDao.getProduct(product.getProductId()); }
			 */
		return new Execution<>(StateEnum.CHECK, product);
	}

	private void deleteProductImgList(long productId) {
		List<ProductImg> productImgs = productDao.listProductImgs(productId);
		productImgs.forEach(x -> FileUtils.deleteFileOrPath(x.getImgAddr()));
		productDao.deleteProductImgs(productId);
	}
}
