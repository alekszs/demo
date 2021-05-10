package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByCategory(String itemCategory);

    List<Item> findAllByCategoryAndStockGreaterThan(String itemCategory, int stock);

}
