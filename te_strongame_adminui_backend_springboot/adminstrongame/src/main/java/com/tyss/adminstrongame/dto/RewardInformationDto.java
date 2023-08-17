package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class RewardInformationDto {
  
	private String name;
	
	private String email;
	
	private long mobileNo;
	
	private double rewardCoins;
	
	private String photo;

	public RewardInformationDto(String name, String email, long mobileNo, double rewardCoins, String photo) {
		super();
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.rewardCoins = rewardCoins;
		this.photo = photo;
	}

	public RewardInformationDto() {
		super();
	}

		
}
