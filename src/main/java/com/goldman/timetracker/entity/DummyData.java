package com.goldman.timetracker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DUMMY_DATA")
public class DummyData {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer dummyDataSk;
	private String description;
	public Integer getDummyDataSk() {
		return dummyDataSk;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
