package com.gospell.aas.service.sys;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.entity.sys.DataBaseRecord;
import com.gospell.aas.repository.hibernate.sys.DataBaseRecordDao;
import com.gospell.aas.service.BaseService;


/**
 * 数据库备份记录Service
 * @author zjh
 *
 */
@Service
@Transactional(readOnly = true)
public class DataBaseRecordService extends BaseService {
	@Autowired
	private DataBaseRecordDao thisDao;

	/**
	 * 分页获取备份记录信息
	 * 
	 * @return
	 */
	public List<DataBaseRecord> findAll(DataBaseRecord model) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		if(StringUtils.isNotEmpty(model.getId())){
			dc.add(Restrictions.eq("id", model.getId()));
		}
		if(StringUtils.isNotEmpty(model.getRecordName())){
			dc.add(Restrictions.like("recordName", "%"+model.getRecordName()+"%"));
		}
		if(StringUtils.isNotEmpty(model.getRecordPath())){
			dc.add(Restrictions.eq("recordPath", model.getRecordPath()));
		}
		if(model.getCreateDateStart()!=null && !model.getCreateDateStart().equals("")){
			dc.add(Restrictions.ge("createDate", model.getCreateDateStart()));
		}
		if(model.getCreateDateEnd()!=null && !model.getCreateDateEnd().equals("")){
			dc.add(Restrictions.le("createDate", model.getCreateDateEnd()));
		}         
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG,
				BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));

		return thisDao.find(dc);
	}

	/**
	 * 分页获取备份记录信息
	 * 
	 * @return
	 */
	public Page<DataBaseRecord> find(Page<DataBaseRecord> page, DataBaseRecord model) {
		// Hibernate 查询
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		if(StringUtils.isNotEmpty(model.getId())){
			dc.add(Restrictions.eq("id", model.getId()));
		}
		if(StringUtils.isNotEmpty(model.getRecordName())){
			dc.add(Restrictions.like("recordName", "%"+model.getRecordName()+"%"));
		}
		if(StringUtils.isNotEmpty(model.getRecordPath())){
			dc.add(Restrictions.eq("recordPath", model.getRecordPath()));
		}
		if(model.getCreateDateStart()!=null && !model.getCreateDateStart().equals("")){
			dc.add(Restrictions.ge("createDate", model.getCreateDateStart()));
		}
		if(model.getCreateDateEnd()!=null && !model.getCreateDateEnd().equals("")){
			dc.add(Restrictions.le("createDate", model.getCreateDateEnd()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG,
				BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));


		return thisDao.find(page, dc);
	}

	/**
	 * 插入一条备份记录
	 * 
	 * @param jobItem
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void save(DataBaseRecord model) throws Exception {
		thisDao.save(model);
	}

	/**
	 * 删除一条备份记录
	 * 
	 * @param job
	 */
	@Transactional(readOnly = false)
	public void delete(String id) throws Exception {
		thisDao.deleteById(id);
	}

	/**
	 * 根据ID获取一条记录信息
	 * 
	 * @param id
	 * @return
	 */
	public DataBaseRecord get(String id) {
		return thisDao.get(id);
	}
}
