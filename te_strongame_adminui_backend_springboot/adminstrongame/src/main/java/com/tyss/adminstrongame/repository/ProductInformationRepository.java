package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.ShoppingBannerProductDto;
import com.tyss.adminstrongame.dto.TransformationCoachDto;
import com.tyss.adminstrongame.entity.ProductInformation;

public interface ProductInformationRepository extends JpaRepository<ProductInformation, Integer>{

	@Query("SELECT p FROM ProductInformation p WHERE p.productName = ?1")
	public List<ProductInformation> findProductByName(String name);
	
	@Query("SELECT p FROM ProductInformation p WHERE p.productId = ?1")
	ProductInformation getProductById(int productId);
	
	@Query("SELECT p FROM ProductInformation p WHERE p.productName = ?1 or productCoins=?2")
	public List<ProductInformation> findProduct(String name,Double coins);
	
	@Modifying
	@Query( value = "delete from strongameapp_db.product_information where product_id=:productId", 
			  nativeQuery = true)
	void deleteProduct(int productId);
	
	@Query( value = "select new com.tyss.adminstrongame.entity.ProductInformation(p.productId, p.productName, p.productDescription, p.productFeatures,"
			+ "p.productDisclaimer, p.productCoins, p.productImage, p.discount) from ProductInformation p")
     List<ProductInformation> getAllProducts();
	
	@Modifying
	@Query( value = "update product_information set product_coins=:productCoins, product_description=:productDescription, product_disclaimer=:productDisclaimer, "
			+ "product_features=:productFeatures, product_image=:productImage, product_name=:productName, discount=:discount where product_id=:productId", nativeQuery = true)		
    void updateProducts(int productId, String productName, String productDescription, String productFeatures,
			String productDisclaimer, double productCoins, String productImage, double discount);
	
	@Query("select new com.tyss.adminstrongame.dto.ShoppingBannerProductDto(p.productId,p.productName) from ProductInformation p")
	List<ShoppingBannerProductDto> getAllProductNames();
	
}
