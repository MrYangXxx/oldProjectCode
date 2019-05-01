<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib uri="/struts-tags" prefix="s" %>
删除成功!<br>
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
<a href="<s:property value="url"/>">立即返回列表界面</a>
<script type="text/javascript">
delayURL(' <s:property value="url"/>')
</script>