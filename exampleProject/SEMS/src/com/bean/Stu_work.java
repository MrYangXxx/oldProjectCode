package com.bean;

public class Stu_work {
	private int id;
	private String name;
	private String company;
	private String position;
	private String salary;
	private String other;
	
	public Stu_work() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Stu_work(int id, String name, String company, String position,
			String salary, String other) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.position = position;
		this.salary = salary;
		this.other = other;
	}
	public Stu_work(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
}
