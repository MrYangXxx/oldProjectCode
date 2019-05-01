package site.jimblog.dto;

import java.util.List;

import lombok.Data;
import site.jimblog.entity.Shop;
import site.jimblog.enums.ShopStateEnum;

/**
 * <p>Title: ShopExecution</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 14, 2018  
 * 
 */
@Data
public class ShopExecution {
	private int state; //shop state
	private String stateInfo;
	private int count; //shop count
	private Shop shop;
	private List<Shop> shopList;
	
	
	// manipulate shop error
	public ShopExecution(ShopStateEnum stateEnum){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	// success
	public ShopExecution(ShopStateEnum stateEnum,Shop shop){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shop=shop;
	}
	
	// success
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shopList=shopList;
	}

	public ShopExecution() {
		super();
	}
}
