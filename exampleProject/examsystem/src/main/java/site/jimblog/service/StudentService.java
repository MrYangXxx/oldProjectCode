package site.jimblog.service;

import java.util.List;

import site.jimblog.model.PageBean;
import site.jimblog.model.Student;

/**
 * <p>Title: StudentService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
public interface StudentService {
	Student login(Student student);
	
	Student getStudentById(String id);
	
	void saveStudent(Student student);
	
	List<Student> getStudents(Student student,PageBean pageBean);
	
	int getStudentCount(Student student);
	
	void deleteStudent(Student student);
}
