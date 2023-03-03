package com.digitexx.scheduler;


import java.util.HashMap;
import java.util.Set;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.digitexx.dao.DataDao;

public class MainJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		getJob();
	}

	public void getJob() {
		DataDao dao = new DataDao();
		changeConfig(dao.getListConfig());
	}

	private void changeConfig(HashMap<String, ChildSheduler> listSchedulerTemp) {
		Set<String> keySet = listSchedulerTemp.keySet();
		System.out.println("Reading Config have "+keySet.size()+ " Project in Config Export");
		for (String key : keySet) {
			if (!MainSheduler.listScheduler.containsKey(key)) {
				MainSheduler.listScheduler.put(key, listSchedulerTemp.get(key));
				if (MainSheduler.listScheduler.get(key).getConfig().isStatus()) {
					MainSheduler.listScheduler.get(key).start();
				} else {
//					System.out.println("not active with id "+key);
				}
			} else {
				ChildSheduler schedulerDB = listSchedulerTemp.get(key);
				ChildSheduler schedulerWorker = MainSheduler.listScheduler.get(key);
				int checkActive = checkActive(schedulerDB, schedulerWorker);
				if (checkActive == 1) {
					checkDBConfigChange(schedulerDB, schedulerWorker);
					
					schedulerWorker.stop();
					schedulerWorker.start();
				} else if (checkActive == 2) {
					schedulerWorker.stop();
				} else {
					checkDBConfigChange(schedulerDB, schedulerWorker);
					
				}
			}

		}
	}

	// check active 1 = restart ; 2 = stop; 0 = nothing
	private int checkActive(ChildSheduler schedulerDB, ChildSheduler schedulerWK) {
		System.out.println("vao checkActive ========================================");
		if (schedulerDB.getConfig().isStatus()) {
			if (schedulerWK.getConfig().isStatus()) {
				schedulerWK.getConfig().setStatus(true);
				return 0;
			} else {
				schedulerWK.getConfig().setStatus(true);
				return 1;
			}
		} else {
			if (schedulerWK.getConfig().isStatus()) {
				schedulerWK.getConfig().setStatus(false);
				return 2;
			} else {
				schedulerWK.getConfig().setStatus(false);
				return 0;
			}
		}
	}

	private static void checkDBConfigChange(ChildSheduler schedulerDB, ChildSheduler schedulerWK) {

		
		if (!schedulerDB.getConfig().getProject_name().trim().equals(schedulerWK.getConfig().getProject_name().trim())) {
			schedulerWK.getConfig().setProject_name(schedulerDB.getConfig().getProject_name().toString().trim());
		}
		if (!schedulerDB.getConfig().getCron_trigger().trim().equals(schedulerWK.getConfig().getCron_trigger().trim())) {
			schedulerWK.getConfig().setCron_trigger(schedulerDB.getConfig().getCron_trigger().toString().trim());
		}
		if(schedulerDB.getConfig().getType_export().toLowerCase().equals("doc")){
			if (!schedulerDB.getConfig().getDocument_id().equals(schedulerWK.getConfig().getDocument_id())) {
				schedulerWK.getConfig().setDocument_id(schedulerDB.getConfig().getDocument_id().toString().trim());
			}
		}
		
		if (!schedulerDB.getConfig().isStatus()==schedulerWK.getConfig().isStatus()) {
			schedulerWK.getConfig().setStatus(schedulerDB.getConfig().isStatus());
		}
		if(schedulerDB.getConfig().getType_export().toLowerCase().equals("batch")){
			if (!schedulerDB.getConfig().getBatch_id().equals(schedulerWK.getConfig().getBatch_id())) {
				schedulerWK.getConfig().setBatch_id(schedulerDB.getConfig().getBatch_id());
			}
		}
//		if (!schedulerDB.getConfig().getType_of_label().trim().equals(schedulerWK.getConfig().getType_of_label().trim())) {
//			schedulerWK.getConfig().setType_of_label(schedulerDB.getConfig().getType_of_label().toString());
//		}
//		if (!schedulerDB.getConfig().getType().trim().equals(schedulerWK.getConfig().getType().trim())) {
//			schedulerWK.getConfig().setType(schedulerDB.getConfig().getType().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getHost().trim().equals(schedulerWK.getConfig().getHost().trim())) {
//			schedulerWK.getConfig().setHost(schedulerDB.getConfig().getHost().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getPort().trim().equals(schedulerWK.getConfig().getPort().trim())) {
//			schedulerWK.getConfig().setPort(schedulerDB.getConfig().getPort().toString());
//		}
//
//		if (!schedulerDB.getConfig().getUser().trim().equals(schedulerWK.getConfig().getUser().trim())) {
//			schedulerWK.getConfig().setUser(schedulerDB.getConfig().getUser().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getPass().trim().equals(schedulerWK.getConfig().getPass().trim())) {
//			schedulerWK.getConfig().setPass(schedulerDB.getConfig().getPass().toString());
//		}
//		if (!schedulerDB.getConfig().getDb_name().trim().equals(schedulerWK.getConfig().getDb_name().trim())) {
//			schedulerWK.getConfig().setDb_name(schedulerDB.getConfig().getDb_name().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getSchema().trim().equals(schedulerWK.getConfig().getSchema().trim())) {
//			schedulerWK.getConfig().setSchema(schedulerDB.getConfig().getSchema().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getCollection_name().trim().equals(schedulerWK.getConfig().getCollection_name().trim())) {
//			schedulerWK.getConfig().setCollection_name(schedulerDB.getConfig().getCollection_name().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getQuery().trim().equals(schedulerWK.getConfig().getQuery().trim())) {
//			schedulerWK.getConfig().setQuery(schedulerDB.getConfig().getQuery().toString());
//		}
//		if (!schedulerDB.getConfig().getFolder_path_image().trim().equals(schedulerWK.getConfig().getFolder_path_image().trim())) {
//			schedulerWK.getConfig().setFolder_path_image(schedulerDB.getConfig().getFolder_path_image().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getFile_extension().trim().equals(schedulerWK.getConfig().getFile_extension().trim())) {
//			schedulerWK.getConfig().setFile_extension(schedulerDB.getConfig().getFile_extension().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getFolder_path_ftp().trim().equals(schedulerWK.getConfig().getFolder_path_ftp().trim())) {
//			schedulerWK.getConfig().setFolder_path_ftp(schedulerDB.getConfig().getFolder_path_ftp().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getSize().equals(schedulerWK.getConfig().getSize().trim())) {
//			schedulerWK.getConfig().setSize(schedulerDB.getConfig().getSize().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getJson_path_image().trim().equals(schedulerWK.getConfig().getJson_path_image().trim())) {
//			schedulerWK.getConfig().setJson_path_image(schedulerDB.getConfig().getJson_path_image().toString().trim());
//		}
//		if (schedulerDB.getConfig().isGet_image()!=schedulerWK.getConfig().isGet_image()) {
//			schedulerWK.getConfig().setGet_image(schedulerDB.getConfig().isGet_image());
//		}
//		if (!schedulerDB.getConfig().getCondition_query().trim().equals(schedulerWK.getConfig().getCondition_query().trim())) {
//			schedulerWK.getConfig().setCondition_query(schedulerDB.getConfig().getCondition_query().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getWin_first_path().trim().equals(schedulerWK.getConfig().getWin_first_path().trim())) {
//			schedulerWK.getConfig().setWin_first_path(schedulerDB.getConfig().getWin_first_path().toString());
//		}
//		if (!schedulerDB.getConfig().getLinux_first_path().trim().equals(schedulerWK.getConfig().getLinux_first_path().trim())) {
//			schedulerWK.getConfig().setLinux_first_path(schedulerDB.getConfig().getLinux_first_path().toString().trim());
//		}
//		if (!schedulerDB.getConfig().getId_history().equals(schedulerWK.getConfig().getId_history())) {
//			schedulerWK.getConfig().setId_history(schedulerDB.getConfig().getId_history().toString());
//		}

	}


}
