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
        return itemRepository.findById(id).map(obj -> itemMapper.toDTO(obj))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id: " + id));
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
        if (items.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find items");
        }
        return itemMapper.toDTO(items);
    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {
        log.debug(String.format("[%s]:Updating item", ENTITY_NAME));
        return itemRepository.findById(itemDTO.getId()).map(obj -> {

            if (itemDTO.getName() != null)
                obj.setName(itemDTO.getName());
            if (itemDTO.getCategory() != null)
                obj.setCategory(itemDTO.getCategory());
            if (itemDTO.getPrice() != 0)
                obj.setPrice(itemDTO.getPrice());
            if (itemDTO.getDescription() != null)
                obj.setDescription(itemDTO.getDescription());

            itemRepository.save(obj);
            return itemMapper.toDTO(obj);

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an item with id: " + itemDTO.getId()));
    }

    @Override
    public void updateItemStock(int id, int stockSize) {
        log.debug(String.format("[%s]:Updating item stock", ENTITY_NAME));
        itemRepository.findById(id).ifPresentOrElse((item) -> {
            item.setStock(stockSize);
            itemRepository.save(item);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id: " + id);
        });
    }

    @Override
    public void removeItem(int id) {
        log.debug(String.format("[%s]:Removing item", ENTITY_NAME));
        itemRepository.findById(id).ifPresentOrElse((item) -> {
            itemRepository.deleteById(id);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with id: " + id);
        });
    }
}
