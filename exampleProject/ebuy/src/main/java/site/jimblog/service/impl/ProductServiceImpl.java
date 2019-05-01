package site.jimblog.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mchange.v2.lang.StringUtils;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.service.ProductService;

/**
 * <p>Title: ProductServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	@Resource
	private BaseDao<Product> baseDao;

	@Override
	public List<Product> findProductList(Product product, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from Product");
		if(product!=null){
			if(product.getBigType()!=null){
				hql.append(" and bigType.id=?");
				param.add(product.getBigType().getId());
			}
			if(product.getSmallType()!=null){
				hql.append(" and smallType.id=?");
				param.add(product.getSmallType().getId());
			}
			if(StringUtils.nonWhitespaceString(product.getName())){
				hql.append(" and name like ?");
				param.add("%"+product.getName()+"%");
			}
			if(product.getSpecialPrice()==1){
				hql.append(" and specialPrice=1 order by specialPriceTime desc");
			}
			if(product.getHot()==1){
				hql.append(" and hot=1 order by hotTime desc");
			}
		}
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}
		return null;
	}

	@Override
	public Long getProductCount(Product product) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from Product");
		if(product!=null){
			if(product.getBigType()!=null){
				hql.append(" and bigType.id=?");
				param.add(product.getBigType().getId());
			}
			if(product.getSmallType()!=null){
				hql.append(" and smallType.id=?");
				param.add(product.getSmallType().getId());
			}
			if(StringUtils.nonWhitespaceString(product.getName())){
				hql.append(" and name like ?");
				param.add("%"+product.getName()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public Product getProductById(int id) {
		return baseDao.get(Product.class, id);
	}

	@Override
	public void saveProduct(Product product) {
		baseDao.merge(product);
	}

	@Override
	public void deleteProduct(Product product) {
		baseDao.delete(product);
	}

	@Override
	public void setProductWithHot(int productId) {
		Product product = baseDao.get(Product.class, productId);
		product.setHot(1);
		product.setHotTime(new Date());
		baseDao.save(product);
	}

	@Override
	public void setProductWithSpecialPrice(int productId) {
		Product product = baseDao.get(Product.class, productId);
		product.setSpecialPrice(1);
		product.setSpecialPriceTime(new Date());
		baseDao.save(product);
	}

	@Override
	public boolean existProductWithSmallTypeId(int id) {
		String hql="from Product where smallType.id=?";
		if(baseDao.find(hql, new Object[]{id}).size()>0){
			return true;
		}
		return false;
	}

}
