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
import com.tyss.adminstrongame.dto.CoachDetailsDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.service.CoachService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Coaches Page . Here you find GET, POST, PUT,
 * DELETE controllers for the Coaches Page. Here you can find the URL path for
 * all the Coaches Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/coach")
public class CoachController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private CoachService coachService;


	/**
	 * This method is to save Coach Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addCoachDetails(@RequestParam String data,
			@RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		CoachDetailsDto coachDetailsDto = new ObjectMapper().readValue(data, CoachDetailsDto.class);
		CoachDetailsDto dto = coachService.addCoachDetails(coachDetailsDto,image);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Email id already exists");
			responseDTO.setError(true);
			responseDTO.setData("Email id already exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if (coachDetailsDto.getValidationMessage().length() != 0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			} else {
				log.debug("Coach details added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Coach details added successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addCoachDetails

	/**
	 * This method is to get all Coach Details
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllCoachDetails() {

		List<CoachDetailsDto> data = coachService.getAllCoachDetails();

		ResponseDto responseDTO = new ResponseDto();

		if (data == null) {
			log.error("No coaches exist");
			responseDTO.setError(true);
			responseDTO.setData("No coaches exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);

		} else {
			log.debug("list of coaches fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllCoachDetails

	/**
	 * This method is to get Coach Details by specifying email id or name or phone
	 * number
	 * 
	 * @param emailId,coachName,phoneNumber
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping(value = { "/getbyid/{emailId}", "/getbyname/{coachName}", "/getbynumber/{phoneNumber}" })
	public ResponseEntity<ResponseDto> getCoachDetails(@PathVariable(required = false) String emailId,
			@PathVariable(required = false) String coachName, @PathVariable(required = false) Long phoneNumber) {
		List<CoachDetailsDto> dto = coachService.getCoachDetails(emailId, coachName, phoneNumber);
		ResponseDto responseDTO = new ResponseDto();
		if (dto.isEmpty()) {
			responseDTO.setError(true);
			responseDTO.setData("Coach details does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getCoachDetails

	/**
	 * This method is to update Coach Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateCoachDetails(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		CoachDetailsDto coachDetailsDto = new ObjectMapper().readValue(data, CoachDetailsDto.class);
		CoachDetailsDto dto = coachService.updateCoachDetails(coachDetailsDto,image);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Please enter the details for existing coach");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing coach");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if (coachDetailsDto.getValidationMessage().length() != 0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			} else {
				log.debug("Coach details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Coach details updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updateCoachDetails

	/**
	 * This method is to delete Coach Details
	 * 
	 * @param coachId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@DeleteMapping("/delete/{coachId}")
	public ResponseEntity<ResponseDto> deleteCoach(@PathVariable("coachId") int coachId) {

		boolean flag = coachService.deleteCoachDetails(coachId);
		ResponseDto responseDTO = new ResponseDto();

		if (!flag) {
			log.error("Coach details does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Coach details does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Coach details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Coach details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteCoach

}// End Of the Class
