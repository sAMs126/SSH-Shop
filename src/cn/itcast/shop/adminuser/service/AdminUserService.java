package cn.itcast.shop.adminuser.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.adminuser.dao.AdminUserDao;
import cn.itcast.shop.adminuser.entity.AdminUser;

/**
 * 后台管理业务层
 * @author admin
 *
 */
@Transactional
public class AdminUserService {
	
	// 注入AdminUserDao
	private AdminUserDao adminUserDao;

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	// 后台登录
	public AdminUser login(AdminUser adminUser) {
		return adminUserDao.login(adminUser);
	}
	
}
