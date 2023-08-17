package com.tyss.adminstrongame.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.SpecializationDetailsDto;
import com.tyss.adminstrongame.entity.SpecializationDetails;
import com.tyss.adminstrongame.repository.SpecializationDetailsRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for SpecializationService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * Specialization Details
 * 
 * @author Trupthi
 * 
 */
@Service
public class SpecializationServiceImpli implements SpecializationService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private SpecializationDetailsRepository specializationRepo;

	@Autowired
	private SSSUploadFile sssUploadFile;

	/**
	 * This method is implemented to save Specialization Details
	 * 
	 * @param data
	 * @return SpecializationDetailsDto
	 */
	@Transactional
	@Override
	public SpecializationDetailsDto addSpecializationDetails(SpecializationDetailsDto data, MultipartFile image,
			MultipartFile icon) {
		if (data != null) {
			// convert dto to entity
			SpecializationDetails specializationEntity = new SpecializationDetails();
			BeanUtils.copyProperties(data, specializationEntity);

			if (specializationRepo.getSpecializationByType(specializationEntity.getSpecializationType()) != null) {
				return null;
			} else {
				SpecializationDetails savedSpecialization = specializationRepo.save(specializationEntity);
				if (!image.isEmpty()) {
				String uploadImagePath = sssUploadFile.uploadFile(image, savedSpecialization.getSpecializationId(),
						"Specialization");
				savedSpecialization.setSpecializationImage(uploadImagePath);
				}
				if(!icon.isEmpty()) {
				String uploadIconPath = sssUploadFile.uploadFile(icon, savedSpecialization.getSpecializationId(),
						"Specialization");
				savedSpecialization.setSpecializationIcon(uploadIconPath);
				}
				specializationRepo.save(savedSpecialization);
				BeanUtils.copyProperties(savedSpecialization, data);
				return data;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.SpecializationException(
					"Failed to add new specialization: specialization data should not be empty!");
		}
	}// End Of the addSpecializationDetails

	/**
	 * This method is implemented to get all Specialization Details
	 * 
	 * @return List<SpecializationDetailsDto>
	 */
	@Override
	public List<SpecializationDetails> getAllSpecializationDetails() {
		return specializationRepo.findAll();
	}// End Of the getAllSpecializationDetails

	/**
	 * This method is implemented to update Specialization Details
	 * 
	 * @param data
	 * @return int
	 */
	@Transactional
	@Override
	public int updateSpecializationDetails(SpecializationDetailsDto data, MultipartFile image,
			MultipartFile icon) {
		if (data != null) {

			// convert dto to entity
			SpecializationDetails specializationEntity = new SpecializationDetails();
			BeanUtils.copyProperties(data, specializationEntity);

			if (!specializationRepo.findById(specializationEntity.getSpecializationId()).isPresent()) {
				return 1;
			} else {
				if (specializationRepo.getSpecialization(specializationEntity.getSpecializationType(),
						specializationEntity.getSpecializationId()) != null) {
					return 2;
				} else {
					if (!image.isEmpty()) {
						String filePath = "Specialization/"+specializationEntity.getSpecializationId();
						sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
						String uploadImagePath = sssUploadFile.uploadFile(image, specializationEntity.getSpecializationId(),
								"Specialization");
						specializationEntity.setSpecializationImage(uploadImagePath);
						}
						if(!icon.isEmpty()) {
						String uploadIconPath = sssUploadFile.uploadFile(icon, specializationEntity.getSpecializationId(),
								"Specialization");
						specializationEntity.setSpecializationIcon(uploadIconPath);
						}
					
					specializationRepo.save(specializationEntity);
					BeanUtils.copyProperties(specializationEntity, data);
					return 3;
				}
			}
		} else {
			throw new com.tyss.adminstrongame.exception.SpecializationException(
					"Failed to update specialization: specialization data should not be empty!");
		}

	}// End Of the updateSpecializationDetails

	/**
	 * This method is implemented to delete Specialization Details
	 * 
	 * @param SpecializationId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteSpecializationDetails(int specializationId) {
		if (specializationId != 0) {

			if (!specializationRepo.findById(specializationId).isPresent()) {
				return false;
			} else {
				String filePath = "Specialization/"+specializationId;
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				specializationRepo.deleteById(specializationId);
				return true;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.SpecializationException(
					"Failed to delete specialization: specialization data should not be empty!");
		}
	}// End Of the deleteSpecializationDetails

}// End Of the Class
