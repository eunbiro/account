package com.account.dto;

import org.modelmapper.ModelMapper;

import com.account.entity.SubCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryDto {

	private String subCtgName;
	
	private MainCategoryDto mainCategoryDto;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static SubCategoryDto of(SubCategory subCategory) {
		
		return modelMapper.map(subCategory, SubCategoryDto.class);
	}
}
