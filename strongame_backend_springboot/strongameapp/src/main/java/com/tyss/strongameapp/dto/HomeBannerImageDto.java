package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class HomeBannerImageDto {
	
	private int homeBannerImageId;
	
	private String homeBannerImage;

	public HomeBannerImageDto() {
		super();
	}

	public HomeBannerImageDto(int homeBannerImageId, String homeBannerImage) {
		super();
		this.homeBannerImageId = homeBannerImageId;
		this.homeBannerImage = homeBannerImage;
	}

}
