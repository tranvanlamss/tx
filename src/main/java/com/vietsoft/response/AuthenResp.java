package com.vietsoft.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@SuppressWarnings("serial")
public class AuthenResp implements Serializable {
  int status;
  String error;
  String message;
  Date timestamp = new Date();

  public AuthenResp() {
  }

  public AuthenResp(int status, String error, String message, Date timestamp) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.timestamp = timestamp;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getError() {
    return this.error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public AuthenResp status(int status) {
    this.status = status;
    return this;
  }

  public AuthenResp error(String error) {
    this.error = error;
    return this;
  }

  public AuthenResp message(String message) {
    this.message = message;
    return this;
  }

  public AuthenResp timestamp(Date timestamp) {
    this.timestamp = timestamp;
    return this;
  }
}
