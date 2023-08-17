package com.tyss.adminstrongame.service;

import java.util.List;

import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.dto.UserDetailsDto;
import com.tyss.adminstrongame.dto.UserInformationDto;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.entity.UserInformation;

/**
 * This is the service interface for Users Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * User Information
 * 
 * @author Trupthi
 * 
 */
public interface UserService {
	
	/**
	 * This method is implemented by its implementation class to update User Information
	 * 
	 * @param data
	 * @return UserInformationDto
	 */
	UserInformationDto updateUser(UserInformationDto data);

	/**
	 * This method is implemented by its implementation class to delete User Information
	 * 
	 * @param userId
	 * @return boolean
	 */
	boolean deleteUser(int userId);

	/**
	 * This method is implemented by its implementation class to get User Information by email or name or mobile number
	 * 
	 * @param email,name,mobileNo
	 * @return List<UserInformation>
	 */
	List<UserInformation> getUser(String email, String name,Long mobileNo);

	/**
	 * This method is implemented by its implementation class to get all User Informations 
	 * 
	 * @return List<UserInformation>
	 */
	List<UserDetailsDto> getAllUsers();
	
	/**
	 * This method is implemented by its implementation class to get all admin Notification Information
	 * 
	 * @return List<NotificationInformationDto>
	 */
	List<NotificationInformationDto> getAdminNotifications();
	


}
