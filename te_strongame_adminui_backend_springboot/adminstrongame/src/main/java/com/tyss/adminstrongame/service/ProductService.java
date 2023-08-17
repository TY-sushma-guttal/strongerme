package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.ProductInformationDto;
import com.tyss.adminstrongame.entity.ProductInformation;

/**
 * This is the service interface for E-Commers Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * Product Information
 * 
 * @author Trupthi
 * 
 */
public interface ProductService {
	
	/**
	 * This method is implemented by its implementation class to save Product Information
	 * 
	 * @param data
	 * @param image 
	 * @return ProductInformationDto
	 */
	ProductInformationDto addProductInformation(ProductInformationDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to get all Product Information
	 * 
	 * @return List<ProductInformation>
	 */
	List<ProductInformation> getAllProductInformation();

	/**
	 * This method is implemented by its implementation class to update Product Information
	 * 
	 * @param data
	 * @param image 
	 * @return ProductInformationDto
	 */
	ProductInformationDto updateProductInformation(ProductInformationDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to get Product Information by name,coins
	 * 
	 * @param name,coins
	 * @return List<ProductInformation>
	 */
	List<ProductInformation> getProductInformation(String name,Double coins);
	
	/**
	 * This method is implemented by its implementation class to delete Product Information
	 * 
	 * @param productId
	 * @return boolean
	 */
	boolean deleteProductInformation(int productId);

}
