package com.tyss.adminstrongame.entity;

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
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_information")
//@NoArgsConstructor
//@AllArgsConstructor
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

	@Column(name = "product_description",length = 999)
	private String productDescription;

	@Column(name = "product_features")
	private String productFeatures;

	@Column(name = "product_disclaimer")
	private String productDisclaimer;

	@Column(name = "product_coins")
	private double productCoins;

	@Column(name="product_image")
	private String productImage;
	
	@Column(name="discount")
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
    @JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "product")
	private List<UserInformation> user;
    
    @Exclude
	@JsonManagedReference(value="product-shopbanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shopbannerProduct")
	private List<ShoppingBannerInformation> productShopBanner;
    
    public ProductInformation() {
		super();
	}

    public ProductInformation(int productId, String productName, String productDescription, String productFeatures,
			String productDisclaimer, double productCoins, String productImage, double discount,
			List<OrderInformation> order, List<UserInformation> user,
			List<ShoppingBannerInformation> productShopBanner) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productFeatures = productFeatures;
		this.productDisclaimer = productDisclaimer;
		this.productCoins = productCoins;
		this.productImage = productImage;
		this.discount = discount;
		this.order = order;
		this.user = user;
		this.productShopBanner = productShopBanner;
	}

    public ProductInformation(int productId, String productName, String productDescription, String productFeatures,
			String productDisclaimer, double productCoins, String productImage, double discount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productFeatures = productFeatures;
		this.productDisclaimer = productDisclaimer;
		this.productCoins = productCoins;
		this.productImage = productImage;
		this.discount = discount;
	}
	
	@PreRemove
	public void makeProductNull() {
		for (UserInformation userInformation : user) {
			userInformation.getProduct().remove(this);	
		}	
	}

}
