package com.tyss.adminstrongame.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name ="transformation_like_details")
//@NoArgsConstructor
//@AllArgsConstructor
public class TransformationLikeDetails {
		
	public TransformationLikeDetails() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transformation_like_id")
	private int transformationLikeId;

	@Column(name="transformation_like")
	private boolean like;
	
	@Exclude
    @JsonBackReference(value="user-transformationlike")
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserInformation transformationLikeUser;
	
	@Exclude
    @JsonBackReference(value="trans-translike")
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="transformation_id")
	private TransformationDetails transformationLike;
	
    
}
