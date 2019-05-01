package site.jimblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductImg;

/**
 * <p>
 * Title: ProductDao
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Sep 14, 2018
 * 
 */
public interface ProductDao {
	int saveProduct(Product product);

	int updateProduct(Product product);
	
	int deleteProduct(long id);

	Product getProduct(long id);

	List<Product> listProduct(@Param("productCondition") Product productCondition, @Param("pageBean") PageBean pageBean);

	int countProduct(@Param("productCondition") Product productCondition);
	
	List<ProductImg> listProductImgs(long productId);
	
	int saveProductImgs(List<ProductImg> imgs);
	
	int deleteProductImgs(long productId);
}
