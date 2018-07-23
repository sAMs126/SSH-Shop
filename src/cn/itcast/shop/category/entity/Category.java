package cn.itcast.shop.category.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.categorysecond.entity.CategorySecond;

/**
 * 一级分类的实体类
 * @author admin
 *
 */
public class Category implements Serializable{
	private Integer cid;
	private String cname;
	/*
	 * 一级分类:二级分类(1:N)
	 * 在一方创建一个存放二级分类的集合
	 * (双向关联，两边都能查)
	 */
	private Set<CategorySecond> categorySeconds = new HashSet<CategorySecond>();
	
	public Set<CategorySecond> getCategorySeconds() {
		return categorySeconds;
	}
	public void setCategorySeconds(Set<CategorySecond> categorySeconds) {
		this.categorySeconds = categorySeconds;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
