package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * Title: Area
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Jun 8, 2018
 * 
 */
@Data
public class Area {
	private Integer areaId;
	private String areaName;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
}
