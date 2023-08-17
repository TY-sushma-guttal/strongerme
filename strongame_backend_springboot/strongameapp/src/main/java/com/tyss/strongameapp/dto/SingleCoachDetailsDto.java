package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class SingleCoachDetailsDto {

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
	
	private String cases;
	
	private boolean subscribed;
	
	private List<TransformationDetailsDto> transformations ;

	public SingleCoachDetailsDto(int coachId, String coachName, String certifications, String coachDetails,
			long phoneNumber, String emailId, String badge, int experience, int noOfUserTrained, String specialization,
			String photo, boolean subscribed) {
		super();
		this.coachId = coachId;
		this.coachName = coachName;
		this.certifications = certifications;
		this.coachDetails = coachDetails;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.badge = badge;
		this.experience = experience;
		this.noOfUserTrained = noOfUserTrained;
		this.specialization = specialization;
		this.photo = photo;
		this.subscribed = subscribed;
	}

	public SingleCoachDetailsDto() {
		super();
	}
	
}
