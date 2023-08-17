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
import com.tyss.adminstrongame.dto.AdvertisementInformationDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.entity.AdvertisementInformation;
import com.tyss.adminstrongame.service.AdvertisementService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Adds Page . Here you find GET, POST, PUT,
 * DELETE controllers for the Adds Page. Here you can find the URL path for all
 * the Adds Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private AdvertisementService advertisementService;


	/**
	 * This method is to save Advertisement Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addAdvertisementInformation(@RequestParam String data,
			@RequestParam MultipartFile[] image) throws JsonProcessingException {

		AdvertisementInformationDto obj = new ObjectMapper().readValue(data, AdvertisementInformationDto.class);
		AdvertisementInformationDto dto = advertisementService.addAdvertisementInformation(obj, image);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Advertisement details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Advertisement details already exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Advertisement details added successfully");
			responseDTO.setError(false);
			responseDTO.setData("Advertisement details added successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
		}

	}// End Of the addAdvertisementInformation

	/**
	 * This method is to get all Advertisement Information
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAdvertisementInformation() {

		List<AdvertisementInformation> data = advertisementService.getAdvertisementInformation();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No advertisements exist");
			responseDTO.setError(true);
			responseDTO.setData("No advertisements exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of advertisements fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAdvertisementInformation

	/**
	 * This method is to delete Advertisement Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAdvertisementInformation(@RequestBody AdvertisementInformationDto data) {

		AdvertisementInformationDto dto = advertisementService.deleteAdvertisementInformation(data);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Advertisement does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Advertisement does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			log.debug("Advertisement details deleted successfully");
			responseDTO.setData("Advertisement details deleted successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteAdvertisementInformation

	/**
	 * This method is to get Advertisement Information by specifying Id
	 * 
	 * @param id
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<ResponseDto> getAdvertisementInformationById(@PathVariable int id) {

		AdvertisementInformation dto = advertisementService.getAdvertisementInformationById(id).orElse(null);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Advertisement does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Advertisement does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAdvertisementInformationById

	/**
	 * This method is to update Advertisement Information
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAdvertisementInformation(@RequestParam String data,
			@RequestParam MultipartFile[] imageList) throws JsonProcessingException {

		AdvertisementInformationDto obj = new ObjectMapper().readValue(data, AdvertisementInformationDto.class);
		AdvertisementInformationDto dto = advertisementService.updateAdvertisementInformation(obj, imageList);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing advertisement");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing advertisement");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Advertisement details updated successfully");
			responseDTO.setError(false);
			responseDTO.setData("Advertisement details updated successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End Of the updateAdvertisementInformation

}// End Of the Class
