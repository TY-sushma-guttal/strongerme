package com.tyss.adminstrongame.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "session_details")
@Data
public class SessionDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "session_id")
	private int sessionId;

	@Column(name = "session_link")
	private String sessionLink;

	@Column(name = "session_type")
	private String sessionType;

	@Column(name = "session_date")
	private Date sessionDate;

	@Column(name = "session_time")
	private Time sessionTime;

	@Column(name = "session_duration")
	private double sessionDuration;

	@Column(name = "slots_available")
	private int slotsAvailable;

	@Exclude
	@JsonBackReference(value="coach-session")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="coach_for_session_id")
	private CoachForSessionDetails coachForSession;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "userSessions")
	private List<UserInformation> sessionUsers;

	public SessionDetails() {
		super();
	}

	public SessionDetails(int sessionId, String sessionLink, String sessionType, Date sessionDate, Time sessionTime,
			double sessionDuration, int slotsAvailable, CoachForSessionDetails coachForSession,
			List<UserInformation> sessionUsers) {
		super();
		this.sessionId = sessionId;
		this.sessionLink = sessionLink;
		this.sessionType = sessionType;
		this.sessionDate = sessionDate;
		this.sessionTime = sessionTime;
		this.sessionDuration = sessionDuration;
		this.slotsAvailable = slotsAvailable;
		this.coachForSession = coachForSession;
		this.sessionUsers = sessionUsers;
	}

	@PreRemove
	public void makeSessionNull() {

		for (UserInformation userInformation : sessionUsers) {
			userInformation.getUserSessions().remove(this);
		}
	}

}
