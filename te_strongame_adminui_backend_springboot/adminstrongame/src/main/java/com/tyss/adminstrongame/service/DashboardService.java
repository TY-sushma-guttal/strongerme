package com.tyss.adminstrongame.service;

import com.tyss.adminstrongame.dto.DashboardDto;

/**
 * This is the service interface for Dashboard Page . Here you find 
 * abstract methods for fetching the Dashboard Details.
 * 
 * @author Trupthi
 * 
 */
public interface DashboardService {
	
	/**
	 * This method is implemented by its implementation class to get Dashboard Details
	 * 
	 * @return DashboardDto
	 */
	DashboardDto getDashboardDetails();

}
