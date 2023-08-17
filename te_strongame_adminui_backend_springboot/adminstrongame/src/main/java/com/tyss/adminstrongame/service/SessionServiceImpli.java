package com.tyss.adminstrongame.service;


import java.util.ArrayList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.adminstrongame.dto.SessionCoachDto;
import com.tyss.adminstrongame.dto.SessionDetailsDto;
import com.tyss.adminstrongame.dto.SessionNotificationDetailsDto;
import com.tyss.adminstrongame.entity.CoachForSessionDetails;
import com.tyss.adminstrongame.entity.SessionDetails;
import com.tyss.adminstrongame.repository.CoachForSessionRepository;
import com.tyss.adminstrongame.repository.SessionDetailsRepository;
import com.tyss.adminstrongame.repository.SessionNotificationRepo;

/**
 * This is the service implementation class for SessionService interface.
 * Here you find implementation for saving, updating, fetching and deleting the 
 * Session Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class SessionServiceImpli implements SessionService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private SessionDetailsRepository sessionRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private CoachForSessionRepository coachRepo;

	@Autowired
	private SessionNotificationRepo sessionNotificationRepo;

	/**
	 * This method is implemented to save Session Details
	 * 
	 * @param data
	 * @return SessionDetailsDto
	 */
	@Transactional
	@Override
	public SessionDetailsDto addSessionDetails(SessionDetailsDto data) {
		if (data!= null) {
			data.setValidationMessage("");
			if(data.getSessionDuration()<=0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative or zero for Duration field.");
			}
			if(data.getSlotsAvailable()<0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative for Slots Available field.");
			}
			if(data.getValidationMessage().length()!=0) {
				return data;
			}else {
				// convert dto to entity
				SessionDetails sessionEntity = new SessionDetails();
				BeanUtils.copyProperties(data,sessionEntity);


				CoachForSessionDetails coachForSessionDetails = coachRepo.getCoachById(data.getCoachForSessionId());
				if(coachForSessionDetails==null) {
					data.setValidationMessage("Coach For Session does not exist");
					return data;
				}else {
					sessionEntity.setCoachForSession(coachForSessionDetails);

					sessionRepo.save(sessionEntity);

					BeanUtils.copyProperties(sessionEntity,data);
					return data;
				}
			}

		} else{
			throw new com.tyss.adminstrongame.exception.SessionException(
					"Failed to add new session: session data should not be empty!");
		}

	}// End Of the addSessionDetails

	/**
	 * This method is implemented to get all Session Details
	 * 
	 * @return List<SessionDetailsDto>
	 */
	@Override
	public List<SessionDetailsDto> getAllSessionDetails() {
		List<SessionDetails> list = sessionRepo.getAllSessions();

		List<SessionDetailsDto> dto = new ArrayList<SessionDetailsDto>();
		for (SessionDetails SessionDetails : list) {
			SessionDetailsDto data = new SessionDetailsDto();
			BeanUtils.copyProperties(SessionDetails,data);
			CoachForSessionDetails coachForSessionDetails =SessionDetails.getCoachForSession();
			if(coachForSessionDetails!=null) {
				data.setSessionCoachName(coachForSessionDetails.getCoachFullName());
				data.setCoachForSessionId(coachForSessionDetails.getCoachForSessionId());
				data.setPhoto(coachForSessionDetails.getCoachImage());
			}
			dto.add(data);
		}
		return dto;
	}// End Of the getAllSessionDetails

	/**
	 * This method is implemented to update Session Details
	 * 
	 * @param data
	 * @return SessionDetailsDto
	 */
	@Transactional
	@Override
	public SessionDetailsDto updateSessionDetails(SessionDetailsDto data) {
		if (data!= null) {
			data.setValidationMessage("");
			if(data.getSessionDuration()<=0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative or zero for Duration field.");
			}
			if(data.getSlotsAvailable()<0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative for Slots Available field.");
			}
			if(data.getValidationMessage().length()!=0) {
				return data;
			}else {
				// convert dto to entity
				SessionDetails sessionEntity = new SessionDetails();
				BeanUtils.copyProperties(data,sessionEntity);

				if(!sessionRepo.findById(sessionEntity.getSessionId()).isPresent()) {
					return null;
				}else {
					CoachForSessionDetails coachForSessionDetails = coachRepo.getCoachById(data.getCoachForSessionId());
					if(coachForSessionDetails==null) {
						data.setValidationMessage("Coach For Session does not exist");
						return data;
					}else {
						sessionRepo.updateSession(sessionEntity.getSessionId(), sessionEntity.getSessionLink(), sessionEntity.getSessionType(), 
								sessionEntity.getSessionDate(), sessionEntity.getSessionTime(),
								sessionEntity.getSessionDuration(), sessionEntity.getSlotsAvailable(), data.getCoachForSessionId());

						return data;
					}
				}
			}
		} else{
			throw new com.tyss.adminstrongame.exception.SessionException(
					"Failed to update session: session data should not be empty!");
		}
	}// End Of the updateSessionDetails

	/**
	 * This method is implemented to delete Session Details
	 * 
	 * @param SessionId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteSessionDetails(int sessionId) {
		if (sessionId!= 0) {

			if(!sessionRepo.findById(sessionId).isPresent()) {
				return false;
			}else {
				sessionRepo.deleteById(sessionId);
				return true;
			}

		} else{
			throw new com.tyss.adminstrongame.exception.SessionException (
					"Failed to delete session: session data should not be empty!");
		}
	}// End Of the deleteSessionDetails

	/**
	 * This method is implemented to get all Coaches
	 * 
	 * @return List<SessionCoachDto>
	 */
	@Override
	public List<SessionCoachDto> getAllCoaches() {
		return coachRepo.getAllCoaches();
	}// End Of the getAllCoaches

	@Override
	public List<SessionNotificationDetailsDto> getSessionNotifications() {
		return sessionNotificationRepo.getSessionNotifications();
	}



}// End Of the Class
