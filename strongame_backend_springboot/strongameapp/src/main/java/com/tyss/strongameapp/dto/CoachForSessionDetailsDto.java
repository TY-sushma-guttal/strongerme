package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.SessionDetails;
import com.tyss.strongameapp.entity.SpecializationDetails;

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
	
	private List<SessionDetails> sessions;
	
	private List<SpecializationDetails> specializations;
	
	public CoachForSessionDetailsDto() {
		super();
	}

	public CoachForSessionDetailsDto(int coachForSessionId, String coachFullName, String coachDescription,
			long coachNumber, String coachEmailId, String coachCertifications, String coachImage,
			List<SessionDetails> sessions, List<SpecializationDetails> specializations) {
		super();
		this.coachForSessionId = coachForSessionId;
		this.coachFullName = coachFullName;
		this.coachDescription = coachDescription;
		this.coachNumber = coachNumber;
		this.coachEmailId = coachEmailId;
		this.coachCertifications = coachCertifications;
		this.coachImage = coachImage;
		this.sessions = sessions;
		this.specializations = specializations;
	}

}
