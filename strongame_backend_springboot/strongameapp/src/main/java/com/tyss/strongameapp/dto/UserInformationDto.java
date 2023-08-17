package com.tyss.strongameapp.dto;

import java.util.Date;
import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.UserStepsStats;

import lombok.Data;

@Data
public class UserInformationDto {

	private static final long serialVersionUID = 8222627122512465779L;
	
	private Double coins;
	
	private int position;
	
	private String otp;
	
	private Date packageExpiryDate;
	
	private int notificationCount;
	
	private int userId;

	private String name;

	private Date dateOFBirth;

	private String email;

	private String password;

	private String confirmPassword;

	private long mobileNo;

	private String referalCode;
	
	private double height;
	
	private double weight;
	
	private String gender;
	
	private String photo;
	
	private String token;
	
	private UserStepsStats steps;
	
	private TransformationDetails trans;
	
	private String cases;
	
	public UserInformationDto() {
		
	}

	public UserInformationDto(Double coins, int position, String otp, Date packageExpiryDate, int notificationCount,
			int userId, String name, Date dateOFBirth, String email, String password, String confirmPassword,
			long mobileNo, String referalCode, double height, double weight, String gender, String photo, String token,
			UserStepsStats steps, TransformationDetails trans) {
		super();
		this.coins = coins;
		this.position = position;
		this.otp = otp;
		this.packageExpiryDate = packageExpiryDate;
		this.notificationCount = notificationCount;
		this.userId = userId;
		this.name = name;
		this.dateOFBirth = dateOFBirth;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.mobileNo = mobileNo;
		this.referalCode = referalCode;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.photo = photo;
		this.token = token;
		this.steps = steps;
		this.trans = trans;
	}

	
}
