package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: HeadLine</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class HeadLine {
	private Long lineId;
	private String lineName;
	private String lineLink;
	private String lineImg;
	private Integer priority;
	private Integer enableStatus; // 1.ban 2.pass
	private Date createTime;
	private Date lastEditTime;
}
