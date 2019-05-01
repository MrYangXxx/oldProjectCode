package site.jimblog.model;

/**
 * 
 * <p>Title: PageBean</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Mar 14, 2018  
 *
 */

public class PageBean {

	private int page; 
	private int pageSize; 
	private int start;  
	
	
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (page-1)*pageSize;
	}
	
	
}
