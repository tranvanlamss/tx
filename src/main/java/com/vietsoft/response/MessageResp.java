package com.vietsoft.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vietsoft.common.enumerate.ErrorCodesEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@SuppressWarnings("serial")
@Setter
@Getter
public class MessageResp implements Serializable {

	String error = ""; // CODE

	String message = "success";// message content

	Object result = null;

	Long total = null;

	Integer size = null;

	Integer page = null;

	Date timestamp = new Date();

	public MessageResp() {
		super();
		this.message = "success";
	}

	public MessageResp(Object result) {
		super();
		this.message = "success";
		this.result = result;
	}

	public MessageResp(String message) {
		super();
		this.message = message;
	}

	public MessageResp(String message, Object result) {
		super();
		this.message = message;
		this.result = result;
	}

	public MessageResp(String error, String message, Object result) {
		super();
		this.error = error;
		this.message = message;
		this.result = result;
	}

	public MessageResp(Long total, int size, int page, Object result) {
		super();
		this.message = "success";
		this.total = total;
		this.size = size;
		this.page = page;
		this.result = result;
	}


	public MessageResp(String error, String message, Object result, Long total, int size, int page, Date timestamp) {
		this.error = error;
		this.message = message;
		this.result = result;
		this.total = total;
		this.size = size;
		this.page = page;
		this.timestamp = timestamp;
	}
	

	public MessageResp(String error, String message, Object result, Long total, Integer size, Integer page, Date timestamp) {
		this.error = error;
		this.message = message;
		this.result = result;
		this.total = total;
		this.size = size;
		this.page = page;
		this.timestamp = timestamp;
	}
	public static MessageResp ok() {
		MessageResp res = new MessageResp ();
		res.setMessage("success");
		return res;
	}
	public static MessageResp page(Page page) {
		MessageResp res = new MessageResp ();
		res.setMessage("success");
		res.setResult(page.getContent());
		res.setTotal(page.getTotalElements());
		res.setPage(page.getPageable().getPageNumber());
		res.setSize(page.getPageable().getPageSize());
		return res;
	}

	public MessageResp error(String error) {
		this.error = error;
		return this;
	}

	public MessageResp error(ErrorCodesEnum error) {
		this.error = error.name();
		return this;
	}

	public MessageResp message(String message) {
		this.message = message;
		return this;
	}

	public MessageResp result(Object result) {
		this.result = result;
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" error='" + getError() + "'" +
			", message='" + getMessage() + "'" +
			", result='" + getResult() + "'" +
			", total='" + getTotal() + "'" +
			", size='" + getSize() + "'" +
			", page='" + getPage() + "'" +
			", timestamp='" + getTimestamp() + "'" +
			"}";
	}
	

}
