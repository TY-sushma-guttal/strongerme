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
import com.tyss.adminstrongame.dto.TransformationCoachDto;
import com.tyss.adminstrongame.dto.TransformationDetailsDto;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.service.TransformationService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Transformation Page . Here you find GET,
 * POST, PUT, DELETE controllers for the Transformation Page. Here you can
 * find the URL path for all the Transformation Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/transformation" )
public class TransformationController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private TransformationService transformationService;


	/**
	 * This method is to save Transformation Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addTransformationDetails(@RequestParam String data, @RequestParam MultipartFile[] imageList) throws JsonMappingException, JsonProcessingException {
		TransformationDetailsDto transformationDetailsDto = new ObjectMapper().readValue(data, TransformationDetailsDto.class);
		TransformationDetailsDto dto = transformationService.addTransformationDetails(transformationDetailsDto, imageList);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Transformation details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Transformation details already exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		}else {
			if(dto.getValidationMessage()!=null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				if(dto.getValidationMessage().equalsIgnoreCase("Coach does not exist")) {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			}  else {
				log.debug("Transformation details added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Transformation details added successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addTransformationDetails

	/**
	 * This method is to get all Transformation Details 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllTransformationDetails() {

		List<TransformationDetailsDto> data = transformationService.getAllTransformationDetails();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No transformations exist");
			responseDTO.setError(true);
			responseDTO.setData("No transformations exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of transformations fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllTransformationDetails

	/**
	 * This method is to delete Transformation Details 
	 * 
	 * @param transformationId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/delete/{transformationId}")
	public ResponseEntity<ResponseDto> deleteTransformationDetails(@PathVariable("transformationId") int transformationId) {

		boolean flag = transformationService.deleteTransformationDetails(transformationId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Transformation does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Transformation does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Transformation details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Transformation deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteTransformationDetails

	/**
	 * This method is to get Transformation Details by specifying id
	 * 
	 * @param id
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<ResponseDto> getTransformationDetailsById(@PathVariable int id) {

		Optional<TransformationDetails> dto = transformationService.getTransformationDetailsById(id);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("Transformation does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getTransformationDetailsById

	/**
	 * This method is to update Transformation Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateTransformationDetails(@RequestParam String data, @RequestParam MultipartFile[] imageList) throws JsonMappingException, JsonProcessingException {
		TransformationDetailsDto transformationDetailsDto = new ObjectMapper().readValue(data, TransformationDetailsDto.class);
		TransformationDetailsDto dto = transformationService.updateTransformationDetails(transformationDetailsDto,imageList);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing transformation");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing transformation");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}else {
			if(dto.getValidationMessage()!=null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				if(dto.getValidationMessage().equalsIgnoreCase("Coach does not exist")) {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			}else {
				log.debug("Transformation details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Transformation details updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updateTransformationDetails

	@GetMapping("/getcoachnames")
	public ResponseEntity<ResponseDto> getAllCoachNames() {

		List<TransformationCoachDto> data = transformationService.getAllCoachNames();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No coaches exist");
			responseDTO.setError(true);
			responseDTO.setData("No coaches exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of coach names fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllCoachNames

}// End Of the Class
