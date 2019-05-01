package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.ProductSmallType;

/**
 * <p>
 * Title: ProductSmallTypeService
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Jun 3, 2018
 * 
 */
public interface ProductSmallTypeService {
	List<ProductSmallType> findProductSmallTypeList(ProductSmallType productSmallType,PageBean pageBean);

	boolean existSmallTypeWithBigTypeId(int id);

	Long getProductSmallTypeCount(ProductSmallType productSmallType);

	void saveProductSmallType(ProductSmallType productSmallType);

	void deleteProductSmallType(ProductSmallType productSmallType);

	ProductSmallType getProductSmallTypeById(int id);
}
