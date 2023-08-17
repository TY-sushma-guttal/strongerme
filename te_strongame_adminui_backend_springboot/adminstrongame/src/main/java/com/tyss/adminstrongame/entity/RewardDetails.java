package com.tyss.adminstrongame.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "rewards_details")
public class RewardDetails {
	
	public RewardDetails() {
		super();
	}
	
	public RewardDetails(int rewardId, double noOfSteps, int rewardCoins) {
		super();
		this.rewardId = rewardId;
		this.noOfSteps = noOfSteps;
		this.rewardCoins = rewardCoins;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reward_id")
	private int rewardId;
	
	@Column(name = "no_of_steps")
	private double noOfSteps;
	
	@Column(name = "reward_coins")
	private double rewardCoins;
	
//  @OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="user_id")
//	private UserInformation user;
	
}
