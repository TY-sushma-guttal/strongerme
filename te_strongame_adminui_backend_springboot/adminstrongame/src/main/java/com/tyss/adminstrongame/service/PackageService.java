package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.PackageDetailsDto;
import com.tyss.adminstrongame.repository.PackageDetailsRepository;

public interface PackageService {
	
	/**
	 * This method is implemented by its implementation class to save Package Details
	 * 
	 * @param data
	 * @return PackageDetailsDto
	 */
	PackageDetailsDto addPackageDetails(PackageDetailsDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to get all Package Details
	 * 
	 * @return List<PackageDetailsDto>
	 */
	List<PackageDetailsDto> getAllPackageDetails();

	/**
	 * This method is implemented by its implementation class to update Package Details
	 * 
	 * @param data
	 * @return PackageDetailsDto
	 */
	PackageDetailsDto updatePackageDetails(PackageDetailsDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to delete Package Details
	 * 
	 * @param packageId
	 * @return boolean
	 */
	boolean deletePackageDetails(int packageId);


}
