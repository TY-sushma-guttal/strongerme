package com.tyss.adminstrongame.repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.SessionDetails;
import com.tyss.adminstrongame.entity.UserInformation;

public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Integer>{
	
	@Modifying
	@Query(value="update session_details set session_date=:sessionDate, session_duration=:sessionDuration, session_link=:sessionLink, session_time=:sessionTime,"
			+ " session_type=:sessionType, slots_available=:slotsAvailable, coach_for_session_id=:coachForSessionId where session_id=:sessionId", nativeQuery = true)
	void updateSession(int sessionId, String sessionLink, String sessionType, Date sessionDate, Time sessionTime, double sessionDuration,
			double slotsAvailable,int coachForSessionId);
	
	@Query("SELECT s FROM SessionDetails s order by s.sessionId desc")
    List<SessionDetails> getAllSessions();

}
