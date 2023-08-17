package com.tyss.adminstrongame.dto;

import java.io.Serializable;
import java.util.List;

import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class PlanDetailsDto implements Serializable{

	private int planId;

	private String planName;

	private String planDetails;

	private double noOfWeeks;

	private double planPrice;

	private List<CoachDetails> coach;
	
	private List<UserInformation> planUsers;
	
    private List<CoachDetails> planCoaches;
    
    private String validationMessage;

}
