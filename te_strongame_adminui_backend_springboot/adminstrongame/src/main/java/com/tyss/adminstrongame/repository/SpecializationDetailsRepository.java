package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.SpecializationDetails;

public interface SpecializationDetailsRepository extends JpaRepository<SpecializationDetails, Integer> {
	
	@Query("SELECT s FROM SpecializationDetails s where s.specializationType=?1")
    SpecializationDetails getSpecializationByType(String specializationType);
	
	@Query("SELECT s FROM SpecializationDetails s where s.specializationType=?1 and s.specializationId not in ?2")
    SpecializationDetails getSpecialization(String specializationType,int specializationId);
	
	@Query("SELECT s FROM SpecializationDetails s where s.specializationType in ?1")
    List<SpecializationDetails> getAllSpecializationByType(List<String> specializationNames);
	
	@Query("SELECT s.specializationType FROM SpecializationDetails s")
	List<String> getAllSpecializationType();
  
}
