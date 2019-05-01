package site.jimblog.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;

import com.mysql.jdbc.StringUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import site.jimblog.model.Paper;
import site.jimblog.model.Question;
import site.jimblog.service.PaperService;
import site.jimblog.service.QuestionService;
import site.jimblog.util.ResponseUtil;

/**
 * <p>Title: PaperAction</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Mar 8, 2018  
 * 
 */
@AllowedMethods(value={"list","getDetailPaper","paperList","deletePaper","preSave","savePaper"})
@Action(results={@Result(name="success",location="/main.jsp"),@Result(name = "save", type = "redirectAction", location = "paper!paperList")})
public class PaperAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Resource
	private PaperService paperService;
	@Resource
	private QuestionService questionService;
	private String mainPage;
	private List<Paper> paperList;
	private String paperId;
	private Paper paper;
	private List<Question> squestionList=new ArrayList<>();
	private List<Question> mquestionList=new ArrayList<>();
	private String title;
	
	public String getTitle() {
		return title;
	}
	public List<Question> getSquestionList() {
		return squestionList;
	}
	public List<Question> getMquestionList() {
		return mquestionList;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	public String getMainPage() {
		return mainPage;
	}
	public List<Paper> getPaperList() {
		return paperList;
	}
	public String list(){
		paperList=paperService.getPapers();
		mainPage="exam/selectPaper.jsp";
		return SUCCESS;
	}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	
	
	public String getDetailPaper(){
		paper = paperService.getPaper(paperId);
		Set<Question> questionsList = paper.getQuestions();
		Iterator<Question> iterator = questionsList.iterator();
		while(iterator.hasNext()){
			Question q = iterator.next();
			if(Objects.equals("1", q.getType())){
				squestionList.add(q);
			}else{
				mquestionList.add(q);
			}
		}
		squestionList=this.getRandomQuestion(squestionList, 3);
		mquestionList=this.getRandomQuestion(mquestionList, 2);
		mainPage="exam/paper.jsp";
		return SUCCESS;
	}
	
	private List<Question> getRandomQuestion(List<Question> question,int num){
		List<Question> resultList=new ArrayList<>();
		Random random=new Random();
		if(num>0){
			for(int i=1;i<=num;i++){
				int n=random.nextInt(question.size());
				Question q = question.get(n);
				if(resultList.contains(q)){
					i--;
				}else{
					resultList.add(q);
				}
			}
		}
		return resultList;
	}
	
	public String paperList(){
		paperList=paperService.getPapers();
		mainPage="paper/paperList.jsp";
		return SUCCESS;
	}
	
	public void deletePaper(){
		paper=paperService.getPaper(paperId);
		JSONObject resultJson=new JSONObject();
		if(questionService.existQuestionByPaperId(paperId)){
			resultJson.put("error", "试卷下有题目，不能删除");
		}else{
			paperService.deletePaper(paper);
			resultJson.put("success", true);
		}
		try {
			ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String preSave() {
		ServletActionContext.getRequest().getSession().removeAttribute("paper");;
		if (StringUtils.isNullOrEmpty(paperId)) {
			title = "添加试卷信息";
		}else{
			paper=paperService.getPaper(paperId);
			ServletActionContext.getRequest().getSession().setAttribute("paper", paper);
			title="修改试卷信息";
		}
		mainPage = "paper/paperSave.jsp";
		return SUCCESS;
	}

	public String savePaper() {
		if (StringUtils.isNullOrEmpty(paperId)) {
				paper.setJoinDate(new Date());
		}else{
			paper.setId(Integer.parseInt(paperId));
		}
		paperService.savePaper(paper);
		return "save";
	}
}
