package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.CoachDetailsDto;
import com.tyss.adminstrongame.dto.TransformationCoachDto;
import com.tyss.adminstrongame.dto.TransformationDetailsDto;
import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.entity.TransformationImage;
import com.tyss.adminstrongame.repository.CoachDetailsRepo;
import com.tyss.adminstrongame.repository.TransformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for TransformationService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * Transformation Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class TransformationServiceImpli implements TransformationService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	TransformationRepository transformationRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	CoachDetailsRepo coachRepo;

	private static final String namePattern = "^[a-z A-Z]{1,50}$";

	@Autowired
	private SSSUploadFile uploadFile;

	/**
	 * This method is implemented to save Transformation Details
	 * 
	 * @param data
	 * @return TransformationDetailsDto
	 */
	@Override
	public TransformationDetailsDto addTransformationDetails(TransformationDetailsDto data,
			MultipartFile[] imageList) {
		if (data != null) {
			if (!(isNameValid(data.getUserName()))) {
				data.setValidationMessage("Please enter valid User Name");
				return data;
			} else {
				TransformationDetails transformationEntity = new TransformationDetails();
				BeanUtils.copyProperties(data, transformationEntity);

				CoachDetails coachDetails = coachRepo.getCoachById(data.getCoachId());
				if (coachDetails == null) {
					data.setValidationMessage("Coach does not exist");
					return data;
				} else {
					transformationEntity.setCoach(coachDetails);
					transformationEntity.setCoachName(coachDetails.getCoachName());
					transformationEntity = transformationRepo.save(transformationEntity);
					if(!imageList[0].isEmpty())  {
						List<TransformationImage> transformationImages = new ArrayList<>();
						for (MultipartFile multipartFile : imageList) {
							System.err.println("multipart file");
							TransformationImage image = new TransformationImage();
							image.setTransformationImage(uploadFile.uploadFile(multipartFile,
									transformationEntity.getTransformationId(), "Transformation"));
							transformationImages.add(image);
						}
						transformationEntity.setImage(transformationImages);
						transformationEntity = transformationRepo.save(transformationEntity);
					}
					BeanUtils.copyProperties(transformationEntity, data);
					return data;
				}
			}
		} else {
			throw new com.tyss.adminstrongame.exception.TransformationException(
					"Failed to add new transformation: transformation data should not be empty!");
		}
	}// End Of the addTransformationDetails

	/**
	 * This method is implemented to get all Transformation Details
	 * 
	 * @return List<TransformationDetailsDto>
	 */
	@Override
	public List<TransformationDetailsDto> getAllTransformationDetails() {
		List<TransformationDetails> list = transformationRepo.findAll();

		List<TransformationDetailsDto> dto = new ArrayList<TransformationDetailsDto>();
		for (TransformationDetails transformationDetails : list) {
			TransformationDetailsDto data = new TransformationDetailsDto();
			BeanUtils.copyProperties(transformationDetails, data);
			if (transformationDetails.getCoach() != null) {
				data.setCoachId(transformationDetails.getCoach().getCoachId());
				data.setPhoto(transformationDetails.getCoach().getPhoto());
			}
			dto.add(data);
		}

		return dto;
	}// End Of the getAllTransformationDetails

	/**
	 * This method is implemented to update Transformation Details
	 * 
	 * @param data
	 * @return TransformationDetailsDto
	 */
	@Transactional
	@Override
	public TransformationDetailsDto updateTransformationDetails(TransformationDetailsDto data,
			MultipartFile[] imageList) {
		if (data != null) {
			if (!(isNameValid(data.getUserName()))) {
				data.setValidationMessage("Please enter valid User Name");
				return data;
			} else {
				// convert dto to entity
				TransformationDetails transformationEntity = new TransformationDetails();
				BeanUtils.copyProperties(data, transformationEntity);

				if (!transformationRepo.findById(transformationEntity.getTransformationId()).isPresent()) {
					return null;
				} else {
					CoachDetails coachDetails = coachRepo.getCoachById(data.getCoachId());

					if (coachDetails == null) {
						data.setValidationMessage("Coach does not exist");
						return data;
					} else {
						String image = null;
						List<TransformationImage> lists = transformationEntity.getImage();
						if(!imageList[0].isEmpty()) {
							String folderPath = "Transformation/" + data.getTransformationId();
							uploadFile.deleteS3Folder(uploadFile.bucketName, folderPath);
							for (MultipartFile multipartFile : imageList) {
								image = uploadFile.uploadFile(multipartFile, transformationEntity.getTransformationId(),
										"Transformation");
							}
						} else {
							for (TransformationImage list : lists) {
								image = list.getTransformationImage();
							}
						}
						transformationRepo.updateTransformation(transformationEntity.getTransformationId(),
								transformationEntity.getUserName(), coachDetails.getCoachName(),
								transformationEntity.getPlan(), transformationEntity.getTransformationDetail(),
								transformationEntity.getTransformationVideo(), image, data.getCoachId());

						BeanUtils.copyProperties(transformationEntity, data);
						return data;
					}
				}
			}
		} else {
			throw new com.tyss.adminstrongame.exception.TransformationException(
					"Failed to update transformation: transformation data should not be empty!");
		}
	}// End Of the updateTransformationDetails

	/**
	 * This method is implemented to get Transformation Details by id
	 * 
	 * @param id
	 * @return Optional<TransformationDetails>
	 */
	@Override
	public Optional<TransformationDetails> getTransformationDetailsById(int id) {
		Optional<TransformationDetails> transformationInformation = transformationRepo.findById(id);

		if (!transformationInformation.isPresent()) {
			return null;
		} else {
			return transformationInformation;
		}
	}// End Of the getTransformationDetailsById

	/**
	 * This method is implemented to delete Transformation Details
	 * 
	 * @param transformationId
	 * @return boolean
	 */
	@Override
	public boolean deleteTransformationDetails(int transformationId) {
		if (transformationId != 0) {
			if (!transformationRepo.findById(transformationId).isPresent()) {
				return false;
			} else {
				String folderPath = "Transformation/" + transformationId;
				uploadFile.deleteS3Folder(uploadFile.bucketName, folderPath);
				transformationRepo.deleteById(transformationId);
				return true;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.TransformationException(
					"Failed to delete  transformation:  transformation data should not be empty!");
		}
	}// End Of the deleteTransformationDetails

	@Override
	public List<TransformationCoachDto> getAllCoachNames() {
		return coachRepo.getAllCoachNames();
	}// End Of the getAllCoachNames

	public static boolean isNameValid(final String name) {
		Pattern pattern = Pattern.compile(namePattern);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

}// End Of the Class
