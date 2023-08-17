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
import org.springframework.web.bind.annotation.RestController;

import com.tyss.adminstrongame.dto.PlanDetailsDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.entity.PlanDetails;
import com.tyss.adminstrongame.service.PlanService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Plans Page . Here you find GET,
 * POST, PUT, DELETE controllers for the Plans Page. Here you can
 * find the URL path for all the Plans Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/plan")
public class PlanController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private PlanService planService;


	/**
	 * This method is to save Plan Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addPlanDetails(@RequestBody PlanDetailsDto data) {
		PlanDetailsDto dto = planService.addPlanDetails(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Plan details already exists");
			responseDTO.setError(true);
			responseDTO.setData("Plan details already exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if(data.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else {
				log.debug("Plan details added successfully");
				responseDTO.setError(false);
				responseDTO.setData("Plan details added successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addPlanDetails

	/**
	 * This method is to get all Plan Details 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllPlanDetails() {

		List<PlanDetails> data = planService.getAllPlanDetails();

		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No plans exist");
			responseDTO.setError(true);
			responseDTO.setData("No plans exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of plans fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllPlanDetails

	/**
	 * This method is to delete Plan Details 
	 * 
	 * @param planId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/delete/{planId}")
	public ResponseEntity<ResponseDto> deletePlanDetails(@PathVariable("planId") int planId) {

		boolean flag = planService.deletePlanDetails(planId) ;
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.error("Plan does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Plan does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Plan details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Plan details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deletePlanDetails

	/**
	 * This method is to get Plan Details by specifying name or id
	 * 
	 * @param name,id
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping(value={"/getbyname/{name}","/getbyid/{id}"})
	public ResponseEntity<ResponseDto> getPlanDetails(@PathVariable ( required = false) String name,@PathVariable ( required = false) Integer id) {

		List<PlanDetails> dto = planService.getPlanDetails(name, id);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("plan does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getPlanDetails

	/**
	 * This method is to update Plan Details 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updatePlanDetails(@RequestBody PlanDetailsDto data) {

		PlanDetailsDto dto = planService.updatePlanDetails(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing plan");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing plan");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(data.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else {
				log.debug("plan information Updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("plan information Updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updatePlanDetails


}// End Of the Class
