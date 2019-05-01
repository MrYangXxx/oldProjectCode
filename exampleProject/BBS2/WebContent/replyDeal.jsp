<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.java.dbc.DatabaseConn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 request.setCharacterEncoding("utf-8");
 
 int pid=Integer.parseInt(request.getParameter("pid"));
 int rootId=Integer.parseInt(request.getParameter("rootId"));
 
 
 String title=request.getParameter("title");
 String cont=request.getParameter("cont");
 if(title!=null||title.trim()!=""||cont!=null||cont.trim()!=""){
 DatabaseConn databaseConn=new DatabaseConn();
 Connection conn=databaseConn.getConn();
 
 boolean autoCommit=conn.getAutoCommit();
 conn.setAutoCommit(false);
 
 String sql="insert into article values(null,?,?,?,?,now(),?)";
 PreparedStatement pstmt=databaseConn.preparStmt(conn, sql);
 pstmt.setInt(1, pid);
 pstmt.setInt(2, rootId);
 pstmt.setString(3, title);
 pstmt.setString(4, cont);
 pstmt.setInt(5, 0);
 pstmt.executeUpdate();
 
 Statement stmt=databaseConn.createStmt(conn);
 stmt.executeUpdate("update article set isleaf=1 where id="+pid);
 
 conn.commit();
 conn.setAutoCommit(autoCommit);
 databaseConn.close(pstmt);   //preparStatement是Statement子类，应用多态
 databaseConn.close(conn);
 }
 %>   回复成功<br>
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
<a href="article.jsp">返回列表界面</a>
<script type="text/javascript">
delayURL("article.jsp")
</script>