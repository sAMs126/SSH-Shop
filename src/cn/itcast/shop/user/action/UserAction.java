package cn.itcast.shop.user.action;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import cn.itcast.shop.user.entity.User;
import cn.itcast.shop.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	// 1.提供实体类,作为使用模型驱动的对象
	private User user = new User();

	// 2.提供一个getModel方法
	public User getModel() {
		return user;
	}

	// 3.注入UserService
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 4.接收用户输入的验证码
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	/**
	 * 跳转到注册页面
	 */
	public String registPage() {
		return "registPage";
	}

	/**
	 * AJAX进行异步校验用户名
	 * 
	 * @throws IOException
	 */
	public String findByName() throws IOException {
		// 在模型驱动得到User的信息后,调用service进行查询
		User existUser = userService.findByUserName(user.getUsername());
		// AJAX异步操作,不需要页面跳转,只需要获得response对象,向页面输入提示即可
		HttpServletResponse response = ServletActionContext.getResponse();
		// 输出中文,设置字符集编码
		response.setContentType("text/html;charset=UTF-8");
		// 对查询的结果对象做判断
		if (existUser != null) {
			// 查询到该用户:用户名已存在
			response.getWriter().println("<font color='red'>用户名已经存在</font>");
		} else {
			// 没查到该用户:用户名可以使用
			response.getWriter().println("<font color='green'>用户名可以使用!</font>");
		}
		return NONE;
	}

	/**
	 * 用户注册
	 */
	public String regist() {
		// 判断验证码:
		// 从Session中获取验证码的真值
		String sessionCode = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(sessionCode)){
			// 验证码不相等
			this.addActionError("验证码输入错误!");
			return "checkError";
		}
		userService.save(user);
		// 向msg页面回显信息
		this.addActionMessage("注册成功!请去邮箱进行激活!");
		return "msg";
	}

	/**
	 * 用户激活
	 */
	public String active() {
		// 根据激活码查询用户
		User existUser = userService.findByCode(user.getCode());
		// 对查到的信息进行判断
		if (existUser == null) {
			// 激活码错误
			// 用户没查到,激活码可能出错
			this.addActionMessage("激活失败!激活码错误!");
		} else {
			// 激活成功
			// 修改用户状态,1:已激活;code=null:只允许激活一次
			existUser.setState(1);
			existUser.setCode(null);
			// 返回修改信息
			userService.update(existUser);
			this.addActionMessage("激活成功!请去登录!");
		}
		return "msg";
	}

	/**
	 * 跳转到登录页面
	 */
	public String loginPage() {
		return "loginPage";
	}

	/**
	 * 用户登录
	 */
	public String login() {
		// 模型驱动获取用户信息: username,password,state
		// 查询用户:传过去的只有用户名和密码,传回来的是整个user对象信息
		User existUser = userService.login(user);
		// 对查询结果进行判断
		if (existUser == null) {
			// 登陆失败
			this.addActionError("登陆失败:请检查登录信息或前去激活!");
			return LOGIN;
		} else {
			// 登录成功
			// 将用户信息存入session中
			ServletActionContext.getRequest().getSession()
					.setAttribute("existUser", existUser);
			// 进行页面跳转
			return "loginSuccess";
		}
	}
	
	/**
	 * 用户退出
	 */
	public String quit(){
		// 销毁Session
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}

}
