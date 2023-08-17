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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "diet_recipe_details")
//@NoArgsConstructor
//@AllArgsConstructor
public class DietRecipeDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diet_id")
	private int dietId;

	@Column(name = "diet_name")
	private String dietName;

	@Column(name = "calories")
	private double calories;

	@Column(name = "protine")
	private double protine;

	@Column(name = "fat")
	private double fat;

	@Column(name = "carbs")
	private double carbs;

	@Column(name = "diet_details", length = 999)
	private String dietDetails;

	@Column(name = "diet_image")
	private String dietImage;

	@Column(name = "diet_video")
	private String dietVideo;

	@Column(name="ingredients")
	private String ingredients;
	
	@Column(name="steps")
	private long steps;

	@Exclude
	@JsonManagedReference(value="diet-dietlike")
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "dietRecipe")
    private List<DietRecipeLike> likes;
	
	@Exclude
	@JsonManagedReference(value="diet-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerDiet")
	private List<HomeBannerInformation> dietHomeBanner;

	public DietRecipeDetails() {
		super();
	}

	public DietRecipeDetails(int dietId, String dietName, double calories, double protine, double fat, double carbs,
			String dietDetails, String dietImage, String dietVideo, String ingredients, long steps) {
		super();
		this.dietId = dietId;
		this.dietName = dietName;
		this.calories = calories;
		this.protine = protine;
		this.fat = fat;
		this.carbs = carbs;
		this.dietDetails = dietDetails;
		this.dietImage = dietImage;
		this.dietVideo = dietVideo;
		this.ingredients = ingredients;
		this.steps = steps;
	}

	public DietRecipeDetails(int dietId, String dietName, double calories, double protine, double fat, double carbs,
			String dietDetails, String dietImage, String dietVideo, String ingredients, long steps,
			List<DietRecipeLike> likes, List<HomeBannerInformation> dietHomeBanner) {
		super();
		this.dietId = dietId;
		this.dietName = dietName;
		this.calories = calories;
		this.protine = protine;
		this.fat = fat;
		this.carbs = carbs;
		this.dietDetails = dietDetails;
		this.dietImage = dietImage;
		this.dietVideo = dietVideo;
		this.ingredients = ingredients;
		this.steps = steps;
		this.likes = likes;
		this.dietHomeBanner = dietHomeBanner;
	}	

}
