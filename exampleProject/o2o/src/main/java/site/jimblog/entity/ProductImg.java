package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: ProductImg</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class ProductImg {
	private Long productImgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Long productId;
}
