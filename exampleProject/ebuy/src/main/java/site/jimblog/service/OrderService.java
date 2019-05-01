package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.Order;
import site.jimblog.entity.PageBean;

/**
 * <p>Title: OrderService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 25, 2018  
 * 
 */
public interface OrderService {
	void saveOrder(Order order);
	
	List<Order> findOrderList(Order order,PageBean pageBean);
	
	void updateOrderStatus(int status,String orderNo);
	
	Long getOrderCount(Order order);
	
	Order getOrderById(int id);
}
