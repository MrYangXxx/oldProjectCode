package site.jimblog.service;

import java.util.List;

import site.jimblog.model.PageBean;
import site.jimblog.model.Question;

/**
 * <p>Title: QuestionService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
public interface QuestionService {
	 Question getQuestion(String id);
	 
	 boolean existQuestionByPaperId(String paperId);
	 
	 List<Question> getQuestions(Question question, PageBean pageBean);
	 
	 int questionCount(Question question);
	 
	 void deleteQuestion(Question question);
	 
	 void saveQuestion(Question question);
}
