package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

public class ProgramDetail implements Serializable {

	int programId;
	String programName;

	String channelName;
	Date programSceduledTime;

	public ProgramDetail() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {

		return "program id " + this.programId + ", programName " + this.programName + ", channel name "
				+ this.channelName + ", program sceduled time " + this.programSceduledTime;
	}

	public ProgramDetail(int programId, String programName, String channelName, Date programSceduledTime) {
		super();
		this.programId = programId;
		this.programName = programName;
		this.channelName = channelName;
		this.programSceduledTime = programSceduledTime;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getProgramSceduledTime() {
		return programSceduledTime;
	}

	public void setProgramSceduledTime(Date programSceduledTime) {
		this.programSceduledTime = programSceduledTime;
	}

}
