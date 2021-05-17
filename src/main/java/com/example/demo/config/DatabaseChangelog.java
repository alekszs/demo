package com.example.demo.config;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class DatabaseChangelog {

    @Value("${data.set}")
    private String PATH;

    private static final Logger log = LoggerFactory.getLogger(DatabaseChangelog.class);

    @Autowired
    ItemRepository itemRepository;

    @EventListener(ApplicationReadyEvent.class)
    private void populateDatabase() {
        log.debug("Populating database from json file");
        try{
            final ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<Item>> typeReference = new TypeReference<List<Item>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream(PATH);
            itemRepository.saveAll(objectMapper.readValue(inputStream, typeReference));
        }catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
