package com.java.model;

public class PageBean {

	final int PAGE_SIZE = 8;
	private int page; // µÚ¼¸Ò³
	int totalPages;
	int totalRecords;
	
	
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getPage() {
			return page;
	}
	
	public void setPage(int page) {
		if(this.page>this.totalPages){
			this.page = totalPages;
		}else{
			this.page=page;
		}
	}

	public int getStart() {
		if(page==0){
			return 1;
		}
		return (page-1)*PAGE_SIZE;
	}
	public int getTotalPages() {
		return (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
	}
	public void setTotalPages(int totalRecords) {
		this.totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
	}
	public int getPAGE_SIZE() {
		return PAGE_SIZE;
	}
	
}
