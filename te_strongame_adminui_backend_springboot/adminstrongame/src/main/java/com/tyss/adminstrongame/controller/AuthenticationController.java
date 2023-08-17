
package com.tyss.adminstrongame.controller;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.adminstrongame.dto.AdminDto;
import com.tyss.adminstrongame.dto.LoginDto;
import com.tyss.adminstrongame.dto.ResponseDto;
import com.tyss.adminstrongame.entity.AdminInformation;
import com.tyss.adminstrongame.entity.AdvertisementInformation;
import com.tyss.adminstrongame.entity.AuthRequest;
import com.tyss.adminstrongame.entity.JwtResponse;
import com.tyss.adminstrongame.repository.AdminInformationRepository;
import com.tyss.adminstrongame.service.CustomUserDetailService;
import com.tyss.adminstrongame.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.core.context.SecurityContextHolder; 


/**
 * Authentication Controller class is for registering and authentication
 * 
 * @author Rahul
 *
 */
@Slf4j
@CrossOrigin(origins = "*" )
@RestController
public class AuthenticationController {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService userDetailsService;

	@Autowired
	private AdminInformationRepository adminRepo;


	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtUtil.generateToken(userDetails);

		//		JwtResponse jwtResponse = new JwtResponse(token);

		AdminInformation adminInfo=adminRepo.findByUsername(authenticationRequest.getEmail());

		LoginDto loginDto = new LoginDto(adminInfo.getAdminId(),adminInfo.getCount(),adminInfo.getSessionViewCount(), token);

		return ResponseEntity.ok(loginDto);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveEmployeeInfo(@RequestBody AdminDto admin) {
		AdminDto dto=userDetailsService.save(admin);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Email Id already exists");
			responseDTO.setError(true);
			responseDTO.setData("Email Id already exists");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			if(dto.getValidationMessage().length()!=0) {
				log.error(dto.getValidationMessage());
				responseDTO.setError(true);
				responseDTO.setData(dto.getValidationMessage());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
			}else {
				responseDTO.setError(false);
				log.error("Registered successfully");
				responseDTO.setData("Registered successfully");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
			}
		}
	}

	@Transactional
	@RequestMapping(value="/adminlogout", method=RequestMethod.GET)  
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;

		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		if (auth != null){      
			new SecurityContextLogoutHandler().logout(request, response, auth); 
		} 
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);

		}
		String dateName = jwtUtil.getUsernameFromToken(jwtToken);
		String[] name=dateName.split("[,]", 0);
		AdminInformation adminInfo=adminRepo.findByUsername(name[1]);
		adminInfo.setLogoutDate(new Date(System.currentTimeMillis()));
		adminRepo.save(adminInfo);
		return "successfull"; 
	}  

	@GetMapping("/logout/{adminId}/{count}/{sessionViewCount}")
	public ResponseEntity<ResponseDto> adminLogout(@PathVariable int adminId, @PathVariable int count,@PathVariable int sessionViewCount ) {

		AdminInformation dto = userDetailsService.adminLogout(adminId, count, sessionViewCount);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Admin does not exist");
			responseDTO.setError(true);
			responseDTO.setData("Admin does not exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.error("logout successfull");
			responseDTO.setError(false);
			responseDTO.setData("logout successfull");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}

	}// End Of the getAdvertisementInformationById


	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			//			PropertyConfigurator.configure("log4j.properties");
			log.error("INVALID_CREDENTIALS");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
