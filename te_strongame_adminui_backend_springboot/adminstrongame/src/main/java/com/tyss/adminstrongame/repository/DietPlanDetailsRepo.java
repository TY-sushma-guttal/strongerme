package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.HomeDropDownDto;
import com.tyss.adminstrongame.dto.TransformationCoachDto;
import com.tyss.adminstrongame.entity.DietRecipeDetails;

public interface DietPlanDetailsRepo extends JpaRepository<DietRecipeDetails, Integer> {

	@Query("SELECT d FROM DietRecipeDetails d where d.dietName=?1")
	public List<DietRecipeDetails> fetchDietPlan(String name);
	
	@Query("SELECT d FROM DietRecipeDetails d where d.dietId=?1")
	DietRecipeDetails getDietById(int dietId);
	
	@Query( value = "select new com.tyss.adminstrongame.entity.DietRecipeDetails(d.dietId,d.dietName, d.calories, d.protine, d.fat, d.carbs,"
			+ "	d.dietDetails, d.dietImage, d.dietVideo, d.ingredients,d.steps)  from DietRecipeDetails d")
	public List<DietRecipeDetails> getAllDietRecipe();
	
	@Modifying
	@Query( value = "update diet_recipe_details set  calories=:calories, carbs=:carbs, diet_details=:dietDetails, "
			+ "diet_image=:dietImage, diet_name=:dietName, diet_video=:dietVideo, fat=:fat, ingredients=:ingredients, protine=:protine, "
			+ "steps=:steps where diet_id=:dietId", nativeQuery = true)	
	void updateDietRecipe(int dietId, String dietName, double calories, double protine, double fat, double carbs,
			String dietDetails, String dietImage, String dietVideo, String ingredients, long steps);
	
	@Query("select new com.tyss.adminstrongame.dto.HomeDropDownDto(d.dietId,d.dietName) from DietRecipeDetails d")
	List<HomeDropDownDto> getDietRecipeNames();

	
}
