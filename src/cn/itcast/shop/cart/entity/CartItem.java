package cn.itcast.shop.cart.entity;

import cn.itcast.shop.product.entity.Product;

/**
 * 购物项对象
 * @author admin
 *
 */
public class CartItem {
	private Product product;	//商品信息
	private int count;			//购买某种商品的数量
	private double subtotal;	//购买某种商品的小计
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	//小计自动计算 --> 数量 * 价格
	public double getSubtotal() {
		return count * product.getShop_price();
	}
}
