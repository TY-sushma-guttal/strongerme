package com.tyss.strongameapp.dto;
/*
 * @author gunapal.p
 */
import java.util.List;

import com.tyss.strongameapp.entity.HomeBannerImage;

import lombok.Data;

@Data
public class HomeBannerInformationDto {

	private int homeBannerId;

	private String homeBannerDescription;

	private List<HomeBannerImage> homeBannerImage;
	
	private int id;
	
	private String homeBannerType;

	public HomeBannerInformationDto() {
		super();
	}

}

