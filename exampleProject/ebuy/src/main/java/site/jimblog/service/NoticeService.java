package site.jimblog.service;

import java.util.List;

import site.jimblog.entity.Notice;
import site.jimblog.entity.PageBean;

/**
 * <p>Title: NoticeService</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
public interface NoticeService {
	List<Notice> findNoticeList(Notice notice,PageBean pageBean);
	
	Notice getNoticeById(int noticeId);
	
	Long getNoticeCount(Notice notice);
	
	void saveNotice(Notice notice);
	
	void deleteNotice(Notice notice);
}
