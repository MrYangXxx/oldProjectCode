package site.jimblog.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import site.jimblog.model.Manager;
import site.jimblog.service.ManagerService;

/**
 * <p>Title: ManagerAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Mar 14, 2018  
 * 
 */
@AllowedMethods(value={"login","logout"})
@Action(results={@Result(name="success",location="/main.jsp"),@Result(name="error",location="/login.jsp"),@Result(name="logout",type="redirect",location="/login.jsp")})
public class ManagerAction extends ActionSupport implements ModelDriven<Manager>{

	private static final long serialVersionUID = 1L;
	private Manager manager=new Manager();
	@Resource
	private ManagerService managerService;
	private String error;
	
	private String mainPage;
	
	public String getMainPage() {
		return mainPage;
	}
	
	public String getError() {
		return error;
	}
	
	@Action(results={@Result(name="success",type="redirect",location="/main.jsp"),@Result(name="error",location="/login2.jsp")})
	public String login(){
		Manager currentUser = managerService.login(manager);
		if(currentUser==null){
			error="用户名或密码错误";
			return ERROR;
		}else{
			ServletActionContext.getRequest().getSession().setAttribute("currentUser", currentUser);
			mainPage="common/default.jsp";
			return SUCCESS;
		}
	}

	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "logout";
	}
	
	@Override
	public Manager getModel() {
		// TODO Auto-generated method stub
		return manager;
	}

}
