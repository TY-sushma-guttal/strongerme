package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class PackageDetailsDto {
	
	private int packageId;
	
	private String packageName;
	
	private double packageDuration;
	
	private double actualPrice;
	
	private double offerPercentage;
	
	private String packageType;
	
	private String packageIcon;
	
	private List<UserInformation> packageUsers;
	
	private String validationMessage;

	public PackageDetailsDto() {
		super();
	}

	public PackageDetailsDto(int packageId, String packageName, double packageDuration, double actualPrice,
			double offerPercentage, String packageType, String packageIcon) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.packageDuration = packageDuration;
		this.actualPrice = actualPrice;
		this.offerPercentage = offerPercentage;
		this.packageType = packageType;
		this.packageIcon = packageIcon;
	}

	public PackageDetailsDto(int packageId, String packageName, double packageDuration, double actualPrice,
			double offerPercentage, String packageType, String packageIcon, List<UserInformation> packageUsers,
			String validationMessage) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.packageDuration = packageDuration;
		this.actualPrice = actualPrice;
		this.offerPercentage = offerPercentage;
		this.packageType = packageType;
		this.packageIcon = packageIcon;
		this.packageUsers = packageUsers;
		this.validationMessage = validationMessage;
	}

}
