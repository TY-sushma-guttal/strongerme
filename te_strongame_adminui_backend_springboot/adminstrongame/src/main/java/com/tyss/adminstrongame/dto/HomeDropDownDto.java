package com.tyss.adminstrongame.dto;

import lombok.Data;

@Data
public class HomeDropDownDto {

	private int id;

	private String name;

	public HomeDropDownDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
