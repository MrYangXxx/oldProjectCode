package site.jimblog.serviceImpl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import site.jimblog.dao.BaseDao;
import site.jimblog.model.PageBean;
import site.jimblog.model.Student;
import site.jimblog.service.StudentService;

/**
 * <p>Title: StudentServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService{
	
	@Resource
	private BaseDao<Student> baseDao;

	@Override
	public Student login(Student student) {
		String hql="from Student where id=? and password=?";
		return baseDao.get(hql, new Object[]{student.getId(),student.getPassword()});
	}

	@Override
	public Student getStudentById(String id) {
		return baseDao.get(Student.class,id);
	}

	@Override
	public void saveStudent(Student student) {
		baseDao.saveOrUpdate(student);
	}

	@Override
	public List<Student> getStudents(Student student, PageBean pageBean) {
		StringBuffer hql=new StringBuffer("from Student");
		if(!StringUtils.isNullOrEmpty(student.getId())){
			hql.append(" and id like '%"+student.getId()+"%'");
		}
		if(!StringUtils.isNullOrEmpty(student.getName())){
			hql.append(" and name like '%"+student.getName()+"%'");
		}
		if(pageBean!=null){
			List<Student> list = baseDao.find(hql.toString().replaceFirst("and", "where"), new LinkedList<>(), pageBean);
			return list;
		}
		return null;
	}

	@Override
	public int getStudentCount(Student student) {
		StringBuffer hql=new StringBuffer("select count(id) from Student");
		if(!StringUtils.isNullOrEmpty(student.getId())){
			hql.append(" and id like '%"+student.getId()+"%'");
		}
		if(!StringUtils.isNullOrEmpty(student.getName())){
			hql.append(" and name like '%"+student.getName()+"%'");
		}
		Long count = baseDao.count(hql.toString().replaceFirst("and", "where"));
		return count.intValue();
	}

	@Override
	public void deleteStudent(Student student) {
		baseDao.delete(student);
	}

}
