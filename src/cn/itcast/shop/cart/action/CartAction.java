package cn.itcast.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.entity.Cart;
import cn.itcast.shop.cart.entity.CartItem;
import cn.itcast.shop.product.entity.Product;
import cn.itcast.shop.product.service.ProductService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 购物车Action
 * @author admin
 *
 */
public class CartAction extends ActionSupport {
	// 1.接收参数
	// 接收pid
	private Integer pid;
	// 接收数量count
	private Integer count;
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	// 2. 注入ProductService
	private ProductService productService; 
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// 将购物项添加到购物车
	public String addCart(){
		// 封装一个CartItem对象，调用Cart中addCart方法
		CartItem cartItem = new CartItem();
		// 设置数量
		cartItem.setCount(count);
		// 根据pid查询对应的商品信息
		Product product = productService.findByPid(pid);
		// 设置商品信息
		cartItem.setProduct(product);
		// 将购物项添加到购物车(从session中获得购物车)
		Cart cart = getCart();
		cart.addCart(cartItem);
		
		return "addCart";
	}
	
	// 清空购物车
	public String clearCart(){
		// 获取购物车对象
		Cart cart = getCart();
		// 调用购物车中清空的方法
		cart.clearCart();
		return "clearCart";
	}
	
	// 从购物车中移除购物项
	public String removeCart(){
		// 获取购物车对象
		Cart cart = getCart();
		// 调用购物车中移除的方法
		cart.removeCart(pid);
		return "removeCart";
	}
	
	// 页面转向到“我的购物车”
	public String myCart(){
		return "myCart";
	}
	
	/**
	 *	从session中获得购物车信息 
	 */
	private Cart getCart() {
		// 从session中获取
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		// 若是第一次添加，则创建一个新的购物车添加到session中
		if (cart == null) {
			cart = new Cart();
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
		}
		return cart;
	}
}
