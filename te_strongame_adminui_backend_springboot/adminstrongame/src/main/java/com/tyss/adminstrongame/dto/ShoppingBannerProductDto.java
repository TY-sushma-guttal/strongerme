package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class ShoppingBannerProductDto {

	private int productId;

	private String productName;

	public ShoppingBannerProductDto(int productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}
	
}
