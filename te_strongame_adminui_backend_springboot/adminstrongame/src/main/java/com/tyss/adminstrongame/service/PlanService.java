package com.tyss.adminstrongame.service;

import java.util.List;

import com.tyss.adminstrongame.dto.PlanDetailsDto;
import com.tyss.adminstrongame.entity.PlanDetails;

/**
 * This is the service interface for Plans Page . Here you find 
 * abstract methods for saving, updating, fetching and deleting the
 * Plan Details
 * 
 * @author Trupthi
 * 
 */
public interface PlanService {
	
	/**
	 * This method is implemented by its implementation class to save Plan Details
	 * 
	 * @param data
	 * @return PlanDetailsDto
	 */
	PlanDetailsDto addPlanDetails(PlanDetailsDto data);

	/**
	 * This method is implemented by its implementation class to get all Plan Details 
	 * 
	 * @return List<PlanDetails>
	 */
	List<PlanDetails> getAllPlanDetails();

	/**
	 * This method is implemented by its implementation class to update Plan Details
	 * 
	 * @param data
	 * @return PlanDetailsDto
	 */
	PlanDetailsDto updatePlanDetails(PlanDetailsDto data);

	/**
	 * This method is implemented by its implementation class to get Plan Details by name or id
	 * 
	 * @param name,id
	 * @return List<PlanDetails>
	 */
	List<PlanDetails> getPlanDetails(String name,Integer id);

	/**
	 * This method is implemented by its implementation class to delete Plan Details
	 * 
	 * @param planId
	 * @return boolean
	 */
	boolean deletePlanDetails(int planId);

}
