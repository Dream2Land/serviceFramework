package cn.xdaoy.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.xdaoy.common.bean.BaseBean;


/**
 * DAO abstract methods
 * 
 * @author xdtand
 *
 * @param <T>
 */
@Mapper
public interface IBaseMapper<T extends BaseBean> {

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
	int insertBatch(@Param("list")List<T> list);
	
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
	int update(@Param("bean")T t, @Param("params") Map<String,String> parmas);
	
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
	 * delete record multiple,input was bean
	 * 
	 * @param t
	 * @return
	 */
	int deleteBatch(T t);
	
	/**
	 * delete record multiple,input was id list
	 * 
	 * @param ids
	 * @return
	 */
	int deleteBatch(@Param("list")List<String> ids);
}
