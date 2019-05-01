package site.jimblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.jimblog.entity.HeadLine;

/**
 * <p>Title: HeadLineDao</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Sep 21, 2018  
 * 
 */
public interface HeadLineDao {
	List<HeadLine> listHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
