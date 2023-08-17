package com.tyss.strongameapp.entity;

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
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "home_banner_information")
public class HomeBannerInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "home_banner_id")
	private int homeBannerId;

	@Column(name = "home_banner_description", length = 999)
	private String homeBannerDescription;
	
	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "home_banner_id", referencedColumnName = "home_banner_id")
	private List<HomeBannerImage> homeBannerImage;
	
	@Exclude
    @JsonBackReference(value="diet-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="diet_id")
	private DietRecipeDetails homeBannerDiet;
	
	@Exclude
    @JsonBackReference(value="coach-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="coach_id")
	private CoachDetails homeBannerCoach;
	
	@Exclude
    @JsonBackReference(value="transformation-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="transformation_id")
	private TransformationDetails homeBannerTransformation;
	
	@Column(name="home_banner_type")
	private String homeBannerType;

	public HomeBannerInformation() {
		super();
	}

}