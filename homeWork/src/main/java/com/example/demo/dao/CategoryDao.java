package com.example.demo.dao;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Category;

public interface CategoryDao extends JpaRepository<Category,Long>{
	
  List<Category> findByCategoryIdIn(List<Integer> categoryIdList);

}
