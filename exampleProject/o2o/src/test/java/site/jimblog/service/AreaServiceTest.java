package site.jimblog.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import site.jimblog.entity.Area;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: AreaServiceTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
public class AreaServiceTest extends BaseTest{
	@Resource
	private AreaService areaService;
	
	@Test
	public void testAreaService(){
		List<Area> areas = areaService.listArea();
		assertEquals("东苑",areas.get(0).getAreaName());
	}
}
