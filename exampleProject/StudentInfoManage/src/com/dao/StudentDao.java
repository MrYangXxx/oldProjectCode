package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.Student;
import com.model.Student;
import com.model.Page;
import com.util.DateUtil;
import com.util.StringUtil;

public class StudentDao {
	public ResultSet studentList(Connection con,Page page,Student student,String bbirthday,String ebirthday) throws SQLException{
		StringBuffer sb=new StringBuffer("select * from t_student s,t_grade g where s.gradeId=g.id");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getGender())){
			sb.append(" and s.gender ='"+student.getGender()+"'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('"+bbirthday+"')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('"+ebirthday+"')");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		if(page!=null){
			sb.append(" limit "+page.getStart()+","+page.getRows());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public int gradeCount(Connection con,Student student,String bbirthday,String ebirthday) throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId=g.id");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getGender())){
			sb.append(" and s.gender ='"+student.getGender()+"'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('"+bbirthday+"')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('"+ebirthday+"')");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}else{
			return 0;
		}
	}
	
	public int studentDelete(Connection con, String delIds) throws SQLException {
		String sql = "delete from t_student where stuId in(" + delIds + ")";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	public int studentAdd(Connection con,Student student) throws SQLException{
		String sql="insert into t_student values(null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getGender());
		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setInt(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getStuDesc());
		return pstmt.executeUpdate();
	}
	
	public int studentModify(Connection con,Student student) throws SQLException{
		String sql="update t_student set stuNo=?,stuName=?,gender=?,birthday=?,gradeId=?,email=?,stuDesc=? where stuId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getStuNo());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getGender());
		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setInt(5, student.getGradeId());
		pstmt.setString(6, student.getEmail());
		pstmt.setString(7, student.getStuDesc());
		pstmt.setInt(8, student.getStuId());
		return pstmt.executeUpdate();
	}
	
	public boolean getStudentByGradeId(Connection con,String gradeId) throws SQLException{
		String sql="select stuNo from t_student where gradeId=? limit 1";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
}
