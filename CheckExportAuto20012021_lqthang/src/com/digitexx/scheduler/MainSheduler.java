package com.digitexx.scheduler;

import java.util.HashMap;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.digitexx.config.Configuration;


public class MainSheduler {

	public static HashMap<String, ChildSheduler> listScheduler = new HashMap<String, ChildSheduler>();
	public static Scheduler schedulerConfig;
	public static void start() {
		try {
			JobDetail job = JobBuilder.newJob(MainJob.class).withIdentity(new JobKey("config")).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("config")).withSchedule(CronScheduleBuilder.cronSchedule(Configuration.CRON_TRIGGER)).build();
			if(schedulerConfig == null || schedulerConfig.isShutdown()) {
				schedulerConfig = new StdSchedulerFactory().getScheduler();
				schedulerConfig.start();				
			}
			schedulerConfig.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) throws InterruptedException {
//		Configuration.configDB();
//		start();
//		
//		
//	}

}
