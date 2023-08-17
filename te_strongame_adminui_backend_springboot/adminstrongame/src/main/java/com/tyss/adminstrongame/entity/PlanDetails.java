package com.tyss.adminstrongame.entity;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "plan_details")
//@NoArgsConstructor
//@AllArgsConstructor
public class PlanDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private int planId;

	@Column(name = "plan_name")
	private String planName;
	
	@Column(name = "plan_details",length = 999)
	private String planDetails;
	
	@Column(name="no_of_weeks")
	private double noOfWeeks;
	
	@NotNull
	@Column(name = "plan_price")
	private double planPrice;
	
	@Exclude
    @JsonBackReference(value= "coach-plan")
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "coachPlans")
    private List<CoachDetails> planCoaches;
	
	@Exclude
    @JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "userPlan")
	private List<UserInformation> planUsers;

	public PlanDetails() {
		super();
	}
	
	@PreRemove
	public void makePlanNull() {
		
		for (UserInformation planUser : planUsers) {
			planUser.getUserPlan().remove(this)	;
		}
		
		for (CoachDetails planCoach : planCoaches) {
			planCoach.getCoachPlans().remove(this);
		}
	}
	
	
}
