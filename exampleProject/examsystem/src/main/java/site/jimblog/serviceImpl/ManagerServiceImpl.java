package site.jimblog.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.model.Manager;
import site.jimblog.service.ManagerService;

/**
 * <p>Title: ManagerServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

	@Resource
	private BaseDao<Manager> baseDao;
	
	@Override
	public Manager login(Manager manager) {
		String hql="from Manager where userName=? and password=?";
		return baseDao.get(hql, new Object[]{manager.getUserName(),manager.getPassword()});
		
	}

}
