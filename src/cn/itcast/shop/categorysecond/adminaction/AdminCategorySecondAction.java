package cn.itcast.shop.categorysecond.adminaction;

import java.util.List;

import cn.itcast.shop.category.entity.Category;
import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.categorysecond.entity.CategorySecond;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 管理二级分类Action
 * @author admin
 *
 */
public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond> {

	// 1. 注入CategorySecond
	private CategorySecond categorySecond = new CategorySecond();
	
	public CategorySecond getModel() {
		return categorySecond;
	}

	// 2. 注入CategorySecondService
	private CategorySecondService categorySecondService;

	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	
	// 3. 接收参数
	// 接收page
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}
	
	// 4. 注入一级分类service
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 查询二级分类
	public String findAll(){
		PageBean<CategorySecond> pageBean = categorySecondService.findByPage(page);
		// 将pageBean中的数据保存到值栈中
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	// 跳转到添加二级分类页面
	public String addPage(){
		// 查询所有的一级分类
		List<Category> cList = categoryService.findAll();
		// 将一级分类显示到页面下拉列表中
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "addPage";
	}
	
	// 保存二级分类
	public String save(){
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}
	
	// 删除二级分类
	public String delete(){
		// 级联删除（配置cascade），先查询再删除
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		// 普通删除（非级连删除）
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}
	
	// 编辑二级分类
	public String edit(){
		// 根据二级分类csid查询二级分类的对象
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		// 查询所有一级分类
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "editSuccess";
	}
	
	// 保存二级分类修改
	public String update(){
		categorySecondService.updte(categorySecond);
		return "updateSuccess";
	}
	
}
