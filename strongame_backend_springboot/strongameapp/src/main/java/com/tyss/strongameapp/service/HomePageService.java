package com.tyss.strongameapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.CoachDetailsDto;
import com.tyss.strongameapp.dto.DietPlanDetailsDto;
import com.tyss.strongameapp.dto.DietRecipeLikeDto;
import com.tyss.strongameapp.dto.HomeDisplayDto;
import com.tyss.strongameapp.dto.ProductInformationDto;
import com.tyss.strongameapp.dto.SingleTransformationDto;
import com.tyss.strongameapp.dto.TransformationDetailsDto;
import com.tyss.strongameapp.dto.TransformationLikeDetailsDto;

/**
 * HomePageService is implemented by HomePageServiceImple class, which is used to display home page content.
 * @author Sushma Guttal
 *
 */
@Service
public interface HomePageService {

	/**
	 * This method is implemented by its implementation class, used to fetch home page content.
	 * @param userId
	 * @return HomeDisplayDto
	 */
	public HomeDisplayDto homePageDisplay(int userId);
	
	/**
	 * This method is implemented by its implementation class, used to fetch list of diet recipes.
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	public List<DietPlanDetailsDto> getDietList(Integer userId);
	
	/**
	 * This method is implemented by its implementation class, used to fetch list of transformations.
	 * @param userId
	 * @return List<TransformationDetailsDto>
	 */
	public List<TransformationDetailsDto> getTransformationList(Integer userId);

	/**
	 * This method is implemented by its implementation class, used to fetch likes count for specified diet.
	 * @param dietDto
	 * @return Integer
	 */
	public Integer getDietLikeCount(DietPlanDetailsDto dietDto);

	/**
	 * This method is implemented by its implementation class, used to fetch likes count for specified transformation.
	 * @param transformationDto
	 * @return Integer
	 */
	public Integer getTransformationLikeCount(TransformationDetailsDto transformationDto);

	/**
	 * This method is implemented by its implementation class, used to get like given by user for specified transformation.
	 * @param transformationId
	 * @param userId
	 * @return Boolean
	 */
	public Boolean getTransformationflag(int transformationId, Integer userId);

	/**
	 * This method is implemented by its implementation class to save the like for specified diet recipe.
	 * @param like
	 * @return DietRecipeLikeDto
	 */
	public DietRecipeLikeDto save(DietRecipeLikeDto like);

	/**
	 * This method is implemented by its implementation class to save the like for specified transformation.
	 * @param like
	 * @return TransformationLikeDetailsDto
	 */
	public TransformationLikeDetailsDto save(TransformationLikeDetailsDto like);

	/**
	 * This method is implemented by its implementation class to get list of coaches.
	 * @return List<CoachDetailsDto>
	 */
	public List<CoachDetailsDto> getCoachList();

	/**
	 * This method is implemented by its implementation class to get transformation like count.
	 * @param transformationDto
	 * @return Integer
	 */
	public Integer getTransformationLikeCount(SingleTransformationDto transformationDto);

	/**
	 * This method is implemented by its implementation class to filter diet recipe by name in descending order.
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	public List<DietPlanDetailsDto> filterDietByNameDesc(int userId);

	/**
	 * This method is implemented by its implementation class to filter diet recipe by name.
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	public List<DietPlanDetailsDto> filterDietByName(int userId);

	/**
	 * This method is implemented by its implementation class to search diet recipe.
	 * @param userId
	 * @param keyword
	 * @return List<DietPlanDetailsDto>
	 */
	public List<DietPlanDetailsDto> searchDiet(int userId, String keyword);

	
	/**
	 * This method is implemented by its implementation class to fetch diet recipe details using diet id.
	 * @param userId
	 * @param dietId
	 * @return
	 */
	public DietPlanDetailsDto getDietById(int userId, int dietId);
}//End of HomePageService interface.
