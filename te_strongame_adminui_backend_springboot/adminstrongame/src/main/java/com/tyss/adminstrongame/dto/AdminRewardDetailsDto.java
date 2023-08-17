package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class AdminRewardDetailsDto {

	private int adminRewardId;

	private double adminRewardCoins;
	
	private String validationMessage;

	public AdminRewardDetailsDto() {
		super();
	}
	
	public AdminRewardDetailsDto(int adminRewardId, double adminRewardCoins) {
		super();
		this.adminRewardId = adminRewardId;
		this.adminRewardCoins = adminRewardCoins;
	}

	public AdminRewardDetailsDto(int adminRewardId, double adminRewardCoins, String validationMessage) {
		super();
		this.adminRewardId = adminRewardId;
		this.adminRewardCoins = adminRewardCoins;
		this.validationMessage = validationMessage;
	}


}
