package cn.itcast.shop.order.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.order.entity.Order;
import cn.itcast.shop.order.entity.OrderItem;
import cn.itcast.shop.utils.PageBean;
import cn.itcast.shop.utils.PageHibernateCallback;

/**
 * 订单持久层
 * @author admin
 *
 */
public class OrderDao extends HibernateDaoSupport {

	// 保存订单信息
	public void save(Order order) {
		this.getHibernateTemplate().save(order);
	}

	// 用户订单个数统计
	public Integer findByCountUid(Integer uid) {
		String hql = "select count(*) from Order o where o.user.uid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql, uid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return null;
	}

	// 用户订单信息查询
	public List<Order> findByPageUid(Integer uid, Integer began, Integer number) {
		// 查询结果根据日期倒叙排序
		String hql = "from Order o where o.user.uid = ? order by ordertime desc";
		List <Order> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Order>(hql, new Object[]{uid}, began, number));
		return list;
	}

	// 根据订单id查询订单信息
	public Order findByOid(Integer oid) {
		return this.getHibernateTemplate().get(Order.class, oid);
	}

	// 修改订单信息
	public void update(Order currOrder) {
		this.getHibernateTemplate().update(currOrder);
	}

	// 查询订单个数
	public int findByCount() {
		String hql = "select count(*) from Order o";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	// 分页查询所有订单信息
	public List<Order> findByPage(int began, int number) {
		String hql = "from Order order by ordertime desc";
		List <Order> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Order>(hql, null, began, number));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	// 根据订单id查询订单详情
	public List<OrderItem> findOrderItem(Integer oid) {
		String hql = "from OrderItem oi where oi.order.oid = ?  ";
		List <OrderItem> list = this.getHibernateTemplate().find(hql,oid);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	
}
