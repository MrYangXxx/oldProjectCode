package site.jimblog.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.jimblog.dto.Execution;
import site.jimblog.entity.PageBean;
import site.jimblog.entity.Product;
import site.jimblog.entity.ProductCategory;
import site.jimblog.entity.Shop;
import site.jimblog.enums.StateEnum;
import site.jimblog.service.ProductCategoryService;
import site.jimblog.service.ProductService;
import site.jimblog.util.CodeUtil;
import site.jimblog.util.HttpServletRequestUtil;
import site.jimblog.util.ImageUtil;

/**
 * <p>
 * Title: ProductManagementController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Jim
 * @date Sep 15, 2018
 * 
 */
@Controller
@RequestMapping("/shop")
public class ProductManagementController {
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping("/saveproduct")
	@ResponseBody
	private Map<String, Object> saveProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// check verify code
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 1.get product info
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// 缩略图
		CommonsMultipartFile productImg = null;
		// 详情图
		List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
			for (int i = 0; i < ImageUtil.IMGMAXCOUNT; i++) {
				CommonsMultipartFile img = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
				if (img != null) {
					productImgs.add(img);
				} else {
					break;
				}
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2.save
		if (product != null && productImg != null) {
			Shop shop = (Shop) HttpServletRequestUtil.getSessionAttr(request, "currentShop");
			product.setShop(shop);
			Execution<Product> productExecution = productService.saveProduct(product, productImg, productImgs);
			if (productExecution.getState() == StateEnum.CHECK.getState()) {
				modelMap.put("success", true);

			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", productExecution.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品信息和商品图片不能为空");
		}
		return modelMap;
	}

	@RequestMapping("/getproductbyid")
	@ResponseBody
	public Map<String, Object> getProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long productId = HttpServletRequestUtil.getLong(request, "productid");
		Shop shop = (Shop) HttpServletRequestUtil.getSessionAttr(request, "currentShop");
		if (productId > 0) {
			Product product = productService.getOne(productId);
			List<ProductCategory> productCategorys = productCategoryService.listProductCategory(shop.getShopId());
			modelMap.put("productCategorys", productCategorys);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping("/getproductcategorylist")
	@ResponseBody
	public Map<String, Object> getProductCategoryList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Shop shop = (Shop) HttpServletRequestUtil.getSessionAttr(request, "currentShop");
		if (shop != null) {
			List<ProductCategory> productCategorys = productCategoryService.listProductCategory(shop.getShopId());
			modelMap.put("productCategorys", productCategorys);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping("/updateproduct")
	@ResponseBody
	public Map<String, Object> updateProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 判断是编辑还是上/下架
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 1.get product info
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// 缩略图
		CommonsMultipartFile productImg = null;
		// 详情图
		List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
			for (int i = 0; i < ImageUtil.IMGMAXCOUNT; i++) {
				CommonsMultipartFile img = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
				if (img != null) {
					productImgs.add(img);
				} else {
					break;
				}
			}
		} 
		// 2.save
		if (product != null && product.getProductId() !=null) {
			Shop shop = (Shop) HttpServletRequestUtil.getSessionAttr(request, "currentShop");
			product.setShop(shop);
			Execution<Product> productExecution = productService.updateProduct(product, productImg, productImgs);
			if (productExecution.getState() == StateEnum.CHECK.getState()) {
				modelMap.put("success", true);

			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", productExecution.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品信息不能为空");
		}
		return modelMap;
	}

	@RequestMapping("/productlist")
	@ResponseBody
	public Map<String, Object> getProductList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Shop shop = (Shop) HttpServletRequestUtil.getSessionAttr(request, "currentShop");
		Product productCondition = new Product();
		productCondition.setShop(shop);
		Execution<Product> pe = productService.listProduct(productCondition, new PageBean(0, 8));
		modelMap.put("success", true);
		modelMap.put("productList", pe.getList());
		return modelMap;
	}

}
