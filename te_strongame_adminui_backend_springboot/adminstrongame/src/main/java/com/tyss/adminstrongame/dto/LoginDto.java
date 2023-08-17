package com.tyss.adminstrongame.dto;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import com.tyss.adminstrongame.entity.JwtResponse;

import lombok.Data;

@Data
public class LoginDto {
	
	private int adminId;
	
	private int count;
	
	private int sessionViewCount;
	
	private String jwtToken;
	
	public LoginDto() {
		super();
	}

	public LoginDto(int adminId, int count, int sessionViewCount, String jwtToken) {
		super();
		this.adminId = adminId;
		this.count = count;
		this.sessionViewCount = sessionViewCount;
		this.jwtToken = jwtToken;
	}

}
