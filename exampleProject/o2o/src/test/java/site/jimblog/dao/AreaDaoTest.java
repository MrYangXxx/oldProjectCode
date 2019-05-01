package site.jimblog.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.entity.Area;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: AreaDaoTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
public class AreaDaoTest extends BaseTest{
	@Resource
	private AreaDao areaDao;
	
	@Test
	public void testListArea(){
		List<Area> areas = areaDao.listArea();
		assertEquals(4,areas.size());
	}
}
