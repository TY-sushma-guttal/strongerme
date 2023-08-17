package com.tyss.adminstrongame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.adminstrongame.dto.TaglineDetailsDto;
import com.tyss.adminstrongame.entity.TaglineDetails;
import com.tyss.adminstrongame.repository.TaglineDetailsRepository;
import com.tyss.adminstrongame.util.SSSUploadFile;

@Service
public class TaglineServiceImpli implements TaglineService{

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	SSSUploadFile sssUploadFile;
	
	@Autowired
	private TaglineDetailsRepository taglineRepo;

	@Transactional
	@Override
	public TaglineDetailsDto addTaglinenDetails(TaglineDetailsDto data, MultipartFile image) {
		if (data!= null) {

			// convert dto to entity
			TaglineDetails taglineEntity = new TaglineDetails();
			BeanUtils.copyProperties(data,taglineEntity);
			TaglineDetails savedTagline = taglineRepo.save(taglineEntity);
			if (!image.isEmpty()) {
				String uploadFilePath = sssUploadFile.uploadFile(image, savedTagline.getTaglineDetailsId(), "Tagline");
				savedTagline.setImage(uploadFilePath);
			}
			taglineRepo.save(savedTagline);

			BeanUtils.copyProperties(savedTagline,data);
			return data;


		} else{
			throw new com.tyss.adminstrongame.exception.TaglineException(
					"Failed to add new tagline: tagline data should not be empty!");
		}
	}

	@Override
	public List<TaglineDetailsDto> getAllTaglineDetails() {
		List<TaglineDetails> taglineEntities= taglineRepo.findAll();

		List<TaglineDetailsDto> dto = new ArrayList<TaglineDetailsDto>();

		for (TaglineDetails taglineDetails : taglineEntities) {
			TaglineDetailsDto data = new TaglineDetailsDto();

			BeanUtils.copyProperties(taglineDetails,data);
			dto.add(data);
		}
		return dto;
	}

	@Transactional
	@Override
	public TaglineDetailsDto updateTaglineDetails(TaglineDetailsDto data, MultipartFile image) {
		if (data!= null) {

			// convert dto to entity
			TaglineDetails taglineEntity = new TaglineDetails();
			BeanUtils.copyProperties(data,taglineEntity);

			if(!taglineRepo.findById(taglineEntity.getTaglineDetailsId()).isPresent()) {
				return null;
			}else {

				if (!image.isEmpty()) {
					String filePath = "Tagline/"+taglineEntity.getTaglineDetailsId();
					sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
					String uploadImagePath = sssUploadFile.uploadFile(image, taglineEntity.getTaglineDetailsId(),
							"Tagline");
					taglineEntity.setImage(uploadImagePath);
				}
				taglineRepo.save(taglineEntity);

				BeanUtils.copyProperties(taglineEntity,data);
				return data;
			}
		} else{
			throw new com.tyss.adminstrongame.exception.TaglineException(
					"Failed to update tagline: tagline data should not be empty!");
		}
	}

	@Transactional
	@Override
	public boolean deleteTaglineDetails(int taglineId) {
		if (taglineId!= 0) {

			if(!taglineRepo.findById(taglineId).isPresent()) {
				return false;
			}else {
				String filePath = "Tagline/"+taglineId;
				sssUploadFile.deleteS3Folder(sssUploadFile.bucketName, filePath);
				taglineRepo.deleteById(taglineId);
				return true;
			}

		} else{
			throw new com.tyss.adminstrongame.exception.TaglineException (
					"Failed to delete tagline: tagline data should not be empty!");
		}
	}

}
