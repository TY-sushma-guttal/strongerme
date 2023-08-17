package com.tyss.strongameapp.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.strongameapp.dto.LeaderBoardDto;
import com.tyss.strongameapp.dto.MyOrderDto;
import com.tyss.strongameapp.dto.NotificationInformationDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.MyOrderDetailsRepository;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.util.SSSUploadFile;
/**
 * SettingsServiceImple is implemented to display the content of setting page.
 * @author Sushma Guttal
 *
 */
@Service
public class SettingsServiceImple implements SettingsService {

	/**
	 * This field is used to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository userRepo;


	/**
	 * This field is used to invoke persistence layer method.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepo;


	/**
	 * This field is used to invoke business layer method.
	 */
	@Autowired
	private MyShopService myShopService;


	/**
	 * This field is used to invoke persistence layer method.
	 */
	@Autowired
	private MyOrderDetailsRepository myOrderRepository;
	
	@Autowired
	private SSSUploadFile sssUploadFile;
	
	/**
	 * This method is implemented to edit user profile.
	 * @param data
	 * @return UserInformationDto
	 */
	@Override
	public UserInformationDto editUser(UserInformationDto data) {
		boolean nameFlag = CustomUserDetailService.isValidName(data.getName());
		data.setCases("");
		if(nameFlag==false) {
			data.setCases(data.getCases()+" Enter valid name.");
		}
		if(data.getHeight()<=0) {
			data.setCases(data.getCases()+" Height cannot be zero or negative.");
		}
		if(data.getWeight()<=0) {
			data.setCases(data.getCases()+" Weight cannot be zero or negative.");
		}
		if(data.getGender()!=null) {
			if(data.getGender().equalsIgnoreCase("Female")==false && data.getGender().equalsIgnoreCase("Male")==false) {
				data.setCases(data.getCases()+" Enter valid gender.");	
			}
		}
		if(data.getCases().equalsIgnoreCase("")==false) {
			return data;
		}
		UserInformation user=userRepo.findUserById(data.getUserId());
		if(user!=null) {
			Date dateOfBirth=data.getDateOFBirth();
			user.setDateOFBirth(dateOfBirth);
			String gender=data.getGender();
			user.setGender(gender);
			double height=data.getHeight();
			user.setHeight(height);
			String name=data.getName();
			user.setName(name);
			double weight=data.getWeight();
			user.setWeight(weight);
			UserInformation updatedUser= userRepo.save(user);
			BeanUtils.copyProperties(updatedUser, data);
			return data;
		}else return null;
	}
//End of editUser method.


	/**
	 * This method is implemented to display the leaderboard.
	 * @param userId
	 * @return LeaderBoardDto
	 */
	@Override
	public LeaderBoardDto leadBoard(int userId) {
		UserInformation userEntity= userRepo.findUserById(userId);
		if(userEntity!=null) {
			LeaderBoardDto leaderBoardDto=new LeaderBoardDto();
			List<UserInformation> userList=userRepo.findAll();
			List<UserInformationDto> userDto=new ArrayList<UserInformationDto>();
			for (UserInformation userInformation : userList) {
				UserInformationDto dto=new UserInformationDto();
				dto.setCoins(myShopService.getCoin(userInformation));
				BeanUtils.copyProperties(userInformation, dto);
				dto.setTrans(null);
				dto.setSteps(null);
				userDto.add(dto);
			}
			Collections.sort(userDto, (b, a) -> {
				return a.getCoins().compareTo(b.getCoins());
			});
			for (UserInformationDto userInformationDto : userDto) {
				userInformationDto.setPosition(userDto.indexOf(userInformationDto)+1);
				if(userId==userInformationDto.getUserId())
					leaderBoardDto.setYourPosition(userInformationDto.getPosition());
			}
			leaderBoardDto.setUserListDto(userDto);
			return leaderBoardDto;
		}else return null;
	}//End of leadBoard method.


	/**
	 * This method is implemented to display the content of my order page
	 * @param userId
	 * @return MyOrderDto
	 */
	@Override
	public List<MyOrderDto> myOrder(Integer userId) {
		if(userId!=null) {
			UserInformation userEntity= userRepo.findUserById(userId);
			if(userEntity!=null) {
				List<MyOrderDto> myOrderDtoList = new ArrayList<>();
				List<MyOrderDetails> myOrderlist= myOrderRepository.getMyOrder(userId);

				for (MyOrderDetails myOrderDetails : myOrderlist) {
					MyOrderDto myOrderDto = new MyOrderDto();
					BeanUtils.copyProperties(myOrderDetails, myOrderDto);
					myOrderDtoList.add(myOrderDto);
				}
				Collections.reverse(myOrderDtoList);
				return myOrderDtoList;
			}else return null;
		}
		else throw new com.tyss.strongameapp.exception.UserException("user data cannot be null");

	}//End of myOrder method.


	/**
	 * This method is implemented to fetch list of notifications.
	 * @param userId
	 * @return List<NotificationInformationDto>
	 */
	@Override
	public List<NotificationInformationDto> notification(Integer userId) {
		UserInformation userEntity= userRepo.findUserById(userId);
		if(userEntity!=null) {
			List<NotificationInformation> notificationList= notificationRepo.getAllNotifications(userEntity.getUserId());
			List<NotificationInformation> genericNotification=notificationRepo.getGenericNotification();
			notificationList.addAll(genericNotification);
			List<NotificationInformationDto> notificationDtoList=new ArrayList<NotificationInformationDto>();
			for (NotificationInformation notificationInformation : notificationList) {
				NotificationInformationDto notificationDto=new NotificationInformationDto();
				BeanUtils.copyProperties(notificationInformation, notificationDto);
				notificationDtoList.add(notificationDto);
			}
			Collections.reverse(notificationDtoList);
			return notificationDtoList;
		}else return null;
	}//End of notification method


	@Override
	public UserInformationDto uploadImage(UserInformationDto data, MultipartFile image) {
		UserInformation userEntity = userRepo.findUserById(data.getUserId());
		if(userEntity!=null) {
				String filePath = "User/"+userEntity.getUserId();
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				String uploadFilePath = sssUploadFile.uploadFile(image, userEntity.getUserId(), "User");
				userEntity.setPhoto(uploadFilePath);
				UserInformation updatedUser= userRepo.save(userEntity);
				BeanUtils.copyProperties(updatedUser, data);
				return data ;
		}else
			return null;
	}

}//End of SettingsServiceImple class.


