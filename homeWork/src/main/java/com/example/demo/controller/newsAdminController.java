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

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.GeneralResult;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.PageableDto;
import com.example.demo.dto.AdminNewsDto;
import com.example.demo.paramDto.ParamNewsFlexibleDto;
import com.example.demo.paramDto.ParamSaveNewsDto;
import com.example.demo.paramDto.ParamUpdateNewsDto;
import com.example.demo.service.NewsAdminService;
import com.fasterxml.jackson.annotation.JsonIgnore;


@RestController
public class newsAdminController {

	@Autowired
	private NewsAdminService newsAdminService;

	@JsonIgnore Logger logger = LogManager.getLogger();
	
	
	//新增多筆新聞資料	 
	@RequestMapping(value = "/newsAdmin/releaseNews", method = RequestMethod.POST)
	public GeneralResult doSaveNews(@RequestBody List<ParamSaveNewsDto> releaseNewsList) {
		logger.debug("===== into doSaveNews ======");
	    GeneralResult gr = newsAdminService.doSaveNews(releaseNewsList);
		logger.debug("===== into doSaveNews ======");
		return gr;
	}

	//下架多筆新聞資料	
	@RequestMapping(value = "/newsAdmin/removeNews", method = RequestMethod.PUT)
	public GeneralResult doRemoveNews(@RequestBody List<Integer> removeNewsIdList) {
		logger.debug("===== into doRemoveNews ======");
	    GeneralResult gr = newsAdminService.doRemoveNews(removeNewsIdList);
		logger.debug("===== into doRemoveNews ======");
		return gr;
	}	
	//刪除多筆新聞資料
	@RequestMapping(value = "/newsAdmin/deleteNews", method = RequestMethod.POST)
	public GeneralResult doDeleteNews(@RequestBody List<Integer> deleteNewsIdList) {
		logger.debug("===== into doDeleteNews ======");
	    GeneralResult gr = newsAdminService.doDeleteNews(deleteNewsIdList);
		logger.debug("===== into doDeleteNews ======");
		return gr;
	}
	//修改多筆新聞資料	 
	@RequestMapping(value = "/newsAdmin/updateNews", method = RequestMethod.PUT)
	public GeneralResult doUpdateNews(@RequestBody List<ParamUpdateNewsDto> updateNewsList) {
		logger.debug("===== into doUpdateNews ======");
	    GeneralResult gr = newsAdminService.doUpdateNews(updateNewsList);
		logger.debug("===== into doUpdateNews ======");
		return gr;
	}
	
	
	@RequestMapping(value = "/news/category", method = RequestMethod.GET)
	public List<CategoryDto> getNewsCategory() {
		logger.debug("===== into getNewsCategory for select ======");
  		List<CategoryDto> returnDtoList = newsAdminService.getNewsNewsCategory();
		logger.debug("===== into getNewsCategory for select ======");
		return returnDtoList;
	}
	
	@RequestMapping(value = "/newsAdmin/releasedNewsByFlexibleSearch/pageable/{size}/{pageNumber}", method = RequestMethod.POST)
	public PageableDto<AdminNewsDto> getNewsByFlexibleSearch(@PathVariable("size") int size,
			@PathVariable("pageNumber") int pageNumber,
			@RequestBody ParamNewsFlexibleDto paramNewsFlexibleDto) {
		logger.debug("===== into getNewsByFlexibleSearch ======");
		PageableDto<AdminNewsDto> pageDto = newsAdminService.getNewsByFlexibleSearch(size,pageNumber,paramNewsFlexibleDto);
		logger.debug("===== End getNewsByFlexibleSearch ======");
		return pageDto;
	}
	
	
}