package com.tyss.strongameapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.AddsAndBannersDto;
import com.tyss.strongameapp.dto.AdvertisementInformationDto;
import com.tyss.strongameapp.dto.ProductInformationDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.entity.AdvertisementInformation;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.service.MyShopService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal
 * My shop controller is used to display my shop page.
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
@RequestMapping("/myshop")
public class MyShopController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private MyShopService myShopService;
	
	@Autowired
	private UserInformationRepository userRepo;
	
	
	/**
	 * This method is used to fetch the product list.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> getProductList() {
		List<ProductInformationDto> dto = myShopService.getProductList();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("No Products found");
			responseDTO.setError(true);
			responseDTO.setData("No Products found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Product is fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get product list method
	
	/**
	 * This method is used to fetch single product details by its id.
	 * @param productid
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("productbyid/{productid}")
	public ResponseEntity<ResponseDto> getProductById(@PathVariable int productid) {
		ProductInformationDto dto = myShopService.getProductById(productid);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("Product not found");
			responseDTO.setError(true);
			responseDTO.setData("Product not found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Product is fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get product by id method

	/**
	 * This method is used to fetch single product details by its name.
	 * @param productName
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("productbyname/{productName}")
	public ResponseEntity<ResponseDto> getProductByName(@PathVariable String productName) {
		ProductInformationDto dto = myShopService.getProductByName(productName);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("Product not found");
			responseDTO.setError(true);
			responseDTO.setData("Product not found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Product is fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get product by name method

	/**
	 * This method is used to filter the products in ascending order based on coins.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterbycoins")
	public ResponseEntity<ResponseDto> filterProductByCoin() {
		List<ProductInformationDto> dto = myShopService.filterProductByCoin();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("No Products found");
			responseDTO.setError(true);
			responseDTO.setData("No Products found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Products are fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of filter product by coins method

	/**
	 * This method is used to filter the products in ascending order based on name.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterbyname")
	public ResponseEntity<ResponseDto> filterProductByName() {
		List<ProductInformationDto> dto = myShopService.filterProductByName();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("No Products found");
			responseDTO.setError(true);
			responseDTO.setData("No Products found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Products are fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of filter product by name method

	/**
	 * This method is used to filter the products in descending order based on name.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterbynamedesc")
	public ResponseEntity<ResponseDto> filterProductByNameDesc() {
		List<ProductInformationDto> dto = myShopService.filterProductByNameDesc();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("No Products found");
			responseDTO.setError(true);
			responseDTO.setData("No Products found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Products are fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of filter product by name desc method

	/**
	 * This method is used to filter the products in descending order based on coins.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterbycoinsdesc")
	public ResponseEntity<ResponseDto> filterProductByCoinDesc() {
		List<ProductInformationDto> dto = myShopService.filterProductByCoinDesc();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("No Products found");
			responseDTO.setError(true);
			responseDTO.setData("No Products found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Products are fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of filter product by coins desc method
	
	@GetMapping("getcoin/{userId}")
	public ResponseEntity<ResponseDto> getCoin(@PathVariable int userId) {
		ResponseDto responseDTO = new ResponseDto();
		Optional<UserInformation> userEntity= userRepo.findById(userId);
		if(!userEntity.isPresent()) {
			log.error("User Not Found");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}
		UserInformation userEntity2= userEntity.get();
		double coins = myShopService.getCoin(userEntity2);
		// create and return ResponseEntity object
		if (coins==0) {
			log.error("No coins available for you");
			responseDTO.setError(true);
			responseDTO.setData("No coins available for you");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Coins are displaying");
			responseDTO.setError(false);
			responseDTO.setData(coins);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}///End of get coin method
	
	@GetMapping("getadds")
	public ResponseEntity<ResponseDto> getAddsBanners() {
	   AddsAndBannersDto dto = myShopService.getAddsBanners();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("No advertisements and banners available for you");
			responseDTO.setError(true);
			responseDTO.setData("No advertisements and banners available for you");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Advertisements and banners are fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of getAddsBanners method
	
}//End of My Shop Controller.
