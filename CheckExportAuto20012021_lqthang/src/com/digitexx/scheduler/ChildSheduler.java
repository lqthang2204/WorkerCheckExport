package com.digitexx.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.digitexx.bean.BeanConfig;


public class ChildSheduler {
	private BeanConfig config;
	private Scheduler scheduler;

	public ChildSheduler(BeanConfig config) {
		this.config = config;
	
	}
	public void start() {
		try {
			JobDataMap dataMap = new JobDataMap();
			dataMap.put("config", this.config);
			JobDetail job = JobBuilder.newJob(ChildJob.class).withIdentity(new JobKey(config.getId())).usingJobData(dataMap).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(config.getId())).withSchedule(CronScheduleBuilder.cronSchedule(config.getCron_trigger())).build();
			if(this.scheduler == null || this.scheduler.isShutdown()) {
				this.scheduler = new StdSchedulerFactory().getScheduler();
				this.scheduler.start();
			}	
			this.scheduler.scheduleJob(job, trigger);
	
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		
		try {
			if(this.scheduler != null && this.scheduler.isStarted() && !this.scheduler.isShutdown()) {
				this.scheduler.unscheduleJob(new TriggerKey(config.getId()));
				this.scheduler.interrupt(new JobKey(config.getId()));
				this.scheduler.deleteJob(new JobKey(config.getId()));
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void restart() {
		try {
			if(this.scheduler != null && this.scheduler.isStarted() && !this.scheduler.isShutdown()) {
				stop();
				System.out.println("=== Stop ===");
			}
			start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public BeanConfig getConfig() {
		return config;
	}

	public void setConfig(BeanConfig config) {
		this.config = config;
	}

	
}
