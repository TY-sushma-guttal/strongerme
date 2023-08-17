package com.tyss.adminstrongame.dto;

import com.tyss.adminstrongame.entity.DietRecipeDetails;
import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class DietRecipeLikeDto {

	private int dietLikeId;

	private boolean userLike;
	
	private UserInformation dietLikeUser;
	
	private DietRecipeDetails dietRecipe;
	
	public DietRecipeLikeDto() {

	}
	
	public DietRecipeLikeDto(int dietLikeId, boolean userLike, UserInformation dietLikeUser,
			DietRecipeDetails dietRecipe) {
		super();
		this.dietLikeId = dietLikeId;
		this.userLike = userLike;
		this.dietLikeUser = dietLikeUser;
		this.dietRecipe = dietRecipe;
	}

}
