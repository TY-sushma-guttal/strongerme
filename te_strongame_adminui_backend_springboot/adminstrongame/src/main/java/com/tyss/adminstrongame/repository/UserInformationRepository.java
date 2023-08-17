package com.tyss.adminstrongame.repository;

import java.sql.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.AdminRewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardDetailsDto;
import com.tyss.adminstrongame.dto.RewardInformationDto;
import com.tyss.adminstrongame.entity.UserInformation;


public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

	
//	@Query("SELECT new com.tyss.strongameapp.dto.UserInfoDto(u.userId,u.name,u.dateOFBirth,u.email,u.mobileNo,u.height,u.weight,u.gender,u.photo) FROM UserInformation u")
//	public List<UserInfoDto> getUserDetails();

	@Query("SELECT u FROM UserInformation u WHERE email=?1 OR name=?2 OR mobileNo=?3")
	public List<UserInformation> fetchUser(String email,String name,Long mobileNo);

	@Query("SELECT u FROM UserInformation u where email=?1")
	public UserInformation fetchUserById(String email);
	
	@Query("SELECT u FROM UserInformation u where mobileNo=?1")
	public List<UserInformation> fetchUserByNumber(long mobileNo);

	@Query("select new com.tyss.adminstrongame.dto.RewardInformationDto(u.name,u.email, u.mobileNo, a.adminRewardCoins, u.photo) from UserInformation u join u.adminReward a")
	List<RewardInformationDto> getAllRewards();
	
	@Query("select new com.tyss.adminstrongame.dto.RewardInformationDto(u.name,u.email, u.mobileNo, a.adminRewardCoins, u.photo) from UserInformation u join u.adminReward a on u.mobileNo=?1 and u.email=?2")
	List<RewardInformationDto> getReward(Long mobileNo,String email);
	
	@Query("select new com.tyss.adminstrongame.dto.AdminRewardDetailsDto(a.adminRewardId, a.adminRewardCoins) from UserInformation u join u.adminReward a on u.mobileNo=?1 and u.email=?2")
	AdminRewardDetailsDto getRewardDetails(Long mobileNo,String email);
	
	@Modifying
	@Query( value = "update user_information set admin_reward_id=null where admin_reward_id=:id", 
			  nativeQuery = true)
	void updateReward(int id);
		
//	@Query("select u.userId from RewardDetails r join r.user u on u.name=?1 and u.mobileNo=?2 and u.email=?3")
//	int getUserId(String name,Long mobileNo,String email);	
	
	@Modifying
	@Query( value = "update user_information set  date_of_birth=:dateOFBirth, email=:email,"
			+ " gender=:gender, height=:height, mobile_no=:mobileNo, name=:name"
			+ ", photo=:photo, referal_code=:referalCode, weight=:weight where user_id=:userId", 
			  nativeQuery = true)
	void updateUser(int userId,  String name, Date dateOFBirth, long mobileNo, String email,
			 String referalCode, double weight, double height,String gender, String photo);
}
