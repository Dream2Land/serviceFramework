package cn.xdaoy.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.xdaoy.common.bean.BaseBean;
import cn.xdaoy.common.mapper.IBaseMapper;
import cn.xdaoy.common.service.BaseService;
import cn.xdaoy.utils.StringUtils;

@Service
public abstract class BaseServiceImpl<Dao extends IBaseMapper<T>,T extends BaseBean> implements BaseService<Dao,T>{

	@Autowired
	protected Dao dao;
	
	public T get(String id){
		return dao.get(id); 
	}
	
	public T get(T t){
		return get(t.getId()); 
	}
	
	public T getByKey(T t){
		return dao.getByKey(t); 
	}
	
	public List<T> findList(T t){
		return dao.findList(t);
	}
	
	public List<T> findList(Map<String,String> map){
		return dao.findList(map);
	}
	
	public List<Map<String,String>> findColumnsList(T t){
		return dao.findColumnsList(t);
	}
	
	public List<Map<String,String>> findColumnsList(Map<String,String> map){
		return dao.findColumnsList(map);
	}
	
	public PageInfo<T> findPage(T t){
		return new PageInfo<>(findList(t));
	}
	
	public PageInfo<T> findPage(Map<String,String> map ,int pageNum, int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(findList(map));
	}
	
	public PageInfo<Map<String,String>> findColumnsPage(T t ,int pageNum, int pageSize){
		return new PageInfo<>(findColumnsList(t));
	}
	
	public PageInfo<Map<String,String>> findColumnsPage(Map<String,String> map ,int pageNum, int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(findColumnsList(map));
	}
	
	public Integer count(T t){
		return dao.count(t);
	}
	
	public int save(T t){
		if(StringUtils.isEmpty(t.getId())){
			t.setDelFlag("0");
			t.setCreateTime(new Date());
			return insert(t);
		}
		return update(t);
	}
	
	public int insert(T t){
		t.setDelFlag("0");
		t.setCreateTime(new Date());
		return dao.insert(t);
	}
	
	@Transactional(readOnly = false)
	public int insertBatch(List<T> list) {
		if(null == list || list.isEmpty()) {
			return 0;
		}
		return dao.insertBatch(list);
	}
	
	public int update(T t){
		return dao.update(t);
	}
	
	public int update(T t,Map<String,String> params){
		if(null == params || params.isEmpty()) {
			return 0;
		}
		return dao.update(t,params);
	}
	
	public int delete(String id){
		return dao.delete(id);
	}
	
	public int delete(T t){
		if(StringUtils.isEmpty(t.getId())){
			return 0;
		}
		return dao.delete(t.getId());
	}
	
	public int deleteByKey(T t){
		return dao.deleteByKey(t);
	}
	
	@Transactional(readOnly = false)
	public int deleteBatch(T t){
		return dao.deleteBatch(t);
	}
	
	@Transactional(readOnly = false)
	public int deleteBatch(List<String> ids){
		if(ids==null || ids.isEmpty()){
			return 0;
		}
		return dao.deleteBatch(ids);
	}
	
}
