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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "advertisement_image")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class AdvertisementImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertisement_image_id")
	private int advertisementImageId;

	@Column(name = "advertisement_image")
	private String advertisementImage;

	public AdvertisementImage() {
		super();
	}

	public AdvertisementImage(int advertisementImageId, String advertisementImage) {
		super();
		this.advertisementImageId = advertisementImageId;
		this.advertisementImage = advertisementImage;
	}

	
}
