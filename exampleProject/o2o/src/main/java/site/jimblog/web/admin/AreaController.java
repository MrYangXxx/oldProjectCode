package site.jimblog.web.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import site.jimblog.entity.Area;
import site.jimblog.service.AreaService;

/**
 * <p>Title: AreaController</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Jun 9, 2018  
 * 
 */
@Controller
@RequestMapping("/admin")
public class AreaController {
	Logger logger=LoggerFactory.getLogger(AreaController.class);
	
	@Resource
	private AreaService areaService;
	
	@RequestMapping("/listArea")
	@ResponseBody
	private Map<String, Object> listArea(){
		logger.info("=====start=====");
		long startTime=System.currentTimeMillis();
		Map<String, Object> modelMap=new HashMap<>();
		List<Area> list = areaService.listArea();
		modelMap.put("rows", list);
		modelMap.put("total", list.size());
		
		logger.error("test error");
		long endTime=System.currentTimeMillis();
		logger.debug("costTime:[{}ms]",endTime-startTime);
		logger.info("=====end=====");
		
		return modelMap;
	}
}
