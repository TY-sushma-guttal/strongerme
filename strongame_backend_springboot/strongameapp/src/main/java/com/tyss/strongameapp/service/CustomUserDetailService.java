package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.entity.AdminRewardDetails;
import com.tyss.strongameapp.entity.RewardDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.entity.UserStepsStats;
import com.tyss.strongameapp.repository.AdminRewardInformationRepository;
import com.tyss.strongameapp.repository.RewardDetailsRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.repository.UserStepsRepository;

/**
 * CustomUserDetailService is used to save and log out the user.
 * @author Sushma Guttal
 *
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private RewardDetailsRepository rewardRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	UserInformationRepository userRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository dao;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private UserStepsRepository stepsRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private AdminRewardInformationRepository adminRewardRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private MyShopService myShopService;

	/**
	 * This method is used to load the user by name.
	 * @param email
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Hi, this is load user by name method");
		UserInformation userInfo=dao.findByUsername(email);
		if (userInfo == null) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
		if(userInfo.getPassword()!=null)
			return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getPassword(),
					new ArrayList<>());
		else
			return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getEmail(),
					new ArrayList<>());

	}//End of load user by name method


	/**
	 * This method is used to save user details after successfull registration.
	 * @param user
	 * @param token
	 * @return UserInformationDto
	 */
	public UserInformationDto save(UserInformationDto user, String token) {
		boolean flag = isValidEmail(user.getEmail());
		long number=user.getMobileNo();
		boolean numberFlag=isValidNumber(number);
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		boolean nameFlag=isValidName(user.getName());
		
		user.setCases("");
		
		if(nameFlag==false) {
			user.setCases(user.getCases()+" Enter valid name.");
		}
		if(flag==false) {
			user.setCases(user.getCases()+" Enter valid email id.");
		}
		if(numberFlag==false) {
			user.setCases(user.getCases()+" Enter valid phone number.");
		}
		if(user.getHeight()<=0) {
			user.setCases(user.getCases()+" Height cannot be zero or negative.");
		}
		if(user.getWeight()<=0) {
			user.setCases(user.getCases()+" Weight cannot be zero or negative.");
		}
		if(user.getGender()!=null) {
			if(user.getGender().equalsIgnoreCase("Female")==false && user.getGender().equalsIgnoreCase("Male")==false) {
				user.setCases(user.getCases()+" Enter valid gender.");	
			}
		}
		if(password!=null) {
			if(password.length()<8 || password.length()>15) {
				user.setCases(user.getCases()+" Password length should be between 8 to 15.");
			}
		}
		if(confirmPassword!=null) {
			if(confirmPassword.length()<8 || confirmPassword.length()>15) {
				user.setCases(user.getCases()+" Confirm Password length should be between 8 to 15.");
			}
			
		}
		
		if(user.getCases().equalsIgnoreCase("")==false) {
			return user;
		}
		
		UserInformation userEntity= userRepo.getuserId(user.getEmail());
		UserInformation userEntityTwo= userRepo.getUserByPhone(user.getMobileNo());
		if(userEntity==null && userEntityTwo==null) {
			UserInformation newUser=new UserInformation();
			newUser.setName(user.getName());
			newUser.setEmail(user.getEmail());
			if(user.getPassword()!=null) {
				newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			}
			if(user.getConfirmPassword()!=null) {
				newUser.setConfirmPassword(bcryptEncoder.encode(user.getConfirmPassword()));
			}
			newUser.setPhoto(user.getPhoto());
			newUser.setDateOFBirth(user.getDateOFBirth());
			newUser.setGender(user.getGender());
			newUser.setHeight(user.getHeight());
			newUser.setWeight(user.getWeight());
			newUser.setMobileNo(user.getMobileNo());
			newUser.setReferalCode(user.getReferalCode());
//			myShopService.sendMail(user.getEmail(), user.getName(), "register");
			RewardDetails reward=new RewardDetails();
			reward.setNoOfSteps(0);
			reward.setRewardCoins(0);
			newUser.setReward(reward);
			AdminRewardDetails adminReward=new AdminRewardDetails();
			adminReward.setAdminRewardCoins(0);
			newUser.setAdminReward(adminReward);
			UserStepsStats userStepsEntity=new UserStepsStats();
			userStepsEntity.setCoinsEarned(0);
			userStepsEntity.setCurrentSteps(0);
			userStepsEntity.setDay(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			newUser.setSteps(userStepsEntity);

			dao.save(newUser);
			rewardRepo.save(reward);
			adminRewardRepo.save(adminReward);
			stepsRepo.save(userStepsEntity);
			user.setToken(token);
			BeanUtils.copyProperties(newUser, user);
			return user;
		}else
			return null;
	}//End of save method.


	/**
	 * This method is implemented to validate name.
	 * @param name
	 * @return boolean
	 */
	public static boolean isValidName(String name) {
		if(name==null) {
			return false;
		}
		String regex = "[a-zA-Z\\s]*$";
        Pattern p = Pattern.compile(regex);
		
		Matcher m = p.matcher(name);
		return m.matches();
	}


	/**
	 * This method is implemented to validate the phone number.
	 * @param number
	 * @return boolean
	 */
	public static boolean isValidNumber(long number) {
		if(number==0) {
			return false;
		}
		String numberString =  number+"";
		if(numberString.length()<10 || numberString.length()>10)
		    return false ;
		else 
			return true;
	}


	/**
	 * This method is used to save notification count after user logs out.
	 * @param notificationCount
	 * @param userId
	 * @return int
	 */
	public int logOut(int notificationCount, int userId) {
		UserInformation userEntity= userRepo.findUserById(userId);
		if(userEntity!=null) {
			userEntity.setNotificationCount(notificationCount);
			userRepo.save(userEntity);
			return notificationCount;
		} else throw new com.tyss.strongameapp.exception.UserException("User information cannot be null");

	}//End of log out method.


	/**
	 * This method is implemented to validate email.
	 * @param email
	 * @return boolean
	 */
	public static boolean isValidEmail(String email) {
		
		if(email==null) {
			return false;
			}
		
		String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+(com)+$";
		Pattern p = Pattern.compile(regex);
		
		String regexTwo = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+(in)+$";
		Pattern pTwo = Pattern.compile(regexTwo);
		
		Matcher m = p.matcher(email);
		Matcher mTwo = pTwo.matcher(email);
		
		return m.matches()||mTwo.matches();
	}
}//End of CustomUserDetailService class.