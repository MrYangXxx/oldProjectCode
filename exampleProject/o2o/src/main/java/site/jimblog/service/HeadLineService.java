package site.jimblog.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.HeadLine;

/**
 * <p>Title: HeadLineService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Sep 21, 2018  
 * 
 */
public interface HeadLineService {
	List<HeadLine> listHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
