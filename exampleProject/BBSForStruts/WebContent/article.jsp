<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page
	import="java.sql.*,java.io.*,com.java.model.*,java.util.*"%>
<%
boolean logined=false;
String adminLogined=(String)session.getAttribute("adminLogined");
if(adminLogined!=null&&adminLogined.trim().equals("true")){
	logined=true;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Java|Java世界_中文论坛|ChinaJavaWorld技术论坛 : Java语言*初级版</title>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="images/style.css"
	title="Integrated Styles">
<script language="JavaScript" type="text/javascript"
	src="images/global.js"></script>
<link rel="alternate" type="application/rss+xml" title="RSS"
	href="article">
<script language="JavaScript" type="text/javascript"
	src="images/common.js"></script>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td width="140"><a
					href="article"><img
						src="images/header-left.gif"
						alt="Java|Java世界_中文论坛|ChinaJavaWorld技术论坛" border="0"></a></td>
				<td><img src="images/header-stretch.gif" alt="" border="0"
					height="57" width="100%"></td>
				<td width="1%"><img src="images/header-right.gif" alt=""
					border="0"></td>
			</tr>
		</tbody>
	</table>
	<br>
	<div id="jive-forumpage">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr valign="top">
					<td width="90%"><p class="jive-breadcrumbs">论坛: Java语言*初级版
							(模仿)</p>
						<p class="jive-description">探讨Java语言基础知识,基本语法等 大家一起交流
							共同提高！谢绝任何形式的广告</p></td><td>
							<%if (!logined){ %>
							<a href="login.jsp">管理员登录</a><br><br>
							<%} %>
							<%	if (logined) {%>
							<a href="article">管理子帖</a>&nbsp;&nbsp;<a href="articleFlat">管理总帖</a>
							<%} %>
				</tr>
			</tbody>
		</table>
		<div class="jive-buttons">
			<table summary="Buttons" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="jive-icon"><a
							href="post"><img
								src="images/post-16x16.gif" alt="发表新主题" border="0" height="16"
								width="16"></a></td>
						<td class="jive-icon-label"><a id="jive-post-thread"
							href="post">发表新主题</a>
							<a
							href="article"></a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<br>
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<tbody>
				<tr valign="top">
					<td><span class="nobreak"> 页: 1,316 - <span
							class="jive-paginator"> [ <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=0&amp;isBest=0">上一页</a>
								| <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=0&amp;isBest=0"
								class="">1</a> <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=25&amp;isBest=0"
								class="jive-current">2</a> <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=50&amp;isBest=0"
								class="">3</a> <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=75&amp;isBest=0"
								class="">4</a> <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=100&amp;isBest=0"
								class="">5</a> <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=125&amp;isBest=0"
								class="">6</a> | <a
								href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=50&amp;isBest=0">下一页</a>
								]
						</span>
					</span></td>
				</tr>
			</tbody>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr valign="top">
					<td width="99%"><div class="jive-thread-list">
							<div class="jive-table">
								<table summary="List of threads" cellpadding="0" cellspacing="0"
									width="100%">
									<thead>
										<tr>
											<th class="jive-first" colspan="3">主题</th>
											<th class="jive-author"><nobr> 作者 &nbsp; </nobr></th>
											<th class="jive-view-count"><nobr> 浏览 &nbsp; </nobr></th>
											<th class="jive-msg-count" nowrap="nowrap">回复</th>
											<th class="jive-last" nowrap="nowrap">最新帖子</th>
										</tr>
									</thead>
									<tbody>
									
									<%
										Set<Article> articles = (Set<Article>) request.getAttribute("articles");
									for(Iterator<Article> it=articles.iterator();it.hasNext();){
										Article a=it.next();
										String preStr="";
										for(int i=0;i<a.getGrade();i++){
											preStr+="----";
										}
									%>
									
										<tr class="jive-even">
											<td class="jive-first" nowrap="nowrap" width="1%"><div
													class="jive-bullet">
													<img src="images/read-16x16.gif" alt="已读" border="0"
														height="16" width="16">
													<!-- div-->
												</div></td>
											<td nowrap="nowrap" width="1%">
											<%if(logined){ %>
											<a href="delete?id=<%=a.getId()%>&isLeaf=<%=a.isLeaf()%>&pId=<%=a.getpId()%>">DEL</a>
											<%} %>
											</td>
											<td class="jive-thread-name" width="95%"><a
												id="jive-thread-1"
												href="articleDetail?id=<%=a.getId() %>">
												<%=preStr+a.getTitle() %>
												</a></td>
											<td class="jive-author" nowrap="nowrap" width="1%"><span
												class=""> <a
													href="http://bbs.chinajavaworld.com/profile.jspa?userID=226030">qwer</a>
											</span></td>
											<td class="jive-view-count" width="1%">104</td>
											<td class="jive-msg-count" width="1%">${count} </td>
											<td class="jive-last" nowrap="nowrap" width="1%"><div
													class="jive-last-post">
													<%=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(a.getpDate()) %>
													 <br> by: <a
														href="http://bbs.chinajavaworld.com/thread.jspa?messageID=780182#780182"
														title="jingjiangjun" style="">zxcv &#187;</a>
												</div></td>
										</tr>
										<% 
										}
										%>
									</tbody>
								</table>
							</div>
						</div>
						<div class="jive-legend"></div></td>
				</tr>
			</tbody>
		</table>
		<br> <br>
	</div>
</body>
</html>
