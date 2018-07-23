package cn.itcast.shop.categorysecond.service;

import java.util.List;

import cn.itcast.shop.categorysecond.dao.CategorySecondDao;
import cn.itcast.shop.categorysecond.entity.CategorySecond;
import cn.itcast.shop.utils.PageBean;

/**
 * 二级分类管理业务层
 * @author admin
 *
 */
public class CategorySecondService {
	
	// 注入CategorySecondDao
	private CategorySecondDao categorySecondDao;

	public void setCategorySecondDao(CategorySecondDao categorySecondDao) {
		this.categorySecondDao = categorySecondDao;
	}

	// 根据page查询二级分类
	public PageBean<CategorySecond> findByPage(Integer page) {
		PageBean<CategorySecond> pageBean = new PageBean<CategorySecond>();
		// 设置当前页数
		pageBean.setPage(page);
		// 设置每页显示记录数
		int limit = 10;
		pageBean.setLimit(limit);
		// 设置总记录数
		int totalCount = categorySecondDao.findCount();
		pageBean.setTotalCount(totalCount);
		// 设置总页数
		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit ;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置每页显示的集合
		int begin = (page - 1) * limit; 
		int number = limit;
		List<CategorySecond> list = categorySecondDao.findByPage(begin,number);
		pageBean.setList(list);
		return pageBean;
	}

	// 保存二级分类
	public void save(CategorySecond categorySecond) {
		categorySecondDao.save(categorySecond);
	}

	// 根据csid查询二级分类
	public CategorySecond findByCsid(Integer csid) {
		return categorySecondDao.findByCsid(csid);
	}
	// 删除二级分类
	public void delete(CategorySecond categorySecond) {
		categorySecondDao.delete(categorySecond);
	}

	// 保存二级分类修改
	public void updte(CategorySecond categorySecond) {
		categorySecondDao.updte(categorySecond);
	}

	// 查询所有二级分类
	public List<CategorySecond> findAll() {
		return categorySecondDao.findAll();
	}
	
	
}
