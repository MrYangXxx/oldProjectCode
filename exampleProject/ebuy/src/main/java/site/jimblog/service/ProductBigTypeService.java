package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.ProductBigType;

/**
 * <p>Title: ProductBigTypeService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
public interface ProductBigTypeService {
	List<ProductBigType> findAllBigTypeList();
	
	List<ProductBigType> findProductBigTypeList(ProductBigType productBigType,PageBean pageBean);
	
	Long getProductBigTypeCount(ProductBigType productBigType);
	
	void saveProductBigType(ProductBigType productBigType);
	
	void deleteProductBigType(ProductBigType productBigType);
	
	ProductBigType getProductBigTypeById(int id);
}
