package site.jimblog.entity;

import java.util.Date;

import lombok.Data;

/**
 * <p>Title: WeChatAuth</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Data
public class WeChatAuth {
	private Long wechatAuthId;
	private String openId;
	private Date createTime;
	private Person person;
}
