package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.CoachDetailsDto;
import com.tyss.strongameapp.dto.DietPlanDetailsDto;
import com.tyss.strongameapp.dto.DietRecipeLikeDto;
import com.tyss.strongameapp.dto.HomeBannerInformationDto;
import com.tyss.strongameapp.dto.HomeDisplayDto;
import com.tyss.strongameapp.dto.SingleTransformationDto;
import com.tyss.strongameapp.dto.TransformationDetailsDto;
import com.tyss.strongameapp.dto.TransformationLikeDetailsDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.DietRecipeDetails;
import com.tyss.strongameapp.entity.DietRecipeLike;
import com.tyss.strongameapp.entity.HomeBannerInformation;
import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.TransformationLikeDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.AdvertisementInformationRepository;
import com.tyss.strongameapp.repository.CoachDetailsRepo;
import com.tyss.strongameapp.repository.DietLikeRepository;
import com.tyss.strongameapp.repository.DietPlanDetailsRepo;
import com.tyss.strongameapp.repository.HomeBannerInformationRepository;
import com.tyss.strongameapp.repository.TransformationLikeRepository;
import com.tyss.strongameapp.repository.TransformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * This is the implementation class to fetch home page content.
 * @author Sushma Guttal
 *
 */
@Service
public class HomePageServiceImple implements HomePageService {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private HomePageService homePageService;

	/**
	 * This field is used to invoke persistence layer methods. 
	 */
	@Autowired
	private CoachDetailsRepo coachRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private HomeBannerInformationRepository  homeBannerRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private DietLikeRepository dietLikeRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private DietPlanDetailsRepo dietRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private TransformationRepository transformationRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private TransformationLikeRepository transformationLikeRepo;

	/**
	 * This field is used to invoke persistence layer methods.'
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private MyShopService myShopService;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private AdvertisementInformationRepository advertisementRepo;

	/**
	 * This method is implemented to display home page content.
	 * @param userId
	 * @return HomeDisplayDto
	 */
	@Override
	public HomeDisplayDto homePageDisplay(int userId) {
		HomeDisplayDto home = new HomeDisplayDto();
		home.setCases("");
		UserInformation userEntity= userRepo.findUserById(userId);
		if(userEntity==null) {
			home.setCases(home.getCases()+"User Not Found.");
		}
		if(home.getCases().equalsIgnoreCase("")==false) {
			return home ;
		}else {
			home.setDiet(homePageService.getDietList(userId));
			home.setTransformation(homePageService.getTransformationList(userId));
			home.setCoach(homePageService.getCoachList());
			List<HomeBannerInformation> homeBannerList = homeBannerRepo.findAll();
			List<HomeBannerInformationDto> homeBannerDtoList =new ArrayList<>();
			for (HomeBannerInformation homeBannerInformation : homeBannerList) {
				HomeBannerInformationDto homeBannerDto =new HomeBannerInformationDto();
				BeanUtils.copyProperties(homeBannerInformation, homeBannerDto);
				if(homeBannerInformation.getHomeBannerDiet()!=null) {
					int id = homeBannerInformation.getHomeBannerDiet().getDietId();
					homeBannerDto.setId(id);
				}
				else if(homeBannerInformation.getHomeBannerCoach()!=null) {
					int id = homeBannerInformation.getHomeBannerCoach().getCoachId();
					homeBannerDto.setId(id);
				}
				else if(homeBannerInformation.getHomeBannerTransformation()!=null) {
					int id = homeBannerInformation.getHomeBannerTransformation().getTransformationId();
					homeBannerDto.setId(id);
				}
				homeBannerDtoList.add(homeBannerDto);
			}
			home.setAdvertisements(advertisementRepo.findAll());
			home.setHomeBanner(homeBannerDtoList);
			home.setCoins(myShopService.getCoin(userRepo.findUserById(userId)));
			return home;
		}
	}//End of homePageDisplay method.


	/**
	 * This method is used to to check whether user has liked the specified diet recipe or not.
	 * @param dietId
	 * @param userId
	 * @return Boolean
	 */
	public Boolean getflag(int dietId, Integer userId) {
		Boolean flag=dietLikeRepo.getFlag(dietId,userId);
		return flag;
	}//End of getflag method.


	/**
	 * This method is implemented to get list of diet recipes.
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> getDietList(Integer userId) {
		List<DietRecipeDetails> dietList= dietRepo.findAll();
		if(dietList.isEmpty())
			return null;
		else
		{
			List<DietPlanDetailsDto> dietDtoList=new ArrayList<>();
			for (DietRecipeDetails dietPlanDetails : dietList) {
				DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietPlanDetails, dietDto);
				dietDto.setTotalLikes(homePageService.getDietLikeCount(dietDto));
				Boolean flag=getflag(dietDto.getDietId(),userId);
				if(flag==null)
					dietDto.setFlag(false);	
				else 
					dietDto.setFlag(flag);	
				dietDtoList.add(dietDto);

			}
			return dietDtoList;
		}
	}//End of getDietList method.


	/**
	 * This method is implemented to list of transformation.
	 * @param userId
	 * @return List<TransformationDetailsDto>
	 */
	@Override
	public List<TransformationDetailsDto> getTransformationList(Integer userId) {

		List<TransformationDetails> transformationList= transformationRepo.findAll();
		if(transformationList.isEmpty())
			return null;
		else
		{
			List<TransformationDetailsDto> transformationDtoList=new ArrayList<>();
			for (TransformationDetails transformationDetails : transformationList) {
				TransformationDetailsDto transformationDto=new TransformationDetailsDto();
				BeanUtils.copyProperties(transformationDetails, transformationDto);
				CoachDetails coach=transformationDetails.getCoach();
				if(coach!=null) {
					transformationDto.setCoachImage(coach.getPhoto());
				}
				transformationDto.setTotalLikes(homePageService.getTransformationLikeCount(transformationDto));
				Boolean flag=homePageService.getTransformationflag(transformationDto.getTransformationId(),userId);
				if(flag==null)
					transformationDto.setFlag(false);	
				else 
					transformationDto.setFlag(flag);	
				transformationDtoList.add(transformationDto);

			}
			return transformationDtoList;
		}
	}//End of getTransformationList method.


	/**
	 * This method is implemented to fetch total like count for specified diet
	 * @param dietDto
	 * @return Integer
	 */
	@Override
	public Integer getDietLikeCount(DietPlanDetailsDto dietDto) {
		if(dietDto!=null) {
			Optional<DietRecipeDetails> dietEntity= dietRepo.findById(dietDto.getDietId());
			if(dietEntity.isPresent()) {
				DietRecipeDetails dietEntity2= dietEntity.get();
				Integer likes=dietLikeRepo.getDietLikeCount(dietEntity2.getDietId());
				return likes;
			}else return null;
		}else throw new com.tyss.strongameapp.exception.DietRecipeException("Diet recipe details cannot be empty");

	}//End of getDietLikeCount method.


	/**
	 * This method is implemented to fetch total count for specified transformation.
	 * @param transformationDto
	 * @return Integer
	 */
	@Override
	public Integer getTransformationLikeCount(TransformationDetailsDto transformationDto) {
		if(transformationDto!=null) {
			Optional<TransformationDetails> transformationEntity=transformationRepo.findById(transformationDto.getTransformationId());
			if(transformationEntity.isPresent())
			{
				TransformationDetails transformationEntity2= transformationEntity.get();
				Integer likes=transformationLikeRepo.getTransformationLikeCount(transformationEntity2.getTransformationId());
				return likes;
			}
			else return null;
		}else throw new com.tyss.strongameapp.exception.TransformationLikeException("Transformation like cannot be null");
	}//End of getTransformationLikeCount method.


	/**
	 * This method is implemented to check whether user has liked specified transformation or not.
	 * @param userId
	 * @param transformationId
	 * @return Boolean
	 */
	@Override
	public Boolean getTransformationflag(int transformationId, Integer userId) {
		Boolean flag=transformationLikeRepo.getFlag(transformationId,userId);
		return flag;
	}//End of getTransformationflag method.


	/**
	 * This method is used to save like posted by user for specified diet recipe.
	 * @param like DietRecipeLikeDto
	 * @return 
	 */
	@Transactional
	@Override
	public DietRecipeLikeDto save(DietRecipeLikeDto like) {
		if (like!= null) {
			int userId=like.getDietLikeUser().getUserId();
			int dietRecipeId=like.getDietRecipe().getDietId();
			boolean flag=like.isUserLike();
			DietRecipeLike dietLike= dietLikeRepo.findDietLike(userId,dietRecipeId);
			if(dietLike!=null) {
				dietLikeRepo.updateDietLike(userId,dietRecipeId,flag);
				BeanUtils.copyProperties(dietLike, like);
				int dietId= dietLike.getDietRecipe().getDietId();
				DietRecipeDetails dietEntity= dietRepo.findById(dietId).get();
				DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietEntity, dietDto);
				int likeCount= getDietLikeCount(dietDto);
				like.setTotalLikes(likeCount);
				like.setDietLikeUser(null);
				like.setDietRecipe(null);
				like.setUserLike(flag);
				return like;
			}else {
				DietRecipeLike dietLike2=new DietRecipeLike();
				dietLike2.setDietLikeUser(userRepo.findById(like.getDietLikeUser().getUserId()).get());
				dietLike2.setDietRecipe(dietRepo.findById(like.getDietRecipe().getDietId()).get());
				dietLike2.setUserLike(like.isUserLike());
				dietLike2= dietLikeRepo.save(dietLike2);
				BeanUtils.copyProperties(dietLike2, like);
				int dietId= dietLike2.getDietRecipe().getDietId();
				DietRecipeDetails dietEntity= dietRepo.findById(dietId).get();
				DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietEntity, dietDto);
				int likeCount= getDietLikeCount(dietDto);
				like.setTotalLikes(likeCount);
				like.setDietLikeUser(null);
				like.setDietRecipe(null);
				return like;
			}
		} else {
			return null;
		}
	}//End of save  method.

	/**
	 * This method is used to save like posted by user for specified transformation.
	 * @param like
	 * @return TransformationLikeDetailsDto
	 */
	@Transactional
	@Override
	public TransformationLikeDetailsDto save(TransformationLikeDetailsDto like) {
		if (like!= null) {
			int userId=like.getTransformationLikeUser().getUserId();
			int transformId=like.getTransformationLike().getTransformationId();
			boolean flag=like.isLike();
			TransformationLikeDetails likeEntity= transformationLikeRepo.findTransLikeId(userId, transformId);
			if(likeEntity!=null) {
				transformationLikeRepo.update(userId,transformId,flag);
				BeanUtils.copyProperties(likeEntity, like);
				int transformationId= likeEntity.getTransformationLike().getTransformationId();
				TransformationDetails transformationEntity= transformationRepo.findById(transformationId).get();
				TransformationDetailsDto transformationDto=new TransformationDetailsDto();
				BeanUtils.copyProperties(transformationEntity, transformationDto);
				int likeCount= getTransformationLikeCount(transformationDto);
				like.setTotalLikes(likeCount);
				like.setTransformationLikeUser(null);
				like.setTransformationLike(null);
				like.setLike(flag);
				return like;
			}
			else {
				TransformationLikeDetails likeEntity2= new TransformationLikeDetails();
				likeEntity2.setTransformationLike(transformationRepo.findById(like.getTransformationLike().getTransformationId()).get());
				likeEntity2.setTransformationLikeUser(userRepo.findById(like.getTransformationLikeUser().getUserId()).get());
				likeEntity2.setLike(like.isLike());
				likeEntity2= transformationLikeRepo.save(likeEntity2);
				BeanUtils.copyProperties(likeEntity2, like);
				int transformationId= likeEntity2.getTransformationLike().getTransformationId();
				TransformationDetails transformationEntity= transformationRepo.findById(transformationId).get();
				TransformationDetailsDto transformationDto=new TransformationDetailsDto();
				BeanUtils.copyProperties(transformationEntity, transformationDto);
				int likeCount= getTransformationLikeCount(transformationDto);
				like.setTotalLikes(likeCount);
				like.setTransformationLikeUser(null);
				like.setTransformationLike(null);
				return like;
			}
		} else {
			return null;
		}
	}//End of save method.


	/**
	 * This method is implemented to fetch list of coaches.
	 * @return List<CoachDetailsDto>
	 */
	@Override
	public List<CoachDetailsDto> getCoachList() {
		List<CoachDetails> coachList= coachRepo.findAll();
		List<CoachDetailsDto> coachDtoList=new ArrayList<CoachDetailsDto>();
		for (CoachDetails coachDetails : coachList) {
			CoachDetailsDto coachDto=new CoachDetailsDto();
			BeanUtils.copyProperties(coachDetails, coachDto);
			coachDtoList.add(coachDto);
		}
		return coachDtoList;
	}//End of getCoachList method.


	/**
	 * This method is implemented to get total count of likes for specified transformation
	 * @param transformationDto
	 * @return Integer
	 */
	@Override
	public Integer getTransformationLikeCount(SingleTransformationDto transformationDto) {
		if(transformationDto!=null) {
			Optional<TransformationDetails> transformationEntity=transformationRepo.findById(transformationDto.getTransformationId());
			if(transformationEntity.isPresent())
			{
				TransformationDetails transformationEntity2= transformationEntity.get();
				Integer likes=transformationLikeRepo.getTransformationLikeCount(transformationEntity2.getTransformationId());
				return likes;
			}
			else return null;
		}else throw new com.tyss.strongameapp.exception.TransformationLikeException("Transformation like cannot be null");
	}//End of getTransformationLikeCount method.


	/**
	 * This method is implemented to filter diet recipes by name in descending order.
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> filterDietByNameDesc(int userId) {
		List<DietPlanDetailsDto> dietDtoList=new ArrayList<DietPlanDetailsDto>();
		UserInformation user = userRepo.findUserById(userId);
		if(user==null) {
			return null;
		}
		else {
			List<DietRecipeDetails> dietList=dietRepo.filterDietByNameDesc();
			if(dietList!=null) {
				for (DietRecipeDetails dietInformation : dietList) {
					DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
					BeanUtils.copyProperties(dietInformation, dietDto);
					Boolean flag=getflag(dietInformation.getDietId(),userId);
					if(flag==null)
						dietDto.setFlag(false);
					else
						dietDto.setFlag(flag);
					dietDto.setTotalLikes(getDietLikeCount(dietDto));
					dietDtoList.add(dietDto);
				}
			}
			return dietDtoList;
		}
	}//End of filterDietByNameDesc method.


	/**
	 * This method is implemented to filer the diet recipes in ascending order by name.
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> filterDietByName(int userId) {
		List<DietPlanDetailsDto> dietDtoList=new ArrayList<DietPlanDetailsDto>();
		UserInformation user = userRepo.findUserById(userId);
		if(user==null) {
			return null ;
		}else {
			List<DietRecipeDetails> dietList=dietRepo.filterDietByName();
			if(!dietList.isEmpty()) {
				for (DietRecipeDetails dietInformation : dietList) {
					DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
					BeanUtils.copyProperties(dietInformation, dietDto);
					Boolean flag=getflag(dietDto.getDietId(),userId);
					if(flag==null)
						dietDto.setFlag(false);
					else
						dietDto.setFlag(flag);
					dietDto.setTotalLikes(getDietLikeCount(dietDto));
					dietDtoList.add(dietDto);
				}
			}
			return dietDtoList;
		}
	}//End of filterDietByName method.


	/**
	 * This method is implemented to search diet recipes.
	 * @param userID
	 * @param keyword
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> searchDiet(int userId, String keyword) {
		List<DietRecipeDetails> dietList=dietRepo.serach(keyword);
		if(dietList!=null) {
			List<DietPlanDetailsDto> dietDtoList=new ArrayList<DietPlanDetailsDto>();
			for (DietRecipeDetails dietPlanDetails : dietList) {
				DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietPlanDetails, dietDto);
				Boolean flag=getflag(dietDto.getDietId(),userId);
				if(flag==null)
					dietDto.setFlag(false);
				else
					dietDto.setFlag(flag);
				dietDto.setTotalLikes(getDietLikeCount(dietDto));
				dietDtoList.add(dietDto);
			}
			return dietDtoList;
		} else return null;
	}//End of searchDiet method.


	@Override
	public DietPlanDetailsDto getDietById(int userId, int dietId) {
		Optional<DietRecipeDetails> dietRecipe= dietRepo.findById(dietId);
		if(dietRecipe.isPresent()) {
			DietRecipeDetails dietRecipeEntity=dietRecipe.get();
			DietPlanDetailsDto dietDto=new DietPlanDetailsDto();
			BeanUtils.copyProperties(dietRecipeEntity, dietDto);
			dietDto.setTotalLikes(homePageService.getDietLikeCount(dietDto));
			Boolean flag=getflag(dietDto.getDietId(),userId);
			if(flag==null)
				dietDto.setFlag(false);
			else
				dietDto.setFlag(flag);
			return dietDto;
		}
		return null;
	}

}//End of HomePageServiceImple class.
