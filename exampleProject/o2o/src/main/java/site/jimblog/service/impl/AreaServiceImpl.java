package site.jimblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.AreaDao;
import site.jimblog.entity.Area;
import site.jimblog.service.AreaService;

/**
 * <p>Title: AreaServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaDao areaDao;
	
	@Override
	public List<Area> listArea() {
		return areaDao.listArea();
	}

	
	
}
