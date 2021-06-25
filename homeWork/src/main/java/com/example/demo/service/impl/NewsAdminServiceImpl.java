package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.NewsDao;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.GeneralResult;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.PageableDto;
import com.example.demo.dto.AdminNewsDto;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.paramDto.ParamNewsFlexibleDto;
import com.example.demo.paramDto.ParamSaveNewsDto;
import com.example.demo.paramDto.ParamUpdateNewsDto;
import com.example.demo.service.NewsAdminService;
import com.example.demo.service.transaction.NewsTransactionService;

@Service
@Transactional(readOnly = true)
public class NewsAdminServiceImpl implements NewsAdminService {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private NewsTransactionService newsTransactionService;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private NewsDao newsDao;

	@Override
	public GeneralResult doSaveNews(List<ParamSaveNewsDto> releaseNewsList) {

		List<News> insertNewsModelList = new ArrayList<News>();

		List<Integer> categoryIdList = releaseNewsList.stream().map(p -> p.getCategoryId()).distinct()
				.collect(Collectors.toList());

		Map<Integer, Category> categoryModelMap = this.getCategoryMap(categoryIdList);

		for (ParamSaveNewsDto saveDto : releaseNewsList) {

			News newsModel = new News();
			newsModel.setTitle(saveDto.getTitle());
			newsModel.setAuthor(saveDto.getAuthor());
			newsModel.setText(saveDto.getText());
			newsModel.setCategory(categoryModelMap.get(saveDto.getCategoryId()));

			insertNewsModelList.add(newsModel);
		}

		GeneralResult gr = newsTransactionService.upsertNewsList(insertNewsModelList);

		return gr;
	}

	public Map<Integer, Category> getCategoryMap(List<Integer> categoryIdList) {

		Map<Integer, Category> returnMap = new HashMap<Integer, Category>();

		List<Category> categoryModelList = categoryDao.findByCategoryIdIn(categoryIdList);

		for (Category categoryModel : categoryModelList) {
			returnMap.put(categoryModel.getCategoryId(), categoryModel);
		}

		return returnMap;
	}

	@Override
	public GeneralResult doRemoveNews(List<Integer> removeNewsIdList) {

		List<News> removeNewsModelList = newsDao.findBynewsIdIn(removeNewsIdList);

		for (News newsModel : removeNewsModelList) {
			newsModel.setIsRelease(false);
		}
		GeneralResult gr = newsTransactionService.upsertNewsList(removeNewsModelList);

		return gr;
	}

	@Override
	public GeneralResult doDeleteNews(List<Integer> deleteNewsIdList) {

		List<News> deleteNewsModelList = newsDao.findBynewsIdIn(deleteNewsIdList);

		GeneralResult gr = newsTransactionService.deleteNewsList(deleteNewsModelList);

		return gr;
	}

	@Override
	public GeneralResult doUpdateNews(List<ParamUpdateNewsDto> updateNewsDtoList) {

		List<Integer> updateNewsIdList = updateNewsDtoList.stream().map(p -> p.getNewsId())
				.collect(Collectors.toList());
		List<Integer> categoryIdList = updateNewsDtoList.stream().map(p -> p.getCategoryId()).distinct()
				.collect(Collectors.toList());

		Map<Integer, Category> categoryModelMap = this.getCategoryMap(categoryIdList);

		Map<Integer, ParamUpdateNewsDto> updateNewsDtoMap = this.getParamUpdateNewsDtoMap(updateNewsDtoList);

		List<News> updateNewsModelList = newsDao.findBynewsIdIn(updateNewsIdList);

		for (News newsModel : updateNewsModelList) {
			int newsId = newsModel.getNewsId();

			newsModel.setAuthor(updateNewsDtoMap.get(newsId).getAuthor());
			newsModel.setTitle(updateNewsDtoMap.get(newsId).getTitle());
			newsModel.setText(updateNewsDtoMap.get(newsId).getText());
			newsModel.setCategory(categoryModelMap.get(updateNewsDtoMap.get(newsId).getCategoryId()));

		}

		GeneralResult gr = newsTransactionService.upsertNewsList(updateNewsModelList);

		return gr;
	}

	public Map<Integer, ParamUpdateNewsDto> getParamUpdateNewsDtoMap(List<ParamUpdateNewsDto> paramUpdateNewsDtoList) {

		Map<Integer, ParamUpdateNewsDto> returnMap = new HashMap<Integer, ParamUpdateNewsDto>();

		for (ParamUpdateNewsDto paramUpdateNewsDto : paramUpdateNewsDtoList) {
			returnMap.put(paramUpdateNewsDto.getNewsId(), paramUpdateNewsDto);
		}

		return returnMap;
	}

	@Override
	public List<CategoryDto> getNewsNewsCategory() {

		List<Category> categoryModelList = categoryDao.findAll();

		List<CategoryDto> returnDtoList = new ArrayList<CategoryDto>();

		for (Category categoryModel : categoryModelList) {

			CategoryDto dto = new CategoryDto();
			dto.setCategoryId(categoryModel.getCategoryId());
			dto.setCategoryName(categoryModel.getCategoryName());

			returnDtoList.add(dto);
		}

		return returnDtoList;
	}

	@Override
	public PageableDto<AdminNewsDto> getNewsByFlexibleSearch(int size, int pageNumber,
			ParamNewsFlexibleDto paramNewsFlexibleDto) {

		Specification<News> specification = new Specification<News>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<>();

				if (paramNewsFlexibleDto.getCategoryIdList().size() != 0) {
					predicates.add(cb.isTrue(
							root.get("category").get("categoryId").in(paramNewsFlexibleDto.getCategoryIdList())));
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
				LocalDateTime ldtS = LocalDateTime.parse(paramNewsFlexibleDto.getCreateDateFrom(), formatter);
				LocalDateTime ldtE = LocalDateTime.parse(paramNewsFlexibleDto.getCreateDateTo(), formatter);
				Date startDate = Date.from(ldtS.atZone(ZoneId.systemDefault()).toInstant());
				Date endDate = Date.from(ldtE.atZone(ZoneId.systemDefault()).toInstant());
				predicates.add(cb.between(root.get("createDate"), startDate, endDate));

				query.distinct(true);
				return cb.and(predicates.toArray(new Predicate[0]));
			}

		};
		PageRequest pageable = PageRequest.of(pageNumber - 1, size);
		Page<News> pageObj = newsDao.findAll(specification, pageable);

		List<AdminNewsDto> dtoList = new ArrayList<AdminNewsDto>();

		for (News newsModel : pageObj.getContent()) {

			AdminNewsDto dto = new AdminNewsDto();

			dto.setNewsId(newsModel.getNewsId());
			dto.setTitle(newsModel.getTitle());
			dto.setCategoryName(newsModel.getCategory().getCategoryName());
			dto.setAuthor(newsModel.getAuthor());
			dto.setCreateDate(this.convertDateFormat(newsModel.getCreateDate()));
			dto.setText(newsModel.getText());
			dto.setIsRelease(newsModel.getIsRelease());

			dtoList.add(dto);
		}

		PageableDto<AdminNewsDto> pageDto = new PageableDto<AdminNewsDto>(pageObj.getTotalElements(),
				pageObj.getTotalPages(), pageObj.getNumberOfElements(), pageObj.getSize(), pageObj.getNumber(),
				dtoList);

		return pageDto;
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

}
