package com.digitexx.bean;

import java.util.LinkedList;

public class BeanConfig {

	private String id;
	private String project_id;
	private String project_name;
	private String cron_trigger;
	private boolean status;
	private String id_history;

	private Object document_id;
	private String file_filter;
	private String path_write_txt;
	private int limit;
	private int status_check;
	private String seperate;
	private Object template;
	private int row_start_check;
	private String type_export;
	private LinkedList<String> listID;
	private Object batch_id;
	private boolean export_test_case_status;
	private boolean index;
	private String section;
	
	
	
	
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public boolean isIndex() {
		return index;
	}
	public void setIndex(boolean index) {
		this.index = index;
	}
	public boolean isExport_test_case_status() {
		return export_test_case_status;
	}
	public void setExport_test_case_status(boolean export_test_case_status) {
		this.export_test_case_status = export_test_case_status;
	}

	public Object getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(Object batch_id) {
		this.batch_id = batch_id;
	}
	public LinkedList<String> getListID() {
		return listID;
	}
	public void setListID(LinkedList<String> listID) {
		this.listID = listID;
	}
	public Object getDocument_id() {
		return document_id;
	}
	public void setDocument_id(Object document_id) {
		this.document_id = document_id;
	}

	public String getType_export() {
		return type_export;
	}
	public void setType_export(String type_export) {
		this.type_export = type_export;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getCron_trigger() {
		return cron_trigger;
	}
	public void setCron_trigger(String cron_trigger) {
		this.cron_trigger = cron_trigger;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getId_history() {
		return id_history;
	}
	public void setId_history(String id_history) {
		this.id_history = id_history;
	}
	

	public String getFile_filter() {
		return file_filter;
	}
	public void setFile_filter(String file_filter) {
		this.file_filter = file_filter;
	}
	public String getPath_write_txt() {
		return path_write_txt;
	}
	public void setPath_write_txt(String path_write_txt) {
		this.path_write_txt = path_write_txt;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getStatus_check() {
		return status_check;
	}
	public void setStatus_check(int status_check) {
		this.status_check = status_check;
	}
	public String getSeperate() {
		return seperate;
	}
	public void setSeperate(String seperate) {
		this.seperate = seperate;
	}
	public Object getTemplate() {
		return template;
	}
	public void setTemplate(Object template) {
		this.template = template;
	}
	public int getRow_start_check() {
		return row_start_check;
	}
	public void setRow_start_check(int row_start_check) {
		this.row_start_check = row_start_check;
	}
	
	
}
