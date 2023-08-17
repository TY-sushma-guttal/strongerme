package com.tyss.adminstrongame.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import com.tyss.adminstrongame.entity.AdminRewardDetails;
import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.DietRecipeLike;
import com.tyss.adminstrongame.entity.MyOrderDetails;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.entity.OrderInformation;
import com.tyss.adminstrongame.entity.PackageDetails;
import com.tyss.adminstrongame.entity.PlanDetails;
import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.entity.RewardDetails;
import com.tyss.adminstrongame.entity.SessionDetails;
import com.tyss.adminstrongame.entity.SessionNotificationDetails;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.entity.TransformationLikeDetails;
import com.tyss.adminstrongame.entity.UserStepsStats;

import lombok.Data;

@Data
public class UserInformationDto implements Serializable {


	private static final long serialVersionUID = 8222627122512465779L;

	private int userId;

	private String name;

	private Date dateOFBirth;

	private String email;

	private String password;

	private String confirmPassword;

	private long mobileNo;

	private String referalCode;

	private double height;

	private double weight;

	private String gender;

	private String photo;

	private Date packageExpiryDate;
	
	private int notificationCount;

	private UserStepsStats steps;

	private TransformationDetails trans;

	private List<OrderInformation> userOrders;

	private List<ProductInformation> product;

	private List <PlanDetails> userPlan;

	private List<CoachDetails> userCoach;

	private RewardDetails reward;

	private AdminRewardDetails adminReward;

	private List<NotificationInformation> notification;

	private List<DietRecipeLike> dietLike;

	private List<TransformationLikeDetails> transformationLike;

	private double userRewards;

	private int userSteps;

	private List<String> userCoachNames;

	private List<String> userPlanNames;

	private List<SessionDetails> userSessions;

	private List<PackageDetails> userPackages;
	
	private List<SessionNotificationDetails> sessionNotifications;
	
	private List<MyOrderDetails> myorder;

	public UserInformationDto() {

	}

	public UserInformationDto(int userId, String name, Date dateOFBirth, String email, String password,
			String confirmPassword, long mobileNo, String referalCode, double height, double weight, String gender,
			String photo, Date packageExpiryDate, int notificationCount, UserStepsStats steps,
			TransformationDetails trans, List<OrderInformation> userOrders, List<ProductInformation> product,
			List<PlanDetails> userPlan, List<CoachDetails> userCoach, RewardDetails reward,
			AdminRewardDetails adminReward, List<NotificationInformation> notification, List<DietRecipeLike> dietLike,
			List<TransformationLikeDetails> transformationLike, double userRewards, int userSteps,
			List<String> userCoachNames, List<String> userPlanNames, List<SessionDetails> userSessions,
			List<PackageDetails> userPackages, List<SessionNotificationDetails> sessionNotifications,
			List<MyOrderDetails> myorder) {
		super();
		this.userId = userId;
		this.name = name;
		this.dateOFBirth = dateOFBirth;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.mobileNo = mobileNo;
		this.referalCode = referalCode;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.photo = photo;
		this.packageExpiryDate = packageExpiryDate;
		this.notificationCount = notificationCount;
		this.steps = steps;
		this.trans = trans;
		this.userOrders = userOrders;
		this.product = product;
		this.userPlan = userPlan;
		this.userCoach = userCoach;
		this.reward = reward;
		this.adminReward = adminReward;
		this.notification = notification;
		this.dietLike = dietLike;
		this.transformationLike = transformationLike;
		this.userRewards = userRewards;
		this.userSteps = userSteps;
		this.userCoachNames = userCoachNames;
		this.userPlanNames = userPlanNames;
		this.userSessions = userSessions;
		this.userPackages = userPackages;
		this.sessionNotifications = sessionNotifications;
		this.myorder = myorder;
	}
	
}
