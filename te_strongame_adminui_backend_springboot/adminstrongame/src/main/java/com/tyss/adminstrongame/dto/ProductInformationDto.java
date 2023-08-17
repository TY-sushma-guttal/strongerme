package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.OrderInformation;
import com.tyss.adminstrongame.entity.ShoppingBannerInformation;
import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class ProductInformationDto {

	private int productId;

	private String productName;

	private String productDescription;

	private String productFeatures;

	private String productDisclaimer;

	private double productCoins;

	private String productImage;
	
	private double discount;

	private List<OrderInformation> order;

	private List<UserInformation> user;
	
	private List<ShoppingBannerInformation> productShopBanner;
	
	private String validationMessage;

	public ProductInformationDto() {
		super();
	}

	public ProductInformationDto(int productId, String productName, String productDescription, String productFeatures,
			String productDisclaimer, double productCoins, String productImage, double discount,
			List<OrderInformation> order, List<UserInformation> user, List<ShoppingBannerInformation> productShopBanner,
			String validationMessage) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productFeatures = productFeatures;
		this.productDisclaimer = productDisclaimer;
		this.productCoins = productCoins;
		this.productImage = productImage;
		this.discount = discount;
		this.order = order;
		this.user = user;
		this.productShopBanner = productShopBanner;
		this.validationMessage = validationMessage;
	}

}
