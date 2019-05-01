package site.jimblog.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ShoppingCart</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 13, 2018  
 * 
 */
public class ShoppingCart {
	private int userId;
	private List<ShoppingCartItem> shoppingCartItems=new ArrayList<>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<ShoppingCartItem> getShoppingCartItems() {
		return shoppingCartItems;
	}
	public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}
	
}
