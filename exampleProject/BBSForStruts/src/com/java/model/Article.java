package com.java.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Article {

	private int id;
	private int pId;
	private int rootId;
	private String title;
	private String cont;
	private Date pDate;
	private boolean isLeaf;
	private int grade;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getRootId() {
		return rootId;
	}
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public Date getpDate() {
		return pDate;
	}
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public void initFromRs(ResultSet rs){
		try {
			setId(rs.getInt("id"));
			setpId(rs.getInt("pId"));
			setRootId(rs.getInt("rootId"));
			setTitle(rs.getString("title"));
			setLeaf(rs.getInt("isLeaf")==0?true:false);
			setpDate(rs.getTimestamp("pDate"));
			setCont(rs.getString("cont"));
			setGrade(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
