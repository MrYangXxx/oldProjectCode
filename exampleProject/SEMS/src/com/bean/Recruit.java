package com.bean;

public class Recruit {
	private int id;
	private String company;
	private String position;
	private String degree;
	private String salary;
	private String deadline;
	private String other;
	/*private String operation;
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}*/
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public Recruit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Recruit(int id, String company, String position, String degree,
			String salary, String deadline) {
		super();
		this.id = id;
		this.company = company;
		this.position = position;
		this.degree = degree;
		this.salary = salary;
		this.deadline = deadline;
	}
	public Recruit(String company, String position, String degree,
			String deadline) {
		super();
		this.company = company;
		this.position = position;
		this.degree = degree;
		this.deadline = deadline;
	}
	public Recruit(String position, String company, String degree) {
		this.company = company;
		this.position = position;
		this.degree = degree;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	

}
