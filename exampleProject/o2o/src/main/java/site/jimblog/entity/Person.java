package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: Person</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class Person {
	private Long userId;
	private String name;
	private String profileImg;
	private String email;
	private String gender;
	private Integer enableStatus; // 0.ban 1.pass
	private Integer userType; // 1.customer 2.businessman 3.admin
	private Date createTime;
	private Date lastEditTime;
}
