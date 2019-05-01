package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: ShopCategory</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class ShopCategory {
	private Long shopCategoryId;
	private String shopCategoryName;
	private String shopCategoryDesc;
	private String shopCategoryImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Long parentId;
}
