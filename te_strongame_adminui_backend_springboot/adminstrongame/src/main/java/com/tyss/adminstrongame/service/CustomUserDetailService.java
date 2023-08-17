package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tyss.adminstrongame.config.JwtRequestFilter;
import com.tyss.adminstrongame.dto.AdminDto;
import com.tyss.adminstrongame.entity.AdminInformation;
import com.tyss.adminstrongame.entity.CoachForSessionDetails;
import com.tyss.adminstrongame.repository.AdminInformationRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private AdminInformationRepository dao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * This field is used for email validation
	 */
	private static final String emailPattern = "^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(\\.\\w{2,3})+$";


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("Admin's Email Id "+email);	
		AdminInformation adminInfo=dao.findByUsername(email);
		//		logger.info("Employee "+adminInfo);

		if (adminInfo == null) {
			log.error("Admin not found with Email Id: " + email);
			throw new UsernameNotFoundException("Admin not found with Email Id: " + email);
		}
		return new org.springframework.security.core.userdetails.User(adminInfo.getLogoutDate()+","+adminInfo.getEmail(),adminInfo.getPassword(),
				new ArrayList<>());
	}


	public AdminDto save(AdminDto adminDto) {
		AdminDto dto = new AdminDto();
		dto.setValidationMessage("");
		if(!(isEmailValid(adminDto.getEmail()))) {
			dto.setValidationMessage(dto.getValidationMessage()+" Please enter valid Email Id.");
		}
		if(adminDto.getPassword().length()<8) {
			dto.setValidationMessage(dto.getValidationMessage()+" Length of Password should be atleast 8.");
		}
		if(dto.getValidationMessage().length()!=0) {
			return dto;
		}else {
			AdminInformation newAdmin=new AdminInformation();
			BeanUtils.copyProperties(adminDto,newAdmin);
			newAdmin.setPassword(bcryptEncoder.encode(adminDto.getPassword()));

			if(dao.findByUsername(newAdmin.getEmail())!=null) {
				return null;
			}else {
				AdminInformation adminEntity= dao.save(newAdmin);
				BeanUtils.copyProperties(adminEntity,dto);
				return dto;
			}
		}
	}

	public AdminInformation adminLogout(int adminId, int count, int sessionViewCount) {

		AdminInformation adminInfo = dao.findById(adminId);
		adminInfo.setCount(count);
		adminInfo.setSessionViewCount(sessionViewCount);
		return dao.save(adminInfo);

	}
    
	/**
	 * This method is used to validate email
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmailValid(final String email) {
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}// End Of the isEmailValid
	
}// End Of the Class
