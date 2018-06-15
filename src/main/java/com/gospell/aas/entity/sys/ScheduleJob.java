package com.gospell.aas.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gospell.aas.common.persistence.IdEntity;

/**
 * 定时任务封装类
 * 
 * @author zjh
 */
@Entity
@Table(name = "task_detail")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleJob extends IdEntity<ScheduleJob> {

	private static final long serialVersionUID = 1L;
	/** 任务名称 */
	private String jobName;

	/** 任务分组 */
	private String jobGroup;

	/** 任务状态 0禁用 1启用 */
	private String jobStatus;

	/** 任务运行时间表达式 */
	private String cronExpression;	

	/** 任务执行类 */
	private String beanClass;

	/** 任务执行方法 */
	private String executeMethod;

	/** 任务描述 */
	private String jobDesc;
	
	/** 页面展示类 */
	@Transient
	private String showClass;


	public ScheduleJob () {
		super();
	}
	
	public ScheduleJob(String id){
		this();
		this.id = id;
	}
	@Column(name="job_name")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	@Column(name="job_group")
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	@Column(name="job_status")
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	@Column(name="cron_expression")
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	@Column(name="bean_class")
	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	@Column(name="execute_method")
	public String getExecuteMethod() {
		return executeMethod;
	}

	public void setExecuteMethod(String executeMethod) {
		this.executeMethod = executeMethod;
	}
	@Column(name="job_desc")
	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	@Transient
	public String getShowClass() {
		return showClass;
	}
	@Transient
	public void setShowClass(String showClass) {
		this.showClass = showClass;
	}

}
