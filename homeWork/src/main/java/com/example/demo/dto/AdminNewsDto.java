package com.example.demo.dto;

public class AdminNewsDto {

	private Integer newsId; 
	
	private String title; 
	  
    private String author; 
      
    private String createDate;	
 
	private String text;

	private String categoryName;
	
	private Boolean isRelease; 


	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Boolean getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Boolean isRelease) {
		this.isRelease = isRelease;
	}

	
}
