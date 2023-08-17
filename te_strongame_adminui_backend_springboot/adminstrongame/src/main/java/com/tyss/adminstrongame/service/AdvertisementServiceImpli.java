package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.AdvertisementInformationDto;
import com.tyss.adminstrongame.entity.AdvertisementImage;
import com.tyss.adminstrongame.entity.AdvertisementInformation;
import com.tyss.adminstrongame.repository.AdvertisementImageRepository;
import com.tyss.adminstrongame.repository.AdvertisementInformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for AdvertisementService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * Advertisement Information
 * 
 * @author Trupthi
 * 
 */
@Service
public class AdvertisementServiceImpli implements AdvertisementService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	AdvertisementInformationRepository advertisementRepo;

	@Autowired
	private AdvertisementImageRepository advertisementImageRepository;

	@Autowired
	private SSSUploadFile sssUploadFile;

	/**
	 * This method is implemented to save Advertisement Information
	 * 
	 * @param data
	 * @return AdvertisementInformationDto
	 */
	@Transactional
	@Override
	public AdvertisementInformationDto addAdvertisementInformation(AdvertisementInformationDto data,
			MultipartFile[] imageList) {
		if (data != null) {
			// convert dto to entity
			AdvertisementInformation advertisementEntity = new AdvertisementInformation();
			BeanUtils.copyProperties(data, advertisementEntity);

			AdvertisementInformation savedObj = advertisementRepo.save(advertisementEntity);
			if(!imageList[0].isEmpty()) {
				List<AdvertisementImage> imagePathList = new ArrayList<>();
				for (MultipartFile image : imageList) {
					AdvertisementImage imageObj = new AdvertisementImage();
					imageObj.setAdvertisementImage(
							sssUploadFile.uploadFile(image, savedObj.getAdvertisementId(), "Advertisement"));
					imagePathList.add(imageObj);
				}
				advertisementEntity.setAdvertisementImage(imagePathList);
				advertisementRepo.save(advertisementEntity);
			}
			BeanUtils.copyProperties(advertisementEntity, data);
			return data;

		} else {
			throw new com.tyss.adminstrongame.exception.AdvertisementException(
					"Failed to add new Advertisement: Advertisement data should not be empty!");
		}
	}// End Of the addAdvertisementInformation

	/**
	 * This method is implemented to get all Advertisement Information
	 * 
	 * @return List<AdvertisementInformation>
	 */
	@Override
	public List<AdvertisementInformation> getAdvertisementInformation() {
		return advertisementRepo.findAll();

	}// End Of the getAdvertisementInformation

	/**
	 * This method is implemented to update Advertisement Information
	 * 
	 * @param data
	 * @return AdvertisementInformationDto
	 */
	@Transactional
	@Override
	public AdvertisementInformationDto updateAdvertisementInformation(AdvertisementInformationDto data,
			MultipartFile[] imageList) {
		if (data != null) {

			// convert dto to entity
			AdvertisementInformation advertisementEntity = new AdvertisementInformation();
			BeanUtils.copyProperties(data, advertisementEntity);

			AdvertisementInformation getAdvertisement = advertisementRepo
					.findById(advertisementEntity.getAdvertisementId()).orElse(null);
			List<AdvertisementImage> imagePathList = null;
			if (getAdvertisement == null) {
				return null;
			} else {
				String imagePath = "Advertisement/" + data.getAdvertisementId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);

				if (!imageList[0].isEmpty()) {
					imagePathList = new ArrayList<>();
					List<AdvertisementImage> getImageList = getAdvertisement.getAdvertisementImage();
					int multipartArraySize = imageList.length;
					if (getImageList.size() >= multipartArraySize) {
						for (int i = 0; i < getImageList.size(); i++) {
							if (i > multipartArraySize - 1) {
								advertisementImageRepository.deleteById(getImageList.get(i).getAdvertisementImageId());
							} else {
								getImageList.get(i).setAdvertisementImage(sssUploadFile.uploadFile(imageList[i],
										data.getAdvertisementId(), "Advertisement"));
								imagePathList.add(getImageList.get(i));
							}
						}
					} else {
						for (int i = 0; i < multipartArraySize; i++) {
							if (i > getImageList.size() - 1) {
								AdvertisementImage newImage = new AdvertisementImage();
								newImage.setAdvertisementImage(sssUploadFile.uploadFile(imageList[i],
										data.getAdvertisementId(), "Advertisement"));
								imagePathList.add(newImage);
							} else {
								getImageList.get(i).setAdvertisementImage(sssUploadFile.uploadFile(imageList[i],
										data.getAdvertisementId(), "Advertisement"));
								imagePathList.add(getImageList.get(i));
							}
						}
					}
				}
				advertisementEntity.setAdvertisementImage(imagePathList);
				
				advertisementRepo.save(advertisementEntity);

				advertisementImageRepository.deleteAll(advertisementImageRepository.getAdvertisementImage());

				BeanUtils.copyProperties(advertisementEntity, data);
				return data;
			}
		} else {
			throw new com.tyss.adminstrongame.exception.AdvertisementException(
					"Failed to update advertisement: advertisement data should not be empty!");
		}
	}// End Of the updateAdvertisementInformation

	/**
	 * This method is implemented to get Advertisement Information by id
	 * 
	 * @param id
	 * @return Optional<AdvertisementInformation>
	 */
	@Override
	public Optional<AdvertisementInformation> getAdvertisementInformationById(int id) {
		Optional<AdvertisementInformation> advertisementInformation = advertisementRepo.findById(id);

		if (!advertisementInformation.isPresent()) {
			return null;
		} else {
			return advertisementInformation;
		}
	}// End Of the getAdvertisementInformationById

	/**
	 * This method is implemented to delete Advertisement Information
	 * 
	 * @param data
	 * @return AdvertisementInformationDto
	 */
	@Transactional
	@Override
	public AdvertisementInformationDto deleteAdvertisementInformation(AdvertisementInformationDto data) {
		if (data != null) {

			// convert dto to entity
			AdvertisementInformation advertisementEntity = new AdvertisementInformation();
			BeanUtils.copyProperties(data, advertisementEntity);

			if (!advertisementRepo.findById(advertisementEntity.getAdvertisementId()).isPresent()) {
				return null;

			} else {

				String imagePath = "Advertisement/" + data.getAdvertisementId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
				advertisementRepo.delete(advertisementEntity);

				BeanUtils.copyProperties(advertisementEntity, data);
				advertisementImageRepository.deleteAll(advertisementImageRepository.getAdvertisementImage());
				return data;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.AdvertisementException(
					"Failed to delete advertisement: advertisement data should not be empty!");
		}
	}// End Of the deleteAdvertisementInformation

}// End Of the Class
