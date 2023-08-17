package com.tyss.adminstrongame.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.adminstrongame.dto.HomeBannerInformationDto;
import com.tyss.adminstrongame.dto.HomeDropDownDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.entity.HomeBannerInformation;
import com.tyss.adminstrongame.service.HomeBannerService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Banners Page . Here you find GET, POST, PUT,
 * DELETE controllers for the Banners Page. Here you can find the URL path for
 * all the HomeBanner services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/homebanner")
public class HomeBannerController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private HomeBannerService homeBannerService;


	/**
	 * This method is to save HomeBanner Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addHomeBannerInformation(@RequestParam String data,
			@RequestParam MultipartFile[] imageList) throws JsonProcessingException {
		HomeBannerInformationDto homeBannerInformationDto = new ObjectMapper().readValue(data,
				HomeBannerInformationDto.class);
		HomeBannerInformationDto dto = homeBannerService.addHomeBanner(homeBannerInformationDto, imageList);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Home screen banner already exists");
			responseDTO.setError(true);
			responseDTO.setData("Home screen banner already exists");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if (dto.getValidationMessage() != null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
			} else {
				log.debug("Home screen banner added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Home screen banner added successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
			}

		}
	}// End Of the addHomeBannerInformation

	/**
	 * This method is to update HomeBanner Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateHomeBannerInformation(@RequestParam String data,
			@RequestParam MultipartFile[] imageList) throws JsonProcessingException {
		HomeBannerInformationDto homeBannerInformationDto = new ObjectMapper().readValue(data,
				HomeBannerInformationDto.class);
		HomeBannerInformationDto dto = homeBannerService.updateHomeBanner(homeBannerInformationDto, imageList);

		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Home screen banner does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Home screen banner does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if (dto.getValidationMessage() != null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
			} else {
				log.debug("Home screen banner updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Home screen banner updated successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			}

		}
	}// End Of the updateHomeBannerInformation

	/**
	 * This method is to delete HomeBanner Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteHomeBannerInformation(@RequestBody HomeBannerInformationDto data) {
		HomeBannerInformationDto dto = homeBannerService.deleteHomeBanner(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Home screen banner does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Home screen banner does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Home screen banner deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Home screen banner deleted successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the deleteHomeBannerInformation

	/**
	 * This method is to get HomeBanner Information by specifying id
	 * 
	 * @param bannerInfoId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getbyid/{bannerInfoId}")
	public ResponseEntity<ResponseDto> getHomeBannerById(@PathVariable("bannerInfoId") int bannerInfoId) {
		HomeBannerInformation dto = homeBannerService.getHomeBannerById(bannerInfoId).orElse(null);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("Home screen banner does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getHomeBannerById

	/**
	 * This method is to get all HomeBanner Information
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllHomeBanners() {
		List<HomeBannerInformationDto> list = homeBannerService.getAllHomeBanners();
		ResponseDto responseDTO = new ResponseDto();
		if (list == null) {
			log.error("No home screen banners exist");
			responseDTO.setError(true);
			responseDTO.setData(" No home screen banners exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of Home screen banners fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(list);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getAllHomeBanners

	/**
	 * This method is to get all Names
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/{homeBannerType}")
	public ResponseEntity<ResponseDto> getAllNames(@PathVariable("homeBannerType") String homeBannerType) {
		ResponseDto responseDTO = new ResponseDto();
		List<HomeDropDownDto> list = new ArrayList<>();
		if (homeBannerType.equalsIgnoreCase("Coach")) {
			list = homeBannerService.getAllCoachNames();
		} else if (homeBannerType.equalsIgnoreCase("Transformation")) {
			list = homeBannerService.getAllUserNames();
		} else if (homeBannerType.equalsIgnoreCase("Diet Recipe")) {
			list = homeBannerService.getDietRecipeNames();
		}
		if (list == null) {
			log.error("No names exist");
			responseDTO.setError(true);
			responseDTO.setData("No names exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of names fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(list);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End Of the getAllNames

}// End Of the Class
