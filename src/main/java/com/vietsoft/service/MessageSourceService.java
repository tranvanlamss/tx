package com.vietsoft.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceService {
	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String key, String ... arg) {
		return messageSource.getMessage(key, arg, LocaleContextHolder.getLocale());
	}
	
	public String getMessage(Locale loc, String key, String ... arg) {
		if (loc == null) {
			loc = Locale.US;
		}
		return messageSource.getMessage(key, arg, loc);
	}
}
