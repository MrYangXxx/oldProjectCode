package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.model.Book;
import com.util.StringUtil;

public class BookDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int addBook(Book book) {
		String sql = "insert into t_book values(null,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, book.getBookName(), book.getAuthor(),
				book.getSex(), book.getPrice(), book.getBookDesc(),
				book.getBookTypeId());
	}

	public List<Map<String,Object>> bookList(Book book) {
		StringBuffer sb=new StringBuffer("select * from t_bookType bt,t_book b where b.bookTypeId=bt.id");
		if (StringUtil.isNotEmpty(book.getBookName())) {
			sb.append(" and b.bookName like '%" + book.getBookName() + "%'");
		}
		if (StringUtil.isNotEmpty(book.getAuthor())) {
			sb.append(" and b.author like '%" + book.getAuthor() + "%'");
		}
		if (StringUtil.isNotEmpty(book.getSex())) {
			sb.append(" and b.sex ='" + book.getSex().toLowerCase() + "'");
		}
		if (book.getBookTypeId() != -1) {
			sb.append(" and b.bookTypeId = " + book.getBookTypeId());
		}
		return jdbcTemplate.queryForList(
				sb.toString());
	}

	public int deleteBook(String id){
		String sql = "delete from t_book where id=?";
		return jdbcTemplate.update(sql, id);
	}

	public int modifyBook(Book book){
		String sql = "update t_book set bookName=?,author=?,sex=?,price=?,bookDesc=?,bookTypeId=? where id=?";
		return jdbcTemplate.update(sql, book.getBookName(), book.getAuthor(),
				book.getSex(), book.getPrice(), book.getBookDesc(),
				book.getBookTypeId(), book.getId());
	}


	public boolean getBookByBookTypeId(String bookTypeId) {
		String sql = "select count(*) from t_book where bookTypeId="
				+ bookTypeId;
		return jdbcTemplate.queryForObject(sql, Integer.class) >= 1;
	}
}
