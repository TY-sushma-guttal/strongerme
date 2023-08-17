package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.CoachForSessionDetailsDto;
import com.tyss.adminstrongame.entity.CoachForSessionDetails;
import com.tyss.adminstrongame.entity.SpecializationDetails;
import com.tyss.adminstrongame.repository.CoachForSessionRepository;
import com.tyss.adminstrongame.repository.SpecializationDetailsRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for CoachForSessionService interface.
 * Here you find implementation for saving, updating, fetching and deleting the 
 * CoachForSession
 * 
 * @author Trupthi
 * 
 */
@Service
public class CoachForSessionServiceImpli implements CoachForSessionService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private CoachForSessionRepository coachForSesssionRepo;
	
	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	SSSUploadFile sssUploadFile;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private SpecializationDetailsRepository specializationRepo;

	/**
	 * This field is used for email validation
	 */
	private static final String emailPattern = "^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(\\.\\w{2,3})+$";

	/**
	 * This field is used for phone number validation
	 */
	private static final String numberPattern="^(?:(?:\\+|0{0,2})91(\\s*|[-])?|[0]?)?([6789]\\d{2}([ -]?)\\d{3}([ -]?)\\d{4})$";

	/**
	 * This field is used for name validation
	 */
	private static final String namePattern="^[a-z A-Z]{1,50}$";

	/**
	 * This method is implemented to save CoachForSession Details
	 * 
	 * @param data
	 * @return CoachForSessionDetailsDto
	 */
	@Transactional
	@Override
	public CoachForSessionDetailsDto addCoachForSession(CoachForSessionDetailsDto data, MultipartFile coachImage) {
		if (data!= null) {
			String phoneNumber= String.valueOf(data.getCoachNumber());

			data.setValidationMessage("");
			if(!(isNameValid(data.getCoachFullName()))) {
				data.setValidationMessage(data.getValidationMessage()+" Please enter valid Coach Name.");
			}
			if(!(isNumberValid(phoneNumber))) {
				data.setValidationMessage(data.getValidationMessage()+" Please enter valid Phone Number.");
			}
			if(!(isEmailValid(data.getCoachEmailId()))) {
				data.setValidationMessage(data.getValidationMessage()+" Please enter valid Email Id.");
			}
			if(data.getValidationMessage().length()!=0) {
				return data;
			}else {
				// convert dto to entity
				CoachForSessionDetails coachEntity = new CoachForSessionDetails();
				BeanUtils.copyProperties(data,coachEntity);

				if(coachForSesssionRepo.getCoachByEmail(coachEntity.getCoachEmailId())!=null) {
					return null;
				}else {

					List<SpecializationDetails> specializations = specializationRepo.getAllSpecializationByType(data.getSpecializationNames());
					coachEntity.setSpecializations(specializations);
					CoachForSessionDetails savedCoach = coachForSesssionRepo.save(coachEntity);
					if(!coachImage.isEmpty()) {
						String uploadFilePath = sssUploadFile.uploadFile(coachImage, savedCoach.getCoachForSessionId(), "CoachForSession");
						savedCoach.setCoachImage(uploadFilePath);
					}
					coachForSesssionRepo.save(savedCoach);
					BeanUtils.copyProperties(savedCoach,data);
					return data;
				}
			}

		} else{
			throw new com.tyss.adminstrongame.exception.CoachForSessionException(
					"Failed to add new CoachForSession: CoachForSession data should not be empty!");
		}

	}// End Of the addCoachForSession

	/**
	 * This method is implemented to get all CoachForSession Details
	 * 
	 * @return List<CoachForSessionDetailsDto>
	 */
	@Override
	public List<CoachForSessionDetailsDto> getAllCoachForSession() {
		List<CoachForSessionDetails> coaches= coachForSesssionRepo.findAll();

		List<CoachForSessionDetailsDto> coachDetails = new ArrayList<>();

		for (CoachForSessionDetails coach : coaches) {

			CoachForSessionDetailsDto dto = new CoachForSessionDetailsDto();
			BeanUtils.copyProperties(coach,dto);

			List<SpecializationDetails> specializations = coach.getSpecializations();

			List<String> names= new ArrayList<>();
			for (SpecializationDetails specialization : specializations) {
				names.add(specialization.getSpecializationType());
			}

			dto.setSpecializationNames(names);
			coachDetails.add(dto);
		}

		return coachDetails;
	}// End Of the getAllCoachForSession

	/**
	 * This method is implemented to update CoachForSession Details
	 * 
	 * @param data
	 * @return CoachForSessionDetailsDto
	 */
	@Transactional
	@Override
	public CoachForSessionDetailsDto updateCoachForSession(CoachForSessionDetailsDto data,  MultipartFile coachImage) {
		if (data!= null) {
			String phoneNumber= String.valueOf(data.getCoachNumber());

			data.setValidationMessage("");
			if(!(isNameValid(data.getCoachFullName()))) {
				data.setValidationMessage(data.getValidationMessage()+" Please enter valid Coach Name.");
			}
			if(!(isNumberValid(phoneNumber))) {
				data.setValidationMessage(data.getValidationMessage()+" Please enter valid Phone Number.");
			}
			if(!(isEmailValid(data.getCoachEmailId()))) {
				data.setValidationMessage(data.getValidationMessage()+" Please enter valid Email Id.");
			}
			if(data.getValidationMessage().length()!=0) {
				return data;
			}else {
				// convert dto to entity
				CoachForSessionDetails coachEntity = new CoachForSessionDetails();
				BeanUtils.copyProperties(data,coachEntity);

				CoachForSessionDetails entity =coachForSesssionRepo.getCoachById(coachEntity.getCoachForSessionId());

				if(entity==null) {
					return null;
				}else if(coachForSesssionRepo.getCoach(coachEntity.getCoachEmailId(), coachEntity.getCoachForSessionId())!=null) {
					data.setValidationMessage("Email address already exist");
					return data;
				}else {
					List<SpecializationDetails> specializations = specializationRepo.getAllSpecializationByType(data.getSpecializationNames());
					entity.setSpecializations(specializations);
					entity.setCoachCertifications(data.getCoachCertifications());
					entity.setCoachDescription(data.getCoachDescription());
					entity.setCoachEmailId(data.getCoachEmailId());
					entity.setCoachFullName(data.getCoachFullName());
					if(!coachImage.isEmpty()) {
						String filePath = "CoachForSession/"+entity.getCoachForSessionId();
						sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
						String uploadImagePath = sssUploadFile.uploadFile(coachImage, entity.getCoachForSessionId(),
								"CoachForSession");
						entity.setCoachImage(uploadImagePath);
					}
					entity.setCoachNumber(data.getCoachNumber());			
					coachForSesssionRepo.save(entity);
					return data;
				}
			}
		} else{
			throw new com.tyss.adminstrongame.exception.CoachForSessionException(
					"Failed to update CoachForSession: CoachForSession data should not be empty!");
		}

	}// End Of the updateCoachForSession

	/**
	 * This method is implemented to delete CoachForSession Details
	 * 
	 * @param CoachForSessionId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteCoachForSession(int coachForSessionId) {
		if (coachForSessionId!= 0) {

			if(!coachForSesssionRepo.findById(coachForSessionId).isPresent()) {
				return false;
			}else {
				String filePath = "CoachForSession/"+coachForSessionId;
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				coachForSesssionRepo.deleteById(coachForSessionId);
				return true;
			}

		} else{
			throw new com.tyss.adminstrongame.exception.CoachForSessionException(
					"Failed to delete CoachForSession: CoachForSession data should not be empty!");
		}
	}// End Of the deleteCoachForSession

	/**
	 * This method is implemented to get all Specialization Type
	 * 
	 * @return List<String>
	 */
	@Override
	public List<String> getAllSpecializationType() {
		return specializationRepo.getAllSpecializationType();
	}// End Of the getAllSpecializationType

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

	/**
	 * This method is used to validate phone number
	 * 
	 * @param number
	 * @return boolean
	 */
	public static boolean isNumberValid(final String number) {
		Pattern pattern = Pattern.compile(numberPattern);
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}// End Of the isNumberValid

	/**
	 * This method is used to validate name
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean isNameValid(final String name) {
		Pattern pattern = Pattern.compile(namePattern);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}// End Of the isNameValid

}// End Of the Class
