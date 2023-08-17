package com.tyss.adminstrongame.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "payment_details")
//@NoArgsConstructor
//@AllArgsConstructor
public class PaymentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;

	@Column(name = "payment_detail")
	private String paymentDetail;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "amount")
	private double amount;

}
