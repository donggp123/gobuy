package com.cndinuo.base;

import com.cndinuo.page.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T, ID extends Serializable> {

	/**
	 * 新增
	 * @param record
	 * @return
	 */
	int insert(T record);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int deleteById(ID id);
	
	int deleteByMap(Map<String, Object> params);
	
	int deleteByEntity(T t);

	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int updateById(T record);

	int updateByMap(Map<String, Object> params);


	/**
	 * 查询
	 * @param id
	 * @return
	 */
	T getById(ID id);

	List<T> getByMap(Map<String, Object> params);
	
	List<T> getByEntity(T t);
	
	/**
	 * 分页查询
	 * @param t
	 * @return
	 */
	List<T> getByPage(Page t);
	
	int getCount();
	
	int getCountByEntity(T t);
	
	int getCountByMap(Map<String, Object> params);
	
}