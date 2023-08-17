package com.tyss.strongameapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name = "payment_details")
public class PaymentDetails {
	
	public PaymentDetails() {
		super();
	}

	public PaymentDetails(int paymentId, String paymentDetail, String paymentMode, Date paymentDate, double amount) {
		super();
		this.paymentId = paymentId;
		this.paymentDetail = paymentDetail;
		this.paymentMode = paymentMode;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

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
