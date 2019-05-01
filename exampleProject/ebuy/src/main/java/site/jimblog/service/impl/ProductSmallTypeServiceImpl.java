package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.ProductSmallType;
import site.jimblog.service.ProductSmallTypeService;

/**
 * <p>Title: ProductSmallTypeServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 3, 2018  
 * 
 */
@Service("productSmallTypeService")
public class ProductSmallTypeServiceImpl implements ProductSmallTypeService {

	@Resource
	private BaseDao<ProductSmallType> baseDao;
	
	@Override
	public List<ProductSmallType> findProductSmallTypeList(ProductSmallType productSmallType,PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from ProductSmallType");
		if(productSmallType!=null){
			if(productSmallType.getName()!=null){
				hql.append(" and name like ?");
				param.add("%"+productSmallType.getName()+"%");
			}
			if(productSmallType.getBigType()!=null&&productSmallType.getBigType().getId()!=0){
				hql.append(" and bigType.id=?");
				param.add(productSmallType.getBigType().getId());
			}
		}
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}else{
			return baseDao.find(hql.toString().replaceFirst("and", "where"),param);
		}
	}

	@Override
	public boolean existSmallTypeWithBigTypeId(int id) {
		String hql="from ProductSmallType where bigType.id=?";
		if(baseDao.find(hql, new Object[]{id}).size()>0){
			return true;
		}
		return false;
	}

	@Override
	public Long getProductSmallTypeCount(ProductSmallType productSmallType) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from ProductSmallType");
		if(productSmallType!=null){
			if(productSmallType.getName()!=null){
				hql.append(" and name like ?");
				param.add("%"+productSmallType.getName()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"),param);
	}

	@Override
	public void saveProductSmallType(ProductSmallType productSmallType) {
		baseDao.merge(productSmallType);
	}

	@Override
	public void deleteProductSmallType(ProductSmallType productSmallType) {
		baseDao.delete(productSmallType);
	}

	@Override
	public ProductSmallType getProductSmallTypeById(int id) {
		return baseDao.get(ProductSmallType.class, id);
	}

}
