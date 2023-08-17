package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.DietRecipeDetailsDto;
import com.tyss.adminstrongame.entity.DietRecipeDetails;

/**
 * This is the service interface for DietRecipes Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * DietRecipe Details
 * 
 * @author Trupthi
 * 
 */
public interface DietRecipeService {

	/**
	 * This method is implemented by its implementation class to get all Diet Recipes
	 * 
	 * @return List<DietRecipeDetails>
	 */
	List<DietRecipeDetails> getAllDietRecipe();

	/**
	 * This method is implemented by its implementation class to save Diet Recipe
	 * 
	 * @param data
	 * @return DietRecipeDetailsDto
	 */
	DietRecipeDetailsDto addDietRecipe(DietRecipeDetailsDto data,MultipartFile dietImage);

	/**
	 * This method is implemented by its implementation class to update Diet Recipe
	 * 
	 * @param data
	 * @return DietRecipeDetailsDto
	 */
	DietRecipeDetailsDto updateDietRecipe(DietRecipeDetailsDto data, MultipartFile dietImage);

	/**
	 * This method is implemented by its implementation class to delete Diet Recipe
	 * 
	 * @param dietRecipeId
	 * @return boolean
	 */
	boolean deleteDietRecipe(int dietRecipeId);

	/**
	 * This method is implemented by its implementation class to get Diet Recipe
	 * 
	 * @param dietName
	 * @return List<DietRecipeDetails>
	 */
	List<DietRecipeDetails> getDietRecipeByName(String dietName);

}
