package com.tyss.adminstrongame.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfoDto {

	private int userId;

	private String name;

	private Date dateOFBirth;

	private String email;

	private Integer mobileNo;

	private double height;

	private double weight;

	private String gender;

	private String photo;

	public UserInfoDto(int userId, String name, Date dateOFBirth, String email, Integer mobileNo, double height,
			double weight, String gender, String photo) {
		super();
		this.userId = userId;
		this.name = name;
		this.dateOFBirth = dateOFBirth;
		this.email = email;
		this.mobileNo = mobileNo;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.photo = photo;
	}

	public UserInfoDto() {
		super();
	}



}
