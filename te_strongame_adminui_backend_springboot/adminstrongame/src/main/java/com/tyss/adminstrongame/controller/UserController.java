package com.tyss.adminstrongame.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.dto.UserDetailsDto;
import com.tyss.adminstrongame.dto.UserInformationDto;
import com.tyss.adminstrongame.entity.UserInformation;
import com.tyss.adminstrongame.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Users Page . Here you find GET,
 * PUT, DELETE controllers for the Users Page. Here you can find 
 * the URL path for all the Users Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/user" )
public class UserController {
	
	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private UserService userService;
	
	
	/**
	 * This method is to get all User Information 
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllUsers(){
		List<UserDetailsDto> list= userService.getAllUsers();
		ResponseDto responseDTO = new ResponseDto();
		if (list.isEmpty() ) {
			log.error(" No User details exist");
			responseDTO.setError(true);
			responseDTO.setData(" No User details exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of users fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(list);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getAllUsers
	
	/**
	 * This method is to get User Information by specifying email id,name,mobile number
	 * 
	 * @param email,name,mobileNo
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping(value={"/getbyid/{email}","/getbyname/{name}","/getbynumber/{mobileNo}"})
	public ResponseEntity<ResponseDto> getUser(@PathVariable ( required = false) String email,@PathVariable (required = false) String name, @PathVariable (required = false) Long mobileNo){
		List<UserInformation>	 dto= userService.getUser(email,name,mobileNo);
		ResponseDto responseDTO = new ResponseDto();
		if (dto.isEmpty()) {
			responseDTO.setError(true);
			responseDTO.setData("User details does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getUser

	/**
	 * This method is to delete User Information 
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ResponseDto> deleteUser(@PathVariable("userId") int userId){
		boolean flag=userService.deleteUser(userId);
		ResponseDto responseDTO = new ResponseDto();
		if (!flag) {
			log.error("User details does not exist");
			responseDTO.setError(true);
			responseDTO.setData("User details does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("User details deleted Successfully");
			responseDTO.setError(false);
			responseDTO.setData("User details deleted Successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the deleteUser
	
	/**
	 * This method is to update User Information 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PutMapping("/update")
	@Transactional
	public ResponseEntity<ResponseDto> updateUser(@RequestBody UserInformationDto data){
		UserInformationDto dto=userService.updateUser(data);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("User details does not exist");
			responseDTO.setError(true);
			responseDTO.setData("User details does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("User details updated Successfully");
			responseDTO.setError(false);
			responseDTO.setData("User details updated Successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the updateUser
	
	/**
	 * This method is to get all admin Notification Details
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/adminnotification")
	public ResponseEntity<ResponseDto> getAdminNotification(){
		List<NotificationInformationDto> list= userService.getAdminNotifications();
		ResponseDto responseDTO = new ResponseDto();
		if (list==null) {
			log.error("No admin notification details exist");
			responseDTO.setError(true);
			responseDTO.setData("No admin notification details exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of admin notifications fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(list);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getAdminNotification

}// End Of the Class
