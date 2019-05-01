$(function() {

	var shopId = getQueryString('shopid');

	var isEdit = shopId ? true : false;

	var shopInfoUrl = '/o2o/shop/getshopbyid?shopid=' + shopId;
	var initUrl = '/o2o/shop/getshopinitinfo';
	var editShopUrl = '/o2o/shop/registershop';
	if (isEdit) {
		editShopUrl = '/o2o/shop/updateshop';
	}

	function getInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				$('#img-id').val(shop.shopImg);
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				$('#area').attr('data-id', shop.area.areaId);
				$('#area').val(shop.area.areaName);
			//	$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
				console.log(data);
			}
		});
	}

	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#shop-category').removeAttr('disabled');
				$('#area').html(tempAreaHtml);
				console.log(data);
			}
		});
	}

	if (isEdit) {
		getInfo(shopId);
	} else {
		getShopInitInfo();
	}
	
	$('#submit').click(function() {
		var shop = {};
		if(isEdit){
			shop.shopId=shopId;
			shop.shopImg=$('#img-id').val();
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		if(shop.shopName==""||shop.shopAddr==""||shop.phone==""||shop.shopDesc==""){
			alert("基本信息不能为空");
        	return;
		}

		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find(
					'option').not(function() {
				return !this.selected;
			}).data('id')
		};
		shop.area = {
			areaId : $('#area').find('option').not(
					function() {
						return !this.selected;
					}).data('id')
		};

		var shopImg = $("#shop-img")[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : editShopUrl,
			type : 'POST',
			// contentType:
			// "application/x-www-form-urlencoded;
			// charset=utf-8",
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					if (isEdit){
						$('#captcha_img').click();
					} else{
						window.location.href="/shop/shoplist";
					}
				} else {
					$('#captcha_img').click();
					$('#j_captcha').val('');
					$.toast('提交失败！');
				}
			}
		});
	});
});