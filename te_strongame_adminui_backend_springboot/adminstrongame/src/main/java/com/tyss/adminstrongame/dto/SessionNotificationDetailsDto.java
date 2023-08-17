package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class SessionNotificationDetailsDto {
	
	private int sessionNotificationId;
	
	private String sessionNotificationDescription;
	
	private String sessionNotificationType;

	public SessionNotificationDetailsDto() {
		super();
	}

	public SessionNotificationDetailsDto(int sessionNotificationId, String sessionNotificationDescription,
			String sessionNotificationType) {
		super();
		this.sessionNotificationId = sessionNotificationId;
		this.sessionNotificationDescription = sessionNotificationDescription;
		this.sessionNotificationType = sessionNotificationType;
	}


}
