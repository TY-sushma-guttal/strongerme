package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.TransformationDetails;

import lombok.Data;

@Data
public class CoachDetailsDto {

	private int coachId;

	private String coachName;

	private String certifications;

	private String coachDetails;

	private long phoneNumber;

	private String emailId;

	private String badge;
	
	private int experience;
	
	private int noOfUserTrained;
	
	private String specialization;
	
	private String photo;
	
	public CoachDetailsDto() {
	}

}
