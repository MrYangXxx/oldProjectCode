package site.jimblog.dto;

import java.util.List;

import lombok.Data;
import site.jimblog.enums.StateEnum;

/**
 * <p>
 * Title: Execution
 * </p>
 * <p>
 * Description: 通用dto
 * </p>
 * 
 * @author Jim
 * @date Sep 14, 2018
 * 
 */
@Data
public class Execution<T> {
	private int state;
	private String stateInfo;
	private int count;
	private T t;
	private List<T> list;

	// manipulate shop error
	public Execution(StateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// success
	public Execution(StateEnum stateEnum, T t){
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
			this.t=t;
		}

	// success
	public Execution(StateEnum stateEnum, List<T> list) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.list = list;
	}

	public Execution() {
		super();
	}
}
