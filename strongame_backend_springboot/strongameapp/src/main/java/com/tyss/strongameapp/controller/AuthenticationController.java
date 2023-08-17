package com.tyss.strongameapp.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.LoginDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.entity.AuthRequest;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.service.CustomUserDetailService;
import com.tyss.strongameapp.service.PasswordService;
import com.tyss.strongameapp.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;


/**
 * Authentication Controller class is for registering and authentication
 * 
 * @author Sushma
 *
 */
@Slf4j
@RestController
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"},allowCredentials = "true",allowedHeaders = "*")
public class AuthenticationController {

	@Autowired
	private PasswordService registerService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService userDetailsService;

	@Autowired
	private UserInformationRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;



	@PostMapping("/authenticate/{flag}")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest, @PathVariable int flag)
			throws Exception {
		ResponseDto responseDTO = new ResponseDto();
		if(flag==1) {
			String emailOrNumber= authenticationRequest.getEmail();	
			if(Character.isDigit(emailOrNumber.charAt(0))) {
				long number=Long.parseLong(emailOrNumber);
				UserInformation userEntity=userRepo.getUserByPhone(number);
				if(userEntity!=null) {
					if(bcryptEncoder.matches(authenticationRequest.getPassword(), userEntity.getPassword())) {
						final String token=jwtUtil.generateTokenTroughNumber(emailOrNumber);
						UserInformationDto userDto=new UserInformationDto();
						BeanUtils.copyProperties(userEntity, userDto);
						LoginDto loginDto=new LoginDto();
						loginDto.setToken(token);
						loginDto.setUserDto(userDto);
						log.debug("Log in successfull");
						responseDTO.setError(false);
						responseDTO.setData(loginDto);
					} else {
						log.error("Invalid credentials");
						responseDTO.setError(true);
						responseDTO.setMessage("Invalid credentials");
						return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.UNAUTHORIZED);

					}
				}
				else {
					log.error("Account does not exist");
					responseDTO.setError(true);
					responseDTO.setMessage("Account does not exist");
					return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
				}
			}
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
			final String token = jwtUtil.generateToken(userDetails);
			UserInformation user=userRepo.getuserId(userDetails.getUsername());
			UserInformationDto userDto=new UserInformationDto();
			BeanUtils.copyProperties(user, userDto);
			LoginDto loginDto=new LoginDto();
			loginDto.setToken(token);
			loginDto.setUserDto(userDto);
			log.debug("Logged in successfully");
			responseDTO.setError(false);
			responseDTO.setData(loginDto);
		}
		else if(flag==0) {
			UserInformation userEntity=userRepo.getuserId(authenticationRequest.getEmail());
			if(userEntity!=null) {
				final String token = jwtUtil.generateTokenLogin(userEntity);
				UserInformationDto userDto=new UserInformationDto();
				BeanUtils.copyProperties(userEntity, userDto);
				LoginDto loginDto=new LoginDto();
				loginDto.setToken(token);
				loginDto.setUserDto(userDto);
				log.debug("Logged in successfully");
				responseDTO.setError(false);
				responseDTO.setData(loginDto);
			}else {
				log.error("Account does not exist");
				responseDTO.setError(true);
				responseDTO.setMessage("Account doesnot exist");
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveEmployeeInfo(@RequestBody UserInformationDto user) throws Exception {
		ResponseDto responseDTO = new ResponseDto();
		final String token = jwtUtil.generateTokenRegister(user);
		UserInformationDto userDto= userDetailsService.save(user,token);
		if(userDto==null) {
			log.error("Email/Phone number already exist");
			responseDTO.setError(true);
			responseDTO.setMessage("Email/Phone number already exist");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);

		}
		else if(userDto!=null) {
			if(userDto.getCases().equalsIgnoreCase("")) {
				log.debug("User registered successfully");
				responseDTO.setError(false);
				responseDTO.setData(userDto);

			}else {			
				responseDTO.setError(true);
				responseDTO.setMessage(userDto.getCases());
				return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);

			}
		}
		return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

	}

	@GetMapping("/logout")
	//	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout() {
		// Just for our chosen documentation tool, not actually invoked.
	}

	/**
	 * This method is used to send otp.
	 * @param phoneNumber
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/sendotp/{phoneNumber}")
	public ResponseEntity<ResponseDto> sendOtp(@PathVariable(name="phoneNumber") Long phoneNumber) {
		String otpMessage = registerService.sendOtp(phoneNumber);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (otpMessage == null) {
			log.error("Invalid phone number");
			responseDTO.setError(true);
			responseDTO.setData("Invalid phone number");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("otp sent successfully");
			responseDTO.setError(false);
			responseDTO.setData(otpMessage);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}


	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials", e);
		}
	}

	@GetMapping("/logout/{notificationCount}/{userId}")
	public ResponseEntity<ResponseDto> logOut(@PathVariable(name="notificationCount") int  notificationCount, @PathVariable(name="userId") int userId) {
		int count = userDetailsService.logOut(notificationCount, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (count == 0) {
			log.error("Notification count is zero");
			responseDTO.setError(true);
			responseDTO.setData("Notification count is zero");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.error(""+count);
			responseDTO.setError(false);
			responseDTO.setData(count);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}


	}

}
