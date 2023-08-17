package com.tyss.strongameapp.service;

import com.tyss.strongameapp.dto.LeaderBoardDto;
import com.tyss.strongameapp.dto.MyOrderDto;
import com.tyss.strongameapp.dto.NotificationInformationDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * SettingsService is implemented by SettingsServiceImple class to display the content of setting page.
 * @author Sushma Guttal
 *
 */
public interface SettingsService {

	/**
	 * This method is implemented by its implementation class, used to edit the user profile.
	 * @param data
	 * @param image 
	 * @return UserInformationDto
	 */
	UserInformationDto editUser(UserInformationDto data);

	
	/**
	 * This method is implemented by its implementation class, used to display the leader board.
	 * @param userId
	 * @return LeaderBoardDto
	 */
	LeaderBoardDto leadBoard(int userId);

	
	/**
	 * This method is implemented by its implementation class, to display the contend of my order page.
	 * @param userId
	 * @return MyOrderDto
	 */
	List<MyOrderDto> myOrder(Integer userId);

	
	/**
	 * This method is implemented by its implementation class, to fetch all the notifications.
	 * @param userId
	 * @return List<NotificationInformationDto>
	 */
	List<NotificationInformationDto> notification(Integer userId);
	
	UserInformationDto uploadImage(UserInformationDto data, MultipartFile image);

}//End of SettingsService interface.
