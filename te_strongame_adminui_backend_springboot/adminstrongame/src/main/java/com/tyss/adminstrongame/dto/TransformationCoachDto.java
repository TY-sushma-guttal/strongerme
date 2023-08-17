package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class TransformationCoachDto {
	
	private int coachId;

	private String coachName;

	public TransformationCoachDto(int coachId, String coachName) {
		super();
		this.coachId = coachId;
		this.coachName = coachName;
	}

	public TransformationCoachDto() {
		super();
	}

}
