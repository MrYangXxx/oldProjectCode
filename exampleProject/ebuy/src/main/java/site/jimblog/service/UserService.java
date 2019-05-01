package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.PageBean;
import site.jimblog.entity.User;

/**
 * <p>Title: UserService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 6, 2018  
 * 
 */
public interface UserService {
	void saveUser(User user);
	
	boolean existUserWithUserName(String userName);
	
	User login(User user);
	
	List<User> findUserList(User user,PageBean pageBean);
	
	Long getUserCount(User user);
	
	void deleteUser(User user);
	
	User getUserById(int id);
}
