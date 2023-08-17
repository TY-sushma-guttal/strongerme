package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class LeaderBoardDto {
	
	private List<UserInformationDto> userListDto;
	
	private int yourPosition;
	
	public LeaderBoardDto(List<UserInformationDto> userListDto, int yourPosition) {
		super();
		this.userListDto = userListDto;
		this.yourPosition = yourPosition;
	}
	public LeaderBoardDto() {
		super();
	}
}
