package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.service.PasswordService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal
 * Password controller is for user password management.
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100", "https://strongame.web.app"})
@RestController
public class PasswordController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private PasswordService passwordService;


	/**
	 * This method is used for forgot password.
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@PutMapping("/forgot/{email}")
	public ResponseEntity<ResponseDto> forgotPassword(@PathVariable  String email){
		String response=passwordService.forgotPassword(email);
		ResponseDto responseDTO = new ResponseDto();
		if (response == null) {
			log.error("User information does not exist");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found.");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else if(response.equalsIgnoreCase("Mail sent successfully")==true){
			log.debug("Mail sent successfully");
			responseDTO.setError(false);
			responseDTO.setData(response);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}else {
			log.debug("Enter Valid Email");
			responseDTO.setError(true);
			responseDTO.setData(response);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		}
	}//End of forgot password method

	/**
	 * This method is used for change password.
	 * @param data
	 * @return ResponseEntity<ResponseDto>
	 */
	@PutMapping("/change")
	public ResponseEntity<ResponseDto> changePassword(@RequestBody UserInformationDto data){
		UserInformationDto dto=passwordService.changePassword(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("User information does not exist");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found.");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(dto.getCases().equalsIgnoreCase("")) {
				log.debug("password Reset Successfully..");
				responseDTO.setError(false);
				responseDTO.setData("password Reset Successfully..");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}else {
				log.debug(dto.getCases());
				responseDTO.setError(true);
				responseDTO.setData(dto.getCases());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}
		}
	}//End of change password method

}//End of Password Controller class.
