
<%@page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.sql.*,java.io.*,com.java.dbc.*,com.java.model.*,java.util.*"%>

<%
	boolean logined = false;
	String adminLogined = (String) session.getAttribute("adminLogined");
	if (adminLogined != null && adminLogined.trim().equals("true")) {
		logined = true;
	}
%>

<%
	request.setCharacterEncoding("utf-8");
	final int PAGE_SIZE = 5;
	int pageNo = 1;
	String strPageNo = request.getParameter("pageNo");
	String search = request.getParameter("search");
	if (strPageNo != null && !strPageNo.trim().equals("")) {
		try {
			pageNo = Integer.parseInt(strPageNo);
		} catch (NumberFormatException e) {
			pageNo = 1;
		}
	}

	if (pageNo <= 0)
		pageNo = 1;

	int totalPages = 0;

	DatabaseConn databaseConn = new DatabaseConn();
	Set<Article> articles = new LinkedHashSet<Article>();
	Connection conn = databaseConn.getConn();
	Statement stmtCount = databaseConn.createStmt(conn);
	StringBuffer countSql = new StringBuffer("select count(0) from article where pid=0");
	if (search != null && !search.trim().equals("")) {
		countSql.append(" and title like '%" + search + "%'");
	}
	ResultSet rsCount = databaseConn.executeQuery(stmtCount, countSql.toString());
	rsCount.next();
	int totalRecords = rsCount.getInt(1);
	totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
	if (pageNo > totalPages)
		pageNo = totalPages;

	Statement stmt = databaseConn.createStmt(conn);
	int startPos = (pageNo - 1) * PAGE_SIZE;
	StringBuffer sql = new StringBuffer("select * from article where");
	if (search != null && !search.trim().equals("")) {
		sql.append(" title like '%" + search + "%' and");
	}
	sql.append(" pid=0 order by pdate desc");
	if(totalRecords>0){
		sql.append( " limit " + startPos + "," + PAGE_SIZE);
	}
	ResultSet rs = databaseConn.executeQuery(stmt, sql.toString());
	if(rs!=null){
	while (rs.next()) {
		Article a = new Article();
		a.initFromRs(rs);
		articles.add(a);
	}
	}
	databaseConn.close(rsCount);
	databaseConn.close(stmtCount);
	
		if(rs!=null){
	databaseConn.close(rs);
		}
	databaseConn.close(stmt);
	databaseConn.close(conn);
	
	
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
	href="article.jsp">
<script language="JavaScript" type="text/javascript"
	src="images/common.js"></script>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td width="140"><a href="article.jsp"><img
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
							<a href="article.jsp">管理子帖</a>&nbsp;&nbsp;<a href="articleFlat.jsp">管理总帖</a>
							<%} %>
							</td>
				</tr>
			</tbody>
		</table>
		<div class="jive-buttons">
			<table summary="Buttons" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="jive-icon"><a href="post.jsp"><img
								src="images/post-16x16.gif" alt="发表新主题" border="0" height="16"
								width="16"></a></td>
						<td class="jive-icon-label"><a id="jive-post-thread"
							href="post.jsp">发表新主题</a> <a href="article.jsp"></a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<br>
	
		<form action="articleFlat.jsp" method="post">
		<%if(request.getParameter("search")!=null&&!request.getParameter("search").trim().equals("")){%>
			搜索帖子:<input type="text" name="search" size=“10” placeholder="搜索关键字" value="<%=request.getParameter("search")%>"/>
			<%}else{%>
			搜索帖子:<input type="text" name="search" size=“10” placeholder="搜索关键字" />
			<%} %>
			<input type="submit" value="Search">
		</form>

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
											int lineNum = 0;
											for (Iterator<Article> it = articles.iterator(); it.hasNext();) {
												Article a = it.next();

												String classStr = lineNum % 2 == 0 ? "jive-even" : "jive-odd";
										%>

										<tr class="<%=classStr%>">
											<td class="jive-first" nowrap="nowrap" width="1%"><div
													class="jive-bullet">
													<img src="images/read-16x16.gif" alt="已读" border="0"
														height="16" width="16">
													<!-- div-->
												</div></td>
											<td nowrap="nowrap" width="1%">
												<%
													String url = "";
														url += request.getRequestURL();
														url += request.getQueryString() == null ? "" : ("?" + request.getQueryString());
												%> <%
 	if (logined) {
 %> <a
												href="delete.jsp?id=<%=a.getId()%>&isLeaf=<%=a.isLeaf()%>&pId=<%=a.getpId()%>&url=<%=url%>">DEL</a>
												<%
													}
												%>
											</td>
											<td class="jive-thread-name" width="95%"><a
												id="jive-thread-1"
												href="articleDetailFlat.jsp?id=<%=a.getId()%>"> <%=a.getTitle()%>
											</a></td>
											<td class="jive-author" nowrap="nowrap" width="1%"><span
												class=""> <a
													href="http://bbs.chinajavaworld.com/profile.jspa?userID=226030">qwer</a>
											</span></td>
											<td class="jive-view-count" width="1%">104</td>
											<td class="jive-msg-count" width="1%">100</td>
											<td class="jive-last" nowrap="nowrap" width="1%"><div
													class="jive-last-post">
													<%=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(a.getpDate())%>
													<br> by: <a
														href="http://bbs.chinajavaworld.com/thread.jspa?messageID=780182#780182"
														title="jingjiangjun" style="">zxcv &#187;</a>
												</div></td>
										</tr>
										<%
											lineNum++;
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
		<br>

		<%
			if (search == null || search.trim().equals("")) {
		%>
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<tbody>
				<tr valign="top">
					<td><span class="nobreak">第<%=pageNo%>页,共<%=totalPages%>页<span
							class="jive-paginator"> [ <a
								href="articleFlat.jsp?pageNo=1">首页</a> | <a
								href="articleFlat.jsp?pageNo=<%=pageNo - 1%>">上一页</a> | <a
								href="articleFlat.jsp?pageNo=<%=pageNo + 1%>">下一页</a> | <a
								href="articleFlat.jsp?pageNo=<%=totalPages%>">尾页</a> ]
						</span>
					</span></td>

				</tr>
			</tbody>
		</table>
		<%
			} else {
		%>
		<table border="0" cellpadding="3" cellspacing="0" width="100%">
			<tbody>
				<tr valign="top">
					<td><span class="nobreak">第<%=pageNo%>页,共<%=totalPages%>页<span
							class="jive-paginator"> [ <a
								href="articleFlat.jsp?pageNo=1&search=<%=search%>">首页</a> | <a
								href="articleFlat.jsp?pageNo=<%=pageNo - 1%>&search=<%=search%>">上一页</a>
								| <a
								href="articleFlat.jsp?pageNo=<%=pageNo + 1%>&search=<%=search%>">下一页</a>
								| <a
								href="articleFlat.jsp?pageNo=<%=totalPages%>&search=<%=search%>">尾页</a>
								]
						</span>
					</span></td>

				</tr>
			</tbody>
		</table>
		<%
			}
		%>
	</div>
</body>
</html>
