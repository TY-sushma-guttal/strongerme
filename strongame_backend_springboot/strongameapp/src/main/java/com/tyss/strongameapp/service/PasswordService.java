package com.tyss.strongameapp.service;

import com.tyss.strongameapp.dto.UserInformationDto;

/**
 * PasswordService is implemented by PasswordServiceImple class, used for password management.
 * @author Sushma Guttal
 *
 */
public interface PasswordService {

	/**
	 * This method is implemented by its implementation class to send otp to specified number.
	 * @param phoneNumber
	 * @return String
	 */
	String sendOtp(Long phoneNumber);

	
	/**
	 * This method is implemented by its implementation class to provide forgot password functionality to user.
	 * @param email
	 * @return String
	 */
	String forgotPassword(String email);

	
	/**
	 * This method is implemented by its implementation class to provide change password functionality to user.
	 * @param data
	 * @return UserInformationDto
	 */
	UserInformationDto changePassword(UserInformationDto data);

}//End of PasswordService interface.
