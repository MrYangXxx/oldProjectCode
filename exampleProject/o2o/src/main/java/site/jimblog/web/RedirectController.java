package site.jimblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: ShopRedirectController</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Aug 20, 2018  
 * 
 */
@Controller
@RequestMapping("/")
public class RedirectController {

	@RequestMapping(value="/shopadmin/operation")
	public String shopOperation(){
		return "shop/shopedit";
	}
	
	@RequestMapping(value="/shopadmin/list")
	public String shopList(){
		return "shop/shoplist";
	}
	@RequestMapping(value="/shopadmin/manage")
	public String manage(){
		return "shop/shopmanage";
	}
	@RequestMapping(value="/product/manage")
	public String productManage(){
		return "shop/productmanage";
	}
	@RequestMapping(value="/product/categorymanage")
	public String productCategoryManage(){
		return "shop/productcategorymanage";
	}
	@RequestMapping(value="/awardmanage")
	public String awardManage(){
		return "shop/awardmanage";
	}
	@RequestMapping(value="/productbuycheck")
	public String productBuyCheck(){
		return "shop/productbuycheck";
	}
	@RequestMapping(value="/awarddelivercheck")
	public String awardDeliverCheck(){
		return "shop/awarddelivercheck";
	}
	@RequestMapping(value="/usershopcheck")
	public String userShopCheck(){
		return "shop/usershopcheck";
	}
	@RequestMapping(value="/shopadmin/authmanage")
	public String shopAuthManage(){
		return "shop/shopauthmanage";
	}
	@RequestMapping(value="/product/operation")
	public String productOperation(){
		return "shop/productedit";
	}
	@RequestMapping(value="/index")
	public String index(){
		return "frontend/index";
	}
	@RequestMapping(value="/shoplist")
	public String listShops(){
		return "frontend/shoplist";
	}
	@RequestMapping(value="/shopdetail")
	public String shopDetail(){
		return "frontend/shopdetail";
	}
	@RequestMapping(value="/productdetail")
	public String productDetail(){
		return "frontend/productdetail";
	}
}
