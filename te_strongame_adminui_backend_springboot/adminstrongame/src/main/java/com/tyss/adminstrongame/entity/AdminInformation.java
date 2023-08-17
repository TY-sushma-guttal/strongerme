package com.tyss.adminstrongame.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin_information")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class AdminInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5646461742505767488L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adminId")
	private int adminId;
	
	@Column(name = "admin_name")
	private String name;
	
	@Column(name = "admin_email")
	private String email;

	@Column(name = "admin_password")
	private String password;
	
	@Column(name="logout_date")
	private Date logoutDate;
	
	@Column(name="count")
	private int count;
	
	@Column(name="session_view_count")
	private int sessionViewCount;
	
}
