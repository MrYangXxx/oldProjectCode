package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.model.BookType;
import com.util.StringUtil;

public class BookTypeDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int addBookType(BookType bookType){
		String sql="insert into t_bookType values(null,?,?)";
		return jdbcTemplate.update(sql, bookType.getBookTypeName(),bookType.getBookTypeDesc());
	}
	
	public List<Map<String,Object>> bookTypeList(BookType bookType){
		StringBuffer sb=new StringBuffer("select * from t_bookType");
		if(StringUtil.isNotEmpty(bookType.getBookTypeName())){
			sb.append(" and bookTypeName like '%"+bookType.getBookTypeName()+"%'");
		}
		return jdbcTemplate.queryForList(sb.toString().replaceFirst("and", "where"));
	}
	
	public int deleteBookType(String id){
		String sql="delete from t_bookType where id=?";
		return jdbcTemplate.update(sql, id);
	}
	
	public int modifyBookType(BookType bookType){
		String sql="update t_bookType set bookTypeName=?,bookTypeDesc=? where id=?";
		return jdbcTemplate.update(sql,  bookType.getBookTypeName(),bookType.getBookTypeDesc(), bookType.getId());
	}
	
	public int getBookTypeId(String bookTypeName){
		String sql="select id from t_bookType where bookTypeName='"+bookTypeName+"'";
		List<Map<String, Object>> bookTypeList = jdbcTemplate.queryForList(sql);
		return  (int) bookTypeList.get(0).get("id");
	}
	
}
