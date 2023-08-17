package com.tyss.adminstrongame.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Data
@Entity
@Table(name = "transformation_image")
public class TransformationImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transformation_image_id")
	private int transformationImageId;
	
	@Column(name = "transformation_image")
	private String transformationImage;

	
	public TransformationImage() {
		super();
	}


	public TransformationImage(int transformationImageId, String transformationImage) {
		super();
		this.transformationImageId = transformationImageId;
		this.transformationImage = transformationImage;
	}
		

}
