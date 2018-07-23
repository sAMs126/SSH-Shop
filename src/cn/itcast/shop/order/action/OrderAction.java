package cn.itcast.shop.order.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.entity.Cart;
import cn.itcast.shop.cart.entity.CartItem;
import cn.itcast.shop.order.entity.Order;
import cn.itcast.shop.order.entity.OrderItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.user.entity.User;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 订单管理的Action
 * @author admin
 *
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
	// 1. 模型驱动注入Order对象
	private Order order = new Order();
	
	public Order getModel() {
		return order;
	}
	
	// 2. 注入OrderService
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// 3. 接收参数
	// 接收page参数
	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	//	接收支付通道编码
	private String pd_FrpId;
	
	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	// 接收付款成功后的响应数据
	private String Order;
	private String Amt;
	
	public void setOrder(String order) {
		Order = order;
	}

	public void setAmt(String amt) {
		Amt = amt;
	}

	// 提交订单，生成订单
	public String save(){
		// 1. 保存数据到数据库
		// 从session的购物车信息中获取总计的数据
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		// 判断是否经过购物车页面进入到订单页面
		if (cart == null) {
			this.addActionError("亲！您还没有购物！请先去购物！");
			return "msg";
		}
		// 补全订单数据
		order.setTotal(cart.getTotal());
		order.setState(1); //1: 未付款	2:已经付款，但是没有发货	3:已经发货，没有确认收货	4:交易完成
		order.setOrdertime(new Date());
		// 设置订单中的订单项
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem(); 
			// 数量
			orderItem.setCount(cartItem.getCount());
			// 小计
			orderItem.setSubtotal(cartItem.getSubtotal());
			// 商品
			orderItem.setProduct(cartItem.getProduct());
			// 订单的对象
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		// 订单所属的用户
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		// 判断用户是否登录
		if (existUser == null) {
			this.addActionError("亲！您还没有登录！请先登录！");
			return "login";
		}
		order.setUser(existUser);
		orderService.save(order);
		// 2. 将订单对象显示到页面上
		// 通过值栈的方式进行显示：因为要显示的Order对象就是模型驱动的使用对象
		// 3! 清空购物车
		cart.clearCart();
		return "saveSuccess";
	}
	
	// 用户订单查询
	public String findByUid(){
		// 根据用户的uid查询
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		// 调用service进行查询
		Integer uid = existUser.getUid();
		PageBean<Order> pageBean = orderService.findByPageUid(uid,page);
		// 通过值栈将分页数据显示到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}
	
	// 根据订单的Id查询订单
	public String findByOid(){
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}
	
	// 支付订单
	public String payOrder() throws IOException{
		// 修改订单数据
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		// 修改订单
		orderService.update(currOrder);
		// 为订单付款
		// 付款需要的参数:
		String Cmd = "Buy"; // 业务类型:
		String MerId = "Itcast_Shop";// 商户编号:
		String Order = order.getOid().toString();// 订单编号:
		String Amt = "0.01"; // 付款金额:
//		String Cur = "CNY"; // 交易币种:
		// 支付成功后跳转的页面路径
//		String Url = "http://localhost:8080/Shop/order_callBack.action"; // 商户接收支付成功数据的地址:
		String FrpId = this.pd_FrpId;// 支付通道编码:
//		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
		// 向易宝发送请求:
		StringBuffer sb = new StringBuffer("http://localhost:8080/Shop/payment_sendInfo.action?");
		sb.append("Cmd=").append(Cmd).append("&");
		sb.append("MerId=").append(MerId).append("&");
		sb.append("Order=").append(Order).append("&");
		sb.append("Amt=").append(Amt).append("&");
//		sb.append("Cur=").append(Cur).append("&");
//		sb.append("Url=").append(Url).append("&");
		sb.append("FrpId=").append(FrpId).append("&");
//		sb.append("keyValue=").append(keyValue);
		
		// 重定向:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
//		System.out.println(sb);
		return NONE;
	}
	
	// 付款成功后的转向
	public String callBack(){
		// 修改订单状态：修改为已经付款
		Order currOrder = orderService.findByOid(Integer.parseInt(Order));
		currOrder.setState(2);
		orderService.update(currOrder);
		// 在页面上显示付款成功信息
		this.addActionMessage("订单付款成功：订单编号："+Order+";付款的金额："+Amt);
		return "msg";
	}
	
	// 修改订单状态（确认收货）
	public String updateState(){
		// 根据订单id查询订单
		Order currorder = orderService.findByOid(order.getOid());
		// 修改订单状态
		currorder.setState(4);
		orderService.update(currorder);
		return "updateState";
	}
}
