package com.tyss.strongameapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.OrderInformationDto;
import com.tyss.strongameapp.dto.OrderPlaceDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.ProductInformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.service.BuyProductService;

import lombok.extern.slf4j.Slf4j;
/**
 * @author Sushma Guttal
 * Buy Product Controller is for placing the order.
 */

@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
public class BuyProductController {
	/**
	 * This field is to invoke business layer methods
	 */
	@Autowired
	private ProductInformationRepository productRepo;

	/**
	 * This field is to invoke business layer methods
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is to invoke business layer methods
	 */
	@Autowired
	private BuyProductService buyProductService;


	/**
	 * This method is to place the order.
	 * @param orderDto
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PutMapping("/placeorder")
	public ResponseEntity<ResponseDto> placeOrder(@RequestBody OrderPlaceDto orderDto) {
		int productId= orderDto.getProductId();
		OrderInformationDto orderDto2= orderDto.getOrder();
		Optional<ProductInformation> productEntity=productRepo.findById(productId);
		Optional<UserInformation> user= userRepo.findById(orderDto.getUserId());
		String response= buyProductService.placeOrder(orderDto2, productEntity, user);
		ResponseDto responseDTO = new ResponseDto();
		if(response.startsWith("Order placed successfully")) {
			log.error(response);
			responseDTO.setError(false);
			responseDTO.setData(response);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}else {
			// create and return ResponseEntity object
			log.debug(response);
			responseDTO.setError(true);
			responseDTO.setData(response);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}
	}//End of method place order

}//End of the class Buy Product Controller class
