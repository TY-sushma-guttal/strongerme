package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.PaymentDetails;

public interface PaymentRepository extends JpaRepository<PaymentDetails, Integer> {

}
