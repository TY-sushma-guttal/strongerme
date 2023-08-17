package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class ShoppingBannerImageDto {

	private int shoppingBannerImageId;

	private String shoppingBannerImage;

	public ShoppingBannerImageDto() {
		super();
	}

	public ShoppingBannerImageDto(int shoppingBannerImageId, String shoppingBannerImage) {
		super();
		this.shoppingBannerImageId = shoppingBannerImageId;
		this.shoppingBannerImage = shoppingBannerImage;
	}

}
