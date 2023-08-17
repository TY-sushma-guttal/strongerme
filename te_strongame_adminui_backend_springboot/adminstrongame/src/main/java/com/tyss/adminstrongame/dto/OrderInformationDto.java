package com.tyss.adminstrongame.dto;

import java.util.Date;
import java.util.List;

import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class OrderInformationDto {

	private int orderId;

	private String orderStatus;

	private String address;

	private Date deliveryDate ;

	private Date orderDate;
	
	private UserInformation orderUser;
	
	private List<ProductInformation> orderProducts;

	public OrderInformationDto() {
		super();
	}
	
	
}
