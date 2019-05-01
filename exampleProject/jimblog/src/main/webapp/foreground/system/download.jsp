<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/download_icon.png"/>
		抱歉，本站源码尚在整理
	</div>
	<div>
	<p>
    <br/>
</p>
<p>
你可移步java1234小峰的博客<a href="http://blog.java1234.com/">blog.java1234.com</a>下载
</p>
<p>本网站基于java1234小峰的博客改造,目前是<font color="red">未完成品</font>,即使如此也想查看改编后的源码的，可邮箱联系，邮箱在网站上方</p>
<p>本博客<font color="red">今年</font>内会完成所有必备功能并根据实际运行情况做一些优化改动</p>
<p>博客项目整体完成并整理好后会将源码发布到GitHub,GitHub上也有我的一些练手项目，大多是基于java1234的视频项目的改编，经过小锋的授权可以上传到GitHub</p>
</div>
<br/>
<br/>
<p>下面是本博客的介绍，抄写自java1234小峰的博客并根据改编情况做了一些修改:</p>
<p>
    本站源码使用J2EE开发；
</p>
    <br/>
<p>
    使用<del>Spring4</del><ins>Spring5</ins>+Springmvc+Mybatis3架构，
</p>
<p>
    采用Mysql数据库；
</p>
<p>
    使用Maven3管理项目，<del>使用Shiro作为项目安全框架，使用Lucene作为全文检索，</del>支持restful风格；
</p>
<p>
    前台网页使用主流的Bootstrap3 UI框架；后台管理使用主流易用的EasyUI轻量级框架；
</p>
<p>
    数据库连接池使用的是阿里巴巴的Druid；
</p>
<p>
    在线编辑器使用了百度的UEditor，支持单图，多图上传，支持截图上传，支持代码高亮特性；
</p>
<br/>
<br/>
<p>都看到这了还不去下载看看吗</p>
<br/>
<p align="right" style="margin-right: 50px">2018.2.25</p>
<p align="right" style="margin-right: 76px">Jim</p>
<br/>
</div>
<div class="data_list">
	<!--PC和WAP自适应版-->
	<div id="SOHUCS" sid="download_jimblog"></div>
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