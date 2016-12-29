package com.epay.example.tmq.megapayexamplerequest.megapay.entity;

public class MgpMessageCover {

	public MgpMessageCover() {
		// TODO Auto-generated constructor stub
	}

	
	String processing_code;
	String project_id;
	String data;
	String type;
	public String getProcessing_code() {
		return processing_code;
	}
	public void setProcessing_code(String processing_code) {
		this.processing_code = processing_code;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
