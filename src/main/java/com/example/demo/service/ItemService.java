package com.example.demo.service;

import com.example.demo.model.ItemDTO;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    ItemDTO createNewItem(ItemDTO itemDTO);

    ItemDTO retrieveItemById(String id);

    List<ItemDTO> retrieveAllItems();

    List<ItemDTO> retrieveAllItemsByCategory(String itemCategory, Boolean inStock);

    ItemDTO updateItem(ItemDTO item);

    void updateItemStock(String itemId, int stockSize);

    void removeItem(String id);
}
