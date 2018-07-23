package cn.itcast.shop.utils;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class PageHibernateCallback<T> implements HibernateCallback<List<T>>{
	
	private String hql;         //用于传HQL语句
	private Object[] params;    //Object类型的数组，用于传HQL的参数
	private int startIndex;     //开始显示的记录数
	private int pageSize;       //页面总数

	public PageHibernateCallback(String hql, Object[] params,
			int startIndex, int pageSize) {
		super();
		this.hql = hql;
		this.params = params;
		this.startIndex = startIndex;
		this.pageSize = pageSize;
	}



	public List<T> doInHibernate(Session session) throws HibernateException,
			SQLException {
		//1 执行hql语句
		Query query = session.createQuery(hql);
		//2 实际参数
		if(params != null){
			for(int i = 0 ; i < params.length ; i ++){
				query.setParameter(i, params[i]);
			}
		}
		//3 分页
		query.setFirstResult(startIndex);	// 开始
		query.setMaxResults(pageSize);		// 每页显示多少条
		
		return query.list();
	}

}
