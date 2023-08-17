package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.HomeBannerInformation;
import com.tyss.adminstrongame.entity.PlanDetails;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.entity.UserInformation;

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

	private List<PlanDetails> coachPlans;

	private long coachTransformations;

	private List<TransformationDetails> transformations;
	
	private List<HomeBannerInformation> coachHomeBanner;
	
	private String validationMessage;

	public CoachDetailsDto() {
		super();
	}

}
