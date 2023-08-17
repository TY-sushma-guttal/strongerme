package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.CoachForSessionDetailsDto;

public interface CoachForSessionService {
	
	/**
	 * This method is implemented by its implementation class to save CoachForSession Details
	 * 
	 * @param data
	 * @return CoachForSessionDetailsDto
	 */
	CoachForSessionDetailsDto addCoachForSession(CoachForSessionDetailsDto data, MultipartFile coachImage);

	/**
	 * This method is implemented by its implementation class to get all CoachForSession Details
	 * 
	 * @return List<CoachForSessionDetailsDto>
	 */
	List<CoachForSessionDetailsDto> getAllCoachForSession();

	/**
	 * This method is implemented by its implementation class to update CoachForSession Details
	 * 
	 * @param data
	 * @return CoachForSessionDetailsDto
	 */
	CoachForSessionDetailsDto updateCoachForSession(CoachForSessionDetailsDto data, MultipartFile coachImage);

	/**
	 * This method is implemented by its implementation class to delete CoachForSession Details
	 * 
	 * @param coachForSessionId
	 * @return boolean
	 */
	boolean deleteCoachForSession(int coachForSessionId);
	
	/**
	 * This method is implemented by its implementation class to get all Specialization Type
	 * 
	 * @return List<String>
	 */
	List<String> getAllSpecializationType();


}
