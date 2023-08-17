package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.AddsAndBannersDto;
import com.tyss.strongameapp.dto.AdvertisementInformationDto;
import com.tyss.strongameapp.dto.ProductInformationDto;
import com.tyss.strongameapp.dto.ShoppingBannerInformationDto;
import com.tyss.strongameapp.entity.AdminRewardDetails;
import com.tyss.strongameapp.entity.AdvertisementInformation;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.RewardDetails;
import com.tyss.strongameapp.entity.ShoppingBannerInformation;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.AdminRewardInformationRepository;
import com.tyss.strongameapp.repository.AdvertisementInformationRepository;
import com.tyss.strongameapp.repository.ProductInformationRepository;
import com.tyss.strongameapp.repository.RewardDetailsRepository;
import com.tyss.strongameapp.repository.ShoppingBannerInformationRepository;

/**
 * MyShopServiceImple is implementation class to display the content of my shop page.
 * @author Sushma Guttal
 *
 */
@Service
public class MyShopServiceImple implements MyShopService{

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private ProductInformationRepository productRepo;


	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private RewardDetailsRepository rewardRepo;


	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private AdvertisementInformationRepository advertisementRepo;


	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private AdminRewardInformationRepository adminRewardRepo;


	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private ShoppingBannerInformationRepository shopBannerRepo;


	/**
	 * This field is used to invoke variables of application.properties.
	 */
	@Value("${email.username}")
	private String clientEmaidId;


	/**
	 * This field is used to invoke variables of application.properties.
	 */
	@Value("${email.password}")
	private String clientPassword;


	/**
	 * This method is implemented to fetch the list of products.
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<ProductInformationDto> getProductList() {
		List<ProductInformation> productList=productRepo.findAllProducts();
		if(productList!=null) {
			List<ProductInformationDto> productDtoList=new ArrayList<ProductInformationDto>();
			for (ProductInformation productInformation : productList) {
				ProductInformationDto productDto=new ProductInformationDto();
				BeanUtils.copyProperties(productInformation, productDto);
				productDtoList.add(productDto);
			}
			return productDtoList;
		}else
			return null;
	}//End of getProductList method.


	/**
	 *This method is implemented to filter the products by name in descending order.
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<ProductInformationDto> filterProductByNameDesc() {
		List<ProductInformation> productList=productRepo.filterProductByNameDesc();
		if(productList!=null) {
			List<ProductInformationDto> productDtoList=new ArrayList<ProductInformationDto>();
			for (ProductInformation productInformation : productList) {
				ProductInformationDto productDto=new ProductInformationDto();
				BeanUtils.copyProperties(productInformation, productDto);
				productDtoList.add(productDto);
			}
			return productDtoList;
		}else
			return null;
	}//End of filterProductByNameDesc method.


	/**
	 *This method is implemented to filter the products by name in ascending order.
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<ProductInformationDto> filterProductByName() {
		List<ProductInformation> productList=productRepo.filterProductByName();
		if(productList!=null) {
			List<ProductInformationDto> productDtoList=new ArrayList<ProductInformationDto>();
			for (ProductInformation productInformation : productList) {
				ProductInformationDto productDto=new ProductInformationDto();
				BeanUtils.copyProperties(productInformation, productDto);
				productDtoList.add(productDto);
			}
			return productDtoList;
		}else
			return null;
	}//End of filterProductByName method.


	/**
	 *This method is implemented to filter the products by coins in ascending order.
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<ProductInformationDto> filterProductByCoin() {
		List<ProductInformation> productList=productRepo.filterProductByCoin();
		if(productList!=null) {
			List<ProductInformationDto> productDtoList=new ArrayList<ProductInformationDto>();
			for (ProductInformation productInformation : productList) {
				ProductInformationDto productDto=new ProductInformationDto();
				BeanUtils.copyProperties(productInformation, productDto);
				double discount= productInformation.getDiscount();
				double productCoins=productInformation.getProductCoins();
				double savings=(discount*productCoins)/100;
				Double finalCoins=productCoins-savings;
				productDto.setFinalCoins(finalCoins);
				productDtoList.add(productDto);
			}
			Collections.sort(productDtoList, (a, b) -> {
				return a.getFinalCoins().compareTo(b.getFinalCoins());
			});
			return productDtoList;
		}else
			return null;
	}//End of filterProductByCoin method.


	/**
	 *This method is implemented to fetch product details using product name.
	 * @return ProductInformationDto
	 */
	@Override
	public ProductInformationDto getProductByName(String productName) {
		ProductInformation product= productRepo.findProductByName(productName);
		if(product!=null) {
			ProductInformationDto productDto=new ProductInformationDto();
			BeanUtils.copyProperties(product, productDto);
			return productDto;
		}
		return null;
	}//End of getProductByName method.


	/**
	 * This method is implemented to fetch product details by using product id.
	 * @return ProductInformationDto
	 */
	@Override
	public ProductInformationDto getProductById(int productid) {
		ProductInformation product=	productRepo.getProductById(productid);
		if(product!=null) {
			ProductInformationDto productDto=new ProductInformationDto();
			BeanUtils.copyProperties(product, productDto);
			return productDto;
		}
		return null;
	}//End of getProductById method.


	/**
	 *This method is implemented to filter the products by coins in descending order.
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<ProductInformationDto> filterProductByCoinDesc() {
		List<ProductInformation> productList= productRepo.filterProductByCoin();
		if(productList!=null) {
			List<ProductInformationDto> productDtoList=new ArrayList<ProductInformationDto>();
			for (ProductInformation productInformation : productList) {
				ProductInformationDto productDto=new ProductInformationDto();
				BeanUtils.copyProperties(productInformation, productDto);
				double discount= productInformation.getDiscount();
				double productCoins=productInformation.getProductCoins();
				double savings=(discount*productCoins)/100;
				Double finalCoins=productCoins-savings;
				productDto.setFinalCoins(finalCoins);
				productDtoList.add(productDto);
			}
			Collections.sort(productDtoList, (b, a) -> {
				return a.getFinalCoins().compareTo(b.getFinalCoins());
			});
			return productDtoList;
		}else
			return null;
	}//End of filterProductByCoinDesc method.


	/**
	 * This method is implemented to fetch coins of specified user.
	 * @return double
	 */
	@Override
	public double getCoin(UserInformation user) {
		RewardDetails reward=user.getReward();
		double coins=rewardRepo.getCoin(reward.getRewardId());
		AdminRewardDetails adminReward=user.getAdminReward();
		double adminCoins=0;
		if(adminReward!=null) {
			adminCoins= adminRewardRepo.getAdminRewardCoin(adminReward.getAdminRewardId());
		}
		return coins+adminCoins;
	}//End of getCoin method.


	/**
	 * This method is implemented to fetch list of advertisements.
	 * @return List<AdvertisementInformationDto>
	 */
	@Override
	public List<AdvertisementInformationDto> getAdvertisements() {
		List<AdvertisementInformation> advertisementList= advertisementRepo.findAll();
		if(advertisementList.isEmpty())
			return null;
		else
		{
			List<AdvertisementInformationDto> advertisementDtoList=new ArrayList<AdvertisementInformationDto>();
			for (AdvertisementInformation advertisementInformation : advertisementList) {
				AdvertisementInformationDto advertisementDto=new AdvertisementInformationDto();
				BeanUtils.copyProperties(advertisementInformation, advertisementDto);
				advertisementDtoList.add(advertisementDto);
			}
			return advertisementDtoList;
		}
	}//End of getAdvertisements method.


	/**
	 * This method is implemented to send mail to specified user.
	 */
	@Override
	public void sendMail(String email, String name, String type) {
		final String username = clientEmaidId;
		final String password = clientPassword;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtpout.secureserver.net");
		props.put("mail.smtp.port", "80");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {

			Message message = new MimeMessage(session);
			Transport transport = session.getTransport();
			Address[] from = InternetAddress.parse(email); 
			message.addFrom(from);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("support@strongerme.in"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			if(type.equalsIgnoreCase("register")) {
				message.setSubject("Registration Successfull...!");
				message.setText("Dear"+" "+name+" "
						+ "Welcome to Strong Me App");
			}else if(type.equalsIgnoreCase("order")) {
				message.setSubject("Order placed...!");
				message.setText("Dear"+" "+name+" "
						+ "Your order has been placed");
			}else if(type.equalsIgnoreCase("forgotpassword")) {
				message.setSubject("Reset Password");
				message.setContent("<html>\n" +
						"<body>\n" +
						"\n" +
						"<a href=\"https://strongame.web.app/changepass\">\n" +
						"This is a link for reset password</a>\n" +
						"\n" +
						"</body>\n" +
						"</html>", "text/html");

			}

			transport.connect("smtpout.secureserver.net", username, password);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}


	}//End of sendMail method.


	/**
	 * This method is implemented to fetch list of advertisements and shop banners.
	 * @return List<AdvertisementInformationDto>
	 */
	@Override
	public AddsAndBannersDto getAddsBanners() {
		List<AdvertisementInformation> advertisementList= advertisementRepo.findAll();
		List<ShoppingBannerInformation> shopBannerList=shopBannerRepo.findAll();
		if(advertisementList!=null || shopBannerList!=null) {
			AddsAndBannersDto addsBannersDto=new AddsAndBannersDto();
			List<ShoppingBannerInformationDto> shopBannerDtoList = new ArrayList<>();
			for (ShoppingBannerInformation shoppingBannerInformation : shopBannerList) {
				ShoppingBannerInformationDto shopbannerDto=new ShoppingBannerInformationDto();
				BeanUtils.copyProperties(shoppingBannerInformation, shopbannerDto);
				if(shoppingBannerInformation.getShopbannerProduct()!=null) {
					int id=shoppingBannerInformation.getShopbannerProduct().getProductId();
					shopbannerDto.setId(id);
				}
				shopBannerDtoList.add(shopbannerDto);
			}
			addsBannersDto.setAdvertisementList(advertisementList);
			addsBannersDto.setShopBannerList(shopBannerDtoList);

			return addsBannersDto ;
		}
		else return null;
	}

}//End of MyShopServiceImple class
