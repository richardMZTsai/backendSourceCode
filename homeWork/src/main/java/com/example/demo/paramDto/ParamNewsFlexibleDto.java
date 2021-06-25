package com.example.demo.paramDto;

import java.util.List;

public class ParamNewsFlexibleDto {
	
	private String createDateFrom;

	private String createDateTo;
	
   	private List<Integer> categoryIdList;


	public String getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(String createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public String getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(String createDateTo) {
		this.createDateTo = createDateTo;
	}

	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

    
}
