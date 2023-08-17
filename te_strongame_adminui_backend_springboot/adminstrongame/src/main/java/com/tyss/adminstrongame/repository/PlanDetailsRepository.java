package com.tyss.adminstrongame.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.PlanDetails;

public interface PlanDetailsRepository extends JpaRepository<PlanDetails, Integer>{
	
	@Query("select p from PlanDetails p where p.planName=?1 or p.planId=?2")
	List<PlanDetails> getPlan(String name,Integer id);
	
	@Query("select p from PlanDetails p where p.planName=?1")
	List<PlanDetails> getPlanByName(String name);
	
	@Query("select p from PlanDetails p where p.planId=?1")
	PlanDetails getPlanById(int id);
	
//	@Query( value = "select new com.tyss.adminstrongame.entity.PlanDetails(p.planId, p.planName, p.planDetails, p.noOfWeeks, p.planPrice)"
//			+ " from PlanDetails p")
//	List<PlanDetails> getAllPlans();
//	
//	@Modifying
//	@Query( value = "update strongameapp_db.plan_details set no_of_weeks=:noOfWeeks, plan_details=:planDetails, plan_name=:planName, "
//			+ "plan_price=:planPrice where plan_id=:planId;", nativeQuery = true)	
//	void updatePlan(int planId, String planName, String planDetails, double noOfWeeks, double planPrice);

}
