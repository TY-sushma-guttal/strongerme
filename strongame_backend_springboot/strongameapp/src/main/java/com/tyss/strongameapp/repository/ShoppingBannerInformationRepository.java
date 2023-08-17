package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.ShoppingBannerInformation;

public interface ShoppingBannerInformationRepository extends JpaRepository<ShoppingBannerInformation, Integer> {

}
