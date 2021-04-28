package com.goldman.timetracker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LINE_ENTRY")
public class LineEntry {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer lineSk;
	private String line;
	public Integer getLineSk() {
		return lineSk;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
}
