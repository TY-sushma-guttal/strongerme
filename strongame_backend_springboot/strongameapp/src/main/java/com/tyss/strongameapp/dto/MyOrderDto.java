package com.tyss.strongameapp.dto;


import lombok.Data;

@Data
public class MyOrderDto {
	
	private int myOrderId;
	
	private String name;

	private double price;
	
	private String type;
	
	private String image;
	
	public MyOrderDto() {
		super();
	}

	public MyOrderDto(int myOrderId, double price, String type, String image) {
		super();
		this.myOrderId = myOrderId;
		this.price = price;
		this.type = type;
		this.image = image;
	}


	
}
