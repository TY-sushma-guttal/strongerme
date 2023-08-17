package com.tyss.adminstrongame.dto;

import java.util.List;



import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.HomeBannerInformation;
import com.tyss.adminstrongame.entity.TransformationImage;
import com.tyss.adminstrongame.entity.TransformationLikeDetails;

import lombok.Data;

@Data 
public class TransformationDetailsDto {

	private int transformationId;

	private String transformationDetail;

	private String transformationVideo;

	private String plan;

	private String coachName;

	private String userName;

	private List<TransformationImage> image;

	private int coachId;

	private String photo;
	
	private String validationMessage;
	
	public TransformationDetailsDto() {
		super();
	}

}
