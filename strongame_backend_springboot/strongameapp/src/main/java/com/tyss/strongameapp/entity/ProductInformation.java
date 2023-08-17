package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "product_information")
public class ProductInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_description", length = 999)
	private String productDescription;

	@Column(name = "product_features")
	private String productFeatures;

	@Column(name = "product_disclaimer")
	private String productDisclaimer;

	@Column(name = "product_coins")
	private double productCoins;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "product")
	private List<UserInformation> user;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String product_image;
	
	@Column(name = "discount")
	private double discount;

	
	@Exclude
	@JsonManagedReference(value="product-order")
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST})
	@JoinTable(
			name = "order_product", 
			joinColumns = { @JoinColumn(name = "product_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "order_id") }
			)
	private List <OrderInformation> order;

	@Exclude
	@JsonManagedReference(value="product-shopbanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shopbannerProduct")
	private List<ShoppingBannerInformation> productShopBanner;
	
	public ProductInformation(int productId, String productName, String productDescription, String productFeatures,
			String productDisclaimer, double productCoins, List<UserInformation> user, String productImages,
			List<OrderInformation> order) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productFeatures = productFeatures;
		this.productDisclaimer = productDisclaimer;
		this.productCoins = productCoins;
		this.user = user;
		this.product_image = productImages;
		this.order = order;
	}
	public ProductInformation() {
	}


}
