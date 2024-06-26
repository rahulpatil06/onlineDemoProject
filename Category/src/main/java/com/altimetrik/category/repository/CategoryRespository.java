package com.altimetrik.category.repository;

import com.altimetrik.category.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRespository extends MongoRepository<Category, String>{

}
