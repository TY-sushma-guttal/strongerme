package com.tyss.adminstrongame.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDetailsDto {

	private int userId;

	private String name;

	private Date dateOFBirth;

	private String email;

	private long mobileNo;

	private String referalCode;

	private double height;

	private double weight;

	private String gender;

	private String photo;

	private double userRewards;

	private int userSteps;

	private List<String> userCoachNames;

	private List<String> userPlanNames;
	
	private String address;

	public UserDetailsDto() {
		super();
	}

	public UserDetailsDto(int userId, String name, Date dateOFBirth, String email, long mobileNo, String referalCode,
			double height, double weight, String gender, String photo, double userRewards, int userSteps,
			List<String> userCoachNames, List<String> userPlanNames, String address) {
		super();
		this.userId = userId;
		this.name = name;
		this.dateOFBirth = dateOFBirth;
		this.email = email;
		this.mobileNo = mobileNo;
		this.referalCode = referalCode;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.photo = photo;
		this.userRewards = userRewards;
		this.userSteps = userSteps;
		this.userCoachNames = userCoachNames;
		this.userPlanNames = userPlanNames;
		this.address = address;
	}


}
