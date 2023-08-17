package com.tyss.strongameapp.service;

import java.util.Optional;

import com.tyss.strongameapp.dto.OrderInformationDto;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.UserInformation;

/**
 * BuyProductService is implemented by BuyProductServiceImple class to buy product.
 * @author Sushma Guttal
 *
 */
public interface BuyProductService {

	/**
	 * This method is implemented by its implementation class to place the order.
	 * @param orderDto2
	 * @param productEntity
	 * @param user
	 * @return String value
	 */
	String placeOrder(OrderInformationDto orderDto2, Optional<ProductInformation> productEntity, Optional<UserInformation> user);

}//End of BuyProductService interface.
