$(function () {
	var shopId = getQueryString('shopid');
	var initUrl = '/o2o/shop/manage?shopid='+shopId;
	
	$.getJSON(initUrl, function(data) {
		if(data.redirect){
			window.location.href=data.url;
		}else{
			if(data.shopId!=undefined && data.shopId!=null){
				shopId=data.shopId;
			}
		}
	});
})