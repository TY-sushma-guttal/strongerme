package com.tyss.adminstrongame.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tagline_details")
public class TaglineDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tagline_details_id")
	private int taglineDetailsId;

	@Column(name = "tagline", length = 999)
	private String tagline;

	@Column(name = "image")
	private String image;

	public TaglineDetails() {
		super();
	}

	public TaglineDetails(int taglineDetailsId, String tagline, String image) {
		super();
		this.taglineDetailsId = taglineDetailsId;
		this.tagline = tagline;
		this.image = image;
	}


}
