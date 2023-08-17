package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.ShoppingBannerImage;

import lombok.Data;

@Data
public class ShoppingBannerInformationDto {

	private int shoppingBannerId;

	private String shoppingBannerDescription;

	private List<ShoppingBannerImage> shoppingBannerImage;
	
	private int id;

	public ShoppingBannerInformationDto() {
		super();
	}

	public ShoppingBannerInformationDto(int shoppingBannerId, String shoppingBannerDescription,
			List<ShoppingBannerImage> shoppingBannerImage) {
		super();
		this.shoppingBannerId = shoppingBannerId;
		this.shoppingBannerDescription = shoppingBannerDescription;
		this.shoppingBannerImage = shoppingBannerImage;
	}

}
