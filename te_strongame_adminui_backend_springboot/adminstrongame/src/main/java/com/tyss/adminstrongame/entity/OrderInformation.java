package com.tyss.adminstrongame.entity;

import java.util.ArrayList;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_information")
//@NoArgsConstructor
//@AllArgsConstructor
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
    private List<ProductInformation> orderProducts;
	
	@Exclude
    @JsonBackReference(value="user-order")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserInformation orderUser;
	
	public OrderInformation() {
		super();
	}
		
	
}
