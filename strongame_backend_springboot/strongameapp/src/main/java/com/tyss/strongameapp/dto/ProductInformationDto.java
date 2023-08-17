package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class ProductInformationDto {

	private int productId;

	private String productName;

	private String productDescription;

	private String productFeatures;

	private String productDisclaimer;

	private double productCoins;
	
	private String product_image;
	
	private double discount;
	
	private Double finalCoins;

	public ProductInformationDto(int productId, String productName, String productDescription, String productFeatures,
			String productDisclaimer, double productCoins, String product_image, double discount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productFeatures = productFeatures;
		this.productDisclaimer = productDisclaimer;
		this.productCoins = productCoins;
		this.product_image = product_image;
		this.discount = discount;
	}


	public ProductInformationDto() {
		super();
	}
	
	
}
