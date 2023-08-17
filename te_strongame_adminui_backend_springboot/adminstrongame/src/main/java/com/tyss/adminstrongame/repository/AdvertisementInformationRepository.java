package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.entity.AdvertisementInformation;

public interface AdvertisementInformationRepository extends JpaRepository<AdvertisementInformation, Integer>{

}
