package cn.itcast.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.entity.User;
import cn.itcast.shop.utils.MailUtils;
import cn.itcast.shop.utils.UUIDUtils;

/**
 * 用户模块业务层 加入注解，进行事务的管理
 * 
 * @author admin
 * 
 */
@Transactional
public class UserService {
	// 注入UserDao
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// 按用户名查询用户
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	// 实现用户注册
	public void save(User user) {
		// 使用UUID创建随机激活码,表中设置长度为64位
		String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
		user.setState(0);// (0:未激活 1:已激活)
		user.setCode(code);// 设置激活码
		userDao.save(user);
		// 调用Util发送Email
		MailUtils.sendMail(user.getEmail(), code);
	}

	// 根据激活码查询用户
	public User findByCode(String code) {
		return userDao.findByCode(code);
	}

	// 修改用户状态
	public void update(User existUser) {
		userDao.update(existUser);
	}

	// 用户登录
	public User login(User user) {
		return userDao.login(user);
	}

}
