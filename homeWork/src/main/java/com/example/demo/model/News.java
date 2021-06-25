package com.example.demo.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Table(name = "NEWS")
public class News {

	@Column(name = "NEWS_ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer newsId;
	
	@ManyToOne(targetEntity=Category.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns(value={ @JoinColumn(name="CATEGORY_ID", referencedColumnName="CATEGORY_ID", nullable=false) }
	)	
	private Category category;
	
	
	@Column(name = "TITLE")
	private String title;

	@Column(name = "AUTHOR")
	private String author;
	
	@Column(name = "TEXT")
	private String text;
	
	@Column(name = "IS_RELEASE")
	private Boolean isRelease = true;
	
	@Column(name = "CREATE_DATE", insertable = true, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "UPDATE_DATE", insertable = true, updatable = true)
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@PrePersist
	void createDate() {
		long time = new Date().getTime();
		this.createDate = this.updateDate = new Timestamp(time);
	}

	@PreUpdate
	void updateDate() {
		long time = new Date().getTime();
		this.updateDate = new Timestamp(time);
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Boolean isRelease) {
		this.isRelease = isRelease;
	}
	
	
}
