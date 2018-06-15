package com.gospell.aas.service.adv;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.adv.AdExternalProgram;
import com.gospell.aas.repository.hibernate.adv.AdExternalProgramDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdExternalProgramService extends BaseService {

	@Autowired
	private AdExternalProgramDao thisDao;
 
	public AdExternalProgram get(String id) {
		return thisDao.get(id);
	}
	
	public List<AdExternalProgram> findAll(){
		return thisDao.findAll();
	}

	/**
	 * 根据条件查询外部程序
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *         外部程序实体
	 * @return
	 */
	public Page<AdExternalProgram> find(Page<AdExternalProgram> page, AdExternalProgram entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		 
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
 
		return thisDao.find(page, dc);
	}

	 
	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdExternalProgram entity) throws ServiceException {
	 
		thisDao.save(entity);
        UserUtils.removeCache(UserUtils.CACHE_FFMPEG_PROGRAM_LIST);
	 
	}
	
	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdExternalProgram entity) throws ServiceException {
		entity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		thisDao.save(entity);
		UserUtils.removeCache(UserUtils.CACHE_FFMPEG_PROGRAM_LIST);
	 
	}
	
	public AdExternalProgram findProgramByProgramId(String programId){
		return thisDao.findProgramByProgramId(programId);
	}
 


}
