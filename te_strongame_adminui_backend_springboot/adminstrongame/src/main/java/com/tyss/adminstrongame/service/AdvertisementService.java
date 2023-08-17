package com.tyss.adminstrongame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.AdvertisementInformationDto;
import com.tyss.adminstrongame.entity.AdvertisementInformation;

/**
 * This is the service interface for Adds Page . Here you find abstract methods
 * for saving, updating, fetching and deleting the Advertisement Information
 * 
 * @author Trupthi
 * 
 */
public interface AdvertisementService {

	/**
	 * This method is implemented by its implementation class to save Advertisement
	 * Information
	 * 
	 * @param data
	 * @param image
	 * @return AdvertisementInformationDto
	 */
	AdvertisementInformationDto addAdvertisementInformation(AdvertisementInformationDto data, MultipartFile[] image);

	/**
	 * This method is implemented by its implementation class to get all
	 * Advertisement Information
	 * 
	 * @return List<AdvertisementInformation>
	 */
	List<AdvertisementInformation> getAdvertisementInformation();

	/**
	 * This method is implemented by its implementation class to update
	 * Advertisement Information
	 * 
	 * @param data
	 * @param imageList 
	 * @return AdvertisementInformationDto
	 */
	AdvertisementInformationDto updateAdvertisementInformation(AdvertisementInformationDto data, MultipartFile[] imageList);

	/**
	 * This method is implemented by its implementation class to get Advertisement
	 * Information by id
	 * 
	 * @param id
	 * @return Optional<AdvertisementInformation>
	 */
	Optional<AdvertisementInformation> getAdvertisementInformationById(int id);

	/**
	 * This method is implemented by its implementation class to delete
	 * Advertisement Information
	 * 
	 * @param data
	 * @return AdvertisementInformationDto
	 */
	AdvertisementInformationDto deleteAdvertisementInformation(AdvertisementInformationDto data);

}
