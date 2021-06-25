package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.demo.model.News;

public interface NewsDao extends JpaRepository<News,Long>,JpaSpecificationExecutor<News>{
	
  List<News> findByIsRelease(Boolean isRelease);

  List<News> findBynewsIdIn(List<Integer> newsIdList);

}
