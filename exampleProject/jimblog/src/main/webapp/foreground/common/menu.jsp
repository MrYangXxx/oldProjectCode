<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	function checkData(){
		var q=document.getElementById("title").value.trim();
		if(q==null || q==""){
			alert("请输入您要查询的关键字！");
			return false;
		}else{
			window.location.href='${pageContext.request.contextPath}/blog/q.html?title='+q;
		}
	}
</script>
<div class="row">
	<div class="col-md-12" style="padding-top: 0px">
		<nav class="navbar" style="background-color: #606060">
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header" >
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="${pageContext.request.contextPath}/index.html"><font color="black"><strong>首页</strong></font></a>
		    </div>

		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" >
		      <ul class="nav navbar-nav">
		         <li><a href="${pageContext.request.contextPath}/index.html?typeId=13"><font color="black"><strong>书籍</strong></font></a></li>
		         <li><a href="${pageContext.request.contextPath}/index.html?typeId=5"><font color="black"><strong>随笔</strong></font></a></li>
		        <li><a href="${pageContext.request.contextPath}/ad.html"><font color="black"><strong>推广</strong></font></a></li> 
		        <li><a href="${pageContext.request.contextPath}/blogger/aboutMe.html"><font color="black"><strong>关于</strong></font></a></li>
		        <li><a href="${pageContext.request.contextPath}/download.html"><font color="black"><strong>本站源码下载</strong></font></a></li>
		      </ul>
		      <form class="navbar-form navbar-right" role="search">
		        <div class="form-group" >
		          <input type="text" id="title" name="title" value="${title }" class="form-control" placeholder="暂不开通">
		        </div>
		        <button type="button" class="btn btn-default" onclick="checkData()">搜索</button>
		        </form>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
	</div>
</div>