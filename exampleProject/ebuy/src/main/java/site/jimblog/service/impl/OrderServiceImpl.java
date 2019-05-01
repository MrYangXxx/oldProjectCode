package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.Order;
import site.jimblog.entity.PageBean;
import site.jimblog.service.OrderService;

/**
 * <p>Title: OrderServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 25, 2018  
 * 
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
	@Resource
	private BaseDao<Order> baseDao;

	@Override
	public void saveOrder(Order order) {
		baseDao.save(order);
	}

	@Override
	public List<Order> findOrderList(Order order, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from Order");
		if(order!=null){
			if(order.getUser()!=null&&order.getUser().getId()!=0){
				hql.append(" and user.id=?");
				param.add(order.getUser().getId());
			}
			if(order.getUser()!=null&&order.getUser().getUserName()!=null){
				hql.append(" and user.userName like ?");
				param.add("%"+order.getUser().getUserName()+"%");
			}
			if(order.getOrderNo()!=null){
				hql.append(" and orderNo like ?");
				param.add("%"+order.getOrderNo()+"%");
			}
		}
		
		hql.append(" order by createTime desc");if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}
		return baseDao.find(hql.toString().replaceFirst("and", "where"),param);
	}

	@Override
	public void updateOrderStatus(int status, String orderNo) {
		List<Object> param=new LinkedList<>();
		String hql="update Order set status=? where orderNo=?";
		param.add(status);
		param.add(orderNo);
		baseDao.executeHql(hql,param);
	}

	@Override
	public Long getOrderCount(Order order) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from Order");
		if(order!=null){
			if(order.getUser()!=null&&order.getUser().getId()!=0){
				hql.append(" and user.id=?");
				param.add(order.getUser().getId());
			}
			if(order.getUser()!=null&&order.getUser().getUserName()!=null){
				hql.append(" and user.userName like ?");
				param.add("%"+order.getUser().getUserName()+"%");
			}
			if(order.getOrderNo()!=null){
				hql.append(" and orderNo like ?");
				param.add("%"+order.getOrderNo()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public Order getOrderById(int id) {
		return baseDao.get(Order.class, id);
	}

}
