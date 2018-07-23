package cn.itcast.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.category.entity.Category;

/**
 * 一级分类持久层
 * @author admin
 *
 */
public class CategoryDao extends HibernateDaoSupport {
	
	// 查询所有一级分类
	public List<Category> findAll() {
		String hql = "from Category";
		List<Category> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	// 保存一级分类的方法
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	// 根据cid查询一级分类
	public Category findByCid(Integer cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	// 删除一级分类
	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	// 修改一级分类
	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}	
	
}
