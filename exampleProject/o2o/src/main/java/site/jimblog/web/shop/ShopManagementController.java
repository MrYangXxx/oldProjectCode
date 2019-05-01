package site.jimblog.web.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.jimblog.dto.ShopExecution;
import site.jimblog.entity.Area;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Person;
import site.jimblog.entity.Shop;
import site.jimblog.entity.ShopCategory;
import site.jimblog.enums.ShopStateEnum;
import site.jimblog.service.AreaService;
import site.jimblog.service.ShopCategoryService;
import site.jimblog.service.ShopService;
import site.jimblog.util.CodeUtil;
import site.jimblog.util.HttpServletRequestUtil;

/**
 * <p>
 * Title: ShopManagementController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Jun 15, 2018
 * 
 */
@Controller
@RequestMapping("/shop")
public class ShopManagementController {

	@Resource
	private ShopService shopService;
	@Resource
	private ShopCategoryService shopCategoryService;
	@Resource
	private AreaService areaService;

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopid");
		if (shopId <= 0) {
			Object currentShop = HttpServletRequestUtil.getSessionAttr(request, "currentShop");
			if (currentShop == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			} else {
				modelMap.put("redirect", false);
				modelMap.put("shopId", ((Shop) currentShop).getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			HttpServletRequestUtil.setSessionAttr(request, "currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// Person user=(Person) HttpServletRequestUtil.getSessionAttr(request,
		// "user");
		Person user = new Person();
		user.setUserId(8L);
		user.setName("Jim");
		Long userId = user.getUserId();
		Shop shopCondition = new Shop();
		shopCondition.setOwnerId(userId);
		ShopExecution se = shopService.listShop(shopCondition, new PageBean(1, 15));
		modelMap.put("success", true);
		modelMap.put("shopList", se.getShopList());
		modelMap.put("user", user);
		return modelMap;
	}

	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopid");
		if (shopId > 0) {
			Shop shop = shopService.getByShopId(shopId);
			List<Area> listArea = areaService.listArea();
			modelMap.put("shop", shop);
			modelMap.put("areaList", listArea);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping("/getshopinitinfo")
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<>();
		try {
			List<ShopCategory> shopCategoryList = shopCategoryService.listShopCategory(new ShopCategory());
			List<Area> areaList = areaService.listArea();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping("/registershop")
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<>();
		// check verify code
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		// 1.get shop info
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}

		// 2.register
		if (shop != null && shopImg != null) {
			Person person = (Person) HttpServletRequestUtil.getSessionAttr(request, "user");
			shop.setOwnerId(person.getUserId());
			ShopExecution se = shopService.saveShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				@SuppressWarnings("unchecked")
				List<Shop> listShop = (List<Shop>) HttpServletRequestUtil.getSessionAttr(request, "shopList");
				if (listShop == null || listShop.size() == 0) {
					listShop = new ArrayList<>();
				}
				listShop.add(se.getShop());
				HttpServletRequestUtil.setSessionAttr(request, "shopList", listShop);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
	}

	@RequestMapping("/updateshop")
	@ResponseBody
	private Map<String, Object> updateShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// check verify code
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		// 1.get shop info
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}

		// 2.update
		if (shop != null && shop.getShopId() != null) {
			ShopExecution se = shopService.updateShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	}

}
