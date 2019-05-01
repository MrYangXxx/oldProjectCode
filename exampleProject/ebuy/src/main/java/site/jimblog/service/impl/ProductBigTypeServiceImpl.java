package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.ProductBigType;
import site.jimblog.service.ProductBigTypeService;

/**
 * <p>Title: ProductBigTypeServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
@Service("productBigTypeService")
public class ProductBigTypeServiceImpl implements ProductBigTypeService {
	
	@Resource
	private BaseDao<ProductBigType> baseDao;
	
	@Override
	public List<ProductBigType> findAllBigTypeList() {
		return baseDao.find("from ProductBigType");
	}

	@Override
	public List<ProductBigType> findProductBigTypeList(ProductBigType productBigType, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from ProductBigType");
		if(productBigType!=null){
			if(productBigType.getName()!=null){
				hql.append(" and name like ?");
				param.add("%"+productBigType.getName()+"%");
			}
		}
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}else{
			return baseDao.find(hql.toString().replaceFirst("and", "where"),param);
		}
	}

	@Override
	public Long getProductBigTypeCount(ProductBigType productBigType) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from ProductBigType");
		if(productBigType!=null){
			if(productBigType.getName()!=null){
				hql.append(" and name like ?");
				param.add("%"+productBigType.getName()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"),param);
	}

	@Override
	public void saveProductBigType(ProductBigType productBigType) {
		baseDao.merge(productBigType);
	}

	@Override
	public void deleteProductBigType(ProductBigType productBigType) {
		baseDao.delete(productBigType);
	}

	@Override
	public ProductBigType getProductBigTypeById(int id) {
		return baseDao.get(ProductBigType.class, id);
	}

}
