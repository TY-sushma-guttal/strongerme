package com.tyss.strongameapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.DietPlanDetailsDto;
import com.tyss.strongameapp.dto.DietRecipeLikeDto;
import com.tyss.strongameapp.dto.HomeDisplayDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.TransformationLikeDetailsDto;
import com.tyss.strongameapp.service.HomePageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal
 * Home page controller is used to display the home page.
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
@RequestMapping("/homepage")
public class HomePageController {

	/**
	 * This field is to invoke the business layer methods.
	 */
	@Autowired
	private HomePageService homePageService;

	/**
	 * This method is used for home page listing.
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getall/{userId}")
	public ResponseEntity<ResponseDto> homePageDisplay(@PathVariable int userId) {
		HomeDisplayDto dto= homePageService.homePageDisplay(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getCases().equalsIgnoreCase("")==false) {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setData(dto.getCases());
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Home page is displaying");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of home page display method.

	/**
	 * This method allows the user to post like for diet recipe.
	 * @param like
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/dietlike")
	public ResponseEntity<ResponseDto> addDietLike(@RequestBody DietRecipeLikeDto like){
		DietRecipeLikeDto dietRecipeLike=homePageService.save(like);
		ResponseDto responseDTO = new ResponseDto();
		if(dietRecipeLike==null) {
			log.error("No likes available for specified diet recipe");
			responseDTO.setError(true);
			responseDTO.setData("No likes available for specified diet recipe");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}else {
			log.debug("User have posted like to the diet recipe");
			responseDTO.setError(false);
			responseDTO.setData(dietRecipeLike);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of add diet like method

	/**
	 * This method allows the user to post like for transformation.
	 * @param like
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/translike")
	public ResponseEntity<ResponseDto> addTransformationLike(@RequestBody TransformationLikeDetailsDto like){
		TransformationLikeDetailsDto transformationLike=homePageService.save(like);
		ResponseDto responseDTO = new ResponseDto();
		if(transformationLike==null) {
			log.error("No likes available for specified transformation");
			responseDTO.setError(true);
			responseDTO.setData("No likes available for specified transformation");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}else {
			log.debug("User have posted like to the transformation");
			responseDTO.setError(false);
			responseDTO.setData(transformationLike);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of add transformation like method

	/**
	 * This method is used to fetch all diet recipes in descending order by name.
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterdietbynamedesc/{userId}")
	public ResponseEntity<ResponseDto> filterDietByNameDesc(@PathVariable int userId) {
		List<DietPlanDetailsDto> dto = homePageService.filterDietByNameDesc(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("User Not Found");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Diet recipe is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of filter diet by name desc method

	/**
	 * This method is used to fetch all diet recipes in ascending order by name.
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterdietbyname/{userId}")
	public ResponseEntity<ResponseDto> filterDietByName(@PathVariable int userId) {
		List<DietPlanDetailsDto> dto = homePageService.filterDietByName(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			log.error("User Not Found");
			responseDTO.setError(true);
			responseDTO.setData("User Not Found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Diet recipe is fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}// End of filter diet by name method


	@GetMapping("/searchdiet/{userId}/{keyword}")
	public ResponseEntity<ResponseDto> searchDiet(@PathVariable int userId, @PathVariable String keyword) {
		List<DietPlanDetailsDto> dto = homePageService.searchDiet(userId,keyword);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			responseDTO.setError(true);
			responseDTO.setData("No diet recipe found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getdietbyid/{userId}/{dietId}")
	public ResponseEntity<ResponseDto> getDietById(@PathVariable int userId, @PathVariable int dietId) {
		DietPlanDetailsDto dto = homePageService.getDietById(userId,dietId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto==null) {
			responseDTO.setError(true);
			responseDTO.setData("Diet recipe not found");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}
}//End of Home page controller class
