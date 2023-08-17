package com.tyss.strongameapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "myorder_details")
public class MyOrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "myorder_id")
	private int myOrderId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "image")
	private String image;
	
	@Exclude
    @JsonBackReference(value="user-myorder")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserInformation userMyOrder;

	public MyOrderDetails() {
		super();
	}

	public MyOrderDetails(int myOrderId, String name, double price, String type, String image) {
		super();
		this.myOrderId = myOrderId;
		this.name = name;
		this.price = price;
		this.type = type;
		this.image = image;
	}

	
}
