package com.tyss.adminstrongame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.AdminInformation;

public interface AdminInformationRepository extends JpaRepository<AdminInformation, String>{
	@Query("SELECT c FROM AdminInformation c WHERE email=?1")
	AdminInformation findByUsername(String email);
	
	@Query("SELECT c FROM AdminInformation c WHERE adminId=?1")
	AdminInformation findById(int adminId);
}
