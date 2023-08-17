package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.SessionNotificationDetailsDto;
import com.tyss.adminstrongame.entity.SessionNotificationDetails;

public interface SessionNotificationRepo extends JpaRepository<SessionNotificationDetails, Integer>{
	
	@Query("SELECT new com.tyss.adminstrongame.dto.SessionNotificationDetailsDto(s.sessionNotificationId, s.sessionNotificationDescription,\n"
			+ " s.sessionNotificationType) FROM SessionNotificationDetails s order by s.sessionNotificationId desc" )
	public List<SessionNotificationDetailsDto> getSessionNotifications();

}
