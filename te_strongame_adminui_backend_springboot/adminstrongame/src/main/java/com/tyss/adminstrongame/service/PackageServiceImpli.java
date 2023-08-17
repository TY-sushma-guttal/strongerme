package com.tyss.adminstrongame.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.PackageDetailsDto;
import com.tyss.adminstrongame.entity.PackageDetails;
import com.tyss.adminstrongame.repository.PackageDetailsRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

@Service
public class PackageServiceImpli implements PackageService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private PackageDetailsRepository packageRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	SSSUploadFile sssUploadFile;

	/**
	 * This method is implemented to save Package Details
	 * 
	 * @param data
	 * @return PackageDetailsDto
	 */
	@Transactional
	@Override
	public PackageDetailsDto addPackageDetails(PackageDetailsDto data, MultipartFile image) {
		if (data != null) {
			data.setValidationMessage("");
			if (data.getPackageDuration() <= 0) {
				data.setValidationMessage(
						data.getValidationMessage() + " Value cannot be negative or zero for Package Duration field.");
			}
			if (data.getActualPrice() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Price field.");
			}
			if (data.getOfferPercentage() < 0 || data.getOfferPercentage() > 100) {
				data.setValidationMessage(data.getValidationMessage()
						+ " Value is out of range for Offer Percentage field, the valid range is from 0 to 100.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				PackageDetails packageEntity = new PackageDetails();
				BeanUtils.copyProperties(data, packageEntity);
				PackageDetails savedPackage = packageRepo.save(packageEntity);
				if (!image.isEmpty()) {
					String uploadFilePath = sssUploadFile.uploadFile(image, savedPackage.getPackageId(), "Package");
					savedPackage.setPackageIcon(uploadFilePath);
				}
				packageRepo.save(savedPackage);
				return data;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.PackageException(
					"Failed to add new package: package data should not be empty!");
		}
	}// End Of the addPackageDetails

	/**
	 * This method is implemented to get all Package Details
	 * 
	 * @return List<PackageDetailsDto>
	 */
	@Override
	public List<PackageDetailsDto> getAllPackageDetails() {
		return packageRepo.getAllPackages();
	}// End Of the getAllPackageDetails

	/**
	 * This method is implemented to update Package Details
	 * 
	 * @param data
	 * @return PackageDetailsDto
	 */
	@Transactional
	@Override
	public PackageDetailsDto updatePackageDetails(PackageDetailsDto data, MultipartFile image) {
		if (data != null) {
			data.setValidationMessage("");
			if (data.getPackageDuration() <= 0) {
				data.setValidationMessage(
						data.getValidationMessage() + " Value cannot be negative or zero for Package Duration field.");
			}
			if (data.getActualPrice() < 0) {
				data.setValidationMessage(data.getValidationMessage() + " Value cannot be negative for Price field.");
			}
			if (data.getOfferPercentage() < 0 || data.getOfferPercentage() > 100) {
				data.setValidationMessage(data.getValidationMessage()
						+ " Value is out of range for Offer Percentage field, the valid range is from 0 to 100.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				PackageDetails packageEntity = new PackageDetails();
				BeanUtils.copyProperties(data, packageEntity);

				if (!packageRepo.findById(packageEntity.getPackageId()).isPresent()) {
					return null;
				} else {
					if (!image.isEmpty()) {
						String filePath = "Package/"+packageEntity.getPackageId();
						sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
						String uploadImagePath = sssUploadFile.uploadFile(image, packageEntity.getPackageId(),
								"Package");
						packageEntity.setPackageIcon(uploadImagePath);
					}
					packageRepo.updatePackage(packageEntity.getPackageId(), packageEntity.getPackageName(),
							packageEntity.getPackageDuration(), packageEntity.getActualPrice(),
							packageEntity.getOfferPercentage(), packageEntity.getPackageType(),
							packageEntity.getPackageIcon());

					BeanUtils.copyProperties(packageEntity, data);
					return data;

				}
			}
		} else {
			throw new com.tyss.adminstrongame.exception.PackageException(
					"Failed to update new package: package data should not be empty!");
		}
	}// End Of the updatePackageDetails

	/**
	 * This method is implemented to delete Package Details
	 * 
	 * @param packageId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deletePackageDetails(int packageId) {
		if (packageId != 0) {
			if (!packageRepo.findById(packageId).isPresent()) {
				return false;
			} else {
				String filePath = "Package/"+packageId;
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				packageRepo.deleteById(packageId);
				return true;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.PackageException(
					"Failed to delete new package: package data should not be empty!");
		}
	}// End Of the deletePackageDetails

}// End Of the Class
