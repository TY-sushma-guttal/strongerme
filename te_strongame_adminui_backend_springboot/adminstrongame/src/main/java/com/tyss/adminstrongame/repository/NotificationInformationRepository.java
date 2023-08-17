package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.entity.NotificationInformation;


public interface NotificationInformationRepository extends JpaRepository<NotificationInformation, Integer> {
	
	@Query("SELECT n FROM NotificationInformation n where n.notificationType=null")
	public List<NotificationInformation> getAllNotifications();
	
	@Query("SELECT new com.tyss.adminstrongame.dto.NotificationInformationDto(n.notificationId, n.notificationDescription, n.notificationType) FROM "
			+ "NotificationInformation n where n.notificationType='plan' or n.notificationType='product' or n.notificationType='package' order by"
			+ " n.notificationId desc" )
	public List<NotificationInformationDto> getAdminNotifications();
	
}
