package com.tyss.adminstrongame.dto;
import java.util.List;

import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.DietRecipeDetails;
import com.tyss.adminstrongame.entity.HomeBannerImage;
import com.tyss.adminstrongame.entity.TransformationDetails;

import lombok.Data;

@Data
public class HomeBannerInformationDto {

	private int homeBannerId;

	private String homeBannerDescription;
	
	private String homeBannerType;

	private List<HomeBannerImage> homeBannerImage;
	
	private int id;
	
	private String name;
	
	private String validationMessage;
	
	public HomeBannerInformationDto() {
		super();
	}

	public HomeBannerInformationDto(int homeBannerId, String homeBannerDescription, String homeBannerType,
			List<HomeBannerImage> homeBannerImage, int id, String name, String validationMessage) {
		super();
		this.homeBannerId = homeBannerId;
		this.homeBannerDescription = homeBannerDescription;
		this.homeBannerType = homeBannerType;
		this.homeBannerImage = homeBannerImage;
		this.id = id;
		this.name = name;
		this.validationMessage = validationMessage;
	}

}
