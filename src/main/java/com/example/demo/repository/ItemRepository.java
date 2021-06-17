package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findAllByCategory(String itemCategory);

    List<Item> findAllByCategoryAndStockGreaterThan(String itemCategory, int stock);

}
