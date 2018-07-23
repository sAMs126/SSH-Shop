package cn.itcast.shop.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.entity.AdminUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 后台权限校验拦截器
 * 	* 没有登录的用户,不能进行访问
 * @author admin
 *
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	// 执行拦截的方法
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		// 判断session中是否保存了后台用户的信息
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser == null){
			// 没有登录而进行访问
			// 判断访问获取 getAction() 不确定，但所有的action都继承了ActionSupport
			ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
			actionSupport.addActionError("亲！您好没有登录！无访问权限！");
			return "noLogin";
		}else{
			// 用户已登录
			return actionInvocation.invoke();
		}
	}
}
