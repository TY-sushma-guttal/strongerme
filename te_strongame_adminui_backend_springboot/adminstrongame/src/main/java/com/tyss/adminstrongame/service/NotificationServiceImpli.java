package com.tyss.adminstrongame.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.repository.NotificationInformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for NotificationService interface.
 * Here you find implementation for saving, updating, fetching and deleting the 
 * Notification Information
 * 
 * @author Trupthi
 * 
 */
@Service
public class NotificationServiceImpli implements NotificationService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	NotificationInformationRepository notificationRepo;

	
	@Autowired
	private SSSUploadFile sssUploadFile;
	
	
	/**
	 * This method is implemented to delete Notification Information
	 * 
	 * @param notificationId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteNotification(int notificationId) {
		if(notificationId!=0) {

			if (!notificationRepo.findById(notificationId).isPresent()) {
				return false;
			}else {
			String imagePath = "Notification/"+notificationId;
			sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
			notificationRepo.deleteById(notificationId);
			return true;
			}

		}else {
			throw new com.tyss.adminstrongame.exception.NotificationException(
					"Failed to delete notification: notification information should not be empty!");

		}

	}// End Of the deleteNotification

	/**
	 * This method is implemented to update Notification Information
	 * 
	 * @param data
	 * @return NotificationInformationDto
	 */
	@Transactional
	@Override
	public NotificationInformationDto updateNotification(NotificationInformationDto data, MultipartFile image) {
		if(data!=null) {	
			// convert dto to entity
			NotificationInformation notificationEntity = new NotificationInformation();
			BeanUtils.copyProperties(data, notificationEntity);

			
			if (!notificationRepo.findById(notificationEntity.getNotificationId()).isPresent()) {
				return null;
			} else {
				
				String imagePath = "Notification/"+data.getNotificationId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
				if(!image.isEmpty()) {
					notificationEntity.setNotificationImage(sssUploadFile.uploadFile(image, data.getNotificationId(), "Notification"));
				}
				
				NotificationInformation savedObj = notificationRepo.save(notificationEntity);
				BeanUtils.copyProperties(savedObj,data);
				return data;
			}

		} else{
			throw new com.tyss.adminstrongame.exception.NotificationException(
					"Failed to update notification: Notification information should not be empty!");

		}
	}// End Of the updateNotification

	/**
	 * This method is implemented to get Notification Information by id
	 * 
	 * @param notificationId
	 * @return Optional<NotificationInformation>
	 */
	@Override
	public Optional<NotificationInformation> getNotificationById(int notificationId) {
		Optional<NotificationInformation> notificationEntity=notificationRepo.findById(notificationId);
		if(!notificationEntity.isPresent())
			return null;
		else
			return notificationEntity;

	}// End Of the getNotificationById

	/**
	 * This method is implemented to get all Notification Information
	 * 
	 * @return List<NotificationInformation>
	 */
	@Override
	public List<NotificationInformation> getNotification() {
		return notificationRepo.getAllNotifications();
	}// End Of the getNotification

	/**
	 * This method is implemented to save Notification Information
	 * 
	 * @param data
	 * @return NotificationInformationDto
	 */
	@Transactional
	@Override
	public NotificationInformationDto addNotification(NotificationInformationDto data , MultipartFile image) {
		if (data!= null) {
			// convert dto to entity
			NotificationInformation notificationEntity = new NotificationInformation();
			BeanUtils.copyProperties(data, notificationEntity);

			notificationEntity= notificationRepo.save(notificationEntity);
			if(!image.isEmpty()) {
				notificationEntity.setNotificationImage(sssUploadFile.uploadFile(image, notificationEntity.getNotificationId(), "Notification"));
				notificationRepo.save(notificationEntity);
			}
			
			BeanUtils.copyProperties(notificationEntity, data);
			
			return data;

		} else{
			throw new com.tyss.adminstrongame.exception.NotificationException(
					"Failed to add new notification: notification data should not be empty!");

		}

	}// End Of the addNotification


}// End Of the Class
