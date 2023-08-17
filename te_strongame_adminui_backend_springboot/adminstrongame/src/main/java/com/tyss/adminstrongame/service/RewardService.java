package com.tyss.adminstrongame.service;

import java.util.List;

import com.tyss.adminstrongame.dto.AdminRewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardInformationDto;

/**
 * This is the service interface for Rewards Page . Here you find 
 * abstract methods for updating, fetching and deleting the
 * Reward Details
 * 
 * @author Trupthi
 * 
 */
public interface RewardService {

	/**
	 * This method is implemented by its implementation class to get all Reward Details
	 * 
	 * @return List<RewardInformationDto>
	 */
	List<RewardInformationDto> getAllRewards();

	/**
	 * This method is implemented by its implementation class to get Reward Details by name or mobileNo or email
	 * 
	 * @param name,mobileNo,email
	 * @return List<RewardInformationDto>
	 */
	List<RewardInformationDto> getReward(String name,Long mobileNo,String email);

	/**
	 * This method is implemented by its implementation class to update Coins 
	 * 
	 * @param data
	 * @return RewardDetailsDto
	 */
	AdminRewardDetailsDto updateCoins(RewardInformationDto data);

	/**
	 * This method is implemented by its implementation class to delete Reward Details 
	 * 
	 * @param data
	 * @return RewardDetailsDto
	 */
	AdminRewardDetailsDto deleteRewardDetails(RewardInformationDto data);

}
