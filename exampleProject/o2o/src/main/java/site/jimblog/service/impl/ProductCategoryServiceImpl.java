package site.jimblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.ProductCategoryDao;
import site.jimblog.dto.ProductCategoryExecution;
import site.jimblog.entity.ProductCategory;
import site.jimblog.enums.ProductCategoryStateEnum;
import site.jimblog.service.ProductCategoryService;

/**
 * <p>Title: ProductCategoryServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Aug 26, 2018  
 * 
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService{
	@Resource
	private ProductCategoryDao productCategoryDao;

	@Override
	public List<ProductCategory> listProductCategory(Long shopId) {
		return productCategoryDao.listProductCategory(shopId);
	}

	@Override
	public ProductCategoryExecution saveProductCategorys(List<ProductCategory> categories) {
		if(categories!=null&&categories.size()>0){
			int effectNum = productCategoryDao.saveProductCategorys(categories);
			if(effectNum>0){
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}else{
				return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
			}
		}else{
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	public ProductCategoryExecution deleteProductCategory(long productCategoryId) {
		int effectNum = productCategoryDao.deleteProductCategory(productCategoryId);
		if(effectNum>0){
			return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
		}
		return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
	}
	
}
