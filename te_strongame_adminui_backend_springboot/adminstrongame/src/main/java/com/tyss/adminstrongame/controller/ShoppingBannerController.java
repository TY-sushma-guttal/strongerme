package com.tyss.adminstrongame.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.dto.ShoppingBannerInformationDto;
import com.tyss.adminstrongame.dto.ShoppingBannerProductDto;
import com.tyss.adminstrongame.entity.ShoppingBannerInformation;
import com.tyss.adminstrongame.service.ShoppingBannerService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Banners Page . Here you find GET, POST, PUT,
 * DELETE controllers for the Banners Page. Here you can find the URL path for
 * all the ShoppingBanner services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shoppingbanner")
public class ShoppingBannerController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private ShoppingBannerService shoppingBannerService;


	/**
	 * This method is to save Shopping Banner Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addShoppingBanner(@RequestParam String data,
			@RequestParam MultipartFile[] imageList) throws JsonProcessingException {
		ShoppingBannerInformationDto obj = new ObjectMapper().readValue(data, ShoppingBannerInformationDto.class);
		ShoppingBannerInformationDto dto = shoppingBannerService.addShoppingBanner(obj, imageList);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Shop screen banner already exists");
			responseDTO.setError(true);
			responseDTO.setData("Shop screen banner already exists");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if (dto.getValidationMessage() != null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
			} else {
				log.debug("Shop screen banner added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Shop screen banner added successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
			}

		}
	}// End Of the addShoppingBanner

	/**
	 * This method is to update Shopping Banner Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateShoppingBanner(@RequestParam String data,@RequestParam MultipartFile[] imageList) throws JsonProcessingException {
		ShoppingBannerInformationDto obj = new ObjectMapper().readValue(data, ShoppingBannerInformationDto.class);
		ShoppingBannerInformationDto dto = shoppingBannerService.updateShoppingBanner(obj,imageList);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing shop screen banner");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing shop screen banner");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if (dto.getValidationMessage() != null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
			} else {
				log.debug("Shop screen banner updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Shop screen banner updated successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			}

		}
	}// End Of the updateShoppingBanner

	/**
	 * This method is to delete Shopping Banner Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteShoppingBanner(@RequestBody ShoppingBannerInformationDto data) {
		ShoppingBannerInformationDto dto = shoppingBannerService.deleteShoppingBanner(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Shop screen banner does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Shop screen banner does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Shop screen banner deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Shop screen banner deleted successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the deleteShoppingBanner

	/**
	 * This method is to get Shopping Banner Information by specifying id
	 * 
	 * @param bannerInfoId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getbyid/{bannerInfoId}")
	public ResponseEntity<ResponseDto> getShoppingBannerById(@PathVariable("bannerInfoId") int bannerInfoId) {
		Optional<ShoppingBannerInformation> dto = shoppingBannerService.getShoppingBannerById(bannerInfoId);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("Shop screen banner does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getShoppingBannerById

	/**
	 * This method is to get all Shopping Banner Information
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllShoppingBanners() {
		List<ShoppingBannerInformationDto> dto = shoppingBannerService.getAllShoppingBanners();
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("No Shop screen banners exist");
			responseDTO.setError(true);
			responseDTO.setData("No Shop screen banners exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of Shopping Banners fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getAllShoppingBanners

	/**
	 * This method is to get all product names Information
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/product")
	public ResponseEntity<ResponseDto> getAllProductNames() {
		List<ShoppingBannerProductDto> dto = shoppingBannerService.getAllProductNames();
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("No product names exist");
			responseDTO.setError(true);
			responseDTO.setData("No product names exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of product names fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getAllProductNames

}
