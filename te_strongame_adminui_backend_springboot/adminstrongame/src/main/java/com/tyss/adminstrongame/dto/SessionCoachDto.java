package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class SessionCoachDto {

	private int coachForSessionId;

	private String coachFullName;

	public SessionCoachDto() {
		super();
	}

	public SessionCoachDto(int coachForSessionId, String coachFullName) {
		super();
		this.coachForSessionId = coachForSessionId;
		this.coachFullName = coachFullName;
	}


}
