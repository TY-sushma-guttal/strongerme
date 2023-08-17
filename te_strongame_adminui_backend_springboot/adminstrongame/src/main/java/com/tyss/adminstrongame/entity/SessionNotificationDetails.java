package com.tyss.adminstrongame.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "session_notification")
@Data
public class SessionNotificationDetails implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "session_notification_id")
	private int sessionNotificationId;
	
	@Column(name = "session_notification_description", length = 999)
	private String sessionNotificationDescription;
	
	@Column(name="session_notification_type")
	private String sessionNotificationType;
	
	@Exclude
    @JsonBackReference(value="user-sessionnotification")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserInformation sessionNotificationUser;


	public SessionNotificationDetails() {
		super();
	}
		
	public SessionNotificationDetails(int sessionNotificationId, String sessionNotificationDescription,
			String sessionNotificationType, UserInformation sessionNotificationUser) {
		super();
		this.sessionNotificationId = sessionNotificationId;
		this.sessionNotificationDescription = sessionNotificationDescription;
		this.sessionNotificationType = sessionNotificationType;
		this.sessionNotificationUser = sessionNotificationUser;
	}
	

}
