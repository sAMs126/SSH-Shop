package cn.itcast.shop.product.action;

import java.util.List;

import cn.itcast.shop.category.entity.Category;
import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.product.entity.Product;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商品的Action对象
 * 
 * @author admin
 * 
 */
public class ProductAction extends ActionSupport implements
		ModelDriven<Product> {
	// 1. 实现模型驱动
	private Product product = new Product();

	public Product getModel() {
		return product;
	}

	// 2. 注入productService
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// 3. 单独接收一级分类Cid
	private Integer cid;

	public void setCid(Integer cid) {
		this.cid = cid;
	}
	// action本身就在数据栈中，action的属性也在栈中，提供一个get方法就能在页面中获取
	public Integer getCid() {
		return cid;
	}

	// 4. 接收当前页数（第一页）
	private int page;

	public void setPage(int page) {
		this.page = page;
	}
	
	// 5. 接收二级分类的Csid
	private Integer csid;
	
	public Integer getCsid() {
		return csid;
	}
	public void setCsid(Integer csid) {
		this.csid = csid;
	}
	// 4. 注入CategoryService
	// private CategoryService categoryService;
	//
	// public void setCategoryService(CategoryService categoryService) {
	// this.categoryService = categoryService;
	// }

	// 根据商品ID进行商品查询
	public String findByPid() {
		// 查询最新的商品(查询到一个对象)
		/*
		 * 由于已经实现了模型驱动的缘故，不用再使用 Product product =如果再重复写 Product product =
		 * 需要先存入值栈中，才能显示在页面上模型驱动的product已经放在了栈顶
		 */
		product = productService.findByPid(product.getPid());
		return "findByPid";
	}

	// 根据一级分类的Cid查询商品
	public String findByCid() {
		/*
		 * 查询一级分类: 1.已经在 CategoryDao 中定义了findAll()方法,注入一级分类的service直接调用
		 * List<Category> cList = categoryService.findAll();
		 * 2.session中已经含有一级分类,也可以直接在页面中从session中获取
		 */
		// 根据一级分类查询商品（带分页查询）
		PageBean<Product> pageBean = productService.findByPageCid(cid,page);
		// 将pageBean存入值栈
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}
	
	// 根据二级分类的Csid查询商品
	public String findByCsid() {
		// 根据二级分类查询商品（带分页查询）
		PageBean<Product> pageBean = productService.findByCsid(csid,page);
		// 将pageBean存入值栈
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}
}
