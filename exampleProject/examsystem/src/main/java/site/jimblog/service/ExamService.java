package site.jimblog.service;

import java.util.List;

import site.jimblog.model.Exam;
import site.jimblog.model.PageBean;

/**
 * <p>Title: ExamService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
public interface ExamService {
	void saveExam(Exam exam);
	
	List<Exam> getExams(Exam s_exam,PageBean pageBean);
	
	int getExamCount(Exam s_exam);
}
