package com.tyss.adminstrongame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.adminstrongame.dto.ProductInformationDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for E-Commers Page . Here you find GET, POST, PUT,
 * DELETE controllers for the E-Commers Page. Here you can find the URL path for
 * all the E-Commers Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private ProductService productService;

	/**
	 * This method is to save Product Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addProductInformation(@RequestParam String data,
			@RequestParam MultipartFile image) throws JsonProcessingException {
		ProductInformationDto obj = new ObjectMapper().readValue(data, ProductInformationDto.class);
		ProductInformationDto dto = productService.addProductInformation(obj, image);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Product details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Product details already exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if (obj.getValidationMessage().length() != 0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			} else {
				log.debug("Product details added Successfully");
				responseDTO.setError(false);
				responseDTO.setData("Product details added Successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addProductInformation

	/**
	 * This method is to get all Product Information
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getProductInformation() {
		List<ProductInformation> data = productService.getAllProductInformation();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (data == null) {
			log.error("No products exist");
			responseDTO.setError(true);
			responseDTO.setData("No products exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of products fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getProductInformation

	/**
	 * This method is to delete Product Information
	 * 
	 * @param productId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<ResponseDto> deleteProductInformation(@PathVariable("productId") int productId) {
		boolean flag = productService.deleteProductInformation(productId);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Product does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Product does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Product details deleted Successfully");
			responseDTO.setError(false);
			responseDTO.setData("Product details deleted Successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End Of the deleteProductInformation

	/**
	 * This method is to get Product Information by specifying product name or coins
	 * 
	 * @param productName,productCoins
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping(value = { "/getbyname/{productName}", "/getbycoins/{productCoins}" })
	public ResponseEntity<ResponseDto> getProductInformation(@PathVariable(required = false) String productName,
			@PathVariable(required = false) Double productCoins) {

		List<ProductInformation> dto = productService.getProductInformation(productName, productCoins);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("Product does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End Of the getProductInformation

	/**
	 * This method is to update Product Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateProductInformation(@RequestParam String data,
			@RequestParam MultipartFile image) throws JsonProcessingException {

		ProductInformationDto obj = new ObjectMapper().readValue(data, ProductInformationDto.class);
		ProductInformationDto dto = productService.updateProductInformation(obj, image);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Please enter the details for existing products");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing products");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if (obj.getValidationMessage().length() != 0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			} else {
				log.debug("product details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("product details updated successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updateProductInformation

}// End Of the Class
