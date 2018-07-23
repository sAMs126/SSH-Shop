package cn.itcast.shop.index.action;

import java.util.List;

import cn.itcast.shop.category.entity.Category;
import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.product.entity.Product;
import cn.itcast.shop.product.service.ProductService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 访问首页的Action
 * @author admin
 *
 */
public class IndexAction extends ActionSupport {
	// 注入一级分类的Service
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	// 注入商品的Service
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * 访问首页
	 */
	public String execute(){
		// 访问首页Action时进行对一级分类的查询
		List<Category> cList = categoryService.findAll();
		/*
		 * 将一级分类存入到Session域中，用于页面的获取
		 * !不要用servletActionContext来存
		 * !因为首页是最先访问到的Action,要用ActionContext
		 * getSession()之后得到的是map集合，使用put()方法存入
		 */
		ActionContext.getContext().getSession().put("cList", cList);
		
		// 查询热门商品
		List<Product> hList = productService.findHot();
		// 保存到值栈中
		ActionContext.getContext().getValueStack().set("hList", hList);
		
		// 查询最新商品
		List<Product> nList = productService.findNew();
		// 保存到值栈中
		ActionContext.getContext().getValueStack().set("nList", nList);
		return "index";
	}
}
