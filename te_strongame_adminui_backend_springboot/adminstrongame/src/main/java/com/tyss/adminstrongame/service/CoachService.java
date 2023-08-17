package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.CoachDetailsDto;
import com.tyss.adminstrongame.entity.CoachDetails;

/**
 * This is the service interface for Coaches Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * Coach Details
 * 
 * @author Trupthi
 * 
 */
public interface CoachService {
	
	/**
	 * This method is implemented by its implementation class to get all Coach Details
	 * 
	 * @param data
	 * @return List<CoachDetailsDto>
	 */
	List<CoachDetailsDto> getAllCoachDetails();

	/**
	 * This method is implemented by its implementation class to save Coach Details
	 * 
	 * @param data
	 * @param coachImage 
	 * @return CoachDetailsDto
	 */
	CoachDetailsDto addCoachDetails(CoachDetailsDto data, MultipartFile coachImage);

	/**
	 * This method is implemented by its implementation class to get Coach Details
	 * 
	 * @param email,name,mobileNo
	 * @return List<CoachDetailsDto>
	 */
	List<CoachDetailsDto> getCoachDetails(String email, String name,Long mobileNo);
	
	/**
	 * This method is implemented by its implementation class to update Coach Details
	 * 
	 * @param data
	 * @return CoachDetailsDto
	 */
	CoachDetailsDto updateCoachDetails(CoachDetailsDto data, MultipartFile coachImage);

	/**
	 * This method is implemented by its implementation class to delete Coach Details
	 * 
	 * @param coachId
	 * @return boolean
	 */
    boolean deleteCoachDetails(int coachId);

}
