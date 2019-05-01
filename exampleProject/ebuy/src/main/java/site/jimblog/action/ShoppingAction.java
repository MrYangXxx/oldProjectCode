package site.jimblog.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import site.jimblog.entity.Product;
import site.jimblog.entity.ShoppingCart;
import site.jimblog.entity.ShoppingCartItem;
import site.jimblog.entity.User;
import site.jimblog.service.ProductService;
import site.jimblog.util.ResponseUtil;

/**
 * <p>
 * Title: ShoppingAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date May 13, 2018
 * 
 */
public class ShoppingAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductService productService;

	private int productId;
	private String mainPage;
	private String navCode;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMainPage() {
		return mainPage;
	}

	public String getNavCode() {
		return navCode;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	private void shoppingCart() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Product product = productService.getProductById(productId);

		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			User currentUser = (User) session.getAttribute("currentUser");
			shoppingCart.setUserId(currentUser.getId());
		}
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();

		boolean flag = true;
		for (ShoppingCartItem sci : shoppingCartItemList) {
			if (sci.getProduct().getId() == product.getId()) {
				sci.setCount(sci.getCount() + 1);
				flag = false;
			}
		}

		if (flag) {
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}

		session.setAttribute("shoppingCart", shoppingCart);
	}

	public String addShoppingCartItem() throws Exception {
		this.shoppingCart();

		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);

		return null;
	}

	public String list() {
		mainPage = "shopping/shopping.jsp";
		navCode = "购物车";
		return SUCCESS;
	}

	public String buy() {
		this.shoppingCart();

		mainPage = "shopping/shopping.jsp";
		navCode = "购物车";
		return SUCCESS;
	}

	public String updateShoppingCartItem() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Product product = productService.getProductById(productId);
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();
		for (ShoppingCartItem sci : shoppingCartItemList) {
			if (sci.getProduct().getId() == product.getId()) {
				sci.setCount(count);
				break;
			}
		}
		session.setAttribute("shoppingCart", shoppingCart);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		
		return null;
	}
	
	public String removeShoppingCartItem(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();
		for (int i=0;i<shoppingCartItemList.size();i++) {
			if (shoppingCartItemList.get(i).getProduct().getId()==productId) {
				shoppingCartItemList.remove(i);
				break;
			}
		}
		shoppingCart.setShoppingCartItems(shoppingCartItemList);
		session.setAttribute("shoppingCart", shoppingCart);
		return "list";
	}

}
