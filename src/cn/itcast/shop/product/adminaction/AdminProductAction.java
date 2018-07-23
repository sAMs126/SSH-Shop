package cn.itcast.shop.product.adminaction;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.categorysecond.entity.CategorySecond;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.product.entity.Product;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 后台商品管理Action
 * @author admin
 *
 */
public class AdminProductAction extends ActionSupport implements ModelDriven<Product> {

	// 1. 注入Product
	private Product product = new Product(); 
	public Product getModel() {
		return product;
	}
	
	// 2. 注入商品Service
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	// 3. 接收参数
	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	// 4. 注入二级分类Service
	private CategorySecondService categorySecondService;
	
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	// 5. 文件上传需要的参数
	private File upload;	// 上传文件（与表单中的属性名一致）
	private String uploadFileName;	// 接收文件名称
	private String uploadContextType;	// 接收文件MIME类型
	
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}

	// 带分页的查询商品信息
	public String findAll(){
		// 调用service完成查询
		PageBean<Product> pageBean = productService.findByPage(page);
		// 将数据传递值栈上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	// 跳转到添加页面
	public String addPage(){
		// 查询所有二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		// 将数据传递值栈上
		ActionContext.getContext().getValueStack().set("csList", csList);
		// 页面跳转
		return "addPage";
	}
	
	// 保存商品信息
	public String save() throws IOException{
		// 调用service完成保存
		product.setPdate(new Date());
		// 保存图片上传路径
		if (upload != null) {
			// 获得文件上传的磁盘绝对路径
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			// 在磁盘上创建文件
			File destFile = new File(realPath + "//" + uploadFileName);
			// 文件上传
			FileUtils.copyFile(upload, destFile);
			product.setImage("products/" + uploadFileName);
		}
		// 保存商品信息
		productService.save(product);
		return "saveSuccess";
	}
	
	// 删除商品
	public String delete(){
		// 先查询再删除
		product = productService.findByPid(product.getPid());
		// 删除商品图片
		String path = product.getImage();
		if (path != null) {
			String realPath = ServletActionContext.getServletContext().getRealPath("/" + path);
			File file = new File(realPath);
			file.delete();
		}
		productService.delete(product);
		return "deleteSuccess";
	}
	
	// 跳转到编辑页面
	public String edit(){
		// 根据商品id查询该商品(本身已经在模型驱动中了，不需要再向页面上传)
		product = productService.findByPid(product.getPid());
		// 查询所有的二级分类
		List<CategorySecond> csList = categorySecondService.findAll();
		// 将二级分类的集合存入值栈中
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "edit";
	}
	
	// 修改商品的方法
	public String update() throws IOException{
		product.setPdate(new Date());
		// 文件上传(先删除原来图片，再更新图片)
		if (upload != null) {
			// 先删除
			String path = product.getImage();
			File file = new File( ServletActionContext.getServletContext().getRealPath("/" + path) );
			file.delete();
			// 再更新
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			File destFile = new File(realPath + "//" + uploadFileName);
			FileUtils.copyFile(upload, destFile);
			product.setImage("products/" + uploadFileName);
		}
		// 修改商品信息
		productService.update(product);
		return "updateSuccess";
	}
}
