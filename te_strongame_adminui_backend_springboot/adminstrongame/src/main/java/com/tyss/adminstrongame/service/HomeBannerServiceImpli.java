package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.HomeBannerInformationDto;
import com.tyss.adminstrongame.dto.HomeDropDownDto;
import com.tyss.adminstrongame.entity.CoachDetails;
import com.tyss.adminstrongame.entity.DietRecipeDetails;
import com.tyss.adminstrongame.entity.HomeBannerImage;
import com.tyss.adminstrongame.entity.HomeBannerInformation;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.repository.CoachDetailsRepo;
import com.tyss.adminstrongame.repository.DietPlanDetailsRepo;
import com.tyss.adminstrongame.repository.HomeBannerImageRepository;
import com.tyss.adminstrongame.repository.HomeBannerInformationRepository;
import com.tyss.adminstrongame.repository.TransformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for HomeBannerService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * HomeBanner Information
 * 
 * @author Trupthi
 * 
 */
@Service
public class HomeBannerServiceImpli implements HomeBannerService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	HomeBannerInformationRepository homeBannerRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	CoachDetailsRepo coachRepo;
	
	@Autowired
	HomeBannerImageRepository bannerImageRepository;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	SSSUploadFile sssUploadFile;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	TransformationRepository transformationRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	DietPlanDetailsRepo dietRepo;

	/**
	 * This method is implemented to save HomeBanner Information
	 * 
	 * @param data
	 * @return HomeBannerInformationDto
	 */
	@Transactional
	@Override
	public HomeBannerInformationDto addHomeBanner(HomeBannerInformationDto data, MultipartFile[] imageList) {
		if (data != null) {

			// convert dto to entity
			HomeBannerInformation bannerEntity = new HomeBannerInformation();
			BeanUtils.copyProperties(data, bannerEntity);

			if ("Coach".equalsIgnoreCase(data.getHomeBannerType())) {
				CoachDetails coachDetails = coachRepo.getCoachById(data.getId());
				if (coachDetails == null && data.getId() != 0) {
					data.setValidationMessage("Coach does not exist");
					return data;
				} else {
					bannerEntity.setHomeBannerCoach(coachDetails);
				}
			} else if ("Transformation".equalsIgnoreCase(data.getHomeBannerType())) {
				TransformationDetails transformationDetails = transformationRepo.getTransformationById(data.getId());
				if (transformationDetails == null && data.getId() != 0) {
					data.setValidationMessage("Transformation does not exist");
					return data;
				} else {
					bannerEntity.setHomeBannerTransformation(transformationDetails);
				}
			} else if ("Diet Recipe".equalsIgnoreCase(data.getHomeBannerType())) {
				DietRecipeDetails dietRecipeDetails = dietRepo.getDietById(data.getId());
				if (dietRecipeDetails == null && data.getId() != 0) {
					data.setValidationMessage("Diet Recipe does not exist");
					return data;
				} else {
					bannerEntity.setHomeBannerDiet(dietRecipeDetails);
				}
			}

			bannerEntity = homeBannerRepo.save(bannerEntity);
			
			if(!imageList[0].isEmpty()) {
				List<HomeBannerImage> homeBannerImageList = new ArrayList<>();
				for (MultipartFile image : imageList) {
					HomeBannerImage homeBannerImage = new HomeBannerImage();
					String uploadFilePath = sssUploadFile.uploadFile(image, bannerEntity.getHomeBannerId(), "HomeBanner");
					homeBannerImage.setHomeBannerImage(uploadFilePath);
					homeBannerImageList.add(homeBannerImage);
				}
				bannerEntity.setHomeBannerImage(homeBannerImageList);
				homeBannerRepo.save(bannerEntity);
			}
			
			BeanUtils.copyProperties(bannerEntity, data);
			return data;

		} else {
			throw new com.tyss.adminstrongame.exception.BannerException(
					"Failed to add banner: Banner information should not be empty!");

		}
	}// End Of the addHomeBanner

	/**
	 * This method is implemented to update HomeBanner Information
	 * 
	 * @param data
	 * @return HomeBannerInformationDto
	 */
	@Transactional
	@Override
	public HomeBannerInformationDto updateHomeBanner(HomeBannerInformationDto data, MultipartFile[] imageList) {
		if (data != null) {

			// convert dto to entity
			HomeBannerInformation homeBannerEntity = new HomeBannerInformation();
			BeanUtils.copyProperties(data, homeBannerEntity);

			HomeBannerInformation homeBannerInformation = homeBannerRepo
					.getHomeBannerById(homeBannerEntity.getHomeBannerId());
			if (homeBannerInformation == null) {
				return null;
			} else {
				if ("Coach".equalsIgnoreCase(data.getHomeBannerType())) {
					CoachDetails coachDetails = coachRepo.getCoachById(data.getId());
					if (coachDetails == null && data.getId() != 0) {
						data.setValidationMessage("Coach does not exist");
						return data;
					} else {
						homeBannerEntity.setHomeBannerCoach(coachDetails);
					}
				} else if ("Transformation".equalsIgnoreCase(data.getHomeBannerType())) {
					TransformationDetails transformationDetails = transformationRepo
							.getTransformationById(data.getId());
					if (transformationDetails == null && data.getId() != 0) {
						data.setValidationMessage("Transformation does not exist");
						return data;
					} else {
						homeBannerEntity.setHomeBannerTransformation(transformationDetails);
					}
				} else if ("Diet Recipe".equalsIgnoreCase(data.getHomeBannerType())) {
					DietRecipeDetails dietRecipeDetails = dietRepo.getDietById(data.getId());
					if (dietRecipeDetails == null && data.getId() != 0) {
						data.setValidationMessage("Diet Recipe does not exist");
						return data;
					} else {
						homeBannerEntity.setHomeBannerDiet(dietRecipeDetails);
					}

				}
				/*Image update starts*/
				String filePath = "HomeBanner/" + homeBannerInformation.getHomeBannerId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				List<HomeBannerImage> bannerImagePathList =null;
				if(!imageList[0].isEmpty()) {
					bannerImagePathList = new ArrayList<>();
					List<HomeBannerImage> getBannerImageList = homeBannerInformation.getHomeBannerImage();
					int multipartArraySize = imageList.length;
					if (getBannerImageList.size() >= multipartArraySize) {
						for (int i = 0; i < getBannerImageList.size(); i++) {
							if (i > multipartArraySize - 1) {
								bannerImageRepository.deleteById(getBannerImageList.get(i).getHomeBannerImageId());
							} else {
								getBannerImageList.get(i).setHomeBannerImage(
										sssUploadFile.uploadFile(imageList[i], data.getHomeBannerId(), "HomeBanner"));
								bannerImagePathList.add(getBannerImageList.get(i));
							}
						}
					} else {
						for (int i = 0; i < multipartArraySize; i++) {
							if(i>getBannerImageList.size()-1) {
								HomeBannerImage newImage = new HomeBannerImage();
								newImage.setHomeBannerImage(sssUploadFile.uploadFile(imageList[i], data.getHomeBannerId(),"HomeBanner"));
								bannerImagePathList.add(newImage);
							}else {
								getBannerImageList.get(i).setHomeBannerImage(sssUploadFile.uploadFile(imageList[i],data.getHomeBannerId(), "HomeBanner"));
								bannerImagePathList.add(getBannerImageList.get(i));
							}
						}
					}
				}
				
				homeBannerEntity.setHomeBannerImage(bannerImagePathList);
				
				homeBannerRepo.save(homeBannerEntity);
				bannerImageRepository.deleteAll(bannerImageRepository.getHomeBannerImage());
				
				/*Image update ends*/
				BeanUtils.copyProperties(homeBannerEntity, data);
				return data;
			}
		} else {
			throw new com.tyss.adminstrongame.exception.BannerException(
					"Failed to update home banner: home banner data should not be empty!");
		}
	}// End Of the updateHomeBanner

	/**
	 * This method is implemented to delete HomeBanner Information
	 * 
	 * @param data
	 * @return HomeBannerInformationDto
	 */
	@Transactional
	@Override
	public HomeBannerInformationDto deleteHomeBanner(HomeBannerInformationDto data) {
		if (data != null) {

			// convert dto to entity
			HomeBannerInformation homeBannerEntity = new HomeBannerInformation();
			BeanUtils.copyProperties(data, homeBannerEntity);

			if (!homeBannerRepo.findById(homeBannerEntity.getHomeBannerId()).isPresent()) {
				return null;
			} else {
				String filePath = "HomeBanner/"+homeBannerEntity.getHomeBannerId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				homeBannerRepo.delete(homeBannerEntity);
				bannerImageRepository.deleteAll(bannerImageRepository.getHomeBannerImage());
				return data;
			}
		} else {
			throw new com.tyss.adminstrongame.exception.BannerException(
					"Failed to delete home banner: home banner data should not be empty!");
		}
	}// End Of the deleteHomeBanner

	/**
	 * This method is implemented to get HomeBanner Information by id
	 * 
	 * @param bannerInfoId
	 * @return Optional<HomeBannerInformation>
	 */
	@Override
	public Optional<HomeBannerInformation> getHomeBannerById(int bannerInfoId) {
		Optional<HomeBannerInformation> bannerEntity = homeBannerRepo.findById(bannerInfoId);
		if (!bannerEntity.isPresent())
			return null;
		else
			return bannerEntity;
	}// End Of the getHomeBannerById

	/**
	 * This method is implemented to get all HomeBanner Information
	 * 
	 * @return List<HomeBannerInformation>
	 */
	@Override
	public List<HomeBannerInformationDto> getAllHomeBanners() {
		List<HomeBannerInformation> homeBannerEntities = homeBannerRepo.findAll();
		List<HomeBannerInformationDto> dtos = new ArrayList<>();
		for (HomeBannerInformation homeBannerEntity : homeBannerEntities) {
			HomeBannerInformationDto dto = new HomeBannerInformationDto();
			BeanUtils.copyProperties(homeBannerEntity, dto);
			CoachDetails coachDetails = homeBannerEntity.getHomeBannerCoach();
			if (coachDetails != null) {
				dto.setId(coachDetails.getCoachId());
				dto.setName(coachDetails.getCoachName());
			}
			TransformationDetails transformationDetails = homeBannerEntity.getHomeBannerTransformation();
			if (transformationDetails != null) {
				dto.setId(transformationDetails.getTransformationId());
				dto.setName(transformationDetails.getUserName());
			}
			DietRecipeDetails dietRecipeDetails = homeBannerEntity.getHomeBannerDiet();
			if (dietRecipeDetails != null) {
				dto.setId(dietRecipeDetails.getDietId());
				dto.setName(dietRecipeDetails.getDietName());
			}
			dtos.add(dto);
		}
		return dtos;
	}// End Of the getAllHomeBanners

	/**
	 * This method is implemented to get all Diet Recipe Names
	 * 
	 * @return List<HomeBannerDietDto>
	 */
	@Override
	public List<HomeDropDownDto> getDietRecipeNames() {
		return dietRepo.getDietRecipeNames();
	}// End Of the getDietRecipeNames

	/**
	 * This method is implemented to get all user Names
	 * 
	 * @return List<HomeBannerTransformationDto>
	 */
	@Override
	public List<HomeDropDownDto> getAllUserNames() {
		return transformationRepo.getAllUserNames();
	}

	@Override
	public List<HomeDropDownDto> getAllCoachNames() {
		return coachRepo.getHomeCoachNames();
	}

}// End Of the Class




