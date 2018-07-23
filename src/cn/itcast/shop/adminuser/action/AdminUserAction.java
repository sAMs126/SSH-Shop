package cn.itcast.shop.adminuser.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.entity.AdminUser;
import cn.itcast.shop.adminuser.service.AdminUserService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 后台管理的Action
 * @author admin
 *
 */
public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {
	
	// 1. 注入AdminUser
	private AdminUser adminUser = new AdminUser();
	public AdminUser getModel() {
		return adminUser;
	}
	
	// 2. 注入AdminUserService
	private AdminUserService adminUserService;
	
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
	
	
	// 后台登录
	public String login(){
		// 调用service完成登录
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if (existAdminUser == null) {
			// 登陆失败
			this.addActionError("用户名或密码错误！");
			return "loginFail";
		}else{
			// 登录成功
			ServletActionContext.getRequest().getSession().setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}
}
