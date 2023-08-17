package com.tyss.adminstrongame.controller;

import java.util.List;

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

import com.tyss.adminstrongame.dto.AdminRewardDetailsDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.dto.RewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardInformationDto;
import com.tyss.adminstrongame.service.RewardService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Rewards Page . Here you find GET,
 * PUT, DELETE controllers for the Rewards Page. Here you can find 
 * the URL path for all the Rewards Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/reward" )
public class RewardController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private RewardService rewardService;

	/**
	 * This method is to get Reward by specifying name or mobile number or email id
	 * 
	 * @param name,mobileNo,email
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping(value={"/getbyname/{name}","/getbynumber/{mobileNo}","/getbyid/{email}"})
	public ResponseEntity<ResponseDto> getReward(@PathVariable (required = false) String name, @PathVariable (required = false) Long mobileNo,@PathVariable ( required = false) String email) {

		List<RewardInformationDto> dto = rewardService.getReward(name, mobileNo, email);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("reward does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getReward

	/**
	 * This method is to update Reward coins 
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateCoins(@RequestBody RewardInformationDto data) {

		AdminRewardDetailsDto dto= rewardService.updateCoins(data);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Reward does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Reward does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}  else {
			if(dto.getValidationMessage()!=null) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else {
				log.debug("Reward coins updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Reward coins updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}
	}// End Of the updateCoins

	/**
	 * This method is to delete Reward Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 */	
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteRewardDetails(@RequestBody RewardInformationDto data) {

		AdminRewardDetailsDto dto = rewardService.deleteRewardDetails(data);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Reward does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Reward does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("reward details deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("reward details deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteRewardDetails

	/**
	 * This method is to get all Reward Details
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllRewards() {

		List<RewardInformationDto> data = rewardService.getAllRewards();
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (data == null) {
			log.error("No rewards exist");
			responseDTO.setError(true);
			responseDTO.setData("No rewards exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("list of rewards fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllRewards


}// End Of the Class
