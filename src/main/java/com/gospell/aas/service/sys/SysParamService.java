package com.gospell.aas.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.repository.hibernate.sys.SysParamDao;
import com.gospell.aas.repository.mybatis.sys.ISysParamDao;
import com.gospell.aas.service.BaseService;

@Service
@Transactional(readOnly = true)
public class SysParamService extends BaseService {

	@Autowired
	private SysParamDao thisDao;

	@Autowired
	private ISysParamDao mybatisDao;

	public SysParam get(String id) {
		return thisDao.get(id);
	}

	public Page<SysParam> findAll(Page<SysParam> page,
			SysParam entity) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.param_type ,a.enable desc  ");
		}
		entity.setPage(page);
		List<SysParam> list = mybatisDao.findAll(entity);
		page.setList(list);
		return page;
	}
	
	public List<SysParam> getParamList(Map<String,Object> map){
		return  mybatisDao.getParamList(map);		
	}

	
	public SysParam getParam(Map<String,Object> map){
		return  mybatisDao.getParam(map);		
	}
	
	public SysParam getMinParam(Map<String,Object> map){
		return  mybatisDao.getMinParam(map);		
	}
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(SysParam entity) {
		thisDao.clear();
		thisDao.save(entity);
	}

	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		thisDao.deleteById(id);
	}
	
}
