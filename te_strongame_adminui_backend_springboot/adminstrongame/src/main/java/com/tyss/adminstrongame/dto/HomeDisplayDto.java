package com.tyss.adminstrongame.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class HomeDisplayDto {
	
	List deit;
	
	List transformation;
	
	List coach;
	
	List advertisement;	
	
	public HomeDisplayDto(List deit, List trans, List coach, List adds) {
		super();
		this.deit = deit;
		this.transformation = trans;
		this.coach = coach;
		this.advertisement = adds;
	}

	public HomeDisplayDto() {
		
	}
	

}
