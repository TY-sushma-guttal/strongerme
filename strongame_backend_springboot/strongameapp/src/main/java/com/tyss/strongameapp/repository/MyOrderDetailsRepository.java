package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.MyOrderDetails;

public interface MyOrderDetailsRepository extends JpaRepository<MyOrderDetails, Integer> {

	@Query(value="select * from myorder_details where user_id=?1",nativeQuery = true)
	List<MyOrderDetails> getMyOrder(Integer userId);

}
