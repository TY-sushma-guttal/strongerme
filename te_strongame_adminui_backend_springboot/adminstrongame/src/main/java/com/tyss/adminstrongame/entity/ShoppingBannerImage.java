package com.tyss.adminstrongame.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "shopping_banner_image")
public class ShoppingBannerImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopping_banner_image_id")
	private int shoppingBannerImageId;

	@Column(name = "shopping_banner_image")
	private String shoppingBannerImage;

	public ShoppingBannerImage() {
		super();
	}

	public ShoppingBannerImage(int shoppingBannerImageId, String shoppingBannerImage) {
		super();
		this.shoppingBannerImageId = shoppingBannerImageId;
		this.shoppingBannerImage = shoppingBannerImage;
	}
	
	
}
