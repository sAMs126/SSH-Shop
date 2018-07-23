package cn.itcast.shop.user.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.itcast.shop.user.entity.User;

/**
 * 用户模块持久层
 * 
 * @author admin
 * 
 */
public class UserDao extends HibernateDaoSupport {
	// 继承HibernateDaoSupport可以在sessionFactory注入Dao中后直接提供一个hibernate模板

	// 按名称查询该用户名是否为空
	public User findByUserName(String username) {
		// 通过hql语句进行查询
		String hql = "from User where username = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		// 将List集合转成对象的形式,创建数据库时假设username是唯一个,使用get(0)即可
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// 注册用户信息存入数据库
	public void save(User user) {
		this.getHibernateTemplate().save(user);
	}

	// 根据激活码查询用户
	public User findByCode(String code) {
		String hql = "from User where code = ?";
		List<User> list = this.getHibernateTemplate().find(hql, code);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// 修改用户状态
	public void update(User existUser) {
		this.getHibernateTemplate().update(existUser);
	}

	// 用户的登录查询
	public User login(User user) {
		String hql = "from User where username = ? and password = ? and state = ?";
		List<User> list = this.getHibernateTemplate().find(hql, user.getUsername(),user.getPassword(),1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	

}
