package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.CoachDetailsDto;
import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.NotificationInformation;
import com.tyss.adminstrongame.entity.PlanDetails;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.entity.UserInformation;
import com.tyss.adminstrongame.repository.CoachDetailsRepo;
import com.tyss.adminstrongame.repository.NotificationInformationRepository;
import com.tyss.adminstrongame.repository.PlanDetailsRepository;
import com.tyss.adminstrongame.repository.TransformationRepository;
import com.tyss.adminstrongame.repository.UserInformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the service implementation class for CoachService interface. Here you
 * find implementation for saving, updating, fetching and deleting the Coach
 * Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class CoachServiceImpli implements CoachService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	CoachDetailsRepo coachRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	TransformationRepository transformationRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	PlanDetailsRepository planRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	NotificationInformationRepository notificationRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	UserInformationRepository userRepo;

	@Autowired
	SSSUploadFile sssUploadFile;

	/**
	 * This field is used for email validation
	 */
	private static final String emailPattern = "^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(\\.\\w{2,3})+$";

	/**
	 * This field is used for phone number validation
	 */
	private static final String numberPattern = "^(?:(?:\\+|0{0,2})91(\\s*|[-])?|[0]?)?([6789]\\d{2}([ -]?)\\d{3}([ -]?)\\d{4})$";

	/**
	 * This field is used for name validation
	 */
	private static final String namePattern = "^[a-z A-Z]{1,50}$";

	/**
	 * This method is implemented to get all Coach Details
	 * 
	 * @param data
	 * @return List<CoachDetailsDto>
	 */
	@Override
	public List<CoachDetailsDto> getAllCoachDetails() {
		List<CoachDetails> list = coachRepo.getAllCoaches();

		List<CoachDetailsDto> dto = new ArrayList<CoachDetailsDto>();
		for (CoachDetails coachDetails : list) {
			CoachDetailsDto data = new CoachDetailsDto();

			BeanUtils.copyProperties(coachDetails, data);
			data.setCoachTransformations(transformationRepo.countById(coachDetails.getCoachId()));
			dto.add(data);
		}
		return dto;
	}// End Of the getAllCoachDetails

	/**
	 * This method is implemented to save Coach Details
	 * 
	 * @param data
	 * @return CoachDetailsDto
	 */
	@Transactional
	@Override
	public CoachDetailsDto addCoachDetails(CoachDetailsDto data, MultipartFile coachImage) {
		if (data != null) {
			String phoneNumber = String.valueOf(data.getPhoneNumber());

			data.setValidationMessage("");
			if (!(isNameValid(data.getCoachName()))) {
				data.setValidationMessage(data.getValidationMessage() + " Please enter valid Coach Name.");
			}
			if (!(isNumberValid(phoneNumber))) {
				data.setValidationMessage(data.getValidationMessage() + " Please enter valid Phone Number.");
			}
			if (!(isEmailValid(data.getEmailId()))) {
				data.setValidationMessage(data.getValidationMessage() + " Please enter valid Email Id.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				CoachDetails coachEntity = new CoachDetails();
				BeanUtils.copyProperties(data, coachEntity);

				if (coachRepo.fetchCoachById(coachEntity.getEmailId()).isEmpty()) {
					List<PlanDetails> plans = planRepo.getPlanByName(coachEntity.getBadge());
					coachEntity.setCoachPlans(plans);
					CoachDetails saveCoach = coachRepo.save(coachEntity);
					if (!coachImage.isEmpty()) {
						String uploadFilePath = sssUploadFile.uploadFile(coachImage, saveCoach.getCoachId(), "Coach");
						coachEntity.setPhoto(uploadFilePath);
					}
					CoachDetails coachImageSaved = coachRepo.save(coachEntity);
					BeanUtils.copyProperties(coachImageSaved, data);
					return data;
				} else {
					return null;
				}
			}

		} else {
			throw new com.tyss.adminstrongame.exception.CoachException(
					"Failed to add new coach: coach data should not be empty!");
		}
	}// End Of the addCoachDetails

	/**
	 * This method is implemented to get Coach Details
	 * 
	 * @param email,name,mobileNo
	 * @return List<CoachDetailsDto>
	 */
	@Override
	public List<CoachDetailsDto> getCoachDetails(String email, String name, Long mobileNo) {
		List<CoachDetails> list = coachRepo.fetchCoach(email, name, mobileNo);

		List<CoachDetailsDto> dto = new ArrayList<CoachDetailsDto>();
		for (CoachDetails coachDetails : list) {
			CoachDetailsDto data = new CoachDetailsDto();
			BeanUtils.copyProperties(coachDetails, data);
			data.setCoachTransformations(transformationRepo.countById(coachDetails.getCoachId()));
			dto.add(data);
		}
		return dto;
	}// End Of the getCoachDetails

	/**
	 * This method is implemented to update Coach Details
	 * 
	 * @param data
	 * @return CoachDetailsDto
	 */
	@Transactional
	@Override
	public CoachDetailsDto updateCoachDetails(CoachDetailsDto data, MultipartFile coachImage) {
		if (data != null) {
			String phoneNumber = String.valueOf(data.getPhoneNumber());

			data.setValidationMessage("");
			if (!(isNameValid(data.getCoachName()))) {
				data.setValidationMessage(data.getValidationMessage() + " Please enter valid Coach Name.");
			}
			if (!(isNumberValid(phoneNumber))) {
				data.setValidationMessage(data.getValidationMessage() + " Please enter valid Phone Number.");
			}
			if (!(isEmailValid(data.getEmailId()))) {
				data.setValidationMessage(data.getValidationMessage() + " Please enter valid Email Id.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				CoachDetails coachEntity = new CoachDetails();
				BeanUtils.copyProperties(data, coachEntity);

				CoachDetails entity = coachRepo.getCoachById(coachEntity.getCoachId());

				if (entity == null) {
					return null;
				} else {
					List<PlanDetails> plans = planRepo.getPlanByName(coachEntity.getBadge());
					entity.setCoachPlans(plans);
					entity.setBadge(coachEntity.getBadge());
					entity.setCertifications(coachEntity.getCertifications());
					entity.setCoachDetails(coachEntity.getCoachDetails());
					entity.setCoachName(coachEntity.getCoachName());
					entity.setEmailId(coachEntity.getEmailId());
					entity.setExperience(coachEntity.getExperience());
					entity.setPhoneNumber(coachEntity.getPhoneNumber());
					entity.setPhoto(coachEntity.getPhoto());
					entity.setSpecialization(coachEntity.getSpecialization());
					List<TransformationDetails> transformations = entity.getTransformations();

					for (TransformationDetails transformation : transformations) {
						transformation.setCoachName(coachEntity.getCoachName());
					}

					entity.setTransformations(transformations);

					if(!coachImage.isEmpty()) {
						String filePath = "Coach/"+coachEntity.getCoachId();
						sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
						String uploadFilePath = sssUploadFile.uploadFile(coachImage, coachEntity.getCoachId(), "Coach");
						entity.setPhoto(uploadFilePath);
					}
					coachRepo.save(entity);
					BeanUtils.copyProperties(entity, data);
					return data;
				}
			}
		} else {
			throw new com.tyss.adminstrongame.exception.CoachException(
					"Failed to update coach: coach data should not be empty!");
		}
	}// End Of the updateCoachDetails

	/**
	 * This method is implemented to delete Coach Details
	 * 
	 * @param coachId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteCoachDetails(int coachId) {
		if (coachId != 0) {

			CoachDetails coachDetails = coachRepo.getCoachById(coachId);
			if (coachDetails == null) {
				return false;
			} else {

				NotificationInformation notificationInfo = new NotificationInformation();

				String message = "coach, " + coachDetails.getCoachName() + " has been removed by admin";
				notificationInfo.setNotificationDescription(message);
				notificationInfo.setNotificationType("specific");
				notificationInfo = notificationRepo.save(notificationInfo);

				List<UserInformation> users = coachDetails.getUsers();
				for (UserInformation user : users) {
					user.getNotificaton().add(notificationInfo);
				}

				userRepo.saveAll(users);
				String filePath = "Coach/"+coachDetails.getCoachId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				coachRepo.deleteById(coachId);
				return true;
			}
		} else {
			throw new com.tyss.adminstrongame.exception.CoachException(
					"Failed to delete coach: coach data should not be empty!");
		}
	}// End Of the deleteCoachDetails

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
