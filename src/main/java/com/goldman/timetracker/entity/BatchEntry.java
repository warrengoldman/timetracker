package com.goldman.timetracker.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BATCH_ENTRY")
public class BatchEntry {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer batchSk;
	private Date batchDate;
	private Integer lineSk;
	public Integer getBatchSk() {
		return batchSk;
	}
	public Date getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
	public Integer getLineSk() {
		return lineSk;
	}
	public void setLineSk(Integer lineSk) {
		this.lineSk = lineSk;
	}
}
