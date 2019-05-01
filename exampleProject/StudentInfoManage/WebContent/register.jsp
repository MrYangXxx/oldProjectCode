<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function check() {
		var userName = document.getElementById("username").value;
		var xmlHttp;
		if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				//alert(xmlHttp.responseText);
				var dataObj = eval("(" + xmlHttp.responseText + ")");
				if (!dataObj.exist && userName != "") {
					document.getElementById("tip").innerHTML = "<img src='img/ok.png' />&nbsp;CAN USE";
					document.getElementById("submit").disabled = undefined;
				} else {
					document.getElementById("tip").innerHTML = "<img src='img/no.png' />&nbsp;USED";
					document.getElementById("submit").disabled = "disabled";
				}
			}
		};
		xmlHttp.open("post", "login?action=checkUserName&username=" + userName,
				true);
		xmlHttp.send();
	}
</script>


</head>
<body>
	<div align="center" style="padding: 50px">
		<form action="login" method="post">
			<input type="hidden" name="skip" value="register">
			用户名&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" id="username" name="username" onblur="check()" />
			&nbsp;<font id="tip"></font> <br />
			<br /> 密 &nbsp;码 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="password" name="password" />
			<br />
			<br /> 确认密 码
			<input type="password" name="passwordAgain" />
			<br /> <font color="red"> ${error} </font><br /> &nbsp;&nbsp;
			<input type="submit" value="submit" id="submit">
		</form>
	</div>
</body>
</html>