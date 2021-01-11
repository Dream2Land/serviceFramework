package cn.xdaoy.common.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

/**
 * abstract dao methods
 * @author xdtand/matengfei
 *
 * @param <Dao>
 * @param <T>
 */
public interface BaseService<Dao,T> {

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	T get(String id);
	
	/**
	 * get one by id,object must have id
	 * @param t
	 * @return
	 */
	T get(T t);
	
	/**
	 * get by table key ,self SQL key 
	 * @param t
	 * @return
	 */
	T getByKey(T t);
	
	/**
	 * find record page by self condition
	 * 
	 * @param t
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<T> findPage(T t);
	
	/**
	 * find record page by self condition
	 * 
	 * @param t
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<T> findPage(Map<String,String> param ,int pageNum, int pageSize);
	
	/**
	 * find self columns record page by self condition
	 * 
	 * @param t
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Map<String,String>> findColumnsPage(T t ,int pageNum, int pageSize);
	
	/**
	 * find self columns record page by self condition
	 * 
	 * @param t
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Map<String,String>> findColumnsPage(Map<String,String> param ,int pageNum, int pageSize);
	
	/**
	 * find record list by self condition
	 * 
	 * @param t
	 * @return
	 */
	List<T> findList(T t);
	
	
	/**
	 * find record list by self condition
	 * 
	 * @param t
	 * @return
	 */
	List<T> findList(Map<String,String> param);
	
	/**
	 * find self columns record list by self condition
	 * 
	 * @param t
	 * @return
	 */
	List<Map<String,String>> findColumnsList(T t);
	
	/**
	 * find self columns record list by self condition
	 * 
	 * @param t
	 * @return
	 */
	List<Map<String,String>> findColumnsList(Map<String,String> param);
	
	/**
	 * find record count by self condition
	 * 
	 * @param t
	 * @return
	 */
	Integer count(T t);
	
	/**
	 * save item;if id is null,insert;else,update
	 * 
	 * @param t
	 * @return
	 */
	int save(T t);
	
	/**
	 * insert item
	 * 
	 * @param t
	 * @return
	 */
	int insert(T t);
	
	/**
	 * insert item multiple
	 * 
	 * @param t
	 * @return
	 */
	int insertBatch(List<T> list);
	
	/**
	 * update item
	 * 
	 * @param t
	 * @return
	 */
	int update(T t);
	
	/**
	 * update item by self columns
	 * 
	 * @param t
	 * @return
	 */
	int update(T t, Map<String,String> parmas);
	
	/**
	 * delete item by id
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * delete item by id,object must have id
	 * 
	 * @param t
	 * @return
	 */
	int delete(T t);
	
	/**
	 * delete item by key,self table key
	 * 
	 * @param t
	 * @return
	 */
	int deleteByKey(T t);
	
	/**
	 * delete record multiple,input was id list
	 * 
	 * @param ids
	 * @return
	 */
	int deleteBatch(List<String> ids);

}
