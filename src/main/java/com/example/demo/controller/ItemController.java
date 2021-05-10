package com.example.demo.controller;

import com.example.demo.model.ItemDTO;
import com.example.demo.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final String ENTITY_NAME = "ItemController";
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;


    @RequestMapping("/getItem/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable(value="id") int id) {
        log.debug(String.format("[%s]:Received request to get item by id",ENTITY_NAME));
        return ResponseEntity.ok(itemService.retrieveItemById(id));
    }

    @RequestMapping("/getAllItems")
    public ResponseEntity<List<ItemDTO>> getAllItems(@RequestParam(value = "itemCategory") String itemCategory,
                                  @RequestParam(value = "inStock") Boolean inStock) {
        log.debug(String.format("[%s]:Received request to get all items",ENTITY_NAME));
        return ResponseEntity.ok(itemService.retrieveAllItems(itemCategory, inStock));
    }

    @RequestMapping("/updateItem")
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDTO) {
        log.debug(String.format("[%s]:Received request to update an item", ENTITY_NAME));
        return ResponseEntity.ok(itemService.updateItem(itemDTO));
    }

    @RequestMapping("/updateItemStock")
    public ResponseEntity<String> updateItemStock(@RequestParam int itemId,
                                  @RequestParam int stockSize) {
        log.debug(String.format("[%s]:Received request to update an item stock",ENTITY_NAME));
        itemService.updateItemStock(itemId, stockSize);
        return ResponseEntity.ok().build();
    }
}
