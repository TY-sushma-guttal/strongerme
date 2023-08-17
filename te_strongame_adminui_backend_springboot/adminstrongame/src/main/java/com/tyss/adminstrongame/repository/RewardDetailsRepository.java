package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.RewardDetails;


public interface RewardDetailsRepository extends JpaRepository<RewardDetails, Integer> {
  
//	@Query( value = "select new com.tyss.strongameapp.dto.RewardInformationDto(name,email,mobile_no,reward_coins) from strongameapp_db.user_information natural join strongameapp_db.rewards_details", 
//			  nativeQuery = true)
//	List<RewardInformationDto> getAllRewards();
	
//	select name,mobile_no,email,reward_coins from strongameapp_db.user_information natural join strongameapp_db.rewards_details;
	
//	@Query("select new com.tyss.strongameapp.dto.RewardInformationDto(u.name, u.mobileNo, u.email, r.rewardCoins) from RewardDetails r join r.user u")
//	List<RewardInformationDto> getAllRewards();
//	
//	@Query("select new com.tyss.strongameapp.dto.RewardInformationDto(u.name, u.mobileNo, u.email, r.rewardCoins) from RewardDetails r join r.user u on u.name=?1 or u.mobileNo=?2 or u.email=?3")
//	List<RewardInformationDto> getReward(String name,Long mobileNo,String email);
//	
//	@Query("select new com.tyss.strongameapp.dto.RewardDetailsDto(r.rewardId, r.noOfSteps, r.rewardCoins, r.user) from RewardDetails r join r.user u on u.name=?1 and u.mobileNo=?2 and u.email=?3")
//	RewardDetailsDto getRewardDetails(String name,Long mobileNo,String email);
//	
//	@Query("select u.userId from RewardDetails r join r.user u on u.name=?1 and u.mobileNo=?2 and u.email=?3")
//	int getUserId(String name,Long mobileNo,String email);	
//	
//	@Modifying
//	@Query( value = "update strongameapp_db.rewards_details set reward_coins=:coins where reward_id=rewardId;", 
//			  nativeQuery = true)
//	void updateCoin(double coins,int rewardId);
//	
//	@Modifying
//	@Query( value = "delete from strongameapp_db.rewards_details where user_id=:userId", 
//			  nativeQuery = true)
//	void deleteRewardDetails(int userId);

	
}