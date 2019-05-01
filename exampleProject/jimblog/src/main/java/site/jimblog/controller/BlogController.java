package site.jimblog.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import site.jimblog.entity.Blog;
import site.jimblog.entity.PageBean;
import site.jimblog.service.BlogService;
import site.jimblog.util.PageUtil;
import site.jimblog.util.StringUtil;

/**
 * 
 * <p>Title: BlogController</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Feb 22, 2018  
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	
	
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog=blogService.findById(id);
		String keyWords=blog.getKeyWord();
		if(StringUtil.isNotEmpty(keyWords)){
			String arr[]=keyWords.split(" ");
			mav.addObject("keyWords",StringUtil.filterWhite(Arrays.asList(arr)));			
		}else{
			mav.addObject("keyWords",null);			
		}
		mav.addObject("blog", blog);
		blog.setClickHit(blog.getClickHit()+1); 
		blogService.update(blog);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("blogId", blog.getId());
		map.put("state", 1); 
		mav.addObject("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id),blogService.getNextBlog(id),request.getServletContext().getContextPath()));
		mav.addObject("mainPage", "foreground/blog/view.jsp");
		mav.addObject("pageTitle",blog.getTitle()+"_JimBlog");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	private String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>尾页</p>");
		}else{
			pageCode.append("<p>下一篇<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一页</p>");
		}else{
			pageCode.append("<p>上一页<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="title",required=false)String title,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),5);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("title", title);
		List<Blog> blogList=blogService.list(map);
		for(Blog blog:blogList){
			List<String> imagesList=blog.getImagesList();
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src$=.jpg]"); 
			for(int i=0;i<jpgs.size();i++){
				Element jpg=jpgs.get(i);
				imagesList.add(jpg.toString());
				if(i==2){
					break;
				}
			}
		}
		mav.addObject("blogList", blogList);
		StringBuffer param=new StringBuffer(); 
		if(StringUtil.isNotEmpty(title)){
			param.append("title="+title+"&");
		}
		mav.addObject("pageCode",PageUtil.genPagination(request.getContextPath()+"/index.html", blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.addObject("pageTitle","Jim Blog");
		mav.setViewName("mainTemp");
		return mav;
	}
	
//	private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
//		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
//		StringBuffer pageCode=new StringBuffer();
//		if(totalPage==0){
//			return "";
//		}else{
//			pageCode.append("<nav>");
//			pageCode.append("<ul class='pager' >");
//			if(page>1){
//				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
//			}else{
//				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
//			}
//			if(page<totalPage){
//				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");				
//			}else{
//				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");				
//			}
//			pageCode.append("</ul>");
//			pageCode.append("</nav>");
//		}
//		return pageCode.toString();
//	}
}
