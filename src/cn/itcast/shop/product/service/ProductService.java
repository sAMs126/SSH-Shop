package cn.itcast.shop.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.product.dao.ProductDao;
import cn.itcast.shop.product.entity.Product;
import cn.itcast.shop.utils.PageBean;

/**
 * 商品业务层
 * @author admin
 *
 */
@Transactional
public class ProductService {
	//注入ProductDao
	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	// 首页热门商品查询
	public List<Product> findHot() {
		return productDao.findHot();
	}

	// 首页最新商品查询
	public List<Product> findNew() {
		return productDao.findNew();
	}

	// 根据商品ID查询商品
	public Product findByPid(Integer pid) {
		return productDao.findByPid(pid);
	}

	// 根据一级分类的Cid带有分页的查询商品
	public PageBean<Product> findByPageCid(Integer cid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// 设置当前页数
		pageBean.setPage(page);
		// 设置每页显示记录数
		int limit = 8;
		pageBean.setLimit(limit);
		// 设置总的记录数
		int totalCount = 0;
		totalCount = productDao.findCountCid(cid);	// 根据cid查询数据库所属商品总的个数
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		int totalPage = 0;
//		totalPage = Math.ceil(totalCount / limit);	ceil()向上取整
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit ;
		}else {
			totalPage = totalCount / limit + 1 ;
		}
		pageBean.setTotalPage(totalPage);
		// 每页显示的数据集合
		// 每页的商品信息从第几个开始
		int began = (page -1 ) * limit;
		// 每页显示多少条信息
		int number = limit;
		List<Product> list = productDao.findByPageCid(cid,began,number);
		pageBean.setList(list);
		return pageBean;
	}

	// 根据二级分类的Csid带有分页的查询商品
	public PageBean<Product> findByCsid(Integer csid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// 设置当前页数
		pageBean.setPage(page);
		// 设置每页显示记录数
		int limit = 8;
		pageBean.setLimit(limit);
		// 设置总的记录数
		int totalCount = 0;
		totalCount = productDao.findByCsid(csid);	// 根据cid查询数据库所属商品总的个数
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		int totalPage = 0;
//		totalPage = Math.ceil(totalCount / limit);	ceil()向上取整
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit ;
		}else {
			totalPage = totalCount / limit + 1 ;
		}
		pageBean.setTotalPage(totalPage);
		// 每页显示的数据集合
		// 每页的商品信息从第几个开始
		Integer began = (page -1 ) * limit;
		// 每页显示多少条信息
		Integer number = limit;
		List<Product> list = productDao.findByCsid(csid,began,number);
		pageBean.setList(list);
		return pageBean;
	}

	// 分页查询商品
	public PageBean<Product> findByPage(Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		// 设置当前页数
		pageBean.setPage(page);
		// 设置每页显示记录数
		int limit = 10;
		pageBean.setLimit(limit);
		// 设置总的记录数
		int totalCount = productDao.findCount();	// 根据cid查询数据库所属商品总的个数
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit ;
		}else {
			totalPage = totalCount / limit + 1 ;
		}
		pageBean.setTotalPage(totalPage);
		// 每页显示的数据集合
		Integer began = (page -1 ) * limit;
		Integer number = limit;
		List<Product> list = productDao.findByPage(began,number);
		pageBean.setList(list);
		return pageBean;
	}

	// 保存商品信息
	public void save(Product product) {
		productDao.save(product);
	}

	// 删除商品信息
	public void delete(Product product) {
		productDao.delete(product);
	}

	// 修改商品信息
	public void update(Product product) {
		productDao.update(product);
	}
	
}
