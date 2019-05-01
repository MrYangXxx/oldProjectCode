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
import site.jimblog.model.Paper;
import site.jimblog.model.Question;
import site.jimblog.service.PaperService;
import site.jimblog.service.QuestionService;
import site.jimblog.util.PageUtil;
import site.jimblog.util.ResponseUtil;

/**
 * <p>
 * Title: QuestionAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Mar 21, 2018
 * 
 */
@AllowedMethods(value = { "list", "delete", "show", "preSave", "saveQuestion" })
@Action(results = { @Result(name = "success", location = "/main.jsp"),
		@Result(name = "save", type = "redirectAction", location = "question!list") })
public class QuestionAction extends ActionSupport implements ModelDriven<Question> {

	private static final long serialVersionUID = 1L;
	Question question = new Question();
	private String mainPage;
	private String page;
	private int total;
	private String pageCode;
	
	@Resource
	QuestionService questionService;
	@Resource
	PaperService paperService;
	
	private List<Question> questionList = new ArrayList<>();
	private List<Paper> paperList = new ArrayList<>();
	private String questionId;
	private String title;

	public List<Paper> getPaperList() {
		return paperList;
	}

	public String getTitle() {
		return title;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getMainPage() {
		return mainPage;
	}

	public int getTotal() {
		return total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public Question getModel() {
		return question;
	}

	public String list() {
		if (StringUtils.isNullOrEmpty(page)) {
			page = "1";
			ServletActionContext.getRequest().getSession().setAttribute("question", question);
		}
		if (!StringUtils.isNullOrEmpty(page)) {
			Object o = ServletActionContext.getRequest().getSession().getAttribute("question");
			if (o != null) {
				question = (Question) o;
			}
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 5);
		questionList = questionService.getQuestions(question, pageBean);
		mainPage = "question/questionList.jsp";
		total = questionService.questionCount(question);
		pageCode = PageUtil.getPagination(ServletActionContext.getRequest().getContextPath() + "/question!list", total,
				Integer.parseInt(page), 5);
		return SUCCESS;
	}

	public String show() {
		question = questionService.getQuestion(questionId);
		ServletActionContext.getRequest().setAttribute("question", question);
		mainPage = "question/questionShow.jsp";
		return SUCCESS;
	}

	public void delete() {
		question = questionService.getQuestion(questionId);
		questionService.deleteQuestion(question);
		JSONObject resultJson = new JSONObject();
		resultJson.put("success", true);
		try {
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String preSave() {
		paperList = paperService.getPapers();
		if (StringUtils.isNullOrEmpty(questionId)) {
			title = "添加试卷信息";
		} else {
			question = questionService.getQuestion(questionId);
			ServletActionContext.getRequest().setAttribute("question", question);
			title = "修改试卷信息";
		}
		mainPage = "question/questionSave.jsp";
		return SUCCESS;
	}

	public String saveQuestion() {
		if (!StringUtils.isNullOrEmpty(questionId)) {
			question.setId(Integer.parseInt(questionId));
		}
		questionService.saveQuestion(question);
		return "save";
	}

}
