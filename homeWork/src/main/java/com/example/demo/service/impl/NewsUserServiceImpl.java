package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.NewsDao;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.PageableDto;
import com.example.demo.model.News;
import com.example.demo.paramDto.ParamNewsFlexibleDto;
import com.example.demo.service.NewsUserService;



@Service
@Transactional(readOnly = true)
public class NewsUserServiceImpl implements NewsUserService {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private NewsDao newsDao;

	@Override
	public List<NewsDto> getNewsByIsReleased() {
		
		List<News> newsModelList=newsDao.findByIsRelease(true);
		List<NewsDto> returnDtoList=new ArrayList<NewsDto>();
		
		for(News newsModel:newsModelList) {
			
			NewsDto newsDto=new NewsDto();		
			newsDto.setTitle(newsModel.getTitle());
			newsDto.setAuthor(newsModel.getAuthor());
			newsDto.setCreateDate(this.convertDateFormat(newsModel.getCreateDate()));
			newsDto.setText(newsModel.getText());
			newsDto.setCategoryName(newsModel.getCategory().getCategoryName());
					
			returnDtoList.add(newsDto);
		}
		
		return returnDtoList;
	}
	public String convertDateFormat(Date date) {
		String stringDate = null;
		if (date != null) {
			DateTimeFormatter timeFormatters = DateTimeFormatter.ofPattern(DATE_FORMAT);
			LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			stringDate = localDateTime.format(timeFormatters);
		}

		return stringDate;
	}
	@Override
	public PageableDto<NewsDto> getNewsByFlexibleSearch(int size, int pageNumber,
			ParamNewsFlexibleDto paramNewsFlexibleDto) {
		
		Specification<News> specification = new Specification<News>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("isRelease"), true));
				
				if (paramNewsFlexibleDto.getCategoryIdList().size()!=0) {
				predicates.add(cb.isTrue(root.get("category").get("categoryId").in(paramNewsFlexibleDto.getCategoryIdList())));
			    }	
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
				LocalDateTime ldtS = LocalDateTime.parse(paramNewsFlexibleDto.getCreateDateFrom(),
						formatter);
				LocalDateTime ldtE = LocalDateTime.parse(paramNewsFlexibleDto.getCreateDateTo(),
						formatter);
				Date startDate = Date.from(ldtS.atZone(ZoneId.systemDefault()).toInstant());
				Date endDate = Date.from(ldtE.atZone(ZoneId.systemDefault()).toInstant());
				predicates.add(cb.between(root.get("createDate"), startDate, endDate));
				
				query.distinct(true);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		
		};
		PageRequest pageable = PageRequest.of(pageNumber - 1, size);
		Page<News> pageObj = newsDao.findAll(specification, pageable);
		List<NewsDto> dtoList = new ArrayList<NewsDto>();	
		
		for(News newsModel : pageObj.getContent()) {
			
			NewsDto dto=new NewsDto();
			dto.setTitle(newsModel.getTitle());
			dto.setCategoryName(newsModel.getCategory().getCategoryName());
			dto.setAuthor(newsModel.getAuthor());
			dto.setCreateDate(this.convertDateFormat(newsModel.getCreateDate()));
			dto.setText(newsModel.getText());
			
			dtoList.add(dto);
		}
				
		PageableDto<NewsDto> pageDto = new PageableDto<NewsDto>(pageObj.getTotalElements(), pageObj.getTotalPages(),
		pageObj.getNumberOfElements(), pageObj.getSize(), pageObj.getNumber(), dtoList);
		

		return pageDto;
	}

}
