package com.tyss.strongameapp.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "coach_information")
public class CoachDetails implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coach_id")
	private int coachId;

	@NotNull
	@Column(name = "coach_name")
	private String coachName;

	public CoachDetails() {
		super();
	}

	private String certifications;

	@Column(name = "coach_details", length = 999)
	private String coachDetails;

	@NotNull
	@Column(name = "phone_number")
	private long phoneNumber;

	@NotNull
	@Column(name="email_id")
	private String emailId;

	@NotNull
	private String badge;

	@Column(name="experience")
	private int experience;

	@Column(name="specialization")
	private String specialization;

	@Exclude
	@JsonManagedReference(value = "coach-plan")
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "coach_plan", 
			joinColumns = { @JoinColumn(name = "coach_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "plan_id") }
			)
	private List<PlanInformation> coachPlans;

	@Exclude
	@JsonManagedReference(value="coach-transformation")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
	private List<TransformationDetails> transformations;

	@Column(name="photo")
	private String photo;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "userCoach")
	private List<UserInformation> users;
	
	@Exclude
	@JsonManagedReference(value="coach-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerCoach")
	private List<HomeBannerInformation> coachHomeBanner;

	public CoachDetails(int coachId, @NotNull String coachName, String certifications, String coachDetail,
			@NotNull long phoneNumber, @NotNull String emailId, @NotNull String badge, int experience,
			int noOfUserTrained, String specialization, List<PlanInformation> plans,
			List<TransformationDetails> transformations, @NotNull String photo) {
		super();
		this.coachId = coachId;
		this.coachName = coachName;
		this.certifications = certifications;
		this.coachDetails = coachDetail;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.badge = badge;
		this.experience = experience;
		this.specialization = specialization;
		this.coachPlans = plans;
		this.transformations = transformations;
		this.photo = photo;
	}

}
