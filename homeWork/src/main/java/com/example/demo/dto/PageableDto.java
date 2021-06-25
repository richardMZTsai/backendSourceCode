package com.example.demo.dto;

import java.util.List;



public class PageableDto<T> {
	
    //總筆數
	private long totalElements;
    //總頁面數
	private int totalPages;
    //每頁筆數
	private int size;
    //該頁筆數
	private int numberOfElements;
    //當前第幾頁
	private int pageNumber;
	private List<T> content;

	public PageableDto(long totalElements, int totalPages, int numberOfElements, int size, int number,
			List<T> content) {
		super();
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.numberOfElements = numberOfElements;
		this.size = size;
		this.pageNumber = number + 1;
		this.content = content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

}
