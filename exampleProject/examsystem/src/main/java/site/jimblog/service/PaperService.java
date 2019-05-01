package site.jimblog.service;

import java.util.List;

import site.jimblog.model.Paper;

/**
 * <p>Title: PaperService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date May 31, 2018  
 * 
 */
public interface PaperService {
	List<Paper> getPapers();
	
	Paper getPaper(String paperId);
	
	void deletePaper(Paper paper);
	
	void savePaper(Paper paper);
}
