package com.java.model;

public class DiaryType {
	private int diaryTypeId;
	private String typeName;
	private String diaryCount;
	private String releaseDate;
	
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getDiaryTypeId() {
		return diaryTypeId;
	}
	public void setDiaryTypeId(int diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(String diaryCount) {
		this.diaryCount = diaryCount;
	}
	
	
}
