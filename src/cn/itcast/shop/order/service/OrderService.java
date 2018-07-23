package cn.itcast.shop.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.order.dao.OrderDao;
import cn.itcast.shop.order.entity.Order;
import cn.itcast.shop.order.entity.OrderItem;
import cn.itcast.shop.utils.PageBean;

/**
 * 订单业务层
 * @author admin
 *
 */
@Transactional
public class OrderService {
	// 注入OrderDao
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	// 保存订单信息
	public void save(Order order) {
		orderDao.save(order);
	}

	// 查询用户所有订单
	public PageBean<Order> findByPageUid(Integer uid, Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		// 设置当前页数
		pageBean.setPage(page);
		// 设置每页显示记录数
		Integer limit = 5;
		pageBean.setLimit(limit);
		// 设置总的记录数
		Integer totalCount = null;
		totalCount = orderDao.findByCountUid(uid);
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		Integer totalPage = null;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置每页显示数据集合
		// 每页的商品信息从第几个开始
		Integer began = (page -1 ) * limit;
		Integer number = limit;
		List<Order> list = orderDao.findByPageUid(uid,began,number);
		pageBean.setList(list);
		return pageBean;
	}

	// 根据订单id查询订单
	public Order findByOid(Integer oid) {
		return orderDao.findByOid(oid);
	}

	// 修改订单信息
	public void update(Order currOrder) {
		orderDao.update(currOrder);
	}

	// 分页查询订单信息
	public PageBean<Order> findByPage(Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		// 设置当前页数
		pageBean.setPage(page);
		// 设置每页显示记录数
		int limit = 10;
		pageBean.setLimit(limit);
		// 设置总的记录数
		int totalCount = orderDao.findByCount();
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置每页显示数据集合
		// 每页的商品信息从第几个开始
		int began = (page -1 ) * limit;
		int number = limit;
		List<Order> list = orderDao.findByPage(began,number);
		pageBean.setList(list);
		return pageBean;
	}

	// 根据订单id查询订单详情
	public List<OrderItem> findOrderItem(Integer oid) {
		return orderDao.findOrderItem(oid);
	}

	
}
