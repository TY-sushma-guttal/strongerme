package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.adminstrongame.dto.PlanDetailsDto;
import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.entity.PlanDetails;
import com.tyss.adminstrongame.entity.UserInformation;
import com.tyss.adminstrongame.repository.CoachDetailsRepo;
import com.tyss.adminstrongame.repository.NotificationInformationRepository;
import com.tyss.adminstrongame.repository.PlanDetailsRepository;
import com.tyss.adminstrongame.repository.UserInformationRepository;

/**
 * This is the service implementation class for PlanService interface.
 * Here you find implementation for saving, updating, fetching and deleting the 
 * Plan Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class PlanServiceImpli implements PlanService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	PlanDetailsRepository planRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	CoachDetailsRepo coachRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	UserInformationRepository userRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	NotificationInformationRepository notificationRepo;

	/**
	 * This method is implemented to save Plan Details
	 * 
	 * @param data
	 * @return PlanDetailsDto
	 */
	@Transactional
	@Override
	public PlanDetailsDto addPlanDetails(PlanDetailsDto data) {
		if (data!= null) {
			data.setValidationMessage("");
			if(data.getNoOfWeeks()<=0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative or zero for Plan Duration(No Of Weeks) field.");
			}
			if(data.getPlanPrice()<0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative for Plan Cost field.");
			}
			if(data.getValidationMessage().length()!=0) {
				return data;
			}else {
				// convert dto to entity
				PlanDetails planEntity = new PlanDetails();
				BeanUtils.copyProperties(data,planEntity);

				PlanDetails planDetails=planRepo.save(planEntity);

				List<CoachDetails> coaches = coachRepo.getCoachByBadge(planEntity.getPlanName());

				if(!coaches.isEmpty()) {
					for (CoachDetails coach : coaches) {
						coach.getCoachPlans().add(planDetails);
					}

					coachRepo.saveAll(coaches);
				}

				BeanUtils.copyProperties(planDetails,data);
				return data;
			}
		} else{
			throw new com.tyss.adminstrongame.exception.PlanException(
					"Failed to add new plan: plan data should not be empty!");
		}

	}// End Of the addPlanDetails

	/**
	 * This method is implemented to get all Plan Details 
	 * 
	 * @return List<PlanDetails>
	 */
	@Override
	public List<PlanDetails> getAllPlanDetails() {
		return planRepo.findAll();		
	}// End Of the getAllPlanDetails

	/**
	 * This method is implemented to update Plan Details
	 * 
	 * @param data
	 * @return PlanDetailsDto
	 */
	@Transactional
	@Override
	public PlanDetailsDto updatePlanDetails(PlanDetailsDto data) {
		if (data!= null) {
			data.setValidationMessage("");
			if(data.getNoOfWeeks()<=0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative or zero for Plan Duration(No Of Weeks) field.");
			}
			if(data.getPlanPrice()<0) {
				data.setValidationMessage(data.getValidationMessage()+" Value cannot be negative for Plan Cost field.");
			}
			if(data.getValidationMessage().length()!=0) {
				return data;
			}else {
				// convert dto to entity
				PlanDetails planEntity = new PlanDetails();
				BeanUtils.copyProperties(data,planEntity);

				PlanDetails entity = planRepo.getPlanById(planEntity.getPlanId());
				String previousPlanName = entity.getPlanName();

				if(entity==null) {
					return null;
				}else {
					List<CoachDetails> coachesDetails = new ArrayList<>();

					if(!planEntity.getPlanName().equalsIgnoreCase(entity.getPlanName())) {
						coachesDetails  = coachRepo.getCoachByBadge(entity.getPlanName());
						for (CoachDetails coachDetails : coachesDetails) {
							coachDetails.getCoachPlans().remove(entity);
						}
					}

					entity.setNoOfWeeks(planEntity.getNoOfWeeks());
					entity.setPlanDetails(planEntity.getPlanDetails());
					entity.setPlanName(planEntity.getPlanName());
					entity.setPlanPrice(planEntity.getPlanPrice());
					PlanDetails planDetails=planRepo.save(entity);

					if(!planEntity.getPlanName().equalsIgnoreCase(previousPlanName)) {
						List<CoachDetails> coaches = coachRepo.getCoachByBadge(planDetails.getPlanName());
						for (CoachDetails coach : coaches) {
							coach.getCoachPlans().add(planDetails);
						}
						coaches.addAll(coachesDetails);
						coachRepo.saveAll(coaches);
					}

					BeanUtils.copyProperties(planDetails,data);
					return data;
				}
			}

		} else{
			throw new com.tyss.adminstrongame.exception.PlanException(
					"Failed to update plan: plan data should not be empty!");
		}
	}// End Of the updatePlanDetails


	/**
	 * This method is implemented to get Plan Details by name or id
	 * 
	 * @param name,id
	 * @return List<PlanDetails>
	 */
	@Override
	public List<PlanDetails> getPlanDetails(String name, Integer id) {
		List<PlanDetails> planEntity=planRepo.getPlan(name, id);
		if(planEntity.isEmpty()) {
			return null;
		}
		return planEntity;
	}// End Of the getPlanDetails

	/**
	 * This method is implemented to delete Plan Details
	 * 
	 * @param planId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deletePlanDetails(int planId) {
		if (planId!= 0) {

			PlanDetails planDetails = planRepo.getPlanById(planId);

			if(planDetails==null) {
				return false;
			}else {

				NotificationInformation notificationInfo = new NotificationInformation();

				String message=planDetails.getPlanName()+" plan of "+planDetails.getNoOfWeeks()+" weeks duration has been deleted by admin";
				notificationInfo.setNotificationDescription(message);
				notificationInfo.setNotificationType("specific");
				notificationInfo = notificationRepo.save(notificationInfo);

				List<UserInformation> users=planDetails.getPlanUsers();
				for (UserInformation user : users) {
					user.getNotificaton().add(notificationInfo);
				}

				userRepo.saveAll(users);
				planRepo.deleteById(planId);
				return true;
			}
		} else{
			throw new com.tyss.adminstrongame.exception.PlanException(
					"Failed to delete plan: plan data should not be empty!");
		}


	}// End Of the deletePlanDetails

}// End Of the Class
