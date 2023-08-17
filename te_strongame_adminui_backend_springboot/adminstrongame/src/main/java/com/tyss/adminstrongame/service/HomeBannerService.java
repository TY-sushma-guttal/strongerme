package com.tyss.adminstrongame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.HomeBannerInformationDto;
import com.tyss.adminstrongame.dto.HomeDropDownDto;
import com.tyss.adminstrongame.entity.HomeBannerInformation;

/**
 * This is the service interface for Banners Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * HomeBanner Information
 * 
 * @author Trupthi
 * 
 */
public interface HomeBannerService {

	/**
	 * This method is implemented by its implementation class to save HomeBanner Information
	 * 
	 * @param data
	 * @return HomeBannerInformationDto
	 */
	HomeBannerInformationDto addHomeBanner(HomeBannerInformationDto data,  MultipartFile[] imageList);

	/**
	 * This method is implemented by its implementation class to update HomeBanner Information
	 * 
	 * @param data
	 * @return HomeBannerInformationDto
	 */
	HomeBannerInformationDto updateHomeBanner(HomeBannerInformationDto data,  MultipartFile[] imageList);

	/**
	 * This method is implemented by its implementation class to delete HomeBanner Information
	 * 
	 * @param data
	 * @return HomeBannerInformationDto
	 */
	HomeBannerInformationDto deleteHomeBanner(HomeBannerInformationDto data);

	/**
	 * This method is implemented by its implementation class to get HomeBanner Information by id
	 * 
	 * @param bannerInfoId
	 * @return Optional<HomeBannerInformation>
	 */
	Optional<HomeBannerInformation>	 getHomeBannerById(int bannerInfoId);

	/**
	 * This method is implemented by its implementation class to get all HomeBanner Information
	 * 
	 * @return List<HomeBannerInformation>
	 */
	List<HomeBannerInformationDto> getAllHomeBanners();
	
	/**
	 * This method is implemented by its implementation class to get all Diet Recipe Names
	 * 
	 * @return List<HomeBannerDietDto>
	 */
	List<HomeDropDownDto> getDietRecipeNames();
	
	/**
	 * This method is implemented by its implementation class to get all user Names
	 * 
	 * @return List<HomeBannerTransformationDto>
	 */
	List<HomeDropDownDto> getAllUserNames();
	
	List<HomeDropDownDto> getAllCoachNames();

}
