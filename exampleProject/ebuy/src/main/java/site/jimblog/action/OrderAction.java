package site.jimblog.action;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.Order;
import site.jimblog.entity.OrderProduct;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ShoppingCart;
import site.jimblog.entity.ShoppingCartItem;
import site.jimblog.entity.User;
import site.jimblog.service.OrderService;
import site.jimblog.util.DateJsonValueProcessor;
import site.jimblog.util.DateUtil;
import site.jimblog.util.NavUtil;
import site.jimblog.util.ObjectJsonValueProcessor;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: OrderAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 25, 2018  
 * 
 */
@Controller
public class OrderAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private OrderService orderService;
	
	private String mainPage;
	private String navCode;
	
	private Order order;
	private List<Order> orderList;
	
	private int status;
	private String orderNo;
	private String page;
	private String rows;
	private String id;
	private String orderNos;
	
	public void setOrderNos(String orderNos) {
		this.orderNos = orderNos;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getMainPage() {
		return mainPage;
	}

	public String getNavCode() {
		return navCode;
	}

	public String save() throws Exception{
		HttpSession session = ServletActionContext.getRequest().getSession();
		Order order=new Order();
		User currentUser = (User) session.getAttribute("currentUser");
		order.setUser(currentUser);
		order.setCreateTime(new Date());
		order.setOrderNo(DateUtil.getCurrentDateStr());
		order.setStatus(1);
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
		float cost=0;
		List<OrderProduct> orderProductList=new LinkedList<>();
		for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
			OrderProduct orderProduct=new OrderProduct();
			orderProduct.setNum(shoppingCartItem.getCount());
			orderProduct.setOrder(order);
			orderProduct.setProduct(shoppingCartItem.getProduct());
			orderProductList.add(orderProduct);
			cost+=shoppingCartItem.getProduct().getPrice()*shoppingCartItem.getCount();
		}
		order.setOrderProductList(orderProductList);
		order.setCost(cost);
		
		orderService.saveOrder(order);
		navCode=NavUtil.genNavCode("购物");
		mainPage="shopping/shopping-result.jsp";
		
		session.removeAttribute("shoppingCart");
		return SUCCESS;
	}
	
	public String findOrder(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("currentUser");
		if(order==null){
			order=new Order();
		}
		order.setUser(user);
		orderList=orderService.findOrderList(order, null);
		navCode=NavUtil.genNavCode("个人中心");
		mainPage="userCenter/orderList.jsp";
		return "orderList";
	}
	
	public void confirmReceive() throws Exception{
		orderService.updateOrderStatus(status, orderNo);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		if(order!=null&&orderNo!=null){
			order.setOrderNo(orderNo);
		}
		List<Order> findOrderList = orderService.findOrderList(order, pageBean);
		Long total = orderService.getOrderCount(order);
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"orderProductList"});
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		config.registerJsonValueProcessor(User.class,new ObjectJsonValueProcessor(new String[]{"id","userName"}, User.class));
		JSONArray rows = JSONArray.fromObject(findOrderList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void findProductListByOrderId() throws Exception{
		if(id!=null){
			Order orderById = orderService.getOrderById(Integer.parseInt(id));
			List<OrderProduct> orderProductList = orderById.getOrderProductList();
			JSONObject result=new JSONObject();
			JSONArray rows=new JSONArray();
			for (OrderProduct orderProduct : orderProductList) {
				Product product = orderProduct.getProduct();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("productName", product.getName());
				jsonObject.put("proPic", product.getProPic());
				jsonObject.put("price", product.getPrice());
				jsonObject.put("num", orderProduct.getNum());
				jsonObject.put("subtotal", product.getPrice()*orderProduct.getNum());
				rows.add(jsonObject);
			}
			result.put("rows", rows);
			result.put("total", rows.size());
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}
	}
	
	public void modifyOrderStatus() throws Exception{
		String[] orderNoStr = orderNos.split(",");
		for (String orderNo : orderNoStr) {
			orderService.updateOrderStatus(status, orderNo);
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
}
