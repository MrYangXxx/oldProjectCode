package site.jimblog.serviceImpl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.model.Exam;
import site.jimblog.model.PageBean;
import site.jimblog.service.ExamService;

/**
 * <p>Title: ExamServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Resource
	private BaseDao<Exam> baseDao;
	
	@Override
	public void saveExam(Exam exam) {
		baseDao.merge(exam);
	}

	@Override
	public List<Exam> getExams(Exam s_exam, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from Exam exam");
		if(s_exam!=null&&s_exam.getStudent()!=null&&!s_exam.getStudent().getId().isEmpty()){
			hql.append(" and exam.student.id like'%"+s_exam.getStudent().getId()+"%'");
		}
		if(s_exam!=null&&s_exam.getStudent()!=null&&s_exam.getStudent().getName()!=null){
			hql.append(" and exam.student.name like '%"+s_exam.getStudent().getName()+"%'");
		}
		if(pageBean!=null){
			List<Exam> list = baseDao.find(hql.toString().replaceFirst("and", "where"),param,pageBean);
			return list;
		}
		return baseDao.find(hql.toString().replaceFirst("and", "where"));
	}

	@Override
	public int getExamCount(Exam s_exam) {
		StringBuffer sql=new StringBuffer("select count(*) from Exam e,Student s where e.student.id=s.id ");
		if(s_exam!=null&&s_exam.getStudent()!=null&&!s_exam.getStudent().getId().isEmpty()){
			sql.append(" and s.id like'%"+s_exam.getStudent().getId()+"%'");
		}
		if(s_exam!=null&&s_exam.getStudent()!=null&&!s_exam.getStudent().getName().isEmpty()){
			sql.append(" and s.name like '%"+s_exam.getStudent().getName()+"%'");
		}
		Long count = baseDao.count(sql.toString());
		return count.intValue();
	}

}
