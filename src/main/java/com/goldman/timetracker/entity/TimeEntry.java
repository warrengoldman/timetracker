package com.goldman.timetracker.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIME_ENTRY")
public class TimeEntry {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer timeEntrySk;
	private Date timeEntryDate;
	private String activityDescription;
	private BigDecimal hours;
	private Boolean billable;
	private Integer lineSk;
	public Integer getTimeEntrySk() {
		return timeEntrySk;
	}
	public Date getTimeEntryDate() {
		return timeEntryDate;
	}
	public void setTimeEntryDate(Date timeEntryDate) {
		this.timeEntryDate = timeEntryDate;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	public BigDecimal getHours() {
		return hours;
	}
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}
	public Boolean getBillable() {
		return billable;
	}
	public void setBillable(Boolean billable) {
		this.billable = billable;
	}
	public Integer getLineSk() {
		return lineSk;
	}
	public void setLineSk(Integer lineSk) {
		this.lineSk = lineSk;
	}
}
