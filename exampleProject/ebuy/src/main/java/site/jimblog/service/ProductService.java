package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;

/**
 * <p>Title: NoticeService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
public interface ProductService {
	List<Product> findProductList(Product product,PageBean pageBean);
	
	Long getProductCount(Product product);
	
	Product getProductById(int productId);
	
	void saveProduct(Product product);
	
	void deleteProduct(Product product);
	
	void setProductWithHot(int productId);
	
	void setProductWithSpecialPrice(int productId);
	
	boolean existProductWithSmallTypeId(int id);
}
