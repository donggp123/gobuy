package com.cndinuo.base;

import com.cndinuo.page.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础service
 * @author dgb
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID extends Serializable> {
	
	void setBaseDao();

	/********************************删除***********************************************/
	int deleteById(ID id);
	
	int deleteByMap(Map<String, Object> params);
	
	int deleteByEntity(T t);

	/********************************新增***********************************************/
	T insert(T record);

	/********************************查询***********************************************/
	T getById(ID id);
	
	List<T> getByMap(Map<String, Object> params);
	
	T getOneByMap(Map<String, Object> params);
	
	List<T>  getAll();
	
	List<T> getByEntity(T t);
	
	Page<T> getByPage(Map<String, Object> params);
	
	T getOneByEntity(T t);

	/********************************查询数量***********************************************/
	int getCountByMap(Map<String, Object> params);
	
	int getCount();
	
	int getCountByEntity(T t);
	
	
	/*******************************更新数据***********************************************/
	int updateById(T record);

	int updateByMap(Map<String, Object> params);
	
}