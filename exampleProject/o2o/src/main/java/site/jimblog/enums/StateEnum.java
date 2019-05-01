package site.jimblog.enums;

/**
 * <p>Title: StateEnum</p>  
 * <p>Description: 通用状态枚举</p>  
 * @author Jim
 * @date Sep 15, 2018  
 * 
 */
public enum StateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "连接断开"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(-1001,
			"操作失败"), NULL(-1002, "含有空值");
	private int state;
	private String stateInfo;

	private StateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static StateEnum stateOf(int state) {
		for (StateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
}
