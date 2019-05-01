package site.jimblog.serviceImpl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import site.jimblog.dao.BaseDao;
import site.jimblog.model.PageBean;
import site.jimblog.model.Question;
import site.jimblog.service.QuestionService;

/**
 * <p>Title: QuestionServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Resource
	private BaseDao<Question> baseDao;
	
	@Override
	public Question getQuestion(String id) {
		return baseDao.get(Question.class, Integer.parseInt(id));
	}

	@Override
	public boolean existQuestionByPaperId(String paperId) {
		String hql="select count(*) from Question where paper.id=?";
		Long count = baseDao.count(hql,new Object[]{Integer.parseInt(paperId)});
		if(count>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Question> getQuestions(Question question, PageBean pageBean) {
		StringBuffer hql = new StringBuffer("from Question");
		if (!StringUtils.isNullOrEmpty(question.getSubject())) {
			hql.append(" and subject like '%" + question.getSubject() + "%'");
		}
		if(pageBean!=null){
			List<Question> list = baseDao.find(hql.toString(),new LinkedList(),pageBean);
			return list;
		}
		return null;
	}

	@Override
	public int questionCount(Question question) {
		StringBuffer hql = new StringBuffer("select count(id) from Question");
		if (!StringUtils.isNullOrEmpty(question.getSubject())) {
			hql.append(" and subject like '%" + question.getSubject() + "%'");
		}
		Long count = baseDao.count(hql.toString());
		return count.intValue();
	}

	@Override
	public void deleteQuestion(Question question) {
		baseDao.delete(question);
	}

	@Override
	public void saveQuestion(Question question) {
		baseDao.merge(question);
	}

}
