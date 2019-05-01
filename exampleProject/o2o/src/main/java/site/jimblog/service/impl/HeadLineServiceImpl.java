package site.jimblog.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.jimblog.dao.HeadLineDao;
import site.jimblog.entity.HeadLine;
import site.jimblog.service.HeadLineService;

/**
 * <p>Title: HeadLineServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Sep 21, 2018  
 * 
 */
@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	HeadLineDao headLineDao;
	
	@Override
	public List<HeadLine> listHeadLine(@Param("headLineCondition") HeadLine headLineCondition) {
		return headLineDao.listHeadLine(headLineCondition);
	}

}
