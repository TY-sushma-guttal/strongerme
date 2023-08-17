package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.entity.ShoppingBannerImage;

import lombok.Data;

@Data
public class ShoppingBannerInformationDto {

	private int shoppingBannerId;

	private String shoppingBannerDescription;

	private List<ShoppingBannerImage> shoppingBannerImage;

	private int id;

	private String name;
	
	private String validationMessage;

	public ShoppingBannerInformationDto() {
		super();
	}

	public ShoppingBannerInformationDto(int shoppingBannerId, String shoppingBannerDescription,
			List<ShoppingBannerImage> shoppingBannerImage, int id, String name, String validationMessage) {
		super();
		this.shoppingBannerId = shoppingBannerId;
		this.shoppingBannerDescription = shoppingBannerDescription;
		this.shoppingBannerImage = shoppingBannerImage;
		this.id = id;
		this.name = name;
		this.validationMessage = validationMessage;
	}


}
