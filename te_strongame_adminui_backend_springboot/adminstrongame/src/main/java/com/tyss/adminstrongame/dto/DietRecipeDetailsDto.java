package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.DietRecipeLike;
import com.tyss.adminstrongame.entity.HomeBannerInformation;

import lombok.Data;

@Data
public class DietRecipeDetailsDto {

	private int dietId;

	private String dietName;

	private String ingredients;

	private long steps;

	private double calories;

	private double protine;

	private double fat;

	private double carbs;

	private String dietDetails;

	private String dietImage;

	private String dietVideo;

	private List<DietRecipeLike> likes;
	
	private List<HomeBannerInformation> dietHomeBanner;
	
	private String validationMessage;

	public DietRecipeDetailsDto() {
		super();
	}

	public DietRecipeDetailsDto(int dietId, String dietName, String ingredients, long steps, double calories,
			double protine, double fat, double carbs, String dietDetails, String dietImage, String dietVideo,
			List<DietRecipeLike> likes, List<HomeBannerInformation> dietHomeBanner, String validationMessage) {
		super();
		this.dietId = dietId;
		this.dietName = dietName;
		this.ingredients = ingredients;
		this.steps = steps;
		this.calories = calories;
		this.protine = protine;
		this.fat = fat;
		this.carbs = carbs;
		this.dietDetails = dietDetails;
		this.dietImage = dietImage;
		this.dietVideo = dietVideo;
		this.likes = likes;
		this.dietHomeBanner = dietHomeBanner;
		this.validationMessage = validationMessage;
	}
	
}
