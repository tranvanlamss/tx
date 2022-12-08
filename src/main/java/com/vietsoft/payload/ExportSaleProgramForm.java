package com.vietsoft.payload;

import java.util.Date;
import java.util.List;

public class ExportSaleProgramForm {
	Date startDate;
	Date endDate;
	List<String> customers;
	List<String> productions;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getCustomers() {
		return customers;
	}

	public void setCustomers(List<String> customers) {
		this.customers = customers;
	}

	public List<String> getProductions() {
		return productions;
	}

	public void setProductions(List<String> productions) {
		this.productions = productions;
	}

	@Override
	public String toString() {
		return "ExportSaleProgramForm{" +
				"startDate=" + startDate +
				", endDate=" + endDate +
				", customers=" + customers +
				", productions=" + productions +
				'}';
	}
}
