package com.tyss.strongameapp.service;

import java.util.List;

import com.tyss.strongameapp.dto.TaglineDetailsDto;
import com.tyss.strongameapp.dto.BookNowDto;
import com.tyss.strongameapp.dto.CoachForSessionDetailsDto;
import com.tyss.strongameapp.dto.PackageDetailsDto;
import com.tyss.strongameapp.dto.SessionDetailsDto;
import com.tyss.strongameapp.dto.SpecializationDetailsDto;
import com.tyss.strongameapp.dto.TodaysLiveSessionDto;

/**
 * LiveSessionService is implemented by LiveSessionServiceImple class, which is used to fetch contents of live session page.
 * @author Sushma Guttal
 *
 */
public interface LiveSessionService {

	/**
	 * This method is implemented by its implementation class which is used to get all the specializations.
	 * @return List<SpecializationDetailsDto>
	 */
	List<SpecializationDetailsDto> getAllSpecializations();

	
	/**
	 * This method is implemented by its implementation class which is used to fetch all the coaches of live sessions.
	 * @return List<CoachForSessionDetailsDto>
	 */
	List<CoachForSessionDetailsDto> getAllCoaches();
	

	/**
	 * This method is implemented by its implementation class which is used to fetch specified specialization.
	 * @param specializationId
	 * @param userId 
	 * @return SpecializationDetailsDto
	 */
	SpecializationDetailsDto getSpecialization(int specializationId, int userId);
	

	/**
	 * This method is implemented by its implementation class which is used to fetch all todays live sessions.
	 * @param userId 
	 * @return List<SessionDetailsDto>
	 */
	TodaysLiveSessionDto getTodaySessions(int userId);

	
	/**
	 * This method is implemented by its implementation class which is used to get list of packages for live sessions.
	 * @return List<PackageDetailsDto>
	 */
	List<PackageDetailsDto> getPackageList();

	
	/**
	 * This method is implemented by its implementation class which is used to join the session.
	 * @param userId
	 * @param sessionId
	 * @return SessionDetailsDto 
	 */
	SessionDetailsDto joinSession(int userId, int sessionId);


	/**
	 * This method is implemented by its implementation class to book the package.
	 * @param userId
	 * @param packageId
	 * @param addOnId 
	 * @return PackageDetailsDto
	 */
	PackageDetailsDto bookPackage(BookNowDto bookNowDto);


	/**
	 * This method is implemented by its implementation class to fetch package by its id.
	 * @param packageId
	 * @return List<PackageDetailsDto>
	 */
	List<PackageDetailsDto> getPackageById(int packageId);


	/**
	 * This method is implemented by its implementation class to fetch tagline details
	 * @return TaglineDetailsDto
	 */
	TaglineDetailsDto getTagLineDetails();
}//End of LiveSessionService interface
