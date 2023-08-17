package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.PackageDetailsDto;

import com.tyss.adminstrongame.entity.PackageDetails;

public interface PackageDetailsRepository extends JpaRepository<PackageDetails, Integer>{

	@Query("select new com.tyss.adminstrongame.dto.PackageDetailsDto(p.packageId, p.packageName, p.packageDuration, p.actualPrice,"
			+ "p.offerPercentage, p.packageType, p.packageIcon) from PackageDetails p")
	List<PackageDetailsDto> getAllPackages();
	
	@Modifying
	@Query( value = "update package_details set actual_price=:actualPrice, offer_percentage=:offerPercentage, package_duration=:packageDuration,"
			+ " package_name=:packageName, package_type=:packageType, package_icon=:packageIcon where  package_id=:packageId", nativeQuery = true)
	void updatePackage(int packageId, String packageName, double packageDuration, double actualPrice,
			double offerPercentage, String packageType, String packageIcon);
}
