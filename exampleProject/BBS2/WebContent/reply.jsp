<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@page
	import="java.sql.*,java.io.*,com.java.dbc.*,com.java.model.*,java.util.*"%>

<%

int id=Integer.parseInt(request.getParameter("id"));
int rootId=Integer.parseInt(request.getParameter("rootId"));


Article a=null;

DatabaseConn databaseConn=new DatabaseConn();
Connection conn=databaseConn.getConn();
String sql="select * from article where id="+id;
Statement stmt=databaseConn.createStmt(conn);
ResultSet rs=databaseConn.executeQuery(stmt, sql);
if(rs.next()){
	a=new Article();
	a.initFromRs(rs);
}

databaseConn.close(rs);
databaseConn.close(stmt);
databaseConn.close(conn);

if(a==null){
	return;
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
      <td width="140"><a href="article.jsp"><img src="images/header-left.gif" alt="Java|Java世界_中文论坛|ChinaJavaWorld技术论坛" border="0"></a></td>
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
          <p class="jive-page-title"> 主题: <%=a.getTitle() %></p></td>
        <td width="1%"><div class="jive-accountbox"></div></td>
      </tr>
    </tbody>
  </table>
  <div class="jive-buttons">
    <table summary="Buttons" border="0" cellpadding="0" cellspacing="0">
      <tbody>
        <tr>
        <hr>
          <font>回复本主题：</font>
          
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
                  
                  <form action="replyDeal.jsp" method="post" onsubmit="return check()">
                  <input type="hidden" name="pid" value="<%=id %>">
                  <input type="hidden" name="rootId" value="<%=rootId %>">
                  标题：<input id="testTitle" type="text" name="title" style="width: 250px;"><br>
                 内容：<textarea id="textareaCont" class="ckeditor" name="cont"></textarea>
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
                            <td><a href="article.jsp"><img src="images/arrow-left-16x16.gif" alt="返回到主题列表" border="0" height="16" hspace="6" width="16"></a> </td>
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
