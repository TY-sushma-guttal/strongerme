package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class TaglineDetailsDto {
	
	private int taglineDetailsId;
	
	private String tagline;
	
	private String image;

	public TaglineDetailsDto() {
		super();
	}

	public TaglineDetailsDto(int taglineDetailsId, String tagline, String image) {
		super();
		this.taglineDetailsId = taglineDetailsId;
		this.tagline = tagline;
		this.image = image;
	}


}
