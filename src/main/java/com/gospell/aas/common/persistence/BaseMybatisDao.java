package com.gospell.aas.common.persistence;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p> Title: Base基类，Hibernate外使用</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by ACTEC </p>
 * 
 * <p> Company: ACTEC </p>
 * 
 * @author:  Lee.Jiang
 * @Email:   jianglie@acteccom.com.cn
 * @version: 2.0
 * @date:    2014-11-25  下午4:01:45
 *
 */
public interface BaseMybatisDao<T> {
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
    public T findById(String id);
    
    public T find(T t);
    
	public List<T> findAll(T t);
	
	public void save(T t) throws Exception;
	
	public void update(T t) throws Exception;
	
	public void delete(T t) throws Exception;
	
	public void deleteById(String id) throws Exception;

	/**
	 * 返回分页后的数据
	 * @param List
	 * @param t
	 * @return
	 */
	public List<T> findPages(Map<String, Object> map);
	
	/**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<T> findList(T entity);
    
    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);
    
    /**
     * 查询所有数据列表
     * @see public List<T> findAllList(T entity)
     * @return
     */
    @Deprecated
    public List<T> findAllList();
	
}
