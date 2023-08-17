package com.tyss.strongameapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "home_banner_image")
public class HomeBannerImage implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "home_banner_image_id")
	private int homeBannerImageId;

	@Column(name = "home_banner_image")
	private String homeBannerImage;

	public HomeBannerImage(int homeBannerImageId, String homeBannerImage) {
		super();
		this.homeBannerImageId = homeBannerImageId;
		this.homeBannerImage = homeBannerImage;
	}

	public HomeBannerImage() {
		super();
	}

}
