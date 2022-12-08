//package com.vietsoft.payload.user;
//
//import java.util.List;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//
//import com.vietsoft.model.CBizDomain;
//
//public class UserDomains {
//	
//	@NotBlank(message="UID can not be blank")
//	String uid;
//	
//	
//	@NotNull(message="Domains can not be blank")
//	List<CBizDomain> bizDomains;
//
//
//	public String getUid() {
//		return uid;
//	}
//
//
//	public List<CBizDomain> getBizDomains() {
//		return bizDomains;
//	}
//
//
//	public void setUid(String uid) {
//		this.uid = uid;
//	}
//
//
//	public void setBizDomains(List<CBizDomain> bizDomains) {
//		this.bizDomains = bizDomains;
//	}
//	
//}
