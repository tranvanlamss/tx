package com.vietsoft.payload.user;

public class ClientAccess {
  
  String clientId;
  
  String description;
  
  int status = 0;

  public ClientAccess() {
  }

  public ClientAccess(String clientId, String description, int status) {
    this.clientId = clientId;
    this.description = description;
    this.status = status;
  }

  public String getClientId() {
    return this.clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ClientAccess clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public ClientAccess description(String description) {
    this.description = description;
    return this;
  }

  public ClientAccess status(int status) {
    this.status = status;
    return this;
  }
}
