package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name ="user_information")
public class UserInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "name")
	private String name;

	@Column(name = "date_of_birth")
	private Date dateOFBirth;

	@Column(name = "mobile_no", unique = true)
	private long mobileNo;

	@Column(name = "email", unique = true)
	@Email
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "confirm_password")
	private String confirmPassword;

	@Column(name = "referal_code")
	private String referalCode;

	@Column(name="weight")
	private double weight;

	@Column(name="height")
	private double height;

	@Column(name="gender")
	private String gender;

	@Column(name="photo")
	private String photo;
	
	@Column(name="package_expiry_date")
	private Date packageExpiryDate;
	
	@Column(name="notification_count")
	private int notificationCount;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="steps_id")
	private UserStepsStats steps;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="reward_id")
	private RewardDetails reward;
	
	@Exclude
	@JsonManagedReference(value="user-order")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST,mappedBy = "orderUser")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<OrderInformation> userOrders;

	@Exclude
	@JsonManagedReference(value="user_product")
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "user_product", 
			joinColumns = { @JoinColumn(name = "user_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "product_id") }
			)
	private List<ProductInformation> product;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="admin_reward_id")
	private AdminRewardDetails adminReward;
	
	@Exclude
	@JsonManagedReference(value="user-transformationlike")
	@JsonIgnore
	@OneToMany(cascade =  {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "transformationLike")
	private List<TransformationLikeDetails> transformationLike;
	
	@Exclude
	@JsonManagedReference(value="user-dietlike")
	@JsonIgnore
	@OneToMany(cascade =  {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "dietLikeUser")
	private List<DietRecipeLike> dietLike;
	
	@Exclude
	@ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(
        name = "user_coach", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "coach_id") }
    )
	private List <CoachDetails> userCoach;
	
	@Exclude
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JsonManagedReference(value="user-notification")
	@JsonIgnore
    @JoinTable(
        name = "user_notification", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "notificaton_id") }
    )
	private List <NotificationInformation> notificaton;
	
	@Exclude
	@JsonManagedReference
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "user_plan", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "plan_id") }
    )
	private List<PlanInformation> userPlan;
	
	@Exclude
	@JsonManagedReference
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "user_package", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "package_id") }
    )
	private List<PackageDetails> userPackages;
	
	@Exclude
	@JsonManagedReference
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "user_session", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "session_id") }
    )
	private List<SessionDetails> userSessions;
	
	@Exclude
	@JsonManagedReference(value="user-sessionnotification")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sessionNotificationUser")
	private List<SessionNotificationDetails> sessionNotifications;
	
	
	@Exclude
	@JsonManagedReference(value="user-myorder")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userMyOrder")
	private List<MyOrderDetails> myorder;

	public UserInformation(int userId, String name, Date dateOFBirth, long mobileNo, @Email String email,
			String password, String confirmPassword, String referalCode, double weight, double height, String gender,
			String photo, UserStepsStats steps, RewardDetails reward, List<OrderInformation> userOrders,
			List<ProductInformation> product, AdminRewardDetails adminReward,
			List<TransformationLikeDetails> transformationLike, List<DietRecipeLike> dietLike,
			List<CoachDetails> userCoach, List<NotificationInformation> notificaton, List<PlanInformation> userPlan) {
		super();
		this.userId = userId;
		this.name = name;
		this.dateOFBirth = dateOFBirth;
		this.mobileNo = mobileNo;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.referalCode = referalCode;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
		this.photo = photo;
		this.steps = steps;
		this.reward = reward;
		this.userOrders = userOrders;
		this.product = product;
		this.adminReward = adminReward;
		this.transformationLike = transformationLike;
		this.dietLike = dietLike;
		this.userCoach = userCoach;
		this.notificaton = notificaton;
		this.userPlan = userPlan;
	}

	public UserInformation() {
		super();
	}

}
