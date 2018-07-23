package cn.itcast.shop.order.adminaction;

import java.util.List;

import cn.itcast.shop.order.entity.Order;
import cn.itcast.shop.order.entity.OrderItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 后台订单管理Action
 * @author admin
 *
 */
public class AdminOrderAction extends ActionSupport implements ModelDriven<Order>{
	// 1. 注入Order
	private Order order = new Order();

	public Order getModel() {
		return order;
	}
	
	// 2. 注入订单Service
	private OrderService orderService;
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// 3. 接收Page参数
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}
	
	
	// 查询所有订单信息
	public String findAll(){
		// 分页查询
		PageBean<Order> pageBean = orderService.findByPage(page);
		// 通过值栈传递pageBean
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	// 根据订单id查询订单信息
	public String findOrderItem(){
		List<OrderItem> List = orderService.findOrderItem(order.getOid());
		// * 将list集合转成json对象 --> 在jsp中接收并解析
		// 通过值栈显示到一个单独的jsp上，再将jsp在页面指定位置显示
		ActionContext.getContext().getValueStack().set("List", List);
		return "findOrderItem";
	}
	
	// 修改订单状态
	public String updateState(){
		// 根据订单id查询订单
		Order currorder = orderService.findByOid(order.getOid());
		// 修改订单状态
		currorder.setState(3);
		orderService.update(currorder);
		return "updateState";
	}
}
