package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class OrderPlaceDto {
	
	private OrderInformationDto order;
	
	private int productId;
	
	private int userId;
	
	public OrderPlaceDto(OrderInformationDto order, int productId, int user) {
		super();
		this.order = order;
		this.productId = productId;
		this.userId = user;
	}
	public OrderPlaceDto() {
		super();
	}
}
