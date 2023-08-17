package com.tyss.adminstrongame.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AdminDto {

	private int adminId;

	private String name;

	private String email;

	private String password;
	
	private Date logoutDate;
	
	private int count;
	
	private int sessionViewCount;
	
	private String validationMessage;

}
