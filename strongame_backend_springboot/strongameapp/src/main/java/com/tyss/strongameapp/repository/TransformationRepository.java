package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.dto.TransformationDetailsDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.TransformationDetails;

public interface TransformationRepository extends JpaRepository<TransformationDetails, Integer> {

	@Query("SELECT t FROM TransformationDetails t WHERE t.transformationId = ?1")
	public TransformationDetails getTransformation(int transId);

	@Query(value="Select t.coach_id from transformation_details t where t.transformation_id=:transId", nativeQuery = true)
	public int getCoachId(int transId);

	@Query(value="select c.coach_name from coach_information c where c.coach_id=:coachId",nativeQuery = true)
	public String getCoachName(int coachId);

//	@Query("SELECT t.coachId FROM TransformationDetails t WHERE t.transformationId = ?1")
//	public CoachDetails getCoachId(int transId);

	
}
