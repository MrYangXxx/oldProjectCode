package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: Shop</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class Shop {
	private Long shopId;
	private Long ownerId;
	private Long shopCategoryId;
	private String shopName;
	private String shopDesc;
	private String shopAddr;
	private String phone;
	private String shopImg;
	private Double longitude;
	private Double latitude;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus; // -1.ban 0.checking 1.pass
	private String advice;

//	private List<ShopAuthMap> staffList;
	private Area area;
	private ShopCategory shopCategory;
	private ShopCategory parentCategory;


}
