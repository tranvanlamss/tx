package com.vietsoft.common;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaResult {

	static final Logger logger = LoggerFactory.getLogger(CaptchaResult.class);
	private String capt;
	private final long created;

	public CaptchaResult() {
		created = new Date().getTime();
	}

	public void setCaptKey(String captcha) {
		this.capt = captcha;
	}

	public boolean isMatched(String answer) {
		boolean val = capt.equals(answer);
		logger.info("CAPTCHA: {} ENTER: {} RESULT: {}", capt,  answer, val);
		return val;
	}
	
	public boolean isExpired() {
		return isExpired(120);
	}
	
	public boolean isExpired(long ttl) {
		if (ttl < 45) {
			ttl = 45;
		}
		long dis = (new Date().getTime()) -  created - (ttl*1000);
		return dis > 0;
	}
}
