package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class AdvertisementImageDto {

	private int advertisementImageId;

	private String advertisementImage;

	public AdvertisementImageDto(int advertisementImageId, String advertisementImage) {
		super();
		this.advertisementImageId = advertisementImageId;
		this.advertisementImage = advertisementImage;
	}

	public AdvertisementImageDto() {
		super();
	}

}
