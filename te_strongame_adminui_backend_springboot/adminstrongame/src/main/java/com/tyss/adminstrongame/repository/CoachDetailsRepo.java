package com.tyss.adminstrongame.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.dto.HomeDropDownDto;
import com.tyss.adminstrongame.dto.TransformationCoachDto;

public interface CoachDetailsRepo extends JpaRepository<CoachDetails, Integer> {
	
	@Query("SELECT c FROM CoachDetails c WHERE emailId=?1 OR coachName=?2 OR phoneNumber=?3")
	public List<CoachDetails> fetchCoach(String emailId,String coachName,Long phoneNumber);
	
	@Query("SELECT c FROM CoachDetails c where emailId=?1")
	public List<CoachDetails> fetchCoachById(String emailId);
	
	@Query("SELECT c FROM CoachDetails c where coachId=?1")
	public CoachDetails getCoachById(int coachId);
	
	@Query("SELECT c FROM CoachDetails c where badge=?1")
    List<CoachDetails> getCoachByBadge(String badge);
	
	@Query("select new com.tyss.adminstrongame.entity.CoachDetails(c.coachId,c.coachName,c.certifications,c.coachDetails,c.phoneNumber,"
			+ "c.emailId,c.badge,c.experience,c.specialization,c.photo) from CoachDetails c")
	List<CoachDetails> getAllCoaches();
	
	@Modifying
	@Query( value = "update coach_information set badge=:badge, certifications=:certifications, coach_details=:coachDetails,"
			+ " coach_name=:coachName, email_id=:emailId, experience=:experience, phone_number=:phoneNumber, photo=:photo, specialization=:specialization"
			+ " where coach_id=:coachId", nativeQuery = true)		
	void updateCoaches(int coachId,  String coachName, String certifications, String coachDetails,
			 long phoneNumber,  String emailId, String badge, int experience,
			String specialization, String photo);
	
	@Modifying
	@Query( value = "update coach_information JOIN transformation_details ON coach_information.coach_id= transformation_details.coach_id "
			+ " set badge=:badge, certifications=:certifications, coach_details=:coachDetails, coach_information.coach_name=:coachName, email_id=:emailId,"
			+ " experience=:experience, phone_number=:phoneNumber, photo=:photo, specialization=:specialization, transformation_details.coach_name=:coachName"
			+ " where coach_information.coach_id=:coachId;", nativeQuery = true)		
	void updateCoachesAndTransformation(int coachId,  String coachName, String certifications, String coachDetails,
			 long phoneNumber,  String emailId, String badge, int experience,
			String specialization, String photo);
	
	@Query("select new com.tyss.adminstrongame.dto.TransformationCoachDto(c.coachId,c.coachName) from CoachDetails c")
	List<TransformationCoachDto> getAllCoachNames();
	
	@Query("select new com.tyss.adminstrongame.dto.HomeDropDownDto(c.coachId,c.coachName) from CoachDetails c")
	List<HomeDropDownDto> getHomeCoachNames();
}
