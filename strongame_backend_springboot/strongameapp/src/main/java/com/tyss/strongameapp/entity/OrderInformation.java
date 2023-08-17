package com.tyss.strongameapp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "order_information")
public class OrderInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "address")
	private String address;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@Column(name = "delivery_date")
	private Date deliveryDate ;
	
	@Exclude
    @JsonBackReference(value="product-order")
	@ManyToMany(mappedBy = "order")
	private List <ProductInformation> orderProducts;
	
	public OrderInformation() {
		super();
	}

	@Exclude
    @JsonBackReference(value="user-order")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserInformation orderUser;
	
	public OrderInformation(int orderId, String orderStatus, String address, Date orderDate, Date deliveryDate,
			List<ProductInformation> products, UserInformation user) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.address = address;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.orderProducts = products;
		this.orderUser = user;
	}

}
