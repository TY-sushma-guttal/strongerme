package com.tyss.adminstrongame.dto;

import javax.persistence.Column;

import lombok.Data;

@Data
public class RewardDetailsDto {

	private int rewardId;

	private double noOfSteps;

	private double rewardCoins;

	public RewardDetailsDto(int rewardId, double noOfSteps, double rewardCoins) {
		super();
		this.rewardId = rewardId;
		this.noOfSteps = noOfSteps;
		this.rewardCoins = rewardCoins;
	}

}
