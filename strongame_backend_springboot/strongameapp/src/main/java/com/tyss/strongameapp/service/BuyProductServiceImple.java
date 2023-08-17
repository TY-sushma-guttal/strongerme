package com.tyss.strongameapp.service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.OrderInformationDto;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.OrderInformation;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.AdminRewardInformationRepository;
import com.tyss.strongameapp.repository.MyOrderDetailsRepository;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.OrderInformationRepository;
import com.tyss.strongameapp.repository.RewardDetailsRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
/**
 * This is the implementation class to place/order the product.
 * @author Sushma Guttal
 *
 */

@Service
public class BuyProductServiceImple implements BuyProductService{

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private OrderInformationRepository orderRepo;

	/**
	 *This field is to invoke persistence layer method. 
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private RewardDetailsRepository rewardRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private MyShopService myShopService;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private AdminRewardInformationRepository adminRewardRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepo;


	@Autowired
	private MyOrderDetailsRepository myOrderRepository;

	/**
	 * This method is used for placing the order.
	 * @param order, product, user
	 * @return String
	 */
	@Override
	public String placeOrder(OrderInformationDto order,Optional<ProductInformation> productEntity, Optional<UserInformation> userEntity) {
		String response="";
		if(!userEntity.isPresent()) {
			response =response.concat("User Not Found.");
		}
		if(order==null) {
			response =response.concat("Order Not Found.");
		}if(!productEntity.isPresent()) {
			response =response.concat("Product Not Found.");
		}
		if(response.length()!=0) {
			return response ;
		}else {
			ProductInformation product = productEntity.get();
			UserInformation user = userEntity.get();
			double userCoins=myShopService.getCoin(user);
			double productCoins=product.getProductCoins();
			OrderInformation orderEntity=new OrderInformation();
			BeanUtils.copyProperties(order, orderEntity);
			double discount = product.getDiscount();
			double savings=(discount*productCoins)/100;
			double saleCoins=productCoins-savings;
			if(userCoins>=saleCoins) {
				user.getProduct().add(product);
				product.getOrder().add(orderEntity);
				orderEntity.setOrderUser(user);
				orderRepo.save(orderEntity);
				double rewardCoins=user.getReward().getRewardCoins();
				if(rewardCoins>=saleCoins) {
					double balanceCoin=rewardCoins-saleCoins;
					double balanceCoin2= Double.parseDouble(String.format("%.2f", balanceCoin));
					rewardRepo.updateReward(balanceCoin2,user.getReward().getRewardId());
				} else if(rewardCoins<saleCoins) {
					rewardRepo.updateRewardToZero(user.getReward().getRewardId());
					double balanceCoin=saleCoins-rewardCoins;
					double adminCoins=user.getAdminReward().getAdminRewardCoins();
					double adminBalanceCoin=adminCoins-balanceCoin;
					double adminBalanceCoin2= Double.parseDouble(String.format("%.2f", adminBalanceCoin));
					adminRewardRepo.updateAdminReward(adminBalanceCoin2, user.getAdminReward().getAdminRewardId());
				}
				double remainingCoins=userCoins-saleCoins;
				response="Order placed successfully and balance coins are"+" "+Double.parseDouble(String.format("%.2f", remainingCoins));
				myShopService.sendMail(user.getEmail(),user.getName(),"order");
				NotificationInformation notification=new NotificationInformation();
				notification.setNotificationDetails("Your order has been placed for product,"+" "+product.getProductName());
				notification.setNotificationType("specific");

				Date date =new Date();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.MINUTE, 330);

				SimpleDateFormat sdfNow = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String s1= sdfNow.format(calendar.getTime());

				NotificationInformation notificationTwo=new NotificationInformation();
				notificationTwo.setNotificationDetails(user.getName()+" "+"has ordered"+" "+product.getProductName()+" "+"on"+" "+s1);
				notificationTwo.setNotificationType("product");
				notificationRepo.save(notificationTwo);

				user.getNotificaton().add(notification);

				MyOrderDetails myOrder = new MyOrderDetails();
				myOrder.setName(product.getProductName());
				myOrder.setImage(product.getProduct_image());
				myOrder.setType("product");
				myOrder.setPrice(saleCoins);
				myOrder.setUserMyOrder(user);
				user.getMyorder().add(myOrder);

				myOrderRepository.save(myOrder);

				userRepo.save(user);
			} else {
				response = response.concat("Insufficient coins to place order");
			}
			return response;
		}
	}//End of place order method.

}// End of BuyProductServiceImple class.
