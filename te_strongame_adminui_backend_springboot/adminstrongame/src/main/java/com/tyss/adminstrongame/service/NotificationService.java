package com.tyss.adminstrongame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.entity.NotificationInformation;

/**
 * This is the service interface for Notification Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * Notification Information
 * 
 * @author Trupthi
 * 
 */
public interface NotificationService {
	
	/**
	 * This method is implemented by its implementation class to delete Notification Information
	 * 
	 * @param notificationId
	 * @return boolean
	 */
	boolean deleteNotification(int notificationId);

	/**
	 * This method is implemented by its implementation class to update Notification Information
	 * 
	 * @param data
	 * @param image 
	 * @return NotificationInformationDto
	 */
	NotificationInformationDto updateNotification(NotificationInformationDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to get Notification Information by id
	 * 
	 * @param notificationId
	 * @return Optional<NotificationInformation>
	 */
	Optional<NotificationInformation> getNotificationById(int notificationId);

	/**
	 * This method is implemented by its implementation class to get all Notification Information
	 * 
	 * @return List<NotificationInformation>
	 */
	List<NotificationInformation> getNotification();

	/**
	 * This method is implemented by its implementation class to save Notification Information
	 * 
	 * @param data
	 * @param image 
	 * @return NotificationInformationDto
	 */
	NotificationInformationDto addNotification(NotificationInformationDto data, MultipartFile image);

}
