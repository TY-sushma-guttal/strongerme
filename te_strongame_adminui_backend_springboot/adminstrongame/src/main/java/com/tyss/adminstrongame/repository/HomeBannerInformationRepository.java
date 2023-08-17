package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.HomeBannerInformation;

public interface HomeBannerInformationRepository extends JpaRepository<HomeBannerInformation, Integer> {
	
	@Query("SELECT h FROM HomeBannerInformation h where h.homeBannerId=?1")
	HomeBannerInformation getHomeBannerById(int homeBannerId);
	
}
