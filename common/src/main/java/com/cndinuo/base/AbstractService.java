package com.cndinuo.base;

import com.cndinuo.page.Page;
import com.cndinuo.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共抽象类
 * @author dgb
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractService<T, ID extends Serializable> implements BaseService<T, ID> {

	protected static final Logger log = LoggerFactory.getLogger("service");

	private BaseDao<T, ID> baseDao;

	public void setBaseDao(BaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}

	/**********************************删除数据*************************************/
	public int deleteById(ID id) {
		return baseDao.deleteById(id);
	}

	public int deleteByMap(Map<String,Object> params){
		return baseDao.deleteByMap(params);
	}
	public int deleteByEntity(T t){
		return baseDao.deleteByEntity(t);
	}

	/**********************************更新*************************************/
	public int updateById(T record) {
		return baseDao.updateById(record);
	}
	
	public int updateByMap(Map<String,Object> params) {
		return baseDao.updateByMap(params);
	}

	/**********************************新增*************************************/
	public T insert(T record) {
		baseDao.insert(record);
		return record;
	}
	
	
	/**********************************查询公共方法*************************************/
	/**
	 * 根据ID查询
	 */
	public T getById(ID id) {
		return baseDao.getById(id);
	}
	/**
	 *传入查询条件进入mybatis
	 */
	public List<T> getByMap(Map<String,Object> params){
		return baseDao.getByMap(params);
	}
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public Page<T> getByPage(Map<String,Object> params){
		Page<T> page = new Page<T>();
		page.setP(params);
		page.setDefaultPageSize(10);
		page.setPageSize(StringUtil.toInt(params.get("pageSize")));
		page.setCurrentPage(StringUtil.toInt(params.get("pn")+"", 1));
		List<T> list = baseDao.getByPage(page);
		page.setResults(list);
		return page;
	}
	/**
	 * 查询某一条数据
	 */
	public T getOneByMap(Map<String,Object> params){
		List<T> list = baseDao.getByMap(params);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 查询所有数据
	 */
	public List<T>  getAll(){
		return baseDao.getByMap(new HashMap<String, Object>());
	}
	/**
	 * 根据实类传入相关参数
	 */
	public List<T> getByEntity(T t){
		return baseDao.getByEntity(t);
	}
	/**
	 * 查询某一条数据
	 */
	public T getOneByEntity(T t){
		List<T> list = baseDao.getByEntity(t);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**********************************查询数量*************************************/
	public int getCountByEntity(T t){
		return baseDao.getCountByEntity(t);
	}
	public int getCountByMap(Map<String,Object> params){
		return baseDao.getCountByMap(params);
	}

	public int getCount(){
		return baseDao.getCount();
	}
}