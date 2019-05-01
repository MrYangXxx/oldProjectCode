package site.jimblog.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import site.jimblog.dto.Execution;
import site.jimblog.dto.ShopExecution;
import site.jimblog.entity.Area;
import site.jimblog.entity.HeadLine;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductCategory;
import site.jimblog.entity.Shop;
import site.jimblog.entity.ShopCategory;
import site.jimblog.service.AreaService;
import site.jimblog.service.HeadLineService;
import site.jimblog.service.ProductCategoryService;
import site.jimblog.service.ProductService;
import site.jimblog.service.ShopCategoryService;
import site.jimblog.service.ShopService;
import site.jimblog.util.HttpServletRequestUtil;

/**
 * <p>
 * Title: MainPageController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Sep 21, 2018
 * 
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private HeadLineService headLineService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("/listmainpageinfo")
	@ResponseBody
	private Map<String, Object> listMainPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		List<ShopCategory> shopCategorys = shopCategoryService.listShopCategory(null);
		modelMap.put("shopCategoryList", shopCategorys);
		HeadLine headLine = new HeadLine();
		headLine.setEnableStatus(1);
		List<HeadLine> headLines = headLineService.listHeadLine(headLine);
		modelMap.put("headLineList", headLines);
		modelMap.put("success", true);
		return modelMap;
	}

	@GetMapping("/listshopspageinfo")
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long parentId = HttpServletRequestUtil.getLong(request, "parentid");
		List<ShopCategory> shopCategorys = null;
		if (parentId != -1) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setParentId(parentId);
			shopCategorys = shopCategoryService.listShopCategory(shopCategory);
		} else {
			shopCategorys = shopCategoryService.listShopCategory(null);
		}
		modelMap.put("shopCategoryList", shopCategorys);
		List<Area> listArea = areaService.listArea();
		modelMap.put("areaList", listArea);
		modelMap.put("success", true);
		return modelMap;
	}

	@GetMapping(value = "/listshops")
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if ((pageIndex > -1) && (pageSize > -1)) {
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.listShop(shopCondition, new PageBean(pageIndex, pageSize));
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}

		return modelMap;
	}

	private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		if (parentId != -1L) {
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			shopCondition.setParentCategory(parentCategory);
		}
		if (shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}

		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
	
	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != -1) {
			shop = shopService.getByShopId(shopId);
			productCategoryList = productCategoryService.listProductCategory(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
			Execution<Product> pe = productService.listProduct(productCondition, new PageBean(pageIndex, pageSize));
			modelMap.put("productList", pe.getList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if (productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			productCondition.setProductName(productName);
		}
		productCondition.setEnableStatus(1);
		return productCondition;
	}
	
	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if (productId != -1) {
			product = productService.getOne(productId);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}


}
