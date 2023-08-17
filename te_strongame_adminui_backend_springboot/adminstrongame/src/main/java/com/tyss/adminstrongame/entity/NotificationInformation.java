package com.tyss.adminstrongame.entity;

import java.util.ArrayList;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "notification_infomation")
//@NoArgsConstructor
//@AllArgsConstructor
public class NotificationInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notificaton_id")
	private int notificationId;
	
	@Column(name = "notification_description", length = 999)
	private String notificationDescription;
	
	@Column(name = "notification_image")
	private String notificationImage;
	
	@Column(name="notification_type")
	private String notificationType;
		
	@Exclude
    @JsonBackReference(value="user-notification")
	@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},mappedBy = "notificaton")
	private List<UserInformation> notificationUsers;

	public NotificationInformation() {
		super();
	}

	public NotificationInformation(int notificationId, String notificationDescription, String notificationImage,
			String notificationType, List<UserInformation> notificationUsers) {
		super();
		this.notificationId = notificationId;
		this.notificationDescription = notificationDescription;
		this.notificationImage = notificationImage;
		this.notificationType = notificationType;
		this.notificationUsers = notificationUsers;
	}
	
	@PreRemove
	public void makeNotificationNull() {
		for (UserInformation notificationUser : notificationUsers) {
			notificationUser.getNotificaton().remove(this);
		}
	}

}
