package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.CoachForSessionDetails;

import lombok.Data;
@Data
public class SpecializationDetailsDto{

	private int specializationId;

	private String specializationType;

	private String specializationDescription;

	private String specializationImage;

	private String specializationIcon;
	
	private List<CoachForSessionDetails> specializationCoaches;
	
	private List<SessionDetailsDto> sessionList;
	
	private String cases;
	
	public SpecializationDetailsDto() {
		super();
	}

	public SpecializationDetailsDto(int specializationId, String specializationType, String specializationDescription,
			String specializationImage, String specializationIcon, List<CoachForSessionDetails> specializationCoaches,
			List<SessionDetailsDto> sessionList) {
		super();
		this.specializationId = specializationId;
		this.specializationType = specializationType;
		this.specializationDescription = specializationDescription;
		this.specializationImage = specializationImage;
		this.specializationIcon = specializationIcon;
		this.specializationCoaches = specializationCoaches;
		this.sessionList = sessionList;
	}

	
}
