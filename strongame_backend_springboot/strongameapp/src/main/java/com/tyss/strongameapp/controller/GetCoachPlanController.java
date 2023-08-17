package com.tyss.strongameapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.tyss.strongameapp.dto.PlanInformationDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.service.GetCoachPlanService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author Sushma Guttal
 * Get Coach plan controller is used to get plan list of coach.
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
public class GetCoachPlanController {
	
	/**
	 * This field is to invoke business methods.
	 */
	@Autowired
	private GetCoachPlanService coachPlanService;
	
	
	/**
	 * This method is to get plan list of coach.
	 * @param coachId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("getplan/{coachId}")
	public ResponseEntity<ResponseDto> getCoachPlan(@PathVariable int coachId) {
		List<PlanInformationDto>  planListdto=coachPlanService.getCoachPlan(coachId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if(planListdto==null) {
			log.error("Coach not found");
			responseDTO.setError(true);
			responseDTO.setData("Coach Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
			
		}
		else if(planListdto.isEmpty()) {
			log.error("No plan available fo particular coach");
			responseDTO.setError(true);
			responseDTO.setData("No plans available for particular coach");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Plan are fetched successfully for specified coach");
			responseDTO.setError(false);
			responseDTO.setData(planListdto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of method get coach plan

}//End of class Get coach plan controller class
