package com.tyss.adminstrongame.dto;

import java.sql.Date;
import java.sql.Time;

import java.util.List;

import com.tyss.adminstrongame.entity.CoachForSessionDetails;
import com.tyss.adminstrongame.entity.UserInformation;

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

	private int coachForSessionId;

	private String photo;
	
	private String validationMessage;

	public SessionDetailsDto() {
		super();
	}

	public SessionDetailsDto(int sessionId, String sessionLink, String sessionType, Date sessionDate, Time sessionTime,
			String sessionCoachName, double sessionDuration, int slotsAvailable, int coachForSessionId, String photo,
			String validationMessage) {
		super();
		this.sessionId = sessionId;
		this.sessionLink = sessionLink;
		this.sessionType = sessionType;
		this.sessionDate = sessionDate;
		this.sessionTime = sessionTime;
		this.sessionCoachName = sessionCoachName;
		this.sessionDuration = sessionDuration;
		this.slotsAvailable = slotsAvailable;
		this.coachForSessionId = coachForSessionId;
		this.photo = photo;
		this.validationMessage = validationMessage;
	}

}
