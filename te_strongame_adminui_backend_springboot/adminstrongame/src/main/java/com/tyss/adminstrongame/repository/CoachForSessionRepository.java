package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.SessionCoachDto;
import com.tyss.adminstrongame.entity.CoachForSessionDetails;

public interface CoachForSessionRepository extends JpaRepository<CoachForSessionDetails, Integer> {
	
	@Query("SELECT c FROM CoachForSessionDetails c where coachForSessionId=?1")
	CoachForSessionDetails getCoachById(int coachForSessionId);
	
	@Query("select new com.tyss.adminstrongame.dto.SessionCoachDto(c.coachForSessionId,c.coachFullName) FROM CoachForSessionDetails c")
	List<SessionCoachDto> getAllCoaches();
	
	@Query("SELECT c FROM CoachForSessionDetails c where coachEmailId=?1")
	CoachForSessionDetails getCoachByEmail(String coachEmailId);
	
	@Query("SELECT c FROM CoachForSessionDetails c where coachEmailId=?1 and coachForSessionId not in ?2")
	CoachForSessionDetails getCoach(String coachEmailId,int coachForSessionId);

}
