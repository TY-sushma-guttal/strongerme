package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.ProductInformationDto;
import com.tyss.adminstrongame.entity.ProductInformation;
import com.tyss.adminstrongame.repository.ProductInformationRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

/**
 * This is the service implementation class for ProductService interface. Here
 * you find implementation for saving, updating, fetching and deleting the
 * Product Information
 * 
 * @author Trupthi
 * 
 */
@Service
public class ProductServiceImpli implements ProductService {

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private ProductInformationRepository productRepo;

	@Autowired
	private SSSUploadFile sssUploadFile;

	/**
	 * This method is implemented to save Product Information
	 * 
	 * @param data
	 * @return ProductInformationDto
	 */
	@Transactional
	@Override
	public ProductInformationDto addProductInformation(ProductInformationDto data, MultipartFile image) {
		if (data != null) {
			data.setValidationMessage("");
			if (data.getProductCoins() < 0) {
				data.setValidationMessage(
						data.getValidationMessage() + " Value cannot be negative for Product Coins field.");
			}
			if (data.getDiscount() < 0 || data.getDiscount() > 100) {
				data.setValidationMessage(data.getValidationMessage()
						+ " Value is out of range for Discount field, the valid range is from 0 to 100.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				ProductInformation productEntity = new ProductInformation();
				BeanUtils.copyProperties(data, productEntity);

				productEntity = productRepo.save(productEntity);
				if (!image.isEmpty()) {
					productEntity
							.setProductImage(sssUploadFile.uploadFile(image, productEntity.getProductId(), "Product"));
					productRepo.save(productEntity);
				}
				BeanUtils.copyProperties(productEntity, data);
				return data;

			}
		} else {
			throw new com.tyss.adminstrongame.exception.ProductException(
					"Failed to add new product: product data should not be empty!");
		}
	}// End Of the addPlanDetails

	/**
	 * This method is implemented to get all Product Information
	 * 
	 * @return List<ProductInformation>
	 */
	@Override
	public List<ProductInformation> getAllProductInformation() {
		return productRepo.getAllProducts();
	}// End Of the getAllProductInformation

	/**
	 * This method is implemented to update Product Information
	 * 
	 * @param data
	 * @return ProductInformationDto
	 */
	@Transactional
	@Override
	public ProductInformationDto updateProductInformation(ProductInformationDto data, MultipartFile image) {
		if (data != null) {
			data.setValidationMessage("");
			if (data.getProductCoins() < 0) {
				data.setValidationMessage(
						data.getValidationMessage() + " Value cannot be negative for Product Coins field.");
			}
			if (data.getDiscount() < 0 || data.getDiscount() > 100) {
				data.setValidationMessage(data.getValidationMessage()
						+ " Value is out of range for Discount field, the valid range is from 0 to 100.");
			}
			if (data.getValidationMessage().length() != 0) {
				return data;
			} else {
				// convert dto to entity
				ProductInformation productEntity = new ProductInformation();
				BeanUtils.copyProperties(data, productEntity);

				if (!productRepo.findById(productEntity.getProductId()).isPresent()) {
					return null;
				} else {
					// productRepo.save(productEntity);

					String imagePath = "Product/" + data.getProductId();
					sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
					if (!image.isEmpty()) {
						String newImagePath = sssUploadFile.uploadFile(image, data.getProductId(), "Product");
						productRepo.updateProducts(productEntity.getProductId(), productEntity.getProductName(),
								productEntity.getProductDescription(), productEntity.getProductFeatures(),
								productEntity.getProductDisclaimer(), productEntity.getProductCoins(), newImagePath,
								productEntity.getDiscount());
					}

					BeanUtils.copyProperties(productEntity, data);
					return data;
				}
			}
		} else {
			throw new com.tyss.adminstrongame.exception.ProductException(
					"Failed to update product: product data should not be empty!");
		}
	}// End Of the updateProductInformation

	/**
	 * This method is implemented to get Product Information by name,coins
	 * 
	 * @param name,coins
	 * @return List<ProductInformation>
	 */
	@Override
	public List<ProductInformation> getProductInformation(String name, Double coins) {
		List<ProductInformation> productInformation = productRepo.findProduct(name, coins);

		if (productInformation.isEmpty()) {
			return null;
		} else {
			return productInformation;
		}
	}// End Of the getProductInformation

	/**
	 * This method is implemented to delete Product Information
	 * 
	 * @param productId
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteProductInformation(int productId) {
		if (productId != 0) {

			if (!productRepo.findById(productId).isPresent()) {
				return false;
			} else {
				String imagePath = "Product/" + productId;
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, imagePath);
				productRepo.deleteById(productId);
				return true;
			}
		} else {
			throw new com.tyss.adminstrongame.exception.ProductException(
					"Failed to delete product: product data should not be empty!");
		}

	}// End Of the deleteProductInformation

}// End Of the Class
