package com.tyss.adminstrongame.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.DietRecipeDetailsDto;
import com.tyss.adminstrongame.entity.DietRecipeDetails;
import com.tyss.adminstrongame.repository.DietPlanDetailsRepo;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for DietRecipeService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * DietRecipe Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class DietRecipeServiceImpli implements DietRecipeService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	DietPlanDetailsRepo dietRepo;

	@Autowired
	private SSSUploadFile uploadFile;

	/**
	 * This method is implemented to get all Diet Recipes
	 * 
	 * @return List<DietRecipeDetails>
	 */
	@Override
	public List<DietRecipeDetails> getAllDietRecipe() {
		return dietRepo.getAllDietRecipe();
	}// End Of the getAllDietRecipe

	/**
	 * This method is implemented to save Diet Recipe
	 * 
	 * @param data
	 * @return DietRecipeDetailsDto
	 */
	@Transactional
	@Override
	public DietRecipeDetailsDto addDietRecipe(DietRecipeDetailsDto data, MultipartFile dietImage) {
		if (data != null) {
			data.setValidationMessage("");
			if (data.getCalories() < 0) {
				data.setValidationMessage(
						data.getValidationMessage() + " Value cannot be negative for Calories field.");
			}
			if (data.getProtine() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Protine field.");
			}
			if (data.getCarbs() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Carbs field.");
			}
			if (data.getFat() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Fat field.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				DietRecipeDetails dietPlanEntity = new DietRecipeDetails();
				BeanUtils.copyProperties(data, dietPlanEntity);
		
					dietPlanEntity = dietRepo.save(dietPlanEntity);
					if(!dietImage.isEmpty()) {
					dietPlanEntity.setDietImage(uploadFile.uploadFile(dietImage, dietPlanEntity.getDietId(), "Diet"));
					}
					dietRepo.save(dietPlanEntity);
					BeanUtils.copyProperties(dietPlanEntity, data);
					return data;
				
			}

		} else {
			throw new com.tyss.adminstrongame.exception.DietRecipeException(
					"Failed to add new diet plan: diet plan data should not be empty!");
		}
	}// End Of the addDietRecipe

	/**
	 * This method is implemented to update Diet Recipe
	 * 
	 * @param data
	 * @return DietRecipeDetailsDto
	 */
	@Transactional
	@Override
	public DietRecipeDetailsDto updateDietRecipe(DietRecipeDetailsDto data, MultipartFile dietImage) {
		if (data != null) {
			data.setValidationMessage("");
			if (data.getCalories() < 0) {
				data.setValidationMessage(
						data.getValidationMessage() + " Value cannot be negative for Calories field.");
			}
			if (data.getProtine() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Protine field.");
			}
			if (data.getCarbs() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Carbs field.");
			}
			if (data.getFat() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Fat field.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				DietRecipeDetails dietPlanEntity = new DietRecipeDetails();
				BeanUtils.copyProperties(data, dietPlanEntity);

				if (!dietRepo.findById(dietPlanEntity.getDietId()).isPresent()) {
					return null;
				} else {
					if(!dietImage.isEmpty()) { 
						String folderPath = "Diet/" + data.getDietId();
						uploadFile.deleteS3Folder(uploadFile.bucketName, folderPath);
						dietPlanEntity.setDietImage(uploadFile.uploadFile(dietImage, data.getDietId(), "Diet"));
					}

					dietRepo.updateDietRecipe(dietPlanEntity.getDietId(), dietPlanEntity.getDietName(),
							dietPlanEntity.getCalories(), dietPlanEntity.getProtine(), dietPlanEntity.getFat(),
							dietPlanEntity.getCarbs(), dietPlanEntity.getDietDetails(), dietPlanEntity.getDietImage(),
							dietPlanEntity.getDietVideo(), dietPlanEntity.getIngredients(), dietPlanEntity.getSteps());
					BeanUtils.copyProperties(dietPlanEntity, data);
					return data;

				}
			}

		} else {
			throw new com.tyss.adminstrongame.exception.DietRecipeException(
					"Failed to update new diet plan: diet plan data should not be empty!");
		}
	}// End Of the updateDietRecipe

	/**
	 * This method is implemented to delete Diet Recipe
	 * 
	 * @param dietRecipeId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteDietRecipe(int dietRecipeId) {
		if (dietRecipeId != 0) {
			if (!dietRepo.findById(dietRecipeId).isPresent()) {
				return false;
			} else {
				String folderPath = "Diet/" + dietRecipeId;
				uploadFile.deleteS3Folder(uploadFile.bucketName, folderPath);
				dietRepo.deleteById(dietRecipeId);
				return true;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.DietRecipeException(
					"Failed to delete new diet plan: diet plan data should not be empty!");
		}
	}// End Of the deleteDietRecipe

	/**
	 * This method is implemented to get Diet Recipe
	 * 
	 * @param dietName
	 * @return List<DietRecipeDetails>
	 */
	@Override
	public List<DietRecipeDetails> getDietRecipeByName(String dietName) {
		List<DietRecipeDetails> dietRecipes = dietRepo.fetchDietPlan(dietName);
		if (dietRecipes.isEmpty()) {
			return null;
		} else {
			return dietRecipes;
		}
	}// End Of the getDietRecipeByName

}// End Of the Class
