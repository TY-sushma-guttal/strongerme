package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.SessionDetails;
import com.tyss.adminstrongame.entity.SpecializationDetails;

import lombok.Data;

@Data
public class CoachForSessionDetailsDto {
	
	private int coachForSessionId;
	
	private String coachFullName;
	
	private String coachDescription;
	
	private long coachNumber;
	
	private String coachEmailId;
	
	private String coachCertifications;
	
	private String coachImage;
	
	private List<String> specializationNames;
	
	private String validationMessage;
	
	public CoachForSessionDetailsDto() {
		super();
	}

	public CoachForSessionDetailsDto(int coachForSessionId, String coachFullName, String coachDescription,
			long coachNumber, String coachEmailId, String coachCertifications, String coachImage,
			List<String> specializationNames, String validationMessage) {
		super();
		this.coachForSessionId = coachForSessionId;
		this.coachFullName = coachFullName;
		this.coachDescription = coachDescription;
		this.coachNumber = coachNumber;
		this.coachEmailId = coachEmailId;
		this.coachCertifications = coachCertifications;
		this.coachImage = coachImage;
		this.specializationNames = specializationNames;
		this.validationMessage = validationMessage;
	}

	
}
