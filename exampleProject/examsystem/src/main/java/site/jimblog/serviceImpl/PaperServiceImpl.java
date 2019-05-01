package site.jimblog.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.model.Paper;
import site.jimblog.service.PaperService;

/**
 * <p>Title: PaperServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {

	@Resource
	private BaseDao<Paper> baseDao;
	
	@Override
	public List<Paper> getPapers() {
		return baseDao.find("from Paper");
	}

	@Override
	public Paper getPaper(String paperId) {
		return baseDao.get(Paper.class, Integer.parseInt(paperId));
	}

	@Override
	public void deletePaper(Paper paper) {
		baseDao.delete(paper);
	}

	@Override
	public void savePaper(Paper paper) {
		baseDao.saveOrUpdate(paper);
	}

}
