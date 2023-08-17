package com.tyss.adminstrongame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.adminstrongame.dto.DashboardDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.service.CustomUserDetailService;
import com.tyss.adminstrongame.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Dashboard Page . Here you find GET,
 * controller for the Dashboard Page. Here you can find the URL path
 * for Dashboard Page service.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/dashboard" )
public class DashboardController {
	
	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private DashboardService dashboardService;
	
	
	/**
	 * This method is to get Dashboard Details 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getdetails")
	public ResponseEntity<ResponseDto> getDashboardDetails() {

		DashboardDto data = dashboardService.getDashboardDetails();

		ResponseDto responseDTO = new ResponseDto();
	
		log.debug("dashboard details fetched successfully");
		responseDTO.setError(false);
		responseDTO.setData(data);
		return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			
	}// End Of the getDashboardDetails


}// End Of the Class
