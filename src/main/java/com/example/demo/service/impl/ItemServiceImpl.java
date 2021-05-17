package com.example.demo.service.impl;

import com.example.demo.model.Item;
import com.example.demo.model.ItemDTO;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import com.example.demo.service.mapper.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final String ENTITY_NAME = "ItemService";

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ItemDTO createNewItem(ItemDTO itemDTO) {
        log.debug(String.format("[%s]:Creating new item", ENTITY_NAME));
        return itemMapper.toDTO(itemRepository.save(itemMapper.toEntity(itemDTO)));
    }

    @Override
    public ItemDTO retrieveItemById(int id) {
        log.debug(String.format("[%s]:Retrieving item by id", ENTITY_NAME));
        Optional<Item> storedItem = itemRepository.findById(id);
        if (storedItem.isPresent()) {
            return itemMapper.toDTO(storedItem.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id: " + id);
        }
    }

    @Override
    public List<ItemDTO> retrieveAllItems() {
        log.debug(String.format("[%s]:Retrieving all items", ENTITY_NAME));
        return itemMapper.toDTO(itemRepository.findAll());
    }

    @Override
    public List<ItemDTO> retrieveAllItemsByCategory(String itemCategory, Boolean inStock) {
        log.debug(String.format("[%s]:Retrieving all items by category", ENTITY_NAME));
        List<Item> items;
        if (inStock) {
            items = itemRepository.findAllByCategoryAndStockGreaterThan(itemCategory, 0);
        } else {
            items = itemRepository.findAllByCategory(itemCategory);
        }
        if (items.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find items");
        return itemMapper.toDTO(items);
    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {

        Optional<Item> temp = itemRepository.findById(itemDTO.getId());
        if (temp.isPresent()) {
            Item itemEntity = temp.get();

            if (itemDTO.getName() != null)
                itemEntity.setName(itemDTO.getName());
            if (itemDTO.getCategory() != null)
                itemEntity.setCategory(itemDTO.getCategory());
            if (itemDTO.getPrice() != 0)
                itemEntity.setPrice(itemDTO.getPrice());
            if (itemDTO.getDescription() != null)
                itemEntity.setDescription(itemDTO.getDescription());

            itemRepository.save(itemEntity);
            return itemMapper.toDTO(itemEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an item with id: " + itemDTO.getId());
        }
    }

    @Override
    public void updateItemStock(int id, int stockSize) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            Item temp = item.get();
            temp.setStock(stockSize);
            itemRepository.save(temp);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id: " + id);
        }
    }
}
