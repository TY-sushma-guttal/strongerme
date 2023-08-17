package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.AdvertisementInformation;

public interface AdvertisementInformationRepository extends JpaRepository<AdvertisementInformation, Integer>{
	
	@Query(value="Select * from advertisement_information",nativeQuery = true)
	public AdvertisementInformation getAdds();
	
	
}
