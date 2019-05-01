package site.jimblog.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * <p>Title: Product</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class Product {
	private Long productId;
	private String productName;
	private String productDesc;
	private String imgAddr; //small img
	private String normalPrice;
	private String promotionPrice;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus; // 0.withdraw 1.normal
	private Integer point;

	private List<ProductImg> productImgList; //detail img
	private ProductCategory productCategory;
	private Shop shop;


}
