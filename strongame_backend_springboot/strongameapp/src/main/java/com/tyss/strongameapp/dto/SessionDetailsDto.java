package com.tyss.strongameapp.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.tyss.strongameapp.entity.CoachForSessionDetails;
import com.tyss.strongameapp.entity.UserInformation;

import lombok.Data;

@Data
public class SessionDetailsDto {
	
	private int sessionId;
	
	private String sessionLink;
	
	private String sessionType;
	
	private Date sessionDate;
	
	private Time sessionTime;
	
	private String sessionCoachName;
	
	private double sessionDuration;
	
	private int slotsAvailable;
	
	private String photo;
	
	private int cases;
	
	private boolean sessionFlag;
	
	private boolean isUserSessionMapped;
	
	private String validationCase;

	public SessionDetailsDto() {
		super();
	}

	public SessionDetailsDto(int sessionId, String sessionLink, String sessionType, Date sessionDate, Time sessionTime,
			String sessionCoachName, double sessionDuration, int slotsAvailable, String photo, int cases,
			boolean sessionFlag) {
		super();
		this.sessionId = sessionId;
		this.sessionLink = sessionLink;
		this.sessionType = sessionType;
		this.sessionDate = sessionDate;
		this.sessionTime = sessionTime;
		this.sessionCoachName = sessionCoachName;
		this.sessionDuration = sessionDuration;
		this.slotsAvailable = slotsAvailable;
		this.photo = photo;
		this.cases = cases;
		this.sessionFlag = sessionFlag;
	}

	
}
