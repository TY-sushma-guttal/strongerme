package com.tyss.strongameapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.PlanInformationDto;
import com.tyss.strongameapp.dto.SingleCoachDetailsDto;
import com.tyss.strongameapp.dto.TransformationDetailsDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.PlanInformation;
import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.CoachDetailsRepo;
import com.tyss.strongameapp.repository.MyOrderDetailsRepository;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.PlanInformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * This is the implementation class to fetch single coach details and to enroll coach and plan.
 * @author Sushma Guttal
 *
 */
@Service
public class GetSingleCoachServiceImple implements GetSingleCoachService {

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private CoachDetailsRepo coachRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private HomePageService homeService;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private PlanInformationRepository planRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private MyOrderDetailsRepository myOrderRepository;
	

	/**
	 * This method is used to fetch coach details by id.
	 * @param coachId, userId.
	 * @return SingleCoachDetailsDto
	 */
	@Override
	public SingleCoachDetailsDto getCoachById(int coachId, int userId) {
		SingleCoachDetailsDto coachDto=new SingleCoachDetailsDto();
		coachDto.setCases("");
		CoachDetails coach=coachRepo.getCoach(coachId);
		UserInformation userEntity= userRepo.findUserById(userId);
		if(userEntity==null) {
			coachDto.setCases(coachDto.getCases()+"User Not Found");
		}
		if(coach==null) {
			coachDto.setCases(coachDto.getCases()+" Coach Not Found.");
		}
		
		if(coachDto.getCases().equalsIgnoreCase("")==false) {
			return coachDto;
		}else {
			List<TransformationDetails> transformationList=coach.getTransformations();
			String coachImage=coach.getPhoto();
			if(transformationList.isEmpty()) {
				System.out.println("Transformation not found");
			}else {
				List<TransformationDetailsDto> transformationDtoList=new ArrayList<TransformationDetailsDto>();
				for (TransformationDetails transformationDetails : transformationList) {
					TransformationDetailsDto transformationDto =new TransformationDetailsDto();
					BeanUtils.copyProperties(transformationDetails, transformationDto);
					transformationDto.setFlag(homeService.getTransformationflag(transformationDetails.getTransformationId(), userId));
					System.out.println(transformationDto.getFlag());
					transformationDto.setCoachImage(coachImage);
					transformationDto.setTotalLikes(homeService.getTransformationLikeCount(transformationDto));
					transformationDtoList.add(transformationDto);
				}
				coachDto.setTransformations(transformationDtoList);
			}
			List<CoachDetails> coachList = userEntity.getUserCoach();
			if(!coachList.isEmpty())
				for (CoachDetails coachDetails : coachList) {
					if(coachDetails.getCoachId()==coachId) {
						coachDto.setSubscribed(true);
					}

				}
			BeanUtils.copyProperties(coach, coachDto);
			return coachDto;
		}
	}//End of get coach by id method.

	/**
	 * This method is used to enroll the plan and coach.
	 * @param userId, coachId, planDto
	 * @return String
	 */
	@Override
	public String enrollPlan(int userId,int coachId, PlanInformationDto planDto) {
		String response = "";
		Optional<PlanInformation> planEntity=planRepo.findById(planDto.getPlanId());
		Optional<CoachDetails> coachEntity= coachRepo.findById(coachId);
		Optional<UserInformation> userEntity=userRepo.findById(userId);
		if(!userEntity.isPresent()) {
			response = response+"User Not Found.";
		}
		if(!planEntity.isPresent()) {
			response = response+" Plan Not Found.";
		}
		if(!coachEntity.isPresent()) {
			response = response+" Coach Not Found.";
		}
		if(response.equalsIgnoreCase("")==false) {
			return response ;
		} 
		else {
			PlanInformation plan=planEntity.get();
			CoachDetails coach=coachEntity.get();
			UserInformation user=userEntity.get();

			int flag=0;

			List<CoachDetails> coachList = user.getUserCoach();

			if(!coachList.isEmpty()) {
				for (CoachDetails coachDetails : coachList) {
					if(coach.getCoachId()==coachDetails.getCoachId()) {
						flag=1;
					}
				}
				if(flag==0) {
					user.getUserCoach().add(coach);
					NotificationInformation notificationTwo=new NotificationInformation();
					notificationTwo.setNotificationDetails("You have enrolled to"+" "+coach.getCoachName()+" "+"coach");
					notificationTwo.setNotificationType("specific");
					user.getNotificaton().add(notificationTwo);
				}
			} else {
				user.getUserCoach().add(coach);
				NotificationInformation notificationTwo=new NotificationInformation();
				notificationTwo.setNotificationDetails("You have enrolled to"+" "+coach.getCoachName()+" "+"coach");
				notificationTwo.setNotificationType("specific");
				user.getNotificaton().add(notificationTwo);
			}

			user.getUserPlan().add(plan);

			NotificationInformation notification=new NotificationInformation();
			notification.setNotificationDetails("You have enrolled"+" "+plan.getPlanName()+" "+"plan");
			notification.setNotificationType("specific");
			user.getNotificaton().add(notification);

			Date date=new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, 330);

			SimpleDateFormat sdfNow = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
			String s1= sdfNow.format(calendar.getTime());

			NotificationInformation notificationThree=new NotificationInformation();
			notificationThree.setNotificationDetails(user.getName()+"("+user.getEmail()+")"+" "+"has enrolled to coach,"+" "+coach.getCoachName()+"("+coach.getEmailId()+")"+" "+"and"+" "+plan.getPlanName()+" "+"plan of cost"+" "+plan.getPlanPrice()+" "+"on"+" "+s1);
			notificationThree.setNotificationType("plan");
			notificationRepo.save(notificationThree);

			MyOrderDetails myOrder = new MyOrderDetails();
			myOrder.setName(plan.getPlanName());
			myOrder.setPrice(plan.getPlanPrice());
			myOrder.setType("plan");
			myOrder.setUserMyOrder(user);
			myOrderRepository.save(myOrder);
			user.getMyorder().add(myOrder);

			userRepo.save(user);
			response = " Plan Enrolled Successfully" ;
		}
	return response ;

}//End of enroll plan method.

	/**
	 * This method is implemented to fetch plan details
	 * @param planId
	 * @return PlanInformationDto
	 */
	@Override
	public PlanInformationDto getPlanById(int planId) {
		Optional<PlanInformation> planOptionalEntity = planRepo.findById(planId);
		if(planOptionalEntity.isPresent()) {
			PlanInformationDto planDto = new PlanInformationDto();
			PlanInformation planEntity =  planOptionalEntity.get();
			BeanUtils.copyProperties(planEntity, planDto);
			return planDto;
		}
		return null;
	}//End of getPlanById method.

}//End of GetSingleCoachServiceImple class.
