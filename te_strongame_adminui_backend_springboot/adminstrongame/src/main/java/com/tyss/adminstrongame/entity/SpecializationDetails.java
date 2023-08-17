package com.tyss.adminstrongame.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name="specialization_details")
@Data
public class SpecializationDetails implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="specialization_id")
	private int specializationId;
	
	@Column(name="specialization_type")
	private String specializationType;
	
	@Column(name="specialization_description", length = 999)
	private String specializationDescription;
	
	@Column(name="specialization_image")
	private String specializationImage;
	
	@Column(name="specialization_icon")
	private String specializationIcon;
	
	@Exclude
    @JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "specializations")
	private List<CoachForSessionDetails> specializationCoaches;
	
	public SpecializationDetails() {
		super();
	}


	public SpecializationDetails(int specializationId, String specializationType, String specializationDescription,
			String specializationImage, String specializationIcon, List<CoachForSessionDetails> specializationCoaches) {
		super();
		this.specializationId = specializationId;
		this.specializationType = specializationType;
		this.specializationDescription = specializationDescription;
		this.specializationImage = specializationImage;
		this.specializationIcon = specializationIcon;
		this.specializationCoaches = specializationCoaches;
	}

	@PreRemove
	public void makeSpecializationNull() {
		
		for (CoachForSessionDetails coachForSessionDetails : specializationCoaches) {
			coachForSessionDetails.getSpecializations().remove(this);
		}
		
	}

}
