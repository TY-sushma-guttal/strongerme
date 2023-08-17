package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.HomeBannerImage;

public interface HomeBannerImageRepository extends JpaRepository<HomeBannerImage, Integer>{
	@Query(value = "SELECT * FROM home_banner_image where home_banner_id is null", nativeQuery = true)
	public List<HomeBannerImage> getHomeBannerImage();

}
