package com.tyss.adminstrongame.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.adminstrongame.dto.AdminRewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardInformationDto;
import com.tyss.adminstrongame.entity.AdminRewardDetails;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.entity.RewardDetails;
import com.tyss.adminstrongame.entity.UserInformation;
import com.tyss.adminstrongame.repository.AdminRewardRepository;
import com.tyss.adminstrongame.repository.NotificationInformationRepository;
import com.tyss.adminstrongame.repository.RewardDetailsRepository;
import com.tyss.adminstrongame.repository.UserInformationRepository;

import java.lang.Math.*;

/**
 * This is the service implementation class for RewardService interface.
 * Here you find implementation for updating, fetching and deleting the 
 * Reward Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class RewardServiceImpli implements RewardService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	AdminRewardRepository rewardRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	UserInformationRepository userRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	NotificationInformationRepository notificationRepo;

	static String message;
	/**
	 * This method is implemented to get all Reward Details
	 * 
	 * @return List<RewardInformationDto>
	 */
	@Override
	public List<RewardInformationDto> getAllRewards() {
		return userRepo.getAllRewards();		
	}// End Of the getAllRewards

	/**
	 * This method is implemented to get Reward Details by name or mobileNo or email
	 * 
	 * @param name,mobileNo,email
	 * @return List<RewardInformationDto>
	 */
	@Override
	public List<RewardInformationDto> getReward(String name, Long mobileNo, String email) {
		List<RewardInformationDto> rewardDetails = userRepo.getReward(mobileNo, email);

		if(rewardDetails.isEmpty()) {
			return null;
		}else {
			return rewardDetails;
		}

	}// End Of the getReward

	/**
	 * This method is implemented to update Coins 
	 * 
	 * @param data
	 * @return RewardDetailsDto
	 */
	@Transactional
	@Override
	public AdminRewardDetailsDto deleteRewardDetails(RewardInformationDto data) {
		if (data!= null) {
			AdminRewardDetailsDto dto = userRepo.getRewardDetails(data.getMobileNo(), data.getEmail());

			AdminRewardDetails adminRewardEntity = new AdminRewardDetails();
			BeanUtils.copyProperties(dto,adminRewardEntity);

			if(adminRewardEntity==null) {
				return null;
			}else {
				userRepo.updateReward(adminRewardEntity.getAdminRewardId());
				rewardRepo.deleteById(adminRewardEntity.getAdminRewardId());
				return dto;
			}
		} else{
			throw new com.tyss.adminstrongame.exception.RewardException(
					"Failed to update reward: reward data should not be empty!");
		}

	}// End Of the deleteRewardDetails

	/**
	 * This method is implemented to delete Reward Details 
	 * 
	 * @param data
	 * @return RewardDetailsDto
	 */
	@Transactional
	@Override
	public AdminRewardDetailsDto updateCoins(RewardInformationDto data) {
		if (data!= null) {
			if(data.getRewardCoins()<0) {
				AdminRewardDetailsDto adminRewardDetailsDto = new AdminRewardDetailsDto();
				adminRewardDetailsDto.setValidationMessage("Value cannot be negative for Coins field");
				return adminRewardDetailsDto;
			}else {
				AdminRewardDetailsDto dto = userRepo.getRewardDetails(data.getMobileNo(), data.getEmail());

				if(dto==null) {
					return null;
				}else {

					UserInformation userInformation = userRepo.fetchUserById(data.getEmail());

					double newValue = data.getRewardCoins()-(userInformation.getAdminReward().getAdminRewardCoins());

					double restrictedValue= Double.parseDouble(String.format("%.2f", newValue));

					AdminRewardDetails adminRewardEntity = new AdminRewardDetails();
					BeanUtils.copyProperties(dto,adminRewardEntity);

					double restrictedCoin= Double.parseDouble(String.format("%.2f", data.getRewardCoins()));

					adminRewardEntity.setAdminRewardCoins(restrictedCoin);
					AdminRewardDetails updatedreward = rewardRepo.save(adminRewardEntity);

					NotificationInformation notificationInfo = new NotificationInformation();

					if(restrictedValue>0) {
						message = "Admin has added "+restrictedValue+" reward coins for you";
					}else if(restrictedValue<0) {
						message = "Admin has reduced "+Math.abs(restrictedValue)+" reward coins for you";	
					}

					if(restrictedValue!=0) {
						notificationInfo.setNotificationDescription(message);
						notificationInfo.setNotificationType("specific");

						userInformation.getNotificaton().add(notificationInfo);

						userRepo.save(userInformation);
					}

					BeanUtils.copyProperties(updatedreward,dto);
					return dto;
				}
			}
		} else{
			throw new com.tyss.adminstrongame.exception.RewardException(
					"Failed to update reward: reward data should not be empty!");
		}
	}// End Of the updateCoins

}// End Of the Class
