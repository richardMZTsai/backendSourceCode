package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.NewsDto;
import com.example.demo.dto.PageableDto;
import com.example.demo.paramDto.ParamNewsFlexibleDto;


public interface NewsUserService  {	
	
	/*查詢上架新聞*/
	List<NewsDto> getNewsByIsReleased();

	/*分頁查詢上架新聞*/
	PageableDto<NewsDto> getNewsByFlexibleSearch(int size, int pageNumber, ParamNewsFlexibleDto paramNewsFlexibleDto);
	 

}
