package com.gospell.aas.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.sys.SysFileParam;
import com.gospell.aas.repository.hibernate.sys.SysFileParamDao;
import com.gospell.aas.repository.mybatis.sys.ISysFileParamDao;
import com.gospell.aas.service.BaseService;

@Service
@Transactional(readOnly = true)
public class SysFileParamService extends BaseService {

	@Autowired
	private SysFileParamDao thisDao;

	@Autowired
	private ISysFileParamDao mybatisDao;

	public SysFileParam get(String id) {
		return thisDao.get(id);
	}

	public Page<SysFileParam> findAll(Page<SysFileParam> page,
			SysFileParam entity) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("a.enable desc,t.id");
		}
		entity.setPage(page);
		List<SysFileParam> list = mybatisDao.findAll(entity);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdType adtype = list.get(i).getAdType();
				AdTypeUtils.getLocaleAdType(adtype);

			}
		}
		page.setList(list);
		return page;
	}
	
	public List<SysFileParam> getParamList(Map<String,Object> map){
		return  mybatisDao.getParamList(map);		
	}

	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(SysFileParam entity) {
		thisDao.save(entity);
	}

	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		thisDao.deleteById(id);
	}
	
}
