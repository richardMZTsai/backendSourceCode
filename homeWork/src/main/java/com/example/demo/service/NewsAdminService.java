package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.GeneralResult;
import com.example.demo.dto.PageableDto;
import com.example.demo.dto.AdminNewsDto;
import com.example.demo.paramDto.ParamNewsFlexibleDto;
import com.example.demo.paramDto.ParamSaveNewsDto;
import com.example.demo.paramDto.ParamUpdateNewsDto;

public interface NewsAdminService {
	
	
	GeneralResult doSaveNews(List<ParamSaveNewsDto> releaseNewsList);

	GeneralResult doRemoveNews(List<Integer> removeNewsIdList);

	GeneralResult doDeleteNews(List<Integer> deleteNewsIdList);

	GeneralResult doUpdateNews(List<ParamUpdateNewsDto> updateNewsList);

	List<CategoryDto> getNewsNewsCategory();

	PageableDto<AdminNewsDto> getNewsByFlexibleSearch(int size, int pageNumber,
			ParamNewsFlexibleDto paramNewsFlexibleDto);
 
}
