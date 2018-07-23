package cn.itcast.shop.category.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.category.dao.CategoryDao;
import cn.itcast.shop.category.entity.Category;

/**
 * 一级分类业务层
 * @author admin
 *
 */
@Transactional
public class CategoryService {
	// 注入CategoryDao
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	// 查询所有一级分类的集合
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	// 保存一级分类
	public void save(Category category) {
		categoryDao.save(category);
	}

	// 查询一级分类
	public Category findByCid(Integer cid) {
		return categoryDao.findByCid(cid);
	}

	// 删除一级分类
	public void delete(Category category) {
		categoryDao.delete(category);
	}

	// 修改一级分类
	public void update(Category category) {
		categoryDao.update(category);
	}
}
