package com.tyss.adminstrongame.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "shopping_banner_information")
//@NoArgsConstructor
//@AllArgsConstructor
public class ShoppingBannerInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopping_banner_id")
	private int shoppingBannerId;

	@Column(name = "shopping_banner_description", length = 999)
	private String shoppingBannerDescription;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "shopping_banner_id", referencedColumnName = "shopping_banner_id")
	private List<ShoppingBannerImage> shoppingBannerImage;
	
	@Exclude
    @JsonBackReference(value="product-shopbanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private ProductInformation shopbannerProduct;

	public ShoppingBannerInformation() {
		super();
	}

	public ShoppingBannerInformation(int shoppingBannerId, String shoppingBannerDescription,
			List<ShoppingBannerImage> shoppingBannerImage, ProductInformation shopbannerProduct) {
		super();
		this.shoppingBannerId = shoppingBannerId;
		this.shoppingBannerDescription = shoppingBannerDescription;
		this.shoppingBannerImage = shoppingBannerImage;
		this.shopbannerProduct = shopbannerProduct;
	}

	
}
