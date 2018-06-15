package com.gospell.aas.common.utils;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gospell.aas.entity.sys.ScheduleJob;
import com.gospell.aas.service.sys.QuartzJobService;

/**
 * 提供Job任务相关的方法
 * @author zjh
 *
 */
@Component( "JobMethod" )
public class JobMethod implements InitializingBean {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(JobMethod.class);
	   @Resource( name = "schedulerFactoryBean" )
	    private Scheduler scheduler;
	   
	   @Autowired
		private QuartzJobService quartzJobService;
	   
	   


		@Override
		public void afterPropertiesSet() throws Exception {
			init();
		}
	   /**
	     * 任务框架初始化方法
	     * @throws
	     */
	    public void init()
	    {
	        /* 从数据库获得所有的任务信息记录 */
	    	ScheduleJob job = new ScheduleJob();
	        List<ScheduleJob> jobList = quartzJobService.findAll(job);

	        if ( jobList != null && !jobList.isEmpty() )
	        {
	            for ( ScheduleJob scheduleJob : jobList )
	            {
	            	testInit(scheduleJob);
	            }
	        }
	    }

	    
	    public void testInit( ScheduleJob scheduleJob){
	    	/* 判断任务状态，是否为执行状态 */

            TriggerKey triggerKey = TriggerKey.triggerKey( scheduleJob
                                 .getJobName(), scheduleJob.getJobGroup() );
            CronTrigger trigger;
            try
            {
                trigger = (CronTrigger) scheduler.getTrigger( triggerKey );
                if ( null == trigger )
                {
                    JobDetail jobDetail = JobBuilder.newJob(
                        QuartzJobFactory.class ).withIdentity(
                        scheduleJob.getJobName(),
                        scheduleJob.getJobGroup() ).build();

                    jobDetail.getJobDataMap().put( "scheduleJob",
                                 scheduleJob );

                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                                     .cronSchedule( scheduleJob.getCronExpression() );

                    trigger = TriggerBuilder.newTrigger().withIdentity(
                        scheduleJob.getJobName(),
                        scheduleJob.getJobGroup() ).withSchedule(
                        scheduleBuilder ).build();
                    scheduler.scheduleJob( jobDetail, trigger );
                }else {
                    /* Trigger已存在，那么更新相应的定时设置 */
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                                     .cronSchedule( scheduleJob.getCronExpression() );

                    /* 按新的cronExpression表达式重新构建trigger */
                    trigger = trigger.getTriggerBuilder().withIdentity(
                        triggerKey ).withSchedule( scheduleBuilder )
                         .build();

                    /* 按新的trigger重新设置job执行 */
                    scheduler.rescheduleJob( triggerKey, trigger );
                }
                if(scheduleJob.getJobStatus().equals("0")){
                	System.out.println(scheduleJob.getJobName()+"暂停");
                	this.pauseJob(scheduleJob);
                }
            }
            catch ( SchedulerException e )
            {
            	logger.error( "Task init failed.", e );
            }
	    }

	    /**
	     * 暂停一个job
	     *
	     * @param scheduleJob
	     * @throws SchedulerException
	     */
	    public void pauseJob( ScheduleJob scheduleJob )
	    {
	        JobKey jobKey = JobKey.jobKey( scheduleJob.getJobName(), scheduleJob.getJobGroup() );
	        try
	        {
	            scheduler.pauseJob( jobKey );
	        }
	        catch ( SchedulerException e )
	        {
	        	logger.error( "Task pause failed.", e );
	        }
	    }


	    /**
	     * 恢复一个job
	     *
	     * @param scheduleJob
	     * @throws SchedulerException
	     */
	    public void resumeJob( ScheduleJob scheduleJob )
	    {
	        JobKey jobKey = JobKey.jobKey( scheduleJob.getJobName(), scheduleJob.getJobGroup() );
	        try
	        {
	            scheduler.resumeJob( jobKey );
	        }
	        catch ( SchedulerException e )
	        {
	        	logger.error( "Task resume failed.", e );
	        }
	    }


	    /**
	     * 删除一个job
	     *
	     * @param scheduleJob
	     * @throws SchedulerException
	     */
	    public void deleteJob( ScheduleJob scheduleJob )
	    {
	        JobKey jobKey = JobKey.jobKey( scheduleJob.getJobName(), scheduleJob.getJobGroup() );
	        try
	        {
	            scheduler.deleteJob( jobKey );
	        }
	        catch ( SchedulerException e )
	        {
	        	logger.error( "Task delete failed.", e );
	        }
	    }


	    /**
	     * 立即执行job
	     *
	     * @param scheduleJob
	     * @throws SchedulerException
	     */
	    public void runJobNow( ScheduleJob scheduleJob )
	    {
	        JobKey jobKey = JobKey.jobKey( scheduleJob.getJobName(), scheduleJob.getJobGroup() );
	        try
	        {
	        	testInit(scheduleJob);
	            scheduler.triggerJob( jobKey );
	        }
	        catch ( SchedulerException e )
	        {
	        	logger.error( "Task run failed.", e );
	        }
	    }


	    /**
	     * 更新job时间表达式
	     *
	     * @param scheduleJob
	     * @throws SchedulerException
	     */
	    public void updateJobCron( ScheduleJob scheduleJob ) throws SchedulerException
	    {
	        TriggerKey triggerKey = TriggerKey.triggerKey( scheduleJob.getJobName(),
	                             scheduleJob.getJobGroup() );
	        /* 获取trigger，即在spring配置文件中定义的 bean id="schedulerFactoryBean" */
	        CronTrigger trigger = (CronTrigger) scheduler.getTrigger( triggerKey );
	        /* 表达式调度构建器 */
	        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule( scheduleJob
	                                            .getCronExpression() );
	        /*按新的cronExpression表达式重新构建trigger */
	        trigger = trigger.getTriggerBuilder().withIdentity( triggerKey )
	             .withSchedule( scheduleBuilder ).build();
	        /*按新的trigger重新设置job执行 */
	        scheduler.rescheduleJob( triggerKey, trigger );
	    }


	/**
	 * 判断表达式是否可用
	 * @param cron
	 * @return
	 * @throws
	 */
	    public boolean checkCron( String cron )
	    {
	        try
	        {
	            CronScheduleBuilder.cronSchedule( cron );
	        }
	        catch ( Exception e )
	        {
	            return(false);
	        }
	        return(true);
	    }

}
