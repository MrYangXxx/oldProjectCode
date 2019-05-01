package site.jimblog.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;

import com.mysql.jdbc.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

import site.jimblog.model.Exam;
import site.jimblog.model.PageBean;
import site.jimblog.model.Question;
import site.jimblog.service.ExamService;
import site.jimblog.service.QuestionService;
import site.jimblog.util.PageUtil;

/**
 * <p>Title: ExamAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Mar 8, 2018  
 * 
 */
@AllowedMethods(value={"add","getExams","list"})
@Action(results={@Result(name="success",location="/main.jsp")})
public class ExamAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private ExamService examService;
	@Resource
	private QuestionService questionService;
	private Exam exam;
	private String mainPage;
	private List<Exam> examList;
	private String page;
	private int total;
	private String pageCode;
	
	public int getTotal() {
		return total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<Exam> getExamList() {
		return examList;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	public Exam getExam() {
		return exam;
	}

	public String getMainPage() {
		return mainPage;
	}
	
	public String add(){
		Map<String, String[]> keyMap = ServletActionContext.getRequest().getParameterMap();
		Iterator<Entry<String, String[]>> iterator = keyMap.entrySet().iterator();
		int totalScore=0;
		int singleScore=0;
		int moreScore=0;
		while(iterator.hasNext()){
			Entry<String, String[]> entry = iterator.next();
			String keyStr = entry.getKey();
			String[] values = entry.getValue();
			String key;
			String value = "";
			if(Objects.equals("exam.student.id", keyStr)||Objects.equals("exam.paper.id", keyStr)){
				continue;
			}
			if(keyStr.split("-")[1].equals("r")){ //radio
				key=keyStr.split("-")[2];
				value=values[0];
				singleScore+=this.calScore(key, value, "1");
			}else{ //checkbox
				key=keyStr.split("-")[2];
				for (String s : values) {
					value+=s+",";
				}
				value=value.substring(0, value.length()-1);
				moreScore+=this.calScore(key, value, "2");
			}
		}
		totalScore=singleScore+moreScore;
		exam.setSingleScore(singleScore);
		exam.setMoreScore(moreScore);
		exam.setScore(totalScore);
		exam.setExamDate(new Date());
		examService.saveExam(exam);
		mainPage="exam/examResult.jsp";
		return SUCCESS;
	}
	
	private int calScore(String questionId,String userAnswer,String type){
		Question question = questionService.getQuestion(questionId);
		if(userAnswer.equals(question.getAnswer())){
			if("1".equals(type)){
				return 20;
			}else{
				return 30;
			}
		}else{
			return 0;
		}
	}
	
	public String list(){
		if (StringUtils.isNullOrEmpty(page)) {
			page = "1";
			ServletActionContext.getRequest().getSession().setAttribute("exam", exam);
		}
		if (!StringUtils.isNullOrEmpty(page)) {
			Object o = ServletActionContext.getRequest().getSession().getAttribute("exam");
			if (o != null) {
				exam = (Exam) o;
			}
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 5);
		examList = examService.getExams(exam,pageBean);
		mainPage="exam/examList.jsp";
		total = examService.getExamCount(exam);
		pageCode = PageUtil.getPagination(ServletActionContext.getRequest().getContextPath() + "/exam!list", total,
				Integer.parseInt(page), 5);
		return SUCCESS;
	}
	
	public String getExams()throws Exception{
		examList=examService.getExams(exam,null);
		mainPage="exam/myExam.jsp";
		return SUCCESS;
	}
}
