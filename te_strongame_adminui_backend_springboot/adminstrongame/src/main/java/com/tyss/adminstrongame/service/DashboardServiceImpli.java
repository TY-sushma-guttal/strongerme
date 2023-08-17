package com.tyss.adminstrongame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.adminstrongame.dto.DashboardDto;
import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.repository.CoachDetailsRepo;
import com.tyss.adminstrongame.repository.DietPlanDetailsRepo;
import com.tyss.adminstrongame.repository.ProductInformationRepository;
import com.tyss.adminstrongame.repository.TransformationRepository;
import com.tyss.adminstrongame.repository.UserInformationRepository;


/**
 * This is the service implementation class for DashboardService interface.
 * Here you find implementation fetching Dashboard Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class DashboardServiceImpli implements DashboardService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	CoachDetailsRepo coachRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	ProductInformationRepository productRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	UserInformationRepository userRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	TransformationRepository transformationRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	DietPlanDetailsRepo dietRepo;

	/**
	 * This method is implemented to get Dashboard Details
	 * 
	 * @return DashboardDto
	 */
	@Override
	public DashboardDto getDashboardDetails() {
		DashboardDto dto = new DashboardDto();

		dto.setNoOfCoaches(coachRepo.count());
		dto.setNoOfProducts(productRepo.count());
		dto.setNoOfUsers(userRepo.count());
		dto.setNoOfTransformation(transformationRepo.count());
		dto.setNoOfRecepies(dietRepo.count());

		return dto;
	}// End Of the getDashboardDetails

}// End Of the Class

