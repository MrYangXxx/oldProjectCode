package site.jimblog.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import site.jimblog.entity.HeadLine;
import site.jimblog.util.BaseTest;

/**
 * <p>Title: HeadLineDaoTest</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Sep 21, 2018  
 * 
 */
public class HeadLineDaoTest extends BaseTest{
	@Autowired
	HeadLineDao dao;
	
	@Test
	public void testlistHeadLine(){
		List<HeadLine> list = dao.listHeadLine(new HeadLine());
		assertEquals(4, list.size());
	}
}
