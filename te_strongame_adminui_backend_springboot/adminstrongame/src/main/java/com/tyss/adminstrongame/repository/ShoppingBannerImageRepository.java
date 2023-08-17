package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.ShoppingBannerImage;

public interface ShoppingBannerImageRepository extends JpaRepository<ShoppingBannerImage, Integer>{
	@Query(value = "SELECT * FROM shopping_banner_image where shopping_banner_id is null", nativeQuery = true)
	public List<ShoppingBannerImage> getShoppingBannerImage();
	
}
