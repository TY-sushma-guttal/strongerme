package com.tyss.adminstrongame.dto;

import java.util.Date;


import lombok.Data;


@Data
public class UserStepsStatsDto {

	public UserStepsStatsDto() {
		super();	
	}

	private int stepId;

	private Date day;

	private double week;

	private double month;
	
	private double distanceInKm;

	private double caloriesBurent;

	private int currentSteps;

	private int targetSteps;

	private double coinsEarned;

	private UserInformationDto userId;

	
}
