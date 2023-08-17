package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.HomeBannerInformation;

public interface HomeBannerInformationRepository extends JpaRepository<HomeBannerInformation, Integer> {

}
