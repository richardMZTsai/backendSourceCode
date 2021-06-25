package com.example.demo.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewsDto;
import com.example.demo.dto.PageableDto;
import com.example.demo.paramDto.ParamNewsFlexibleDto;
import com.example.demo.service.NewsUserService;
import com.fasterxml.jackson.annotation.JsonIgnore;


@RestController
public class newsUserController {

	@Autowired
	private NewsUserService newsUserService;

	@JsonIgnore Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/newsUser/releasedNews", method = RequestMethod.GET)
	public List<NewsDto> getReleasedNews() {
		logger.debug("===== into getReleasedNews for user ======");
  		List<NewsDto> returnDtoList = newsUserService.getNewsByIsReleased();
		logger.debug("===== into getReleasedNews for user ======");
		return returnDtoList;
	}

	@RequestMapping(value = "/newsUser/releasedNewsByFlexibleSearch/pageable/{size}/{pageNumber}", method = RequestMethod.POST)
	public PageableDto<NewsDto> getNewsByFlexibleSearch(@PathVariable("size") int size,
			@PathVariable("pageNumber") int pageNumber,
			@RequestBody ParamNewsFlexibleDto paramNewsFlexibleDto) {
		logger.debug("===== into getNewsByFlexibleSearch ======");
		PageableDto<NewsDto> pageDto = newsUserService.getNewsByFlexibleSearch(size,pageNumber,paramNewsFlexibleDto);
		logger.debug("===== End getNewsByFlexibleSearch ======");
		return pageDto;
	}
	




}