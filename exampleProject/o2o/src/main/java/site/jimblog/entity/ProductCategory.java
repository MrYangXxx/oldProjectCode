package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: ProductCategory</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class ProductCategory {
	private Long productCategoryId;
	private Long shopId;
	private String productCategoryName;
	private String productCategoryDesc;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
}
