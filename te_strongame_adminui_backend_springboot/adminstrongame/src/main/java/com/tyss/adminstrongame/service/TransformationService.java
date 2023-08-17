package com.tyss.adminstrongame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.TransformationCoachDto;
import com.tyss.adminstrongame.dto.TransformationDetailsDto;
import com.tyss.adminstrongame.entity.TransformationDetails;

/**
 * This is the service interface for Transformation Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * Transformation Details
 * 
 * @author Trupthi
 * 
 */
public interface TransformationService {
	
	/**
	 * This method is implemented by its implementation class to save Transformation Details
	 * 
	 * @param data
	 * @param transformationImage 
	 * @return TransformationDetailsDto
	 */
	TransformationDetailsDto addTransformationDetails(TransformationDetailsDto data, MultipartFile[] transformationImage);

	/**
	 * This method is implemented by its implementation class to get all Transformation Details
	 * 
	 * @return List<TransformationDetailsDto>
	 */
	List<TransformationDetailsDto> getAllTransformationDetails();

	/**
	 * This method is implemented by its implementation class to update Transformation Details
	 * 
	 * @param data
	 * @return TransformationDetailsDto
	 */
	TransformationDetailsDto updateTransformationDetails(TransformationDetailsDto data,MultipartFile[] transformationImage);

	/**
	 * This method is implemented by its implementation class to get Transformation Details by id
	 * 
	 * @param id
	 * @return Optional<TransformationDetails>
	 */
	Optional<TransformationDetails> getTransformationDetailsById(int id);

	/**
	 * This method is implemented by its implementation class to delete Transformation Details
	 * 
	 * @param transformationId
	 * @return boolean
	 */
	boolean deleteTransformationDetails(int transformationId);
	
	/**
	 * This method is implemented by its implementation class to get all Coach Names
	 * 
	 * @return List<TransformationCoachDto>
	 */
	List<TransformationCoachDto> getAllCoachNames();


}
