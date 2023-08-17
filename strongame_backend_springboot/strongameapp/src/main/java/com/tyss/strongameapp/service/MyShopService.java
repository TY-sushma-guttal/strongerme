package com.tyss.strongameapp.service;

import java.util.List;

import com.tyss.strongameapp.dto.AdvertisementInformationDto;
import com.tyss.strongameapp.dto.ProductInformationDto;
import com.tyss.strongameapp.entity.UserInformation;

/**
 * MyShopService is implemented by MyShopServiceImple class which is used to display the content of my shop page
 * @author Sushma Guttal
 *
 */
public interface MyShopService {

	/**
	 * This method is implemented by its implementation class, which is used to display the list of products.
	 * @return List<ProductInformationDto>
	 */
	List<ProductInformationDto> getProductList();

	
	/**
	 * This method is implemented by its implementation class, to filter the products by name in descending order.
	 * @return List<ProductInformationDto>
	 */
	List<ProductInformationDto> filterProductByNameDesc();

	
	/**
	 * This method is implemented by its implementation class, to filter the products by name in ascending order.
	 * @return List<ProductInformationDto>
	 */
	List<ProductInformationDto> filterProductByName();

	
	/**
	 * This method is implemented by its implementation class, to filter the products by coins in ascending order.
	 * @return List<ProductInformationDto>
	 */
	List<ProductInformationDto> filterProductByCoin();

	
	/**
	 * This method is implemented by its implementation class, to fetch product details by using name.
	 * @param productName
	 * @return ProductInformationDto
	 */
	ProductInformationDto getProductByName(String productName);

	
	/**
	 * This method is implemented by its implementation class, to fetch product details by using id.
	 * @param productid
	 * @return ProductInformationDto
	 */
	ProductInformationDto getProductById(int productid);

	
	/**
	 * This method is implemented by its implementation class, to filter the products by coins in ascending order.
	 * @return List<ProductInformationDto>
	 */
	List<ProductInformationDto> filterProductByCoinDesc();

	
	/**
	 * This method is implemented by its implementation class, to fetch the total coins of specified user.
	 * @param userEntity2
	 * @return double
	 */
	double getCoin(UserInformation userEntity2);

	
	/**
	 * This method is implemented by its implementation class, to fetch list of advertisements
	 * @return List<AdvertisementInformationDto>
	 */
	List<AdvertisementInformationDto> getAdvertisements();

	
	/**
	 *  This method is implemented by its implementation class, to send email to user
	 * @param email
	 * @param name
	 * @param string
	 */
	void sendMail(String email, String name, String string);



	/**
	 * This method is implemented by its implementation class, to fetch shopping banner and advertisements
	 * @return AddsAndBannersDto
	 */
	com.tyss.strongameapp.dto.AddsAndBannersDto getAddsBanners();

}// End of MyShopService interface.
