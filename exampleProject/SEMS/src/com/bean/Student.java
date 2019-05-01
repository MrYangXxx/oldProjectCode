package com.bean;

public class Student {
	private int id;
	private int sid;
	private String name;
	private String gender;
	private int age;
	private String state;
	private String degree;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(int id, int sid, String name, String gender, int age,
			String state, String degree) {
		super();
		this.id = id;
		this.sid = sid;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.state = state;
		this.degree = degree;
	}
	public Student(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	
}
