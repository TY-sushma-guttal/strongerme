package com.tyss.strongameapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.BookNowDto;
import com.tyss.strongameapp.dto.CoachForSessionDetailsDto;
import com.tyss.strongameapp.dto.PackageDetailsDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SessionDetailsDto;
import com.tyss.strongameapp.dto.SpecializationDetailsDto;
import com.tyss.strongameapp.dto.TaglineDetailsDto;
import com.tyss.strongameapp.dto.TodaysLiveSessionDto;
import com.tyss.strongameapp.service.LiveSessionService;

import lombok.extern.slf4j.Slf4j;


/**
 * LiveSessionController is used to display the content of live session page.
 * @author Sushma Guttal
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
public class LiveSessionController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private LiveSessionService liveSessionService;


	/**
	 * This method is used to fetch all the specializations.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getallspecializations")
	public ResponseEntity<ResponseDto> getAllSpecializations() {
		List<SpecializationDetailsDto> dto = liveSessionService.getAllSpecializations();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("No Specialization found");
			responseDTO.setError(true);
			responseDTO.setData("No Specialization found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Specialization is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get all specialization method


	/**
	 * This method is used to fetch all the coaches.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getallcoaches")
	public ResponseEntity<ResponseDto> getAllCoaches() {
		List<CoachForSessionDetailsDto> dto = liveSessionService.getAllCoaches();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("No coach found");
			responseDTO.setError(true);
			responseDTO.setData("No coach found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Coach is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get all coaches method


	/**
	 * This method is used to get specialization details by specialization id
	 * @param specializationId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getspecialization/{specializationId}/{userId}")
	public ResponseEntity<ResponseDto> getSpecialization(@PathVariable int specializationId,@PathVariable int userId) {
		SpecializationDetailsDto dto = liveSessionService.getSpecialization(specializationId, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getCases().equalsIgnoreCase("")==false) {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setData(dto.getCases());
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Specialization fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get specialization method


	/**
	 * This method is used to fetch todays session list
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/gettodaysessions/{userId}")
	public ResponseEntity<ResponseDto> getTodaySessions(@PathVariable int userId) {
		TodaysLiveSessionDto dto = liveSessionService.getTodaySessions(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getSessionList()==null) {
			log.error("No session is found");
			responseDTO.setError(true);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else if(dto.getCases().equalsIgnoreCase("")){
			log.debug("Sessions are fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}else {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setMessage(dto.getCases());
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}
	}//End of get all todays session method


	/**
	 * This method is used to fetch list of packages.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getpackagelist")
	public ResponseEntity<ResponseDto> getPackageList() {
		List<PackageDetailsDto> dto = liveSessionService.getPackageList();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("No package is found");
			responseDTO.setError(true);
			responseDTO.setData("No package is found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get all todays session method

	/**
	 * This method is used to fetch package by id.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getpackagebyid/{packageId}")
	public ResponseEntity<ResponseDto> getPackageById(@PathVariable int packageId) {
		List<PackageDetailsDto> dto = liveSessionService.getPackageById(packageId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("Package is not found");
			responseDTO.setError(true);
			responseDTO.setData("Package is not found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get all todays session method


	/**
	 * This method is used to enroll the package.
	 * @param userId
	 * @param packageId
	 * @return ResponseEntity<ResponseDto>
	 */
	@PutMapping("/booknow")
	public ResponseEntity<ResponseDto> bookPackage(@RequestBody BookNowDto bookNowDto) {
		PackageDetailsDto dto = liveSessionService.bookPackage(bookNowDto);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (!dto.getCases().equalsIgnoreCase("")) {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setMessage(dto.getCases());
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package is booked");
			responseDTO.setError(false);
			responseDTO.setData("You have successfully booked the package");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of bookPackage class


	/**
	 * This method is used to perform join now operation
	 * @param userId
	 * @param sessionId
	 * @return ResponseEntity<ResponseDto>
	 */
	@PutMapping("/joinnow/{userId}/{sessionId}")
	public ResponseEntity<ResponseDto> joinSession(@PathVariable int userId, @PathVariable int sessionId) {
		SessionDetailsDto dto = liveSessionService.joinSession(userId,sessionId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if(!dto.getValidationCase().equalsIgnoreCase("")) {
			log.error(dto.getValidationCase());
			responseDTO.setError(true);
			responseDTO.setMessage(dto.getValidationCase());
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}else {
			if (dto.getCases()==1) {
				log.error("You have not enrolled any package.");
				responseDTO.setError(true);
				responseDTO.setData("You have not enrolled any package.");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			} else if(dto.getCases()==2) {
				log.error("Your package is expired");
				responseDTO.setError(true);
				responseDTO.setData("Your package is expired");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else if(dto.getCases()==3) {
				log.debug("User and session are mapped, slots are decreased");
				responseDTO.setError(false);
				responseDTO.setData(dto);
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}else if(dto.getCases()==5){
				log.error("No SLots Available.");
				responseDTO.setError(true);
				responseDTO.setData("No Slots Available..!");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}
			else {
				log.debug("User and session are mapped already");
				responseDTO.setError(false);
				responseDTO.setData(dto);
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

			}
		}
	}//End of get all todays session method


	/**
	 * This method is used to fetch tag line details.
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/tagline")
	public ResponseEntity<ResponseDto> getTaglineDetails() {
		TaglineDetailsDto dto = liveSessionService.getTagLineDetails();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("Tagline is not found");
			responseDTO.setError(true);
			responseDTO.setData("Tagline is not found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Tagline is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get all todays session method

}//End of LiveSessionController
