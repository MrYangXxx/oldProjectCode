<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/about_icon.png"/>
		关于博主
	</div>
	<div style="padding: 30px">
	   ${blogger.proFile }
	</div>
	<p align="right" style="margin-right: 50px">2018.2.27</p>
<p align="right" style="margin-right: 76px">Jim</p>
</div>
<div class="data_list">
	<!--PC和WAP自适应版-->
	<div id="SOHUCS" sid="info_jimblog"></div>
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