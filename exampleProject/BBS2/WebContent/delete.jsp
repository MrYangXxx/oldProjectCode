
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.sql.*,java.io.*,com.java.dbc.*,com.java.model.*,java.util.*"%>
<%@include file="_SessionCheck.jsp" %>
<%!
DatabaseConn databaseConn=new DatabaseConn();
private void delete(Connection conn,int id,boolean isLeaf){
	if(!isLeaf){
		String sql="select * from article where pid="+id;
		Statement stmt=databaseConn.createStmt(conn);
		ResultSet rs=databaseConn.executeQuery(stmt,sql);
		
		try{
			while(rs.next()){
				delete(conn, rs.getInt("id"), rs.getInt("isleaf")==0);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			databaseConn.close(rs);
			databaseConn.close(stmt);
		}
	}
	databaseConn.executeUpdate(conn, "delete from article where id="+id);
}
			
%>
<%
	String url=request.getParameter("url");
	int id=Integer.parseInt(request.getParameter("id"));
	int pId=Integer.parseInt(request.getParameter("pId"));
	boolean isLeaf=Boolean.parseBoolean(request.getParameter("isLeaf"));
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
try{
	conn=databaseConn.getConn();
	boolean autoCommit=conn.getAutoCommit();
	conn.setAutoCommit(false);
	
	delete(conn, id, isLeaf);
	
	stmt=databaseConn.createStmt(conn);
	rs=databaseConn.executeQuery(stmt,"select count(*) from article where pid="+pId);
	rs.next();
	int count=rs.getInt(1);
	if(count<=0){
		databaseConn.executeUpdate(conn, "update article set isleaf=0 where id="+pId);
		}
	conn.commit();
}finally{
	conn.setAutoCommit(true);
	databaseConn.close(rs);
	databaseConn.close(stmt);
	databaseConn.close(conn);
}
%>删除成功!<br>
<span id="time">5</span>秒后自动跳转，如不跳转，请点右边链接返回：
<script language="JavaScript" type="text/javascript">
function delayURL(url) {
	var delay=document.getElementById("time").innerHTML;
	if(delay>0){
		delay--;
		document.getElementById("time").innerHTML=delay;
	}else{
		window.top.location.href=url;
	}
	setTimeout("delayURL('"+url+"')",1000);
}
</script>
<a href="<%=url%>">立即返回列表界面</a>
<script type="text/javascript">
delayURL("<%=url%>")
</script>