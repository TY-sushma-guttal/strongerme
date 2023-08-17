package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tyss.adminstrongame.entity.AdvertisementImage;
@Repository
public interface AdvertisementImageRepository extends JpaRepository<AdvertisementImage, Integer>{
	
	@Query(value = "SELECT * FROM advertisement_image where advertisement_id is null", nativeQuery = true)
	public List<AdvertisementImage> getAdvertisementImage();

}
