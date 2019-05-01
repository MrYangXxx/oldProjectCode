<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@page import="java.sql.*,java.io.*,com.java.dbc.*,com.java.model.*,java.util.*"%>	

<%
request.setCharacterEncoding("utf-8");
String action=request.getParameter("action");
if(action!=null&&action.trim().equals("login")){
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	
	DatabaseConn databaseConn=new DatabaseConn();
	Connection conn=databaseConn.getConn();
	Statement stmt=conn.createStatement();
	ResultSet rs=databaseConn.executeQuery(stmt, "select * from user where username='"+username +"' and password='"+password+"'");
	while(rs.next()){
		session.setAttribute("adminLogined", "true");
		response.sendRedirect("articleFlat.jsp");
		return;
}
	databaseConn.close(rs);
	databaseConn.close(stmt);
	databaseConn.close(conn);
}
%>
	
	
<!DOCTYPE html>
<!-- saved from url=(0034)http://wenda.ghostchina.com/login/ -->
<html class=""><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="renderer" content="webkit">
<title>登录 - BBS</title>

<!--<base href="http://wenda.ghostchina.com/">--><base href="."><!--[if IE]></base><![endif]-->

<link rel="stylesheet" type="text/css" href="./login_files/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./login_files/icon.css">

<link href="./login_files/common.css" rel="stylesheet" type="text/css">
<link href="./login_files/link.css" rel="stylesheet" type="text/css">
<link href="./login_files/style.css" rel="stylesheet" type="text/css">
<link href="./login_files/login.css" rel="stylesheet" type="text/css">


<script src="./login_files/jquery.2.js" type="text/javascript"></script>
<script src="./login_files/jquery.form.js" type="text/javascript"></script>
<script src="./login_files/plug-in_module.js" type="text/javascript"></script>
<script src="./login_files/aws.js" type="text/javascript"></script>
<script src="./login_files/aw_template.js" type="text/javascript"></script>
<script src="./login_files/app.js" type="text/javascript"></script>
<script src="./login_files/md5.js" type="text/javascript"></script>
<script type="text/javascript" src="./login_files/compatibility.js"></script>
<!--[if lte IE 8]>
	<script type="text/javascript" src="http://wenda.ghostchina.com/static/js/respond.js"></script>
<![endif]-->
<style type="text/css">.fancybox-margin{margin-right:0px;}</style></head>

<body><noscript unselectable="on" id="noscript">
    &lt;div class="aw-404 aw-404-wrap container"&gt;
        &lt;img src="http://wenda.ghostchina.com/static/common/no-js.jpg"&gt;
        &lt;p&gt;你的浏览器禁用了JavaScript, 请开启后刷新浏览器获得更好的体验!&lt;/p&gt;
    &lt;/div&gt;
</noscript>
<div id="wrapper">
	<div class="aw-login-box" style="width:500px">
	<div class="mod-body clearfix">			
				
				<center><font size=5>BBS管理员登录</font></center>
				<br><br>			
				<form id="login_form" method="post" action="login.jsp">
					<input type="hidden" name="action" value="login">
					<ul>
						<li>
							<input type="text" id="aw-login-user-name" class="form-control" placeholder="邮箱 / 用户名" name="username">
						</li>
						<li>
							<input type="password" id="aw-login-user-password" class="form-control" placeholder="密码" name="password">
						</li>
						<li class="alert alert-danger hide error_message">
							<i class="icon icon-delete"></i> <em></em>
						</li>
						<li class="last">
							<input type="submit" class="pull-right btn btn-large btn-primary" value="登录"/>
							<label>
								<input type="checkbox" value="1" name="net_auto_login">
								记住我							</label>
							<a href="login.jsp">&nbsp;&nbsp;忘记密码</a>
						</li>
					</ul>
				</form>
			</div>
				</div>
</div>

<script type="text/javascript" src="./login_files/login.js"></script>

<div class="aw-footer-wrap">
	<div class="aw-footer">
		Copyright © 2017<span class="hidden-xs"> - 京ICP备666666号, All Rights Reserved</span>

		<span class="hide">Powered By <a href="http://www.wecenter.com/?copyright" target="blank">WeCenter 3.0 Beta 2</a></span>
		
			</div>
</div>



<a class="aw-back-top hidden-xs" href="javascript:;" onclick="$.scrollTo(1, 600, {queue:true});"><i class="icon icon-up"></i></a>


<!-- DO NOT REMOVE -->
<div id="aw-ajax-box" class="aw-ajax-box"></div>
<div style="display:none;" id="__crond"><img src="./login_files/1506692307" width="1" height="1"></div>

<!-- Escape time: 0.0065238475799561 --><!-- / DO NOT REMOVE -->


</body></html>