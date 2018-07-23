package cn.itcast.shop.categorysecond.entity;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.category.entity.Category;
import cn.itcast.shop.product.entity.Product;

/**
 * 二级分类实体类
 * @author admin
 *
 */
public class CategorySecond {
	private Integer csid;
	private String csname;
	// 所属的一级分类: cid 为外键
	/*
	 * 二级分类:一级分类 (N:1)
	 * 在多方创建一个所属一级分类对象  
	 */
	private Category category;
	// 创建商品类的集合
	private Set<Product> products = new HashSet<Product>();
	
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	public Integer getCsid() {
		return csid;
	}
	public void setCsid(Integer csid) {
		this.csid = csid;
	}
	public String getCsname() {
		return csname;
	}
	public void setCsname(String csname) {
		this.csname = csname;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
