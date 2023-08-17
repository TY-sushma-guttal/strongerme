package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.dto.CoachDetailsDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.ProductInformation;

public interface CoachDetailsRepo extends JpaRepository<CoachDetails, Integer> {
	@Query("SELECT c FROM CoachDetails c where coachId=?1")
	public CoachDetails getCoach(int id);

	@Query("SELECT c FROM CoachDetails c where coachName=?1")
	public CoachDetails getCoachByName(String coachName);

}
