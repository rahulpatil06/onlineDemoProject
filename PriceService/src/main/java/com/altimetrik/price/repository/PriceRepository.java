package com.altimetrik.price.repository;

import com.altimetrik.price.entity.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, String> {
}
