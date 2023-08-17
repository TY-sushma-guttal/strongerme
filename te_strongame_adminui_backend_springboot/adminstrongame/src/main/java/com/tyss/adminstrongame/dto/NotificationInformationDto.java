package com.tyss.adminstrongame.dto;
import java.util.List;

import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class NotificationInformationDto {

	private int notificationId;

	private String notificationImage;

	private String notificationDescription;
	
	private String notificationType;

	private List<UserInformation> notificationUsers;

	public NotificationInformationDto() {
		super();
	}
	
	public NotificationInformationDto(int notificationId, String notificationImage, String notificationDescription,
			String notificationType, List<UserInformation> notificationUsers) {
		super();
		this.notificationId = notificationId;
		this.notificationImage = notificationImage;
		this.notificationDescription = notificationDescription;
		this.notificationType = notificationType;
		this.notificationUsers = notificationUsers;
	}

	public NotificationInformationDto(int notificationId, String notificationDescription, String notificationType) {
		super();
		this.notificationId = notificationId;
		this.notificationDescription = notificationDescription;
		this.notificationType = notificationType;
	}

}
