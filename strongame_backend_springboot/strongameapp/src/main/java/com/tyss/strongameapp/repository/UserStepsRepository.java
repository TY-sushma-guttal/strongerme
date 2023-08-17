package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.UserStepsStats;

public interface UserStepsRepository extends JpaRepository<UserStepsStats, Integer>{


}
