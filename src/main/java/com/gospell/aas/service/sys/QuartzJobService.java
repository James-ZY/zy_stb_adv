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
import com.gospell.aas.entity.sys.ScheduleJob;
import com.gospell.aas.repository.hibernate.sys.QuartzJobDao;
import com.gospell.aas.service.BaseService;


/**
 * 任务调度Service
 * @author zjh
 *
 */
@Service
@Transactional(readOnly = true)
public class QuartzJobService extends BaseService {
	@Autowired
	private QuartzJobDao quartzJobDao;

	/**
	 * 分页获取定时任务记录信息
	 * 
	 * @return
	 */
	public List<ScheduleJob> findAll(ScheduleJob job) {
		DetachedCriteria dc = quartzJobDao.createDetachedCriteria();
		if(StringUtils.isNotEmpty(job.getId())){
			dc.add(Restrictions.eq("id", job.getId()));
		}
		if(StringUtils.isNotEmpty(job.getJobName())){
			dc.add(Restrictions.like("jobName", "%"+job.getJobName()+"%"));
		}
		if(StringUtils.isNotEmpty(job.getJobStatus())){
			dc.add(Restrictions.eq("jobStatus", job.getJobStatus()));
		}
		if(job.getCreateDateStart()!=null && !job.getCreateDateStart().equals("")){
			dc.add(Restrictions.ge("createDate", job.getCreateDateStart()));
		}
		if(job.getCreateDateEnd()!=null && !job.getCreateDateEnd().equals("")){
			dc.add(Restrictions.le("createDate", job.getCreateDateEnd()));
		}         
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG,
				BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate")).addOrder(Order.desc("jobStatus"));

		return quartzJobDao.find(dc);
	}

	/**
	 * 分页获取定时任务执行记录信息
	 * 
	 * @return
	 */
	public Page<ScheduleJob> find(Page<ScheduleJob> page, ScheduleJob job) {
		// Hibernate 查询
		DetachedCriteria dc = quartzJobDao.createDetachedCriteria();
		if(StringUtils.isNotEmpty(job.getId())){
			dc.add(Restrictions.eq("id", job.getId()));
		}
		if(StringUtils.isNotEmpty(job.getJobName())){
			dc.add(Restrictions.like("jobName", "%"+job.getJobName()+"%"));
		}
		if(StringUtils.isNotEmpty(job.getJobStatus())){
			dc.add(Restrictions.eq("jobStatus", job.getJobStatus()));
		}
		if(job.getCreateDateStart()!=null && !job.getCreateDateStart().equals("")){
			dc.add(Restrictions.ge("createDate", job.getCreateDateStart()));
		}
		if(job.getCreateDateEnd()!=null && !job.getCreateDateEnd().equals("")){
			dc.add(Restrictions.le("createDate", job.getCreateDateEnd()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG,
				BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate")).addOrder(Order.desc("jobStatus"));


		return quartzJobDao.find(page, dc);
	}

	/**
	 * 插入一条定时任务执行记录信息
	 * 
	 * @param jobItem
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void save(ScheduleJob jobItem) throws Exception {
		quartzJobDao.save(jobItem);
	}

	/**
	 * 删除一条定时任务记录
	 * 
	 * @param job
	 */
	@Transactional(readOnly = false)
	public void delete(String id) throws Exception {
		quartzJobDao.deleteById(id);
	}

	/**
	 * 根据ID获取一条定时任务执行记录信息
	 * 
	 * @param id
	 * @return
	 */
	public ScheduleJob get(String id) {
		return quartzJobDao.get(id);
	}
}
