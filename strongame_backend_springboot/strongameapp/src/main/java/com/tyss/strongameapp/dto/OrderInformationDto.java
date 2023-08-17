package com.tyss.strongameapp.dto;

import java.util.Date;
import java.util.List;

import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.UserInformation;

import lombok.Data;

@Data
public class OrderInformationDto {

	private int orderId;

	private String orderStatus;

	private String address;

	private Date deliveryDate ;

	private Date orderDate;
	
	private List <ProductInformation> products;
	
	private UserInformation user;
	
	public OrderInformationDto(int orderId, String orderStatus, String address, Date deliveryDate, Date orderDate,
			List<ProductInformation> products, UserInformation user) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.address = address;
		this.deliveryDate = deliveryDate;
		this.orderDate = orderDate;
		this.products = products;
		this.user = user;
	}

}
