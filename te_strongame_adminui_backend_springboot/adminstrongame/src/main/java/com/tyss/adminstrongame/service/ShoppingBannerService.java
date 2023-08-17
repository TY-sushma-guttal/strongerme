package com.tyss.adminstrongame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.ShoppingBannerInformationDto;
import com.tyss.adminstrongame.dto.ShoppingBannerProductDto;
import com.tyss.adminstrongame.entity.ShoppingBannerInformation;

/**
 * This is the service interface for Banners Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * ShoppingBanner Information
 * 
 * @author Trupthi
 * 
 */
public interface ShoppingBannerService {
	
	/**
	 * This method is implemented by its implementation class to save ShoppingBanner Information
	 * 
	 * @param data
	 * @param imageList 
	 * @return ShoppingBannerInformationDto
	 */
	ShoppingBannerInformationDto addShoppingBanner(ShoppingBannerInformationDto data, MultipartFile[] imageList);

	/**
	 * This method is implemented by its implementation class to update ShoppingBanner Information
	 * 
	 * @param data
	 * @param imageList 
	 * @return ShoppingBannerInformationDto
	 */
	ShoppingBannerInformationDto updateShoppingBanner(ShoppingBannerInformationDto data, MultipartFile[] imageList);

	/**
	 * This method is implemented by its implementation class to delete ShoppingBanner Information
	 * 
	 * @param data
	 * @return ShoppingBannerInformationDto
	 */
	ShoppingBannerInformationDto deleteShoppingBanner(ShoppingBannerInformationDto data);

	/**
	 * This method is implemented by its implementation class to get ShoppingBanner Information by id
	 * 
	 * @param bannerInfoId
	 * @return Optional<ShoppingBannerInformation>
	 */
	Optional<ShoppingBannerInformation> getShoppingBannerById(int bannerInfoId);

	/**
	 * This method is implemented by its implementation class to get all ShoppingBanner Informations
	 * 
	 * @return List<ShoppingBannerInformation>
	 */
	List<ShoppingBannerInformationDto> getAllShoppingBanners();

	/**
	 * This method is implemented by its implementation class to get all Product Names
	 * 
	 * @return List<ProductInformation>
	 */
	List<ShoppingBannerProductDto> getAllProductNames();
}
