package com.tyss.adminstrongame.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "transformation_details")
//@NoArgsConstructor
//@AllArgsConstructor
public class TransformationDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transformation_id")
	private int transformationId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "coach_name")
	private String coachName;
	
	@Column(name = "plan")
	private String plan;
	
	@Column(name = "transformation_details", length = 999)
	private String transformationDetail;

	@Column(name = "transformation_video")
	private String transformationVideo;
	
	@Exclude
    @JsonBackReference(value="coach-transformation")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="coach_id")
	private CoachDetails coach;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value= FetchMode.SUBSELECT)
	@JoinColumn(name = "transformation_id", referencedColumnName = "transformation_id")
	private List<TransformationImage> image;
	
	@Exclude
	@JsonManagedReference(value="trans-translike")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transformationLike")
	private List<TransformationLikeDetails> likeDetails;
	
	@Exclude
	@JsonManagedReference(value="transformation-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerTransformation")
	private List<HomeBannerInformation> transformationHomeBanner;

	public TransformationDetails() {
		super();
	}
		

	public TransformationDetails(int transformationId, String userName, String coachName, String plan,
			String transformationDetail, String transformationVideo, CoachDetails coach,
			List<TransformationImage> image) {
		super();
		this.transformationId = transformationId;
		this.userName = userName;
		this.coachName = coachName;
		this.plan = plan;
		this.transformationDetail = transformationDetail;
		this.transformationVideo = transformationVideo;
		this.coach = coach;
		this.image = image;
	}


	public TransformationDetails(int transformationId, String userName, String coachName, String plan,
			String transformationDetail, String transformationVideo, CoachDetails coach,
			List<TransformationImage> image, List<TransformationLikeDetails> likeDetails,
			List<HomeBannerInformation> transformationHomeBanner) {
		super();
		this.transformationId = transformationId;
		this.userName = userName;
		this.coachName = coachName;
		this.plan = plan;
		this.transformationDetail = transformationDetail;
		this.transformationVideo = transformationVideo;
		this.coach = coach;
		this.image = image;
		this.likeDetails = likeDetails;
		this.transformationHomeBanner = transformationHomeBanner;
	}

}
