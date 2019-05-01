/**
 * 
 */
package site.jimblog.entity;

/**
 * <p>Title: BlogType</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 * 
 */
public class BlogType {
	private Integer id;  
	private String typeName; 
	private Integer blogCount; 
	private Integer orderNo; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}
