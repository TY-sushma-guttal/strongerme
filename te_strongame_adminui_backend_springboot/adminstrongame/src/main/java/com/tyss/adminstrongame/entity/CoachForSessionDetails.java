package com.tyss.adminstrongame.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name="coach_for_session")
@Data
public class CoachForSessionDetails implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="coach_for_session_id")
	private int coachForSessionId;
	
	@Column(name="coach_full_name")
	private String coachFullName;
	
	@Column(name="coach_description", length = 999)
	private String coachDescription;
	
	@Column(name="coach_number")
	private long coachNumber;
	
	@Column(name="coach_email_id")
	private String coachEmailId;
	
	@Column(name="coach_certifications")
	private String coachCertifications;
	
	@Column(name="coach_image")
	private String coachImage;
	
	@Exclude
	@JsonManagedReference(value="coach-session")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "coachForSession")
	private List<SessionDetails> sessions;
	
	@Exclude
	@JsonManagedReference
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(
        name = "coach_specialization", 
        joinColumns = { @JoinColumn(name = "coach_for_session_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "specialization_id") }
    )
	private List<SpecializationDetails> specializations;
	
	public CoachForSessionDetails() {
		super();
	}

	public CoachForSessionDetails(int coachForSessionId, String coachFullName, String coachDescription,
			long coachNumber, String coachEmailId, String coachCertifications, String coachImage,
			List<SessionDetails> sessions, List<SpecializationDetails> specializations) {
		super();
		this.coachForSessionId = coachForSessionId;
		this.coachFullName = coachFullName;
		this.coachDescription = coachDescription;
		this.coachNumber = coachNumber;
		this.coachEmailId = coachEmailId;
		this.coachCertifications = coachCertifications;
		this.coachImage = coachImage;
		this.sessions = sessions;
		this.specializations = specializations;
	}	

	
}
