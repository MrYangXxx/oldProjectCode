package com.java.action;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.struts2.ServletActionContext;

import com.java.db.DBUtilConn;
import com.java.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User> {
	QueryRunner qr = new QueryRunner(DBUtilConn.getDataSource());
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	@Override
	public String execute() throws Exception {
		User u = qr.query(
				"select * from user where username='" + user.getUsername() + "' and password='" + user.getPassword()+"'",
				new BeanHandler<User>(User.class));
		if (u != null) {
			ServletActionContext.getRequest().getSession().setAttribute("adminLogined", "true");
			return SUCCESS;
		}
		return ERROR;
	}
}