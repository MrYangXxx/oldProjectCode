package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.User;
import site.jimblog.service.UserService;

/**
 * <p>Title: UserServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 6, 2018  
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private BaseDao<User> baseDao;

	@Override
	public void saveUser(User user) {
		baseDao.merge(user);
	}

	@Override
	public boolean existUserWithUserName(String userName) {
		String hql="select count(*) from User where userName=?";
		Long count = baseDao.count(hql,new Object[]{userName});
		if(count>0){
			return true;
		}
		return false;
	}

	@Override
	public User login(User user) {
		StringBuffer hql=new StringBuffer("from User where userName=? and password=?");
		if(user.getStatus()==2){
			hql.append(" and status=2");
		}
		return baseDao.get(hql.toString(), new Object[]{user.getUserName(),user.getPassword()});
	}

	@Override
	public List<User> findUserList(User user, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from User");
		if(user!=null){
			if(user.getUserName()!=null){
				hql.append(" and userName like ?");
				param.add("%"+user.getUserName()+"%");
			}
		}
		hql.append(" and status=1");
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"),param,pageBean);
		}
		return null;
	}

	@Override
	public Long getUserCount(User user) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from User");
		if(user!=null){
			if(user.getUserName()!=null){
				hql.append(" and userName like ?");
				param.add("%"+user.getUserName()+"%");
			}
		}
		hql.append(" and status=1");
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void deleteUser(User user) {
		baseDao.delete(user);
	}

	@Override
	public User getUserById(int id) {
		return baseDao.get(User.class, id);
	}

}
