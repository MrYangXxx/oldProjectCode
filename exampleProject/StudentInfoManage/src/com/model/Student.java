package com.model;

import java.util.Date;

public class Student {
	private int stuId;
	private String stuNo;
	private String stuName;
	private String gender;
	private Date birthday;
	private int gradeId=-1;
	private String email;
	private String stuDesc;
	
	private String gradeName;
	
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int id) {
		this.stuId = id;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStuDesc() {
		return stuDesc;
	}
	public void setStuDesc(String stuDesc) {
		this.stuDesc = stuDesc;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String stuNo, String stuName, String gender, Date birthday, int gradeId, String email,
			String stuDesc) {
		super();
		this.stuNo = stuNo;
		this.stuName = stuName;
		this.gender = gender;
		this.birthday = birthday;
		this.gradeId = gradeId;
		this.email = email;
		this.stuDesc = stuDesc;
	}
	
	
}
