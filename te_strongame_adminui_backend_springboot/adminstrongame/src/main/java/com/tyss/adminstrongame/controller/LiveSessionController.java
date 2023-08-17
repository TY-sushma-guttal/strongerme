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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.adminstrongame.dto.CoachForSessionDetailsDto;
import com.tyss.adminstrongame.dto.PackageDetailsDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.dto.SessionCoachDto;
import com.tyss.adminstrongame.dto.SessionDetailsDto;
import com.tyss.adminstrongame.dto.SessionNotificationDetailsDto;
import com.tyss.adminstrongame.dto.SpecializationDetailsDto;
import com.tyss.adminstrongame.dto.TaglineDetailsDto;
import com.tyss.adminstrongame.entity.SpecializationDetails;
import com.tyss.adminstrongame.service.CoachForSessionService;
import com.tyss.adminstrongame.service.PackageService;
import com.tyss.adminstrongame.service.SessionService;
import com.tyss.adminstrongame.service.SpecializationService;
import com.tyss.adminstrongame.service.TaglineService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for LiveSession Page . Here you find GET,
 * POST, PUT, DELETE controllers for session, specialization, coach and packages .
 *  Here you can find the URL path for all the LiveSession Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/live" )
public class LiveSessionController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private SessionService sessionService;

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private SpecializationService specializationService;

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private CoachForSessionService coachForSessionService;

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private PackageService packageService;

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private TaglineService taglineService;


	/**
	 * This method is to save Session Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PostMapping("/savesession")
	public ResponseEntity<ResponseDto> addSessionDetails(@RequestBody SessionDetailsDto data) {
		SessionDetailsDto dto = sessionService.addSessionDetails(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Session details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Session details already exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if(data.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				if(dto.getValidationMessage().equalsIgnoreCase("Coach For Session does not exist")) {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			}else { 
				log.debug("Session details added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Session details added successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addSessionDetails

	/**
	 * This method is to get all Session Details  
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getallsessions")
	public ResponseEntity<ResponseDto> getAllSessionDetails() {

		List<SessionDetailsDto> data = sessionService.getAllSessionDetails();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No Sessions exist");
			responseDTO.setError(true);
			responseDTO.setData("No Sessions exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of sessions fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllSessionDetails

	/**
	 * This method is to delete Session Details 
	 * 
	 * @param sessionId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/deletesession/{sessionId}")
	public ResponseEntity<ResponseDto> deleteSessionDetails(@PathVariable("sessionId") int sessionId) {

		boolean flag = sessionService.deleteSessionDetails(sessionId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Session does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Session does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Session details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Session details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteSessionDetails

	/**
	 * This method is to update Session Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PutMapping("/updatesession")
	public ResponseEntity<ResponseDto> updateSessionDetails(@RequestBody SessionDetailsDto data) {

		SessionDetailsDto dto = sessionService.updateSessionDetails(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing session");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing session");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(data.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				if(dto.getValidationMessage().equalsIgnoreCase("Coach For Session does not exist")) {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
				}else {
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			}else { 
				log.debug("Session details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Session details updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updateSessionDetails

	/**
	 * This method is to get all Coaches 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getsessioncoaches")
	public ResponseEntity<ResponseDto> getAllCoaches() {

		List<SessionCoachDto> data = sessionService.getAllCoaches();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No coaches exist");
			responseDTO.setError(true);
			responseDTO.setData("No coaches exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of coachForSession names fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllCoachNames

	/**
	 * This method is to update Session Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/sessionnotification")
	public ResponseEntity<ResponseDto> getSessionNotifications() {

		List<SessionNotificationDetailsDto> dto = sessionService.getSessionNotifications();
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("No session notifications exist");
			responseDTO.setError(true);
			responseDTO.setData("No session notifications exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of session notifications fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the updateSessionDetails

	/**
	 * This method is to save Specialization Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PostMapping("/savespecialization")
	public ResponseEntity<ResponseDto> addSpecializationDetails(@RequestParam String data, @RequestParam MultipartFile image,
			@RequestParam MultipartFile icon) throws JsonMappingException, JsonProcessingException {
		SpecializationDetailsDto specializationDto = new ObjectMapper().readValue(data, SpecializationDetailsDto.class);
		SpecializationDetailsDto dto = specializationService.addSpecializationDetails(specializationDto, image, icon);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Specialization type already exists");
			responseDTO.setError(true);
			responseDTO.setData("Specialization type already exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Specialization details added successfully");
			responseDTO.setError(false);
			responseDTO.setData("Specialization details added successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
		}

	}// End Of the addSpecializationDetails

	/**
	 * This method is to get all Specialization Details 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getallspecializations")
	public ResponseEntity<ResponseDto> getAllSpecializationDetails() {

		List<SpecializationDetails> data = specializationService.getAllSpecializationDetails();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No Specializations exist");
			responseDTO.setError(true);
			responseDTO.setData("No Specializations exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of specializations fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllSessionDetails

	/**
	 * This method is to delete Specialization Details 
	 * 
	 * @param specializationId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/deletespecialization/{specializationId}")
	public ResponseEntity<ResponseDto> deleteSpecializationDetails(@PathVariable("specializationId") int specializationId) {

		boolean flag = specializationService.deleteSpecializationDetails(specializationId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Specialization does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Specialization does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Specialization details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Specialization details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteSpecializationDetails

	/**
	 * This method is to update Specialization Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/updatespecialization")
	public ResponseEntity<ResponseDto> updateSpecializationDetails(@RequestParam String data,@RequestParam MultipartFile image,
			@RequestParam MultipartFile icon) throws JsonMappingException, JsonProcessingException {
		SpecializationDetailsDto specializationDto = new ObjectMapper().readValue(data, SpecializationDetailsDto.class);
		int value = specializationService.updateSpecializationDetails(specializationDto, image, icon);
		ResponseDto responseDTO = new ResponseDto();
		if (value==1) {
			log.error("Please enter the details for existing specialization");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing specialization");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else if(value==2) {
			log.debug("Specialization type already exists");
			responseDTO.setError(true);
			responseDTO.setData("Specialization type already exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		}else {
			log.debug("Specialization details updated successfully");
			responseDTO.setError(false);
			responseDTO.setData("Specialization details updated successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the updateSpecializationDetails

	/**
	 * This method is to save CoachForSession
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PostMapping("/savecoach")
	public ResponseEntity<ResponseDto> addCoachForSession(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		CoachForSessionDetailsDto coachDetailsDto = new ObjectMapper().readValue(data, CoachForSessionDetailsDto.class);
		CoachForSessionDetailsDto dto = coachForSessionService.addCoachForSession(coachDetailsDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Email Id already exists");
			responseDTO.setError(true);
			responseDTO.setData("Email Id already exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else { 
				log.debug("Coach details added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Coach details added successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addSpecializationDetails

	/**
	 * This method is to get all CoachForSession 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getallcoaches")
	public ResponseEntity<ResponseDto> getAllCoachForSession() {

		List<CoachForSessionDetailsDto> data = coachForSessionService.getAllCoachForSession();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No coaches exist");
			responseDTO.setError(true);
			responseDTO.setData("No coaches exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of coach For Session fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllSessionDetails

	/**
	 * This method is to delete CoachForSession 
	 * 
	 * @param coachForSessionId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/deletecoach/{coachForSessionId}")
	public ResponseEntity<ResponseDto> deleteCoachForSession(@PathVariable("coachForSessionId") int coachForSessionId) {

		boolean flag = coachForSessionService.deleteCoachForSession(coachForSessionId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Coach does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Coach does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Coach details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Coach details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteSpecializationDetails

	/**
	 * This method is to update CoachForSession
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/updatecoach")
	public ResponseEntity<ResponseDto> updateCoachForSession(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		CoachForSessionDetailsDto coachDetailsDto = new ObjectMapper().readValue(data, CoachForSessionDetailsDto.class);
		CoachForSessionDetailsDto dto= coachForSessionService.updateCoachForSession(coachDetailsDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing coach");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing coach");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else { 
				log.debug("Coach details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Coach details updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updateSpecializationDetails

	/**
	 * This method is to get all Specialization Type
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getspecializationtype")
	public ResponseEntity<ResponseDto> getAllSpecializationType() {

		List<String> data = coachForSessionService.getAllSpecializationType();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No specialization type exists");
			responseDTO.setError(true);
			responseDTO.setData("No specialization type exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of specialization type fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllSpecializationType

	/**
	 * This method is to save Package Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PostMapping("/savepackage")
	public ResponseEntity<ResponseDto> addPackageDetails(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		PackageDetailsDto packageDetailsDto = new ObjectMapper().readValue(data, PackageDetailsDto.class);
		PackageDetailsDto dto = packageService.addPackageDetails(packageDetailsDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Package details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Package details already exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else { 
				log.debug("Package details added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Package details added successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addPackageDetails

	/**
	 * This method is to get all Package Details  
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getallpackages")
	public ResponseEntity<ResponseDto> getAllPackageDetails() {

		List<PackageDetailsDto> data = packageService.getAllPackageDetails();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No packages exist");
			responseDTO.setError(true);
			responseDTO.setData("No packages exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of package fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllPackageDetails

	/**
	 * This method is to delete Package Details 
	 * 
	 * @param packageId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/deletepackage/{packageId}")
	public ResponseEntity<ResponseDto> deletePackageDetails(@PathVariable("packageId") int packageId) {

		boolean flag = packageService.deletePackageDetails(packageId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Package does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Package does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Package details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteSessionDetails

	/**
	 * This method is to update Package Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/updatepackage")
	public ResponseEntity<ResponseDto> updatePackageDetails(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		PackageDetailsDto packageDetailsDto = new ObjectMapper().readValue(data, PackageDetailsDto.class);
		PackageDetailsDto dto = packageService.updatePackageDetails(packageDetailsDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing package");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing package");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else { 
				log.debug("Package details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Package details updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updatePackageDetails

	/**
	 * This method is to save Tagline Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PostMapping("/savetagline")
	public ResponseEntity<ResponseDto> addTaglineDetails(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		System.out.println(image+" image");
		TaglineDetailsDto taglineDto = new ObjectMapper().readValue(data, TaglineDetailsDto.class);
		TaglineDetailsDto dto = taglineService.addTaglinenDetails(taglineDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Tagline details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Tagline details already exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Tagline details added successfully");
			responseDTO.setError(false);
			responseDTO.setData("Tagline details added successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
		}

	}// End Of the addTaglineDetails

	/**
	 * This method is to get all Session Details  
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getalltaglines")
	public ResponseEntity<ResponseDto> getAllTaglineDetails() {

		List<TaglineDetailsDto> data = taglineService.getAllTaglineDetails();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No taglines exist");
			responseDTO.setError(true);
			responseDTO.setData("No taglines exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of taglines fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllTaglineDetails

	/**
	 * This method is to delete Tagline Details 
	 * 
	 * @param taglineId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/deletetagline/{taglineId}")
	public ResponseEntity<ResponseDto> deleteTaglineDetails(@PathVariable("taglineId") int taglineId) {

		boolean flag = taglineService.deleteTaglineDetails(taglineId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Tagline does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Tagline does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Tagline details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Tagline details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteTaglineDetails

	/**
	 * This method is to update Tagline Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/updatetagline")
	public ResponseEntity<ResponseDto> updateTaglineDetails(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		TaglineDetailsDto taglineDto = new ObjectMapper().readValue(data, TaglineDetailsDto.class);
		TaglineDetailsDto dto = taglineService.updateTaglineDetails(taglineDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing tagline");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing tagline");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Tagline details updated successfully");
			responseDTO.setError(false);
			responseDTO.setData("Tagline details updated successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the updateTaglineDetails




}
