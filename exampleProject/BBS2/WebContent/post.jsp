<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@page
	import="java.sql.*,java.io.*,com.java.dbc.*,com.java.model.*,java.util.*"%>

<%
request.setCharacterEncoding("utf-8");

String action=request.getParameter("action");
if(action!=null&&action.trim().equals("post")){
	String title=request.getParameter("title");
	String cont=request.getParameter("cont");
	DatabaseConn databaseConn=new DatabaseConn();
	Connection conn=databaseConn.getConn();
	
	boolean autoCommit=conn.getAutoCommit();
	conn.setAutoCommit(false);
	
	String sql="insert into article values(null,0,?,?,?,now(),?)";
	PreparedStatement pstmt=databaseConn.preparStmt(conn, sql,Statement.RETURN_GENERATED_KEYS);
	pstmt.setInt(1, -1);
	pstmt.setString(2, title);
	pstmt.setString(3, cont);
	pstmt.setInt(4, 0);
	pstmt.executeUpdate();
	
	ResultSet rsKey=pstmt.getGeneratedKeys();
	rsKey.next();
	int rootId=rsKey.getInt(1);
	rsKey.close();
	
	Statement stmt=databaseConn.createStmt(conn);
	stmt.executeUpdate("update article set rootid="+rootId+" where id="+rootId);
	
	conn.commit();
	conn.setAutoCommit(autoCommit);
	databaseConn.close(pstmt);   //preparStatement是Statement子类，应用多态
	databaseConn.close(stmt);
	databaseConn.close(conn);
	
	response.sendRedirect("articleFlat.jsp");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Java|Java世界_中文论坛|ChinaJavaWorld技术论坛 : 初学java遇一难题！！望大家能帮忙一下 ...</title>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="images/style.css" title="Integrated Styles">
<script language="JavaScript" type="text/javascript" src="images/global.js"></script>
<link rel="alternate" type="application/rss+xml" title="RSS" href="http://bbs.chinajavaworld.com/rss/rssmessages.jspa?threadID=744236">
<script type="text/JavaScript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript">       
        
        CKEDITOR.replace('cont');     
		
		function check() {
			var text=document.getElementById("textTitle").value;
			var textarea=document.getElementById("textareaCont").value;
			if(text==null||text==""||textarea==null||textarea==""){
				alert(text);
				alert(textarea);
				alert("标题和内容不能为空");
				return false;
			}
		}

    </script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td width="140"><a href="http://bbs.chinajavaworld.com/index.jspa"><img src="images/header-left.gif" alt="Java|Java世界_中文论坛|ChinaJavaWorld技术论坛" border="0"></a></td>
      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
    </tr>
  </tbody>
</table>
<br>
<div id="jive-flatpage">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="99%"><p class="jive-breadcrumbs"> <a href="article.jsp">首页</a> &#187; <a href="article.jsp">ChinaJavaWorld技术论坛|Java世界_中文论坛</a> &#187; <a href="article.jsp">Java 2 Platform, Standard Edition (J2SE)</a> &#187; <a href="article.jsp">Java语言*初级版</a> </p>
        <td width="1%"><div class="jive-accountbox"></div></td>
      </tr>
    </tbody>
  </table>
  <div class="jive-buttons">
    <table summary="Buttons" border="0" cellpadding="0" cellspacing="0">
      <tbody>
        <tr>
          
        </tr>
      </tbody>
    </table>
  </div>
  <br>
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="99%"><div id="jive-message-holder">
            <div class="jive-message-list">
              <div class="jive-table">
                <div class="jive-messagebox">
                  
                  <form action="post.jsp" method="post" >
                  <input type="hidden" name="action" value="post"> 
                 <font size="3">新主题：</font><input id="testTitle" type="text" name="title" style="width: 300px;"><br>
                 <font size="2">内容：</font><textarea id="textareaCont" class="ckeditor" name="cont"></textarea>
                  <br>
                  <input type="submit" value="提交" > 
                  </form>
                  
                </div>
              </div>
            </div>
            <div class="jive-message-list-footer">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tbody>
                  <tr>
                    <td nowrap="nowrap" width="1%"></td>
                    <td align="center" width="98%"><table border="0" cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td><a href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20"><img src="images/arrow-left-16x16.gif" alt="返回到主题列表" border="0" height="16" hspace="6" width="16"></a> </td>
                            <td><a href="article.jsp">返回到主题列表</a> </td>
                          </tr>
                        </tbody>
                      </table></td>
                    <td nowrap="nowrap" width="1%">&nbsp;</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div></td>
        <td width="1%">&nbsp;</td>
      </tr>
    </tbody>
  </table>
</div>
</body>
</html>
