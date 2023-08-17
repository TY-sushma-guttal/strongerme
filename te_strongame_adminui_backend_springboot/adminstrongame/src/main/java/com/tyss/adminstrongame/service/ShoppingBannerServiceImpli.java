package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.ShoppingBannerInformationDto;
import com.tyss.adminstrongame.dto.ShoppingBannerProductDto;
import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.entity.ShoppingBannerImage;
import com.tyss.adminstrongame.entity.ShoppingBannerInformation;
import com.tyss.adminstrongame.repository.ProductInformationRepository;
import com.tyss.adminstrongame.repository.ShoppingBannerImageRepository;
import com.tyss.adminstrongame.repository.ShoppingBannerInformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for ShoppingBannerService interface.
 * Here you find implementation for saving, updating, fetching and deleting the
 * ShoppingBanner Information
 * 
 * @author Trupthi
 * 
 */
@Service
public class ShoppingBannerServiceImpli implements ShoppingBannerService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	ShoppingBannerInformationRepository shoppingBannerRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	ProductInformationRepository productRepo;

	@Autowired
	SSSUploadFile sssUploadFile;
	
	@Autowired
	private ShoppingBannerImageRepository shoppingBannerImageRepository;

	/**
	 * This method is implemented to save ShoppingBanner Information
	 * 
	 * @param data
	 * @return ShoppingBannerInformationDto
	 */
	@Transactional
	@Override
	public ShoppingBannerInformationDto addShoppingBanner(ShoppingBannerInformationDto data,
			MultipartFile[] imageList) {
		if (data != null) {

			// convert dto to entity
			ShoppingBannerInformation bannerEntity = new ShoppingBannerInformation();
			BeanUtils.copyProperties(data, bannerEntity);

			ProductInformation productInfo = productRepo.getProductById(data.getId());
			if (productInfo == null && data.getId() != 0) {
				data.setValidationMessage("Product does not exist");
				return data;
			} else {
				bannerEntity.setShopbannerProduct(productInfo);

				bannerEntity = shoppingBannerRepo.save(bannerEntity);

				if(!imageList[0].isEmpty()) {
					List<ShoppingBannerImage> shoppingBannerImagePathList = new ArrayList<>();
					for (MultipartFile image : imageList) {
						ShoppingBannerImage newImage = new ShoppingBannerImage();
						newImage.setShoppingBannerImage(
								sssUploadFile.uploadFile(image, bannerEntity.getShoppingBannerId(), "ShoppingBanner"));
						shoppingBannerImagePathList.add(newImage);
					}
					bannerEntity.setShoppingBannerImage(shoppingBannerImagePathList);
					shoppingBannerRepo.save(bannerEntity);
				}
				
				BeanUtils.copyProperties(bannerEntity, data);
				return data;
			}
		} else {
			throw new com.tyss.adminstrongame.exception.BannerException(
					"Failed to add shoppping banner:Shopping Banner information should not be empty!");

		}
	}// End Of the addShoppingBanner

	/**
	 * This method is implemented to update ShoppingBanner Information
	 * 
	 * @param data
	 * @return ShoppingBannerInformationDto
	 */
	@Transactional
	@Override
	public ShoppingBannerInformationDto updateShoppingBanner(ShoppingBannerInformationDto data,MultipartFile[] imageList) {
		if (data != null) {

			// convert dto to entity
			ShoppingBannerInformation shoppingBannerEntity = new ShoppingBannerInformation();
			BeanUtils.copyProperties(data, shoppingBannerEntity);
			ShoppingBannerInformation fetchedShoppingBanner = shoppingBannerRepo.findById(shoppingBannerEntity.getShoppingBannerId()).orElse(null);
			if (fetchedShoppingBanner==null) {
				return null;
			} else {
				ProductInformation productInfo = productRepo.getProductById(data.getId());
				if (productInfo == null && data.getId() != 0) {
					data.setValidationMessage("Product does not exist");
					return data;
				} else {
					shoppingBannerEntity.setShopbannerProduct(productInfo);
					
					String imagePath = "ShoppingBanner/"+fetchedShoppingBanner.getShoppingBannerId();
					sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
					List<ShoppingBannerImage> shoppingBannerImagePathList =null;
					if(!imageList[0].isEmpty()) {
						shoppingBannerImagePathList = new ArrayList<>();
						List<ShoppingBannerImage> getShoppingImageList = fetchedShoppingBanner.getShoppingBannerImage();
						int multipartArraySize = imageList.length;
						if (getShoppingImageList.size() >= multipartArraySize) {
							for (int i = 0; i < getShoppingImageList.size(); i++) {
								if (i > multipartArraySize - 1) {
									shoppingBannerImageRepository.deleteById(getShoppingImageList.get(i).getShoppingBannerImageId());
								} else {
									getShoppingImageList.get(i).setShoppingBannerImage(
											sssUploadFile.uploadFile(imageList[i], data.getShoppingBannerId(), "ShoppingBanner"));
									shoppingBannerImagePathList.add(getShoppingImageList.get(i));
								}
							}
						} else {
							for (int i = 0; i < multipartArraySize; i++) {
								if(i>getShoppingImageList.size()-1) {
									ShoppingBannerImage newImage = new ShoppingBannerImage();
									newImage.setShoppingBannerImage(sssUploadFile.uploadFile(imageList[i], data.getShoppingBannerId(),"ShoppingBanner"));
									shoppingBannerImagePathList.add(newImage);
								}else {
									getShoppingImageList.get(i).setShoppingBannerImage(sssUploadFile.uploadFile(imageList[i],data.getShoppingBannerId(), "ShoppingBanner"));
									shoppingBannerImagePathList.add(getShoppingImageList.get(i));
								}
							}
						}
					}
					shoppingBannerEntity.setShoppingBannerImage(shoppingBannerImagePathList);
					
					shoppingBannerRepo.save(shoppingBannerEntity);
					shoppingBannerImageRepository.deleteAll(shoppingBannerImageRepository.getShoppingBannerImage());
					BeanUtils.copyProperties(shoppingBannerEntity, data);
					return data;
				}
			}

		} else {
			throw new com.tyss.adminstrongame.exception.BannerException(
					"Failed to update shopping banner: Shopping Banner information should not be empty!");

		}
	}// End Of the updateShoppingBanner

	/**
	 * This method is implemented to delete ShoppingBanner Information
	 * 
	 * @param data
	 * @return ShoppingBannerInformationDto
	 */
	@Transactional
	@Override
	public ShoppingBannerInformationDto deleteShoppingBanner(ShoppingBannerInformationDto data) {
		if (data != null) {

			// convert dto to entity
			ShoppingBannerInformation shoppingBannerEntity = new ShoppingBannerInformation();
			BeanUtils.copyProperties(data, shoppingBannerEntity);

			if (!shoppingBannerRepo.findById(shoppingBannerEntity.getShoppingBannerId()).isPresent()) {
				return null;
			} else {
				String imagePath = "ShoppingBanner/"+data.getShoppingBannerId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
				shoppingBannerRepo.delete(shoppingBannerEntity);
				shoppingBannerImageRepository.deleteAll(shoppingBannerImageRepository.getShoppingBannerImage());
				BeanUtils.copyProperties(shoppingBannerEntity, data);
				return data;
			}

		} else {
			throw new com.tyss.adminstrongame.exception.BannerException(
					"Failed to delete shopping banner: Shopping Banner information should not be empty!");

		}
	}// End Of the deleteShoppingBanner

	/**
	 * This method is implemented to get ShoppingBanner Information by id
	 * 
	 * @param bannerInfoId
	 * @return Optional<ShoppingBannerInformation>
	 */
	@Override
	public Optional<ShoppingBannerInformation> getShoppingBannerById(int bannerInfoId) {
		Optional<ShoppingBannerInformation> bannerEntity = shoppingBannerRepo.findById(bannerInfoId);
		if (!bannerEntity.isPresent())
			return null;
		else
			return bannerEntity;
	}// End Of the getShoppingBannerById

	/**
	 * This method is implemented to get all ShoppingBanner Informations
	 * 
	 * @return List<ShoppingBannerInformation>
	 */
	@Override
	public List<ShoppingBannerInformationDto> getAllShoppingBanners() {
		List<ShoppingBannerInformation> bannerEntities = shoppingBannerRepo.findAll();
		List<ShoppingBannerInformationDto> dtos = new ArrayList<>();

		for (ShoppingBannerInformation bannerEntity : bannerEntities) {
			ShoppingBannerInformationDto dto = new ShoppingBannerInformationDto();
			BeanUtils.copyProperties(bannerEntity, dto);
			ProductInformation productInfo = bannerEntity.getShopbannerProduct();
			if (productInfo != null) {
				dto.setId(productInfo.getProductId());
				dto.setName(productInfo.getProductName());
			}
			dtos.add(dto);
		}
		return dtos;
	}// End Of the getAllShoppingBanners

	/**
	 * This method is implemented to get all Product Names
	 * 
	 * @return List<ProductInformation>
	 */
	@Override
	public List<ShoppingBannerProductDto> getAllProductNames() {
		return productRepo.getAllProductNames();
	}

}// End Of the Class
