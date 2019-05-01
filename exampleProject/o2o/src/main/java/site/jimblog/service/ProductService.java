package site.jimblog.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import site.jimblog.dto.Execution;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;

/**
 * <p>Title: ProductService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Sep 15, 2018  
 * 
 */
public interface ProductService {
	// 参数：1.产品信息 2.产品图片 3.产品详情图
	Execution<Product> saveProduct(Product product,CommonsMultipartFile productImg,List<CommonsMultipartFile> productImgs);

	Execution<Product> updateProduct(Product product,CommonsMultipartFile productImg,List<CommonsMultipartFile> productImgs);
	
	Product getOne(long productId);
	
	Execution<Product> listProduct(Product productCondition,PageBean pageBean);
	
	
}
