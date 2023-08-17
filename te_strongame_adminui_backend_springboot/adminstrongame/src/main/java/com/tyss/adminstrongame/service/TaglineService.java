package com.tyss.adminstrongame.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.TaglineDetailsDto;

public interface TaglineService {

	/**
	 * This method is implemented by its implementation class to save Tagline Details
	 * 
	 * @param data
	 * @return TaglineDetailsDto
	 */
	TaglineDetailsDto addTaglinenDetails(TaglineDetailsDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to get all Tagline Details
	 * 
	 * @return List<TaglineDetailsDto>
	 */
	List<TaglineDetailsDto> getAllTaglineDetails();

	/**
	 * This method is implemented by its implementation class to update Tagline Details
	 * 
	 * @param data
	 * @return TaglineDetailsDto
	 */
	TaglineDetailsDto updateTaglineDetails(TaglineDetailsDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class to delete Tagline Details
	 * 
	 * @param taglineId
	 * @return boolean
	 */
	boolean deleteTaglineDetails(int taglineId);
}
