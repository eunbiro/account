package com.account.dto;

import org.modelmapper.ModelMapper;

import com.account.entity.MainCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCategoryDto {

	private Long id;
	
	private String mainCtgName;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MainCategoryDto of(MainCategory mainCategory) {
		
		return modelMapper.map(mainCategory, MainCategoryDto.class);
	}
}
