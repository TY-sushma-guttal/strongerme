package com.tyss.adminstrongame.dto;

import java.util.List;

import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.entity.UserInformation;

import lombok.Data;

@Data
public class TransformationLikeDetailsDto {

	private int transformationLikeId;

	private boolean like;
	
	private UserInformation transformationLikeUser;
	
	private TransformationDetails transformationLike;


}
