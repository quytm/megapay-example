package com.epay.example.tmq.megapayexamplerequest.megapay.entity;

public class MgpScratchCardRequest {

	public MgpScratchCardRequest() {
		// TODO Auto-generated constructor stub
	}

	
	String serial;
	String mpin;
	String transid;
	String telcocode;
	String username;
	String account;
	String payment_channel;
	String request_ip;
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getTelcocode() {
		return telcocode;
	}
	public void setTelcocode(String telcocode) {
		this.telcocode = telcocode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPayment_channel() {
		return payment_channel;
	}
	public void setPayment_channel(String payment_channel) {
		this.payment_channel = payment_channel;
	}
	public String getRequest_ip() {
		return request_ip;
	}
	public void setRequest_ip(String request_ip) {
		this.request_ip = request_ip;
	}
}
