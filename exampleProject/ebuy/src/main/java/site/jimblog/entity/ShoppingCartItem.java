package site.jimblog.entity;

/**
 * <p>Title: ShoppingCartItem</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 13, 2018  
 * 
 */
public class ShoppingCartItem {
	private int id;
	private Product product;
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
