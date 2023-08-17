package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.CoachForSessionDetails;

import lombok.Data;

@Data
public class SpecializationDetailsDto{

	private int specializationId;

	private String specializationType;

	private String specializationDescription;

	private String specializationImage;

	private String specializationIcon;
	
	private List<CoachForSessionDetails> specializationCoaches;
	
	public SpecializationDetailsDto() {
		super();
	}

	public SpecializationDetailsDto(int specializationId, String specializationType, String specializationDescription,
			String specializationImage, String specializationIcon, List<CoachForSessionDetails> specializationCoaches) {
		super();
		this.specializationId = specializationId;
		this.specializationType = specializationType;
		this.specializationDescription = specializationDescription;
		this.specializationImage = specializationImage;
		this.specializationIcon = specializationIcon;
		this.specializationCoaches = specializationCoaches;
	}

	
}
