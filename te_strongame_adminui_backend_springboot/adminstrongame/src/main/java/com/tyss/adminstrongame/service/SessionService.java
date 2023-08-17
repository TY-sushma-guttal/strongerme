package com.tyss.adminstrongame.service;

import java.util.List;

import com.tyss.adminstrongame.dto.SessionCoachDto;
import com.tyss.adminstrongame.dto.SessionDetailsDto;
import com.tyss.adminstrongame.dto.SessionNotificationDetailsDto;


public interface SessionService {
	
	/**
	 * This method is implemented by its implementation class to save Session Details
	 * 
	 * @param data
	 * @return SessionDetailsDto
	 */
	SessionDetailsDto addSessionDetails(SessionDetailsDto data);

	/**
	 * This method is implemented by its implementation class to get all Session Details
	 * 
	 * @return List<SessionDetailsDto>
	 */
	List<SessionDetailsDto> getAllSessionDetails();

	/**
	 * This method is implemented by its implementation class to update Session Details
	 * 
	 * @param data
	 * @return SessionDetailsDto
	 */
	SessionDetailsDto updateSessionDetails(SessionDetailsDto data);

	/**
	 * This method is implemented by its implementation class to delete Session Details
	 * 
	 * @param sessionId
	 * @return boolean
	 */
	boolean deleteSessionDetails(int sessionId);
	
	/**
	 * This method is implemented by its implementation class to get all Coaches
	 * 
	 * @return List<SessionCoachDto>
	 */
	List<SessionCoachDto> getAllCoaches();
	
	List<SessionNotificationDetailsDto> getSessionNotifications();


}
