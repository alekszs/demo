package com.example.demo.controller;

import com.example.demo.model.ItemDTO;
import com.example.demo.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ItemController {

    private final String ENTITY_NAME = "ItemController";
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/createItem", method = RequestMethod.POST)
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        log.debug(String.format("[%s]:Received request to get item by id", ENTITY_NAME));
        return new ResponseEntity<>(itemService.createNewItem(itemDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getItem/{id}", method = RequestMethod.GET)
    public ResponseEntity<ItemDTO> getItem(@PathVariable(value = "id") int id) {
        log.debug(String.format("[%s]:Received request to get item by id", ENTITY_NAME));
        return new ResponseEntity<>(itemService.retrieveItemById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        log.debug(String.format("[%s]:Received request to get all items", ENTITY_NAME));
        return new ResponseEntity<>(itemService.retrieveAllItems(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllItemsByCategory", method = RequestMethod.GET)
    public ResponseEntity<List<ItemDTO>> getAllItemsByCategory(@RequestParam(value = "itemCategory") String itemCategory,
                                                               @RequestParam(value = "inStock") Boolean inStock) {
        log.debug(String.format("[%s]:Received request to get all items by category", ENTITY_NAME));
        return new ResponseEntity<>(itemService.retrieveAllItemsByCategory(itemCategory, inStock), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateItem", method = RequestMethod.PUT)
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDTO) {
        log.debug(String.format("[%s]:Received request to update an item", ENTITY_NAME));
        return new ResponseEntity<>(itemService.updateItem(itemDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateItemStock", method = RequestMethod.PUT)
    public ResponseEntity<String> updateItemStock(@RequestParam int itemId, @RequestParam int stockSize) {
        log.debug(String.format("[%s]:Received request to update an item stock", ENTITY_NAME));
        itemService.updateItemStock(itemId, stockSize);
        return new ResponseEntity<>(itemId + "item stock set to: " + stockSize, HttpStatus.OK);
    }
}
