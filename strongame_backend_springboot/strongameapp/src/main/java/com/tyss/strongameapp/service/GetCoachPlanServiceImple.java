package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.PlanInformationDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.PlanInformation;
import com.tyss.strongameapp.repository.CoachDetailsRepo;

/**
 * This is the implementation class to fetch plan list of specified coach.
 * @author Sushma Guttal
 *
 */
@Service
public class GetCoachPlanServiceImple implements GetCoachPlanService {

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private CoachDetailsRepo coachRepo;

	/**
	 * This method is implemented to fetch plan list of specified coach.
	 * @param coachId
	 * @return List<PlanInformationDto>
	 */
	@Override
	public List<PlanInformationDto> getCoachPlan(int coachId) {
		Optional<CoachDetails> coachOptional= coachRepo.findById(coachId);
		if(coachOptional.isPresent()) {
			CoachDetails coachEntity= coachOptional.get();
			List<PlanInformation> planList= coachEntity.getCoachPlans();
			List<PlanInformationDto> planDtoList=new ArrayList<PlanInformationDto>();
			for (PlanInformation planInformation : planList) {
				PlanInformationDto planInformationDto=new PlanInformationDto();
				BeanUtils.copyProperties(planInformation, planInformationDto);
				planDtoList.add(planInformationDto);

			}
			return planDtoList;
		}else return null;
	}//End of get coach plan method.

}//End of GetCoachPlanServiceImple class.
