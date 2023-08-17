package com.tyss.strongameapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "admin_reward_details")
public class AdminRewardDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_reward_id")
	private int adminRewardId;
	
	@Column(name = "admin_reward_coins")
	private double adminRewardCoins;

	public AdminRewardDetails() {
		super();
	}

	public AdminRewardDetails(int adminRewardId, double adminRewardCoins) {
		super();
		this.adminRewardId = adminRewardId;
		this.adminRewardCoins = adminRewardCoins;
	}

	
}
