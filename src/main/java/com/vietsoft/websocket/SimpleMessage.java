package com.vietsoft.websocket;

import java.util.Date;

public class SimpleMessage {

	String from;
	String text;
	Date time;

	public String getFrom() {
		return from;
	}

	public String getText() {
		return text;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
