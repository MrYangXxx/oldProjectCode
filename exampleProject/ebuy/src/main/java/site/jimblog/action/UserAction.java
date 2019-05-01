package site.jimblog.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.User;
import site.jimblog.service.UserService;
import site.jimblog.util.DateJsonValueProcessor;
import site.jimblog.util.NavUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: UserAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 6, 2018  
 * 
 */
public class UserAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	@Resource
	private UserService userService;
	
	private String userName;
	private User user;
	private String error;
	private String imageCode;
	private String mainPage;
	private String navCode;
	
	private String page;
	private String rows;
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getMainPage() {
		return mainPage;
	}
	public String getNavCode() {
		return navCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public String getError() {
		return error;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void existUserWithUserName() throws IOException{
		boolean exist = userService.existUserWithUserName(userName);
		JSONObject json=new JSONObject();
		if(exist){
			json.put("exist", true);
		}else{
			json.put("exist", false);
		}
		String result=json.toString();
		ServletActionContext.getResponse().getWriter().write(result);
	}
	
	public String register(){
		userService.saveUser(user);
		return "register";
	}
	
	public String login(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(imageCode.equals(session.getAttribute("sRand"))){
			User currentUser = userService.login(user);
			if(currentUser!=null){
				session.setAttribute("currentUser", currentUser);
				if(user.getStatus()==2){
					return "adminLogin";
				}
				return "login";
			}else{
				error="用户名或密码错误";
				if(user.getStatus()==2){
					return "adminError";
				}
				return ERROR;
			}
		}else{
			error="验证码错误";
			if(user.getStatus()==2){
				return "adminError";
			}
			return ERROR;
		}
	}
	
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "logout";
	}
	public String logout2(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "logout2";
	}
	
	public String userCenter(){
		mainPage = "userCenter/ucDefault.jsp";
		navCode=NavUtil.genNavCode("个人中心");
		return "userCenter";
	}
	
	public String getUserInfo(){
		mainPage = "userCenter/userInfo.jsp";
		navCode=NavUtil.genNavCode("个人中心");
		return "userCenter";
	}
	
	public String preSave(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		user=(User) session.getAttribute("currentUser");
		mainPage = "userCenter/userSave.jsp";
		navCode=NavUtil.genNavCode("个人中心");
		return "userCenter";
	}
	
	//register
	public String save(){
		userService.saveUser(user);
		mainPage = "userCenter/userInfo.jsp";
		navCode=NavUtil.genNavCode("个人中心");
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("currentUser", user);
		return "userCenter";
	}
	
	public void list() throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<User> userList = userService.findUserList(user, pageBean);
		Long count = userService.getUserCount(user);
		
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"orderList"});
		config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(userList,config);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", count);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void deleteUser() throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for (String id : idsStr) {
			User u = userService.getUserById(Integer.parseInt(id));
			userService.deleteUser(u);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	//backstage add
	public void saveUser() throws Exception{
		userService.saveUser(user);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
	public void modifyPassword() throws Exception{
		User userById = userService.getUserById(user.getId());
		userById.setPassword(user.getPassword());
		userService.saveUser(userById);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
	}
	
}
