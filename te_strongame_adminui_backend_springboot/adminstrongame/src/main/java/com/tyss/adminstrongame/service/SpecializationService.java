package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.SpecializationDetailsDto;
import com.tyss.adminstrongame.entity.SpecializationDetails;

public interface SpecializationService {
	
	/**
	 * This method is implemented by its implementation class to save Specialization Details
	 * 
	 * @param data
	 * @return SpecializationDetailsDto
	 */
	SpecializationDetailsDto addSpecializationDetails(SpecializationDetailsDto data, MultipartFile image, MultipartFile icon);

	/**
	 * This method is implemented by its implementation class to get all Specialization Details
	 * 
	 * @return List<SpecializationDetailsDto>
	 */
	List<SpecializationDetails> getAllSpecializationDetails();

	/**
	 * This method is implemented by its implementation class to update Specialization Details
	 * 
	 * @param data
	 * @return int
	 */
	int updateSpecializationDetails(SpecializationDetailsDto data, MultipartFile image,
			MultipartFile icon);

	/**
	 * This method is implemented by its implementation class to delete Specialization Details
	 * 
	 * @param specializationId
	 * @return boolean
	 */
	boolean deleteSpecializationDetails(int specializationId);


}
