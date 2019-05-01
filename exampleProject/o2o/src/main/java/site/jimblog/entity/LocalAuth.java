package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: LocalAuth</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class LocalAuth {
	private Long localAuthId;
	private String username;
	private String password;
	private Date createTime;
	private Date lastEditTime;
	private Person person;
}
