package com.tyss.adminstrongame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.adminstrongame.dto.HomeDropDownDto;
import com.tyss.adminstrongame.entity.TransformationDetails;
import com.tyss.adminstrongame.entity.TransformationImage;

public interface TransformationRepository extends JpaRepository<TransformationDetails, Integer> {

	@Query("SELECT t FROM TransformationDetails t WHERE t.transformationId = ?1")
	TransformationDetails getTransformationById(int transformationId);
	
//	@Query("SELECT count(*) FROM TransformationDetails t ")
//	public long countById(int id);
	
	@Query( value = "select count(*) from transformation_details where coach_id= :id", 
			  nativeQuery = true)
	public long countById(int id);
	
//	@Query( value = "select com.tyss.adminstrongame.entity.TransformationDetails(t.transformationId,t.userName,t.coachName, t.plan,"
//			+ "t.transformationDetail, t.transformationVideo,t.coach, t.image) from TransformationDetails t")
//	List<TransformationDetails> getAllTransformation();
	
	@Modifying
	@Query( value = "UPDATE transformation_details JOIN transformation_image ON transformation_details.transformation_id= transformation_image.transformation_id  "
			+ "SET transformation_image =:image, coach_name=:coachName, plan=:plan, transformation_details=:transformationDetail, transformation_video=:transformationVideo,"
			+ " user_name=:userName,coach_id=:coachId where transformation_details.transformation_id=:transformationId", nativeQuery = true)	
	void updateTransformation(int transformationId, String userName, String coachName, String plan,
			String transformationDetail, String transformationVideo, String image,int coachId);
	
	@Query("select new com.tyss.adminstrongame.dto.HomeDropDownDto(t.transformationId,t.userName) from TransformationDetails t")
	List<HomeDropDownDto> getAllUserNames();
	
	
}
