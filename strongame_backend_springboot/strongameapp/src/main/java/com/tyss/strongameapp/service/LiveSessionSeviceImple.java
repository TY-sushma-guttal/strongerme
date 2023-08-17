package com.tyss.strongameapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.BookNowDto;
import com.tyss.strongameapp.dto.CoachForSessionDetailsDto;
import com.tyss.strongameapp.dto.PackageDetailsDto;
import com.tyss.strongameapp.dto.SessionDetailsDto;
import com.tyss.strongameapp.dto.SpecializationDetailsDto;
import com.tyss.strongameapp.dto.TaglineDetailsDto;
import com.tyss.strongameapp.dto.TodaysLiveSessionDto;
import com.tyss.strongameapp.entity.CoachForSessionDetails;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.PackageDetails;
import com.tyss.strongameapp.entity.SessionDetails;
import com.tyss.strongameapp.entity.SessionNotificationDetails;
import com.tyss.strongameapp.entity.SpecializationDetails;
import com.tyss.strongameapp.entity.TaglineDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.CoachForSesssionRepository;
import com.tyss.strongameapp.repository.MyOrderDetailsRepository;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.PackageDetailsRepository;
import com.tyss.strongameapp.repository.SessionDetailsRepository;
import com.tyss.strongameapp.repository.SessionNotificationRepository;
import com.tyss.strongameapp.repository.SpecializationDetailsRepository;
import com.tyss.strongameapp.repository.TaglineDetailsRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * This is the implementation class to fetch contents of live session page.
 * @author SushmaGuttal
 *
 */
@Service
public class LiveSessionSeviceImple implements LiveSessionService {

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private SpecializationDetailsRepository specializationRepo;


	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private CoachForSesssionRepository sessionCoachRepo;


	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private SessionDetailsRepository sessionRepo;


	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private PackageDetailsRepository packageRepo;


	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private SessionNotificationRepository sessionNotificationRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private MyOrderDetailsRepository myOrderRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private TaglineDetailsRepository taglineRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepository;





	/**
	 * This method is implemented to fetch all the specializations.
	 * @return List<SpecializationDetailsDto>
	 */
	@Override
	public List<SpecializationDetailsDto> getAllSpecializations() {
		List<SpecializationDetails> specializationList=specializationRepo.findAll();
		if(specializationList.isEmpty()) {
			return null;
		}else {
			List<SpecializationDetailsDto> specializationDtoList = new ArrayList<>();
			for (SpecializationDetails specializationDetails : specializationList) {
				SpecializationDetailsDto specializationDto = new SpecializationDetailsDto();
				BeanUtils.copyProperties(specializationDetails, specializationDto);
				specializationDto.setSpecializationCoaches(null);
				specializationDto.setSpecializationImage(null);
				specializationDto.setSpecializationDescription(null);
				specializationDtoList.add(specializationDto);
			}
			return specializationDtoList;
		}
	}//End of getAllSpecializations method.


	/**
	 * This method is implemented to fetch all coaches of live sessions.
	 * @return List<CoachForSessionDetailsDto>
	 */
	@Override
	public List<CoachForSessionDetailsDto> getAllCoaches() {
		List<CoachForSessionDetails> coachList= sessionCoachRepo.findAll();
		if(coachList.isEmpty()) {
			return null;
		}else {
			List<CoachForSessionDetailsDto> coachDtoList=new ArrayList<>();
			for (CoachForSessionDetails coachForSessionDetails : coachList) {
				CoachForSessionDetailsDto coachDto = new CoachForSessionDetailsDto();
				BeanUtils.copyProperties(coachForSessionDetails, coachDto);
				coachDto.setSessions(null);
				coachDto.setSpecializations(null);
				coachDto.setCoachNumber(0);
				coachDto.setCoachCertifications(null);
				coachDto.setCoachDescription(null);
				coachDto.setCoachEmailId(null);
				coachDtoList.add(coachDto);
			}
			return coachDtoList;
		}
	}//End of getAllCoaches method.


	/**
	 * This method is implemented to get specified specialization.
	 * @param specializationId
	 * @return SpecializationDetailsDto
	 */
	@Override
	public SpecializationDetailsDto getSpecialization(int specializationId, int userId) {
		SpecializationDetailsDto specializationdto = new SpecializationDetailsDto();
		specializationdto.setCases("");
		UserInformation userEntity = userRepo.findUserById(userId);
		if(userEntity==null) {
			specializationdto.setCases(specializationdto.getCases()+"User Not Found.");
		}
		Optional<SpecializationDetails> specialization= specializationRepo.findById(specializationId);
		if(!specialization.isPresent()) {
			specializationdto.setCases(specializationdto.getCases()+" Specialization Not Found.");;
		}
		if(specializationdto.getCases().equalsIgnoreCase("")==false) {
			return specializationdto;
		}
		else{
			SpecializationDetails specializationEntity= specialization.get();
			BeanUtils.copyProperties(specializationEntity, specializationdto);
			specializationdto.setSpecializationCoaches(null);

			List<SessionDetails> userSessions = userEntity.getUserSessions();

			java.util.Date utilDate=new java.util.Date();  
			java.sql.Date date=new java.sql.Date(utilDate.getTime());
			List<SessionDetails> sessionList= sessionRepo.getAllSessions(specializationEntity.getSpecializationType(),date);

			if(sessionList.isEmpty()) {
				specializationdto.setSessionList(null);
			}
			else{
				List<SessionDetailsDto> sessionDtoList=new ArrayList<>();
				for (SessionDetails sessionDetails : sessionList) {
					SessionDetailsDto sessionDto = new SessionDetailsDto();
					BeanUtils.copyProperties(sessionDetails, sessionDto);
					sessionDto.setPhoto(sessionDetails.getCoachForSession().getCoachImage());
					sessionDto.setSessionCoachName(sessionDetails.getCoachForSession().getCoachFullName());
					sessionDtoList.add(sessionDto);

					Calendar now=Calendar.getInstance();
					now.setTime(sessionDto.getSessionTime());
					now.add(Calendar.MINUTE, -15);

					Calendar nowTwo=Calendar.getInstance();
					nowTwo.add(Calendar.MINUTE,330);

					SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
					String s1= sdfNow.format(now.getTime());

					SimpleDateFormat sdfNowTwo = new SimpleDateFormat("HH:mm:ss");
					String s2 = sdfNowTwo.format(nowTwo.getTime());

					SimpleDateFormat sdfNowTwoDate = new SimpleDateFormat("yyyy-MM-dd");
					String s3 = sdfNowTwoDate.format(nowTwo.getTime());

					boolean isUserSessionMapped = false ;
					for (SessionDetails userSessionDetails : userSessions) {
						if(userSessionDetails.getSessionId()==sessionDetails.getSessionId()) {
							isUserSessionMapped = true;
						}

					}

					if(isUserSessionMapped==true) {
						sessionDto.setUserSessionMapped(true);
					}

					if(s3.equals(sessionDto.getSessionDate()+"")) {
						if(s2.compareToIgnoreCase(s1)>0){
							sessionDto.setSessionFlag(true);
						}
					}

					Comparator<SessionDetailsDto> c=(p,o)->p.getSessionDate().compareTo(o.getSessionDate());
					c=c.thenComparing((p,o)->p.getSessionTime().compareTo(o.getSessionTime()));
					sessionDtoList.sort(c);

					specializationdto.setSessionList(sessionDtoList);
				}
			}
			return specializationdto;
		}
	}//End of getSpecialization method.


	/**
	 * This method is implemented to fetch todays all sessions.
	 * @return List<SessionDetailsDto>
	 */
	@Override
	public TodaysLiveSessionDto getTodaySessions(int userId) {
		TodaysLiveSessionDto list=new TodaysLiveSessionDto();
		list.setCases("");
		UserInformation userEntity = userRepo.findUserById(userId);
		if(userEntity==null) {
			list.setCases("User not found");
			return list ;
		}

		Calendar currentCalenderDate=Calendar.getInstance();
		java.util.Date currentUtilDate= currentCalenderDate.getTime();

		List<SessionDetails> userSessions = userEntity.getUserSessions();


		if(userEntity.getPackageExpiryDate()==null) {
			list.setFlag(false);
		}
		else if(currentUtilDate.after(userEntity.getPackageExpiryDate())) {
			list.setFlag(false);
		}
		else list.setFlag(true);


		java.util.Date utilDate=new java.util.Date();  
		java.sql.Date date=new java.sql.Date(utilDate.getTime());
		List<SessionDetails> sessionList= sessionRepo.getTodaySession(date);
		if(sessionList.isEmpty()) {
			list.setSessionList(null);
			return list;
		}
		else {
			List<SessionDetailsDto> sessionDtoList=new ArrayList<>();
			for (SessionDetails sessionDetails : sessionList) {
				SessionDetailsDto sessionDto= new SessionDetailsDto();
				BeanUtils.copyProperties(sessionDetails, sessionDto);
				Calendar sessionDate=Calendar.getInstance();
				sessionDate.setTime(sessionDto.getSessionTime());
				sessionDate.add(Calendar.MINUTE, -15);

				Calendar currentDate=Calendar.getInstance();
				currentDate.add(Calendar.MINUTE,330);


				SimpleDateFormat sessionDateSDF = new SimpleDateFormat("HH:mm:ss");
				String sessionDateString= sessionDateSDF.format(sessionDate.getTime());

				SimpleDateFormat currentDateSDF = new SimpleDateFormat("HH:mm:ss");
				String currentDateString = currentDateSDF.format(currentDate.getTime());

				boolean isUserSessionMapped = false ;

				for (SessionDetails userSessionDetails : userSessions) {
					if(userSessionDetails.getSessionId()==sessionDetails.getSessionId()) {
						isUserSessionMapped = true;
					}

				}

				if(isUserSessionMapped==true) {
					sessionDto.setUserSessionMapped(true);
				}

				if(currentDateString.compareToIgnoreCase(sessionDateString)>0){
					sessionDto.setSessionFlag(true);
				}
				sessionDto.setPhoto(sessionDetails.getCoachForSession().getCoachImage());
				sessionDto.setSessionCoachName(sessionDetails.getCoachForSession().getCoachFullName());
				sessionDtoList.add(sessionDto);

				Comparator<SessionDetailsDto> c=(p,o)->p.getSessionDate().compareTo(o.getSessionDate());
				c=c.thenComparing((p,o)->p.getSessionTime().compareTo(o.getSessionTime()));
				sessionDtoList.sort(c);
			}

			list.setSessionList(sessionDtoList);
			return list ;
		}
	}//End of getTodaySessions method.


	/**
	 * This method is implemented to fetch list of packages
	 * @return List<PackageDetailsDto>
	 */
	@Override
	public List<PackageDetailsDto> getPackageList() {
		List<PackageDetails> packageList= packageRepo.findRegularPackages();
		if(packageList.isEmpty())
			return null;
		else {
			List<PackageDetailsDto> packageDtoList = new ArrayList<>();
			for (PackageDetails packageDetails : packageList) {
				PackageDetailsDto packageDto = new PackageDetailsDto();
				BeanUtils.copyProperties(packageDetails, packageDto);
				packageDtoList.add(packageDto);
			}
			return packageDtoList ;
		}
	}//End of getPackageList method.


	/**
	 * This method is implemented to join specified session
	 * @param userId
	 * @param sessionId
	 * @return SessionDetailsDto
	 */
	@Override
	public SessionDetailsDto joinSession(int userId, int sessionId) {
		UserInformation userEntity= userRepo.findUserById(userId);
		SessionDetails sessionEntity= sessionRepo.findSessionById(sessionId);

		SessionDetailsDto sessionDto = new SessionDetailsDto();
		sessionDto.setValidationCase("");
		if(userEntity==null) {
			sessionDto.setValidationCase(sessionDto.getValidationCase()+"User Not Found.");
		}
		if(sessionEntity==null) {
			sessionDto.setValidationCase(sessionDto.getValidationCase()+" Session Not Found.");
		}

		if(sessionDto.getValidationCase().equalsIgnoreCase("")==false) {
			return sessionDto;
		}


		BeanUtils.copyProperties(sessionEntity, sessionDto);
		sessionDto.setPhoto(sessionEntity.getCoachForSession().getCoachImage());
		sessionDto.setSessionCoachName(sessionEntity.getCoachForSession().getCoachFullName());

		java.util.Date date=new java.util.Date();  
		java.util.Date packageInvalidDate = userEntity.getPackageExpiryDate();

		if(packageInvalidDate==null) {
			sessionDto.setCases(1);
			return sessionDto;
		}else if(date.after(packageInvalidDate)) {
			sessionDto.setCases(2);
			return sessionDto;
		}else if(sessionDto.getSlotsAvailable()<=0){
			sessionDto.setCases(5);
			return sessionDto;
		}else{
			SessionDetails session=sessionRepo.isUserSessionMapped(userId,sessionId);
			if(session==null) {
				SessionNotificationDetails sessionNotificationDetails = new SessionNotificationDetails();
				sessionNotificationDetails.setSessionNotificationDescription(userEntity.getName()+"("+userEntity.getEmail()+")"+" "+"wants to join the live session of"+" "+sessionDto.getSessionType()+" "+"by"+" "+sessionDto.getSessionCoachName()+" "+"on"+" "+sessionDto.getSessionDate()+" "+"at"+" "+sessionDto.getSessionTime());
				sessionNotificationDetails.setSessionNotificationType("session");

				sessionNotificationDetails.setSessionNotificationUser(userEntity);
				sessionNotificationRepository.save(sessionNotificationDetails);

				userEntity.getSessionNotifications().add(sessionNotificationDetails);
				userEntity.getUserSessions().add(sessionEntity);
				userRepo.save(userEntity);

				sessionEntity.setSlotsAvailable(sessionEntity.getSlotsAvailable()-1);
				SessionDetails updatedSession = sessionRepo.save(sessionEntity);
				BeanUtils.copyProperties(updatedSession, sessionDto);
				sessionDto.setPhoto(updatedSession.getCoachForSession().getCoachImage());
				sessionDto.setSessionCoachName(updatedSession.getCoachForSession().getCoachFullName());
				sessionDto.setCases(3);
				return sessionDto;
			}else {
				sessionDto.setCases(4);
				return sessionDto ;
			}
		}
	}//End of joinSession method.


	/**
	 * This method is implemented to book package.
	 * @param userId
	 * @param packageId
	 * @return PackageDetailsDto
	 */
	@Override
	public PackageDetailsDto bookPackage(BookNowDto bookNowDto) {
		Optional<UserInformation> userOptional= userRepo.findById(bookNowDto.getUserId());
		Optional<PackageDetails> packageOptional= packageRepo.findById(bookNowDto.getRegularPackageId());
		PackageDetailsDto packageDto=new PackageDetailsDto();
		packageDto.setCases("");
		if(!userOptional.isPresent()) {
			packageDto.setCases(packageDto.getCases()+"User Not Found.");
		}
		if(!packageOptional.isPresent()) {
			packageDto.setCases(packageDto.getCases()+" Package Not Found.");
		} 
		if(packageDto.getCases().equalsIgnoreCase("")==false){
			return packageDto;
		}
		else {

			UserInformation userEntity=userOptional.get();	
			PackageDetails packageEntity= packageOptional.get();

			double packageDuration= packageEntity.getPackageDuration();
			int packageDurationInMins=(int)(packageDuration*10080);

			java.util.Date date=new java.util.Date();

			Calendar nowTwo=Calendar.getInstance();
			nowTwo.setTime(date);
			nowTwo.add(Calendar.MINUTE, 330+packageDurationInMins);
			java.util.Date dateTwo= nowTwo.getTime();

			userEntity.setPackageExpiryDate(dateTwo);

			userEntity.getUserPackages().add(packageEntity);

			NotificationInformation userNotification = new NotificationInformation();
			userNotification.setNotificationDetails("You have enrolled online session package of duration"+" "+packageEntity.getPackageDuration()+" "+"months");
			userNotification.setNotificationType("specific");

			NotificationInformation notification = new NotificationInformation();
			notification.setNotificationDetails(userEntity.getName()+" "+"has enrolled online session package of duration"+" "+packageEntity.getPackageDuration()+" "+"months");
			notification.setNotificationType("package");

			MyOrderDetails myOrder = new MyOrderDetails();
			myOrder.setName(packageEntity.getPackageName());

			double discount=packageEntity.getOfferPercentage();
			double savings=(discount*packageEntity.getActualPrice())/100;
			double finalPrice=packageEntity.getActualPrice()-savings;
			myOrder.setPrice(finalPrice);

			myOrder.setType("package");
			myOrder.setImage(packageEntity.getPackageIcon());
			myOrder.setUserMyOrder(userEntity);
			myOrderRepository.save(myOrder);;
			userEntity.getMyorder().add(myOrder);

			if(!bookNowDto.getAddOnIds().isEmpty()) {
				notification.setNotificationDetails(userEntity.getName()+" "+"has enrolled online session package of duration"+" "+packageEntity.getPackageDuration()+" "+"months along with");			
				userNotification.setNotificationDetails("You have enrolled online session package of duration "+packageEntity.getPackageDuration()+" "+"months along with");
				List<Integer> addOnId= bookNowDto.getAddOnIds();
				for (Integer integer : addOnId) {
					Optional<PackageDetails> addOnPackage=packageRepo.findById(integer);
					if(addOnPackage.isPresent()) {
						MyOrderDetails myOrderAddOn = new MyOrderDetails();	
						myOrderAddOn.setName(addOnPackage.get().getPackageName());
						myOrderAddOn.setType("Add-on");
						myOrderAddOn.setImage(addOnPackage.get().getPackageIcon());

						double offerPercentage=addOnPackage.get().getOfferPercentage();
						double saving=(offerPercentage*addOnPackage.get().getActualPrice())/100;
						double finalAddOnPrice=addOnPackage.get().getActualPrice()-saving;
						myOrderAddOn.setPrice(finalAddOnPrice);

						myOrderAddOn.setUserMyOrder(userEntity);
						myOrderRepository.save(myOrderAddOn);

						userNotification.setNotificationDetails(userNotification.getNotificationDetails()+" "+addOnPackage.get().getPackageName()+", ");
						notification.setNotificationDetails(notification.getNotificationDetails()+" "+addOnPackage.get().getPackageName()+", ");

						userEntity.getMyorder().add(myOrderAddOn);

						userEntity.getUserPackages().add(addOnPackage.get());
					}
				}
			}

			userEntity.getNotificaton().add(userNotification);
			notificationRepository.save(notification);


			userRepo.save(userEntity);

			BeanUtils.copyProperties(packageEntity, packageDto);

			return packageDto;
		}
	}//End of bookPackage method.



	/**
	 * This method is used to fetch package details by package id.
	 * @param packageId
	 * @return List<PackageDetailsDto>
	 */
	@Override
	public List<PackageDetailsDto> getPackageById(int packageId) {
		Optional<PackageDetails> optionalRegularPackage= packageRepo.findById(packageId);
		List<PackageDetailsDto> packageDtoList = new ArrayList<>();
		if(optionalRegularPackage.isPresent()) {
			PackageDetails regularPackage= optionalRegularPackage.get();
			List<PackageDetails> addOn=packageRepo.getAddOn();
			List<PackageDetails> packageList = new ArrayList<>();
			packageList.add(regularPackage);
			packageList.addAll(addOn);

			for (PackageDetails packageDetails : packageList) {
				PackageDetailsDto packageDto=new PackageDetailsDto();
				BeanUtils.copyProperties(packageDetails, packageDto);
				packageDtoList.add(packageDto);
			}

			return packageDtoList;

		}
		else return  packageDtoList;
	}//End of getPackageById method.


	/**
	 * This method is used to fetch tagline details
	 * @return TaglineDetailsDto
	 */
	@Override
	public TaglineDetailsDto getTagLineDetails() {
		List<TaglineDetails> taglineDetailsList= taglineRepository.findAll();
		if(taglineDetailsList.size()>0) {
			TaglineDetails taglineDetails = taglineDetailsList.get(taglineDetailsList.size()-1);

			TaglineDetailsDto taglineDetailsDto=new TaglineDetailsDto();
			BeanUtils.copyProperties(taglineDetails, taglineDetailsDto);

			List<UserInformation> userList= userRepo.getUserDescend();

			List<String> images = new ArrayList<String>();
			for (UserInformation userInformation : userList) {
				if(userInformation.getPhoto()!=null) {
					images.add(userInformation.getPhoto());
				}
			}
			int userCount = userList.size();
			taglineDetailsDto.setUserCount(userCount);
			taglineDetailsDto.getUserImages().addAll(images);

			return taglineDetailsDto;

		}
		else return null;
	}//End of getTagLineDetails method

}//End of LiveSessionSeviceImple class.
