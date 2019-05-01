package site.jimblog.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;

import com.mysql.jdbc.StringUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import site.jimblog.model.PageBean;
import site.jimblog.model.Student;
import site.jimblog.service.StudentService;
import site.jimblog.util.DateUtil;
import site.jimblog.util.PageUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>
 * Title: StudentAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Mar 7, 2018
 * 
 */
@AllowedMethods(value = { "login", "preUpdatePassword", "updatePassword", "logout", "list", "preSave", "saveStudent","deleteStudent" })
@Action(results = { @Result(name = "success", location = "/main.jsp"), @Result(name = "error", location = "/login.jsp"),
		@Result(name = "logout", type = "redirect", location = "/login.jsp"),
		@Result(name = "save", type = "redirectAction", location = "student!list") })
public class StudentAction extends ActionSupport implements ModelDriven<Student> {

	private static final long serialVersionUID = 1L;

	@Resource
	private StudentService studentService;
	private Student student = new Student();
	private String error;
	private String mainPage;
	private List<Student> studentList = new ArrayList<>();
	private String page;
	private int total;
	private String pageCode;
	private String title;
	private String hidden_id;
	
	public void setHidden_id(String hidden_id) {
		this.hidden_id = hidden_id;
	}

	public String getTitle() {
		return title;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public String getMainPage() {
		return mainPage;
	}

	public String getError() {
		return error;
	}

	@Override
	public Student getModel() {
		return student;
	}

	public int getTotal() {
		return total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String login() {
		Student currentUser = studentService.login(student);
		if (currentUser == null) {
			error = "准考证号或密码错误";
			return ERROR;
		} else {
			ServletActionContext.getRequest().getSession().setAttribute("currentUser", currentUser);
			mainPage = "common/default.jsp";
			return SUCCESS;
		}
	}

	public String preUpdatePassword() {
		mainPage = "student/updatePassword.jsp";
		return SUCCESS;
	}

	public String updatePassword() {
		Student s = studentService.getStudentById(student.getId());
		s.setPassword(student.getPassword());
		studentService.saveStudent(s);
		mainPage = "student/updateSuccess.jsp";
		return SUCCESS;
	}

	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "logout";
	}

	public String list() {
		if (StringUtils.isNullOrEmpty(page)) {
			page = "1";
			ServletActionContext.getRequest().getSession().setAttribute("student", student);
		}
		if (!StringUtils.isNullOrEmpty(page)) {
			Object o = ServletActionContext.getRequest().getSession().getAttribute("student");
			if (o != null) {
				student = (Student) o;
			}
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 5);
		studentList = studentService.getStudents(student, pageBean);
		mainPage = "student/studentList.jsp";
		total = studentService.getStudentCount(student);
		pageCode = PageUtil.getPagination(ServletActionContext.getRequest().getContextPath() + "/student!list", total,
				Integer.parseInt(page), 5);
		return SUCCESS;
	}

	public String preSave() {
		ServletActionContext.getRequest().getSession().removeAttribute("student");
		if (StringUtils.isNullOrEmpty(hidden_id)) {
			title = "添加学生信息";
		}else{
			student=studentService.getStudentById(hidden_id);
			ServletActionContext.getRequest().setAttribute("student", student);
			title="修改学生信息";
		}
		mainPage = "student/studentSave.jsp";
		return SUCCESS;
	}

	public String saveStudent() {
		if (StringUtils.isNullOrEmpty(student.getId())) {
			try {
				student.setId("JS" + DateUtil.getCurrentDateStr());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		studentService.saveStudent(student);
		return "save";
	}
	
	public void deleteStudent(){
		student=studentService.getStudentById(hidden_id);
		studentService.deleteStudent(student);
		JSONObject resultJson=new JSONObject();
		resultJson.put("success", true);
		try {
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
