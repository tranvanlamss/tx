package com.vietsoft.payload;

import java.util.Date;

public class LogInfo {
	
	String channel; //web | mobile | other
	
	String appName; // app name
	
	String tag; // tag of log
	
	String level; // level of log
	
	String jcontent; //message, json, journey info
	
	String jscreen; // screen name or log name
	
	String jevent; // log event
	
	String jmethod; //service used http method (GET/POST...)?

	String jurl; // request url
	
	String jpayload; // data info of request parameters
	
	String jtime; //timestamp at event
	
	String userAgent; // user agent of browser or app
	
	String clientIP; // client ip address
	
	String issue; // issue details
	
	Date createdAt; // action at

	public String getChannel() {
		return channel;
	}

	public String getAppName() {
		return appName;
	}

	public String getTag() {
		return tag;
	}

	public String getLevel() {
		return level;
	}

	public String getJcontent() {
		return jcontent;
	}

	public String getJscreen() {
		return jscreen;
	}

	public String getJevent() {
		return jevent;
	}

	public String getJmethod() {
		return jmethod;
	}

	public String getJurl() {
		return jurl;
	}

	public String getJpayload() {
		return jpayload;
	}

	public String getJtime() {
		return jtime;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getClientIP() {
		return clientIP;
	}

	public String getIssue() {
		return issue;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setJcontent(String jcontent) {
		this.jcontent = jcontent;
	}

	public void setJscreen(String jscreen) {
		this.jscreen = jscreen;
	}

	public void setJevent(String jevent) {
		this.jevent = jevent;
	}

	public void setJmethod(String jmethod) {
		this.jmethod = jmethod;
	}

	public void setJurl(String jurl) {
		this.jurl = jurl;
	}

	public void setJpayload(String jpayload) {
		this.jpayload = jpayload;
	}

	public void setJtime(String jtime) {
		this.jtime = jtime;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
