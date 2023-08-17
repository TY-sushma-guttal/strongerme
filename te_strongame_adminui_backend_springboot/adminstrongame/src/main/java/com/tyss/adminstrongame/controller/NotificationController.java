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
import com.tyss.adminstrongame.dto.NotificationInformationDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Notification Page . Here you find GET, POST,
 * PUT, DELETE controllers for the Notification Page. Here you can find the URL
 * path for all the Notification Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notification")
public class NotificationController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private NotificationService notificationService;


	/**
	 * This method is to save Notification Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addNotificationDetails(@RequestParam String data,
			@RequestParam MultipartFile image) throws JsonProcessingException {
		NotificationInformationDto obj = new ObjectMapper().readValue(data, NotificationInformationDto.class);
		NotificationInformationDto dto = notificationService.addNotification(obj, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Enter valid notification");
			responseDTO.setError(true);
			responseDTO.setData("Enter valid notification");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Notification details added successfully");
			responseDTO.setError(false);
			responseDTO.setData("Notification details added successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
		}
	}// End Of the addNotificationDetails

	/**
	 * This method is to get all Notification Details
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getNotificationDetails() {
		List<NotificationInformation> list = notificationService.getNotification();
		ResponseDto responseDTO = new ResponseDto();
		if (list == null) {
			log.error("No notification details exist");
			responseDTO.setError(true);
			responseDTO.setData(" No notification details exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of notifications fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(list);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getNotificationDetails

	/**
	 * This method is to get Notification Details by specifying id
	 * 
	 * @param notificationId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/getbyid/{notificationId}")
	public ResponseEntity<ResponseDto> getNotificationById(@PathVariable("notificationId") int notificationId) {
		NotificationInformation dto = notificationService.getNotificationById(notificationId).orElse(null);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing notification");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing notification");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the getNotificationById

	/**
	 * This method is to update Notification Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateNotificationDetails(@RequestParam String data,
			@RequestParam MultipartFile image) throws JsonProcessingException {
		NotificationInformationDto obj = new ObjectMapper().readValue(data, NotificationInformationDto.class);
		NotificationInformationDto dto = notificationService.updateNotification(obj, image);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Please enter the details for existing notification");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing notification");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Notification details updated successfully");
			responseDTO.setError(false);
			responseDTO.setData("Notification details updated successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End Of the updateNotificationDetails

	/**
	 * This method is to delete Notification Details
	 * 
	 * @param notificationId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@DeleteMapping("/delete/{notificationId}")
	public ResponseEntity<ResponseDto> deleteNotificationDetails(@PathVariable("notificationId") int notificationId) {
		boolean flag = notificationService.deleteNotification(notificationId);
		ResponseDto responseDTO = new ResponseDto();
		if (!flag) {
			log.error("Notification does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Notification does not exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Notification details deleted Successfully");
			responseDTO.setError(false);
			responseDTO.setData("Notification details deleted Successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}
	}// End Of the deleteNotificationDetails

}// End Of the Class
