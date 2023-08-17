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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.adminstrongame.dto.DietRecipeDetailsDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.dto.TransformationDetailsDto;
import com.tyss.adminstrongame.entity.DietRecipeDetails;
import com.tyss.adminstrongame.service.DietRecipeService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for DietRecipes Page . Here you find GET,
 * POST, PUT, DELETE controllers for the DietRecipes Page. Here you can
 * find the URL path for all the DietRecipes Page services.
 * 
 * @author Trupthi
 * 
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/dietrecipe" )
public class DietRecipeController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private DietRecipeService dietRecipeService;


	/**
	 * This method is to save Diet Recipe Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> addDietDetails(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		DietRecipeDetailsDto dietRecipeDetailsDto = new ObjectMapper().readValue(data, DietRecipeDetailsDto.class);
		DietRecipeDetailsDto dto = dietRecipeService.addDietRecipe(dietRecipeDetailsDto,image);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Diet recipe details already exist");
			responseDTO.setError(true);
			responseDTO.setData("Diet recipe details already exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		}  else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else {
				log.debug("Diet recipe details added Successfully");
				responseDTO.setError(false);
				responseDTO.setData("Diet recipe details added Successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.CREATED);
			}
		}

	}// End Of the addDietDetails

	/**
	 * This method is to get all Diet Recipe Details
	 * 
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("getall")
	public ResponseEntity<ResponseDto> getAllDietDetails() {

		List<DietRecipeDetails> data = dietRecipeService.getAllDietRecipe();
		ResponseDto responseDTO = new ResponseDto();

		if (data == null) {
			log.error("No diet recipes exist");
			responseDTO.setError(true);
			responseDTO.setData("No diet recipes exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);

		} else {
			log.debug("list of Diet Recipes fetched successfully");
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAllDietDetails

	/**
	 * This method is to get Diet Recipe Details by specifying diet name
	 * 
	 * @param dietName
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@GetMapping("/getbyname/{dietName}")
	public ResponseEntity<ResponseDto> getDietRecipeByName(@PathVariable ("dietName") String dietName) {
		List<DietRecipeDetails> data =  dietRecipeService.getDietRecipeByName(dietName);
		ResponseDto responseDTO = new ResponseDto();

		if (data == null) {
			log.error("Diet recipe does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Diet recipe does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);

		} else {
			responseDTO.setError(false);
			responseDTO.setData(data);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getDietRecipeByName

	/**
	 * This method is to update Diet Recipe Details
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto> object 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateDietRecipe(@RequestParam String data, @RequestParam MultipartFile image) throws JsonMappingException, JsonProcessingException {
		DietRecipeDetailsDto dietRecipeDetailsDto = new ObjectMapper().readValue(data, DietRecipeDetailsDto.class);
		DietRecipeDetailsDto dto = dietRecipeService.updateDietRecipe(dietRecipeDetailsDto, image);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Please enter the details for existing Diet recipe");
			responseDTO.setError(true);
			responseDTO.setData("Please enter the details for existing Diet recipe");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else {

				log.debug("Diet recipe details updated successfully");
				responseDTO.setError(false);
				responseDTO.setData("Diet recipe details updated successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}

	}// End Of the updateDietRecipe

	/**
	 * This method is to delete Diet Recipe Details
	 * 
	 * @param dietRecipeId
	 * @return ResponseEntity<ResponseDto> object 
	 */
	@DeleteMapping("/delete/{dietRecipeId}")
	public ResponseEntity<ResponseDto> deleteDietRecipe(@PathVariable("dietRecipeId") int dietRecipeId) {
		boolean flag = dietRecipeService.deleteDietRecipe(dietRecipeId);
		ResponseDto responseDTO = new ResponseDto();

		if (!flag) {
			log.error("Diet recipe does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Diet recipe does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		}
		else {
			log.debug("Diet recipe deleted successfully");
			responseDTO.setError(false);
			responseDTO.setData("Diet recipe deleted successfully");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the deleteDietRecipe


}// End Of the Class
