package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.CoachForSessionDetails;

public interface CoachForSesssionRepository extends JpaRepository<CoachForSessionDetails, Integer> {
	
	@Query("SELECT c FROM CoachForSessionDetails c where coachForSessionId=?1")
	CoachForSessionDetails getCoachById(int coachForSessionId);

}
