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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.strongameapp.dto.LeaderBoardDto;
import com.tyss.strongameapp.dto.MyOrderDto;
import com.tyss.strongameapp.dto.NotificationInformationDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.service.SettingsService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal
 *Settings controller is used to display settings page.
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
@RequestMapping("/settings")
public class SettingsController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private SettingsService settingService;

	/**
	 * This method allows the users to edit their profile.
	 * @param data
	 * @return ResponseEntity<ResponseDto>
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/edit")
	public ResponseEntity<ResponseDto> editUser(@RequestBody UserInformationDto data){
		UserInformationDto response=settingService.editUser(data);
		ResponseDto responseDTO = new ResponseDto();
		if (response == null) {
			log.error("User information does not exist");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found.");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(response.getCases().equalsIgnoreCase("")) {
				System.out.println(response);
				log.debug("User information updated Successfully");
				responseDTO.setError(false);
				responseDTO.setData("User information updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			} else {
				responseDTO.setError(true);
				responseDTO.setData(response.getCases());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);

			}

		}
	}//End of edit user method

	/**
	 * This method is used to display the leader board page
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("leaderboard/{userId}")
	public ResponseEntity<ResponseDto> leadBoard(@PathVariable int userId) {
		LeaderBoardDto dto = settingService.leadBoard(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("User Not Found");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("leaderboard is fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of leaderboard method

	/**
	 * This method is used to display the my order page.
	 * @param user
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("myorder/{userId}")
	public ResponseEntity<ResponseDto> myOrder(@PathVariable Integer userId) {
		List<MyOrderDto> dto = settingService.myOrder(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if(dto==null) {
			log.error("User Not Found");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}
		if (dto.isEmpty()) {
			log.error("Your order list is empty");
			responseDTO.setError(true);
			responseDTO.setData("Your order list is empty");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Order is fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}// End of myorder method

	/**
	 * This method is used to fetch all the notifications
	 * @param user
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("notification/{userId}")
	public ResponseEntity<ResponseDto> notification(@PathVariable Integer userId) {
		List<NotificationInformationDto> dto = settingService.notification(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if(dto==null) {
			log.error("User Not Found");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);

		}
		if (dto.isEmpty()) {
			log.error("No notifications");
			responseDTO.setError(true);
			responseDTO.setData("No notifications");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("NOtifications are fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of notification method
	
	@PutMapping("/upload/profile")
	public ResponseEntity<ResponseDto> uploadProfile(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException{
		UserInformationDto userInformationDto = new ObjectMapper().readValue(data, UserInformationDto.class);
		UserInformationDto response=settingService.uploadImage(userInformationDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (response == null) {
			log.error("User information does not exist");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found.");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
				log.debug("User information updated Successfully");
				responseDTO.setError(false);
				responseDTO.setData(response);
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}

}//End of Settings Controller class
