<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="data_list">
	<div class="data_list_title">
		<img
			src="${pageContext.request.contextPath}/static/images/download_icon.png" />
		阿里云产品推广
	</div>
	<div>
		<p>
			<br />
		</p>
		<p>阿里云服务器介绍</p>
		<p>阿里云服务器（Elastic Compute Service,
			ECS）是一种处理能力可弹性伸缩的计算服务，其管理方式比物理服务器更简单高效。
			阿里云服务器帮助您快速构建更稳定、安全的应用，降低开发运维的难度和整体IT成本，使您能够更专注于核心业务创新。</p>

		
		<p>
			推广地址，点<a
				href="https://promotion.aliyun.com/ntms/act/ambassador/sharetouser.html?userCode=vjtlq61e&productCode=DingdingC1&utm_source=vjtlq61e">这里</a>
			，领卷可享优惠购买阿里云服务器等一系列阿里云产品
		</p>
		<p>学生价格低至9.9/月，趁着没毕业不来一发吗？</p>
		<br /> <br />
		
		<p>搭建博客其实很简单，用已经部署好的环境按照攻略改一下配置文件就出来了</p>
		<p>本人试过用ECS搭建WordPress博客(未部署环境的镜像),不到1小时搞定，用轻量级服务器+部署好的镜像(WordPress版)，十来分钟，独享虚机，这个只粗略玩过，貌似只能放静态网页，把网页放到指定地方即可访问。。</p>
		<p>当然，这只是最基本的搭建，域名购买，网站备案，博客的美化乃至最重要的博客的内涵所在-文章，都需要博主自己的精心经营</p>
		<p>
			感兴趣的可以去<a
				href="https://edu.aliyun.com/lab/?spm=5176.11105641.740944.4.IhgTKN">阿里云大学开放实验室</a>感受一下，搭建完成后可以ip访问，当然，是有时间限制的
		</p>
		<br/>
		<p>本网站部署于阿里云服务器，为了满足个人瞎折腾的欲望没用阿里云服务器提供的部署好环境的镜像，而是自己搭建</p>
		<p>服务器环境是CentOS 7,自己安装的 openjdk-1.8 + mysql-5.7 + tomcat-8.5 及一些必须软件</p>
		<p>
			后续会发布关于部署的文章，
			<del>感觉会被黑，写个文章熟悉部署，为重装做准备</del>
		</p>
	</div>
	<br /> <br />
	<p>当学到的知识越多，越发觉自身记忆力水平弱小，即便不自己做一个博客，在一些技术博客论坛注册个账号，发布文章记录下自己的学习历程，也是非常有价值的事情，如果能帮到遇到同样问题的人，岂不更是一大乐事？</p>
	<br/>
	<br/>
	<p align="right" style="margin-right: 50px">2018.2.27</p>
<p align="right" style="margin-right: 76px">Jim</p>
</div>
<div class="data_list">
	<!--PC和WAP自适应版-->
	<div id="SOHUCS" sid="ad_jimblog"></div>
	<script type="text/javascript">
		(function() {
			var appid = 'cytsNxHCH';
			var conf = 'prod_af9b189195accf3a67f3dc1d200399c5';
			var width = window.innerWidth
					|| document.documentElement.clientWidth;
			if (width < 960) {
				window.document
						.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="http://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id='
								+ appid + '&conf=' + conf + '"><\/script>');
			} else {
				var loadJs = function(d, a) {
					var c = document.getElementsByTagName("head")[0]
							|| document.head || document.documentElement;
					var b = document.createElement("script");
					b.setAttribute("type", "text/javascript");
					b.setAttribute("charset", "UTF-8");
					b.setAttribute("src", d);
					if (typeof a === "function") {
						if (window.attachEvent) {
							b.onreadystatechange = function() {
								var e = b.readyState;
								if (e === "loaded" || e === "complete") {
									b.onreadystatechange = null;
									a()
								}
							}
						} else {
							b.onload = a
						}
					}
					c.appendChild(b)
				};
				loadJs("http://changyan.sohu.com/upload/changyan.js",
						function() {
							window.changyan.api.config({
								appid : appid,
								conf : conf
							})
						});
			}
		})();
	</script>
</div>