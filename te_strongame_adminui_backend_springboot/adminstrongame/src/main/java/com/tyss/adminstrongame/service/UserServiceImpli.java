package com.tyss.adminstrongame.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.adminstrongame.dto.CoachDetailsDto;
import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.dto.UserDetailsDto;
import com.tyss.adminstrongame.dto.UserInformationDto;
import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.entity.OrderInformation;
import com.tyss.adminstrongame.entity.PlanDetails;
import com.tyss.adminstrongame.entity.RewardDetails;
import com.tyss.adminstrongame.entity.UserInformation;
import com.tyss.adminstrongame.entity.UserStepsStats;
import com.tyss.adminstrongame.repository.NotificationInformationRepository;
import com.tyss.adminstrongame.repository.UserInformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for UserService interface.
 * Here you find implementation for updating, fetching and deleting the 
 * User Information
 * 
 * @author Trupthi
 * 
 */
@Service
public class UserServiceImpli implements UserService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	UserInformationRepository userRepo;
	
	@Autowired
	SSSUploadFile sssUploadFile;
	
	@Autowired
	private NotificationInformationRepository notificationRepo;

	/**
	 * This method is implemented to update User Information
	 * 
	 * @param data
	 * @return UserInformationDto
	 */
	@Transactional
	@Override
	public UserInformationDto updateUser(UserInformationDto data) {
		if(data!=null) {	
			// convert dto to entity
			UserInformation userEntity = new UserInformation();
			BeanUtils.copyProperties(data, userEntity);

			Optional<UserInformation> banner=userRepo.findById(userEntity.getUserId());

			if (!banner.isPresent()) {
				return null;
			} else {

				//				UserInformation user=userRepo.save(userEntity);

				userRepo.updateUser(userEntity.getUserId(),  userEntity.getName(), userEntity.getDateOFBirth(), userEntity.getMobileNo(), userEntity.getEmail(),
						userEntity.getReferalCode(), userEntity.getWeight(), userEntity.getHeight(),userEntity.getGender(), userEntity.getPhoto());
				BeanUtils.copyProperties(data, userEntity);
				return data;
			}

		} else{
			throw new com.tyss.adminstrongame.exception.UserException(
					"Failed to update user: user information should not be empty!");

		}

	}// End Of the updateUser

	/**
	 * This method is implemented to delete User Information
	 * 
	 * @param userId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteUser(int userId) {
		if(userId!=0) {

			if (!userRepo.findById(userId).isPresent()) {
				return false;
			}else {
				String filePath = "User/"+userId;
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				userRepo.deleteById(userId);
				return true;
			}

		}else {
			throw new com.tyss.adminstrongame.exception.UserException(
					"Failed to delete user: user information should not be empty!");

		}

	}// End Of the deleteUser

	/**
	 * This method is implemented to get User Information by email or name or mobile number
	 * 
	 * @param email,name,mobileNo
	 * @return List<UserInformation>
	 */
	@Override
	public List<UserInformation> getUser(String email, String name, Long mobileNo) {
		List<UserInformation> list= userRepo.fetchUser(email, name,mobileNo);
		return list;
	}// End Of the getUser

	/**
	 * This method is implemented to get all User Informations 
	 * 
	 * @return List<UserInformation>
	 */
	@Override
	public List<UserDetailsDto> getAllUsers() {
		List<UserInformation> list= userRepo.findAll();
		List<UserDetailsDto> dto = new ArrayList<UserDetailsDto>();
		for (UserInformation userInformation : list) {
			List<String> coachNames = new ArrayList<String>();
			List<String> plans = new ArrayList<String>();
			UserDetailsDto data = new UserDetailsDto();
			BeanUtils.copyProperties(userInformation,data);
			String userAddress=null;
			
			List<PlanDetails> planDetails = userInformation.getUserPlan();
			for (PlanDetails planDetail : planDetails) {
				String plan = planDetail.getPlanName();
				plans.add(plan);
			}

			List<CoachDetails> coachDetails = userInformation.getUserCoach();
			for (CoachDetails coachDetail : coachDetails) {
				String coachName= coachDetail.getCoachName();
				coachNames.add(coachName);
			}	

			RewardDetails rewardDetails =userInformation.getReward();
			if(rewardDetails!=null) {
				data.setUserRewards(rewardDetails.getRewardCoins());
			}
			UserStepsStats userStepsStats = userInformation.getSteps();
			if(userStepsStats!=null) {
				data.setUserSteps(userStepsStats.getCurrentSteps());
			}
			
			List<OrderInformation> orderInfo = userInformation.getUserOrders();
			if(!orderInfo.isEmpty()) {
				 userAddress = orderInfo.get(orderInfo.size()-1).getAddress();
			}
			
			data.setUserCoachNames(coachNames);
			data.setUserPlanNames(plans);
			data.setAddress(userAddress);
			dto.add(data);
		}
		return dto;

	}// End Of the getAllUsers
	
	/**
	 * This method is implemented to get all admin Notification Information
	 * 
	 * @return List<NotificationInformation>
	 */
	@Override
	public List<NotificationInformationDto> getAdminNotifications() {
		return notificationRepo.getAdminNotifications();
	}// End Of the getProductNotifications


}// End Of the Class
