package site.jimblog.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import site.jimblog.dao.BaseDao;
import site.jimblog.entity.Notice;
import site.jimblog.entity.PageBean;
import site.jimblog.service.NoticeService;

/**
 * <p>Title: ProductBigTypeServiceImpl</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Apr 30, 2018  
 * 
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	
	@Resource
	private BaseDao<Notice> baseDao;
	
	@Override
	public List<Notice> findNoticeList(Notice notice, PageBean pageBean) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("from Notice");
		if(notice!=null){
			if(notice.getTitle()!=null){
				hql.append(" and title like ?");
				param.add("%"+notice.getTitle()+"%");
			}
		}
		if(pageBean!=null){
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}
		return null;
	}

	@Override
	public Notice getNoticeById(int noticeId) {
		return baseDao.get(Notice.class, noticeId);
	}

	@Override
	public Long getNoticeCount(Notice notice) {
		List<Object> param=new LinkedList<>();
		StringBuffer hql=new StringBuffer("select count(*) from Notice");
		if(notice!=null){
			if(notice.getTitle()!=null){
				hql.append(" and title like ?");
				param.add("%"+notice.getTitle()+"%");
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveNotice(Notice notice) {
		baseDao.merge(notice);
	}

	@Override
	public void deleteNotice(Notice notice) {
		baseDao.delete(notice);
	}

}
