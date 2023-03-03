package com.digitexx.scheduler;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.digitexx.bean.BeanConfig;

import com.digitexx.bll.Collector;

@DisallowConcurrentExecution
public class ChildJob implements InterruptableJob{

	private Thread currentThread;
	public void execute(JobExecutionContext context) throws JobExecutionException {
		currentThread = Thread.currentThread();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		BeanConfig collector = (BeanConfig) dataMap.get("config");
//		System.out.println(new Date().toString()+   "curren thread "+ collector.getProject_name());
		
		 new Collector((BeanConfig) dataMap.get("config")).run();
	}

	public void interrupt() throws UnableToInterruptJobException {
		if(currentThread != null && currentThread.isAlive()) {
			currentThread.stop();
			
		}
	}
	

}
