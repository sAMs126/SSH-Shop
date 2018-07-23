package cn.itcast.shop.category.adminaction;

import java.util.List;

import cn.itcast.shop.category.entity.Category;
import cn.itcast.shop.category.service.CategoryService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 后台一级分类管理的Action
 * @author admin
 *
 */
public class AdminCategoryAction extends ActionSupport implements ModelDriven<Category>{
	
	// 1. 注入Category
	private Category category = new Category();
	
	public Category getModel() {
		return category;
	}
	// 2. 注入一级分类的Service
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 查询所有一级分类
	public String findAll(){
		// 查询所有一级分类
		List<Category> cList = categoryService.findAll();
		// 将集合的数据显示到页面
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "findAll";
	}
	
	// 保存一级分类
	public String save(){
		// 调用service保存一级分类
		categoryService.save(category);
		// 页面跳转
		return "saveSuccess";
	}
	
	// 删除一级分类
	public String delete(){
		// 使用模型驱动接收cid
		/*
		 * 删除一级分类时，级联删除二级分类
		 * 先根据id查询，再进行删除(hibernate特性)
		 */
		category = categoryService.findByCid(category.getCid());
		// 删除
		categoryService.delete(category);
		return "deleteSuccess";
	}
	
	// 编辑一级分类
	public String edit(){
		category = categoryService.findByCid(category.getCid());
		return "editSuccess";
	}
	
	// 修改一级分类
	public String update(){
		categoryService.update(category);
		return "updateSuccess";
	}
}
