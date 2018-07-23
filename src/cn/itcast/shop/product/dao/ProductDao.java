package cn.itcast.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.product.entity.Product;
import cn.itcast.shop.utils.PageHibernateCallback;

/**
 * 商品持久层
 * @author admin
 *
 */
public class ProductDao extends HibernateDaoSupport {

	// 首页显示热门商品(带分页查询)
	public List<Product> findHot() {
	  /* 分页查询两种方法:
	   * 1.使用离线查询
	   * 2.使用hibernate模板中的execute方法,传入一个hibernateCallBack接口,
	   *   实现该接口的同时会传入一个session对象,之后可以用hibernate传统的api执行
	   */
		// 使用离线查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// 查询热门商品的条件: is_hot = 1
		criteria.add(Restrictions.eq("is_hot", 1));
		// 倒序排序输出,根据上传日期倒序排序
		criteria.addOrder(Order.desc("pdate"));
		// 进行查询
		List<Product> list = this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return list;
	}

	// 首页显示最新商品(带分页查询)
	public List<Product> findNew() {
		// 使用离线查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.addOrder(Order.desc("pdate"));
		List<Product> list = this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return list;
	}

	// 根据商品ID查询商品(只查询一个)
	public Product findByPid(Integer pid) {
		return this.getHibernateTemplate().get(Product.class, pid);
	}

	// 根据cid查询数据库所属商品总的个数
	public int findCountCid(Integer cid) {
		String hql = "select count(*) from Product p where p.categorySecond.category.cid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql,cid);
		if(list != null && list.size() > 0){
			// 将Long型转换为int类型
			return list.get(0).intValue();
		}
		return 0;
	}

	// 根据cid查询商品的集合
	public List<Product> findByPageCid(Integer cid, int began, int number) {
		/* sql : select p.* from category c,categorysecond cs, product p
		 * 		 where c.cid = cs.cid
		 * 		   and cs.csid = p.csid
		 *         and c.cid = 1  
		 */
		/* hql : select p from Category c,CategorySecond cs, Product p
		 * 		 where c.cid = cs.category.cid
		 * 		   and cs.csid = p.categorySecond.csid
		 *         and c.cid = ?  
		 */
		// 使用join进行连接
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		// 分页查询的第二种方法:实现hibernateCallBack接口
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, new Object[]{cid}, began, number));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	// 根据Csid查询数据库所属商品总的个数
	public int findByCsid(Integer csid) {
		String hql ="select count(*) from Product p where p.categorySecond.csid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql, csid);
		if(list != null && list.size() > 0){
			// 将Long型转换为int类型
			return list.get(0).intValue();
		}
		return 0;
	}

	// 根据Csid查询商品的集合
	public List<Product> findByCsid(Integer csid, Integer began, Integer number) {
		String hql = "select p from Product p join p.categorySecond cs where cs.csid =?";
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, new Object[]{csid}, began, number));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	// 统计商品个数
	public int findCount() {
		String hql ="select count(*) from Product";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return 0;
	}

	// 查询商品信息（带分页）
	public List<Product> findByPage(Integer began, Integer number) {
		String hql ="from Product order by pdate desc";
		List<Product> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql, null, began, number));
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	// 保存商品信息
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}

	// 删除商品信息
	public void delete(Product product) {
		this.getHibernateTemplate().delete(product);
	}

	// 修改商品信息
	public void update(Product product) {
		this.getHibernateTemplate().update(product);
	}

}
