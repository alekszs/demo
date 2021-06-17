package com.example.demo.config;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "changelog.switch")
public class DatabaseChangelog {

    private final String ENTITY_NAME = "DatabaseChangelog";

    private static final Logger log = LoggerFactory.getLogger(DatabaseChangelog.class);

    @Autowired
    ItemRepository itemRepository;

    @Value("${data.set}")
    private String PATH;

    @EventListener(ApplicationReadyEvent.class)
    private void populateDatabase() {
        log.debug(String.format("[%s]:Populating database from json file", ENTITY_NAME));
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            List<Item> langList = objectMapper.readValue(
                    new ClassPathResource(PATH).getFile(),
                    new TypeReference<List<Item>>() {
                    });

            itemRepository.saveAll(langList);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
